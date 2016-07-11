package Repository;

import Domain.Customer;
import Domain.Order;
import Domain.Pizza;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository("orderRepository")
public class OrderRepositoryImpl extends JdbcDaoSupport
        implements OrderRepository, ApplicationContextAware {
    private ApplicationContext context;
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private CustomerRepository customerRepository;
    private OrderMapper orderMapper = new OrderMapper();

    public boolean create(Order order) {
        if (order == null) {
            return false;
        }
        String sql = "INSERT INTO orders(creation_date, customer_id) VALUES(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            getJdbcTemplate().update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement statement = connection.prepareStatement(
                            sql, new String[]{"order_id"});
                    statement.setDate(1, new Date(order.getCreationDate().getTime()));
                    statement.setInt(2, order.getCustomer().getId());
                    return statement;
                }
            }, keyHolder);
            order.setId(keyHolder.getKey().intValue());
            addOrderList(order.getId(), getPizzasIdFromOrder(order));
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Order order) {
        if (order == null) {
            return false;
        }
        String sql = "UPDATE orders SET customer_id=:customerId, creation_date=:"
                + "creationDate, update_date=:update, cancelled=:cancelled, "
                + "finish_date=:finish WHERE order_id = :id;";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("customerId", order.getCustomer().getId())
                .addValue("creationDate", order.getCreationDate())
                .addValue("cancelled", order.isCancelled())
                .addValue("update", order.getUpdateDate())
                .addValue("finish", order.getFinishDate())
                .addValue("id", order.getId());
        Integer updated = new NamedParameterJdbcTemplate(getJdbcTemplate())
                .update(sql, sqlParameterSource);
        updateOrderList(order.getId(), getPizzasIdFromOrder(order));
        return !updated.equals(0);
    }

    @Override
    public boolean delete(Integer orderId) {
        String sql = "UPDATE orders SET cancelled=true WHERE order_id=?";
        Integer deleted = getJdbcTemplate().update(sql, orderId);
        return !deleted.equals(0);
    }

    @Override
    public List<Order> getOrders() {
        String sql = "SELECT * FROM orders WHERE cancelled!=TRUE;";
        return getJdbcTemplate().query(sql, orderMapper);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "SELECT * FROM orders WHERE order_id=? AND cancelled!=TRUE;";
        try {
            return getJdbcTemplate().queryForObject(
                    sql, new Object[]{orderId}, orderMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getUndoneOrders() {
        String sql = "SELECT * FROM orders WHERE cancelled!=TRUE"
                + " AND finish_date IS NULL;";
        return getJdbcTemplate().query(sql, orderMapper);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public void setPizzaRepository(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private void updateOrderList(Integer orderId, Integer[] pizzasId) {
        deleteOrderList(orderId);
        addOrderList(orderId, pizzasId);
    }

    private Integer deleteOrderList(Integer orderId) {
        String sql = "DELETE FROM con_order_pizza WHERE order_id = ?;";
        return getJdbcTemplate().update(sql, orderId);
    }

    private void addOrderList(Integer orderId, Integer[] pizzasId) {
        SimpleJdbcCall simpleCall = new SimpleJdbcCall(getJdbcTemplate());
        simpleCall.useInParameterNames("orderId", "orderList")
                .withFunctionName("updateOrderList")
                .withoutProcedureColumnMetaDataAccess().declareParameters(
                new SqlParameter("orderId", Types.INTEGER),
                new SqlParameter("orderList", Types.ARRAY));
        Array orderList;
        try {
            orderList = getConnection().createArrayOf("INTEGER", pizzasId);
            SqlParameterSource sqlParameter = new MapSqlParameterSource()
                    .addValue("orderList", orderList)
                    .addValue("orderId", orderId);
            simpleCall.execute(sqlParameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Pizza> getOrderListById(Integer orderId) {
        String sql = "SELECT pizza_id FROM con_order_pizza WHERE order_id=?;";
        List<Integer> pizzasId = getJdbcTemplate()
                .queryForList(sql, new Object[]{orderId}, Integer.class);
        return pizzaRepository
                .getPizzasById(pizzasId);
    }

    private Integer[] getPizzasIdFromOrder(Order order) {
        List<Pizza> orderList = order.getOrderList();
        Integer[] pizzasId = new Integer[orderList.size()];
        int counter = 0;
        for (Pizza pizza : orderList) {
            pizzasId[counter++] = pizza.getId();
        }
        return pizzasId;
    }


    private final class OrderMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            Order order = (Order) context.getBean("order");
            order.setId(resultSet.getInt("order_id"));
            Customer customer = customerRepository
                    .getCustomerById(resultSet.getInt("customer_id"));
            order.setCustomer(customer);
            order.setCreationDate(resultSet.getDate("creation_date"));
            order.setUpdateDate(resultSet.getDate("update_date"));
            order.setOrderList(getOrderListById(order.getId()));
            return order;
        }
    }
}
