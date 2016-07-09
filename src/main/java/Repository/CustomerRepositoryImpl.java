package Repository;

import Domain.Customer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("customerRepository")
public class CustomerRepositoryImpl extends JdbcDaoSupport
        implements CustomerRepository, ApplicationContextAware {
    private ApplicationContext context;
    private CustomerMapper customerMapper = new CustomerMapper();

    @Override
    public Customer getCustomerById(Integer customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id=?;";
        try {
            return getJdbcTemplate().queryForObject(
                    sql, new Object[]{customerId}, customerMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> getCustomers() {
        String sql = "SELECT * FROM customers;";
        return getJdbcTemplate().query(sql, customerMapper);
    }

    @Override
    public boolean delete(Integer customerId) {
        String sql = "DELETE FROM customers where customer_id=?";
        Integer deleted = getJdbcTemplate().update(sql, customerId);
        return deleted.equals(0);
    }

    @Override
    public boolean update(Customer customer) {
        String sql = "UPDATE customers SET firstName=:firstName,"
                + " lastName=:lastName, phoneNumber=:phone, email=:email, "
                + "login=:login, password=:password, role=:role, "
                + "address=:address WHERE customer_id=:id";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", customer.getFirstName())
                .addValue("lastName", customer.getLastName())
                .addValue("phone", customer.getPhoneNumber())
                .addValue("email", customer.getEmail())
                .addValue("login", customer.getLogin())
                .addValue("password", customer.getPassword())
                .addValue("role", customer.getRole().toString())
                .addValue("address", customer.getAddress())
                .addValue("id", customer.getId());
        Integer updated = new NamedParameterJdbcTemplate(getJdbcTemplate())
                .update(sql, sqlParameterSource);
        return updated.equals(0);
    }

    @Override
    public boolean create(Customer customer) {
        if (customer == null) {
            return false;
        }
        String sql = "INSERT INTO customers(firstName, lastName, phoneNumber,"
                + " email, login, password, role, address) values("
                + ":firstName, :lastName, :phone, :email, :login, "
                + ":password, :role, :address);";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("firstName", customer.getFirstName())
                .addValue("lastName", customer.getLastName())
                .addValue("phoneNumber", customer.getPhoneNumber())
                .addValue("email", customer.getEmail())
                .addValue("login", customer.getLogin())
                .addValue("password", customer.getPassword())
                .addValue("role", customer.getRole().toString())
                .addValue("address", customer.getAddress());
        try {
            new NamedParameterJdbcTemplate(getJdbcTemplate())
                    .update(sql, sqlParameterSource);
            return true;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private final class CustomerMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
            Customer customer = (Customer) context.getBean("customer");
            customer.setFirstName(resultSet.getString("firstName"));
            customer.setLastName(resultSet.getString("lastName"));
            customer.setAddress(resultSet.getString("address"));
            customer.setEmail(resultSet.getString("email"));
            customer.setLogin(resultSet.getString("login"));
            customer.setPassword(resultSet.getString("password"));
            customer.setRole(Customer.Role.valueOf(resultSet.getString("role")));
            customer.setPhoneNumber(resultSet.getString("phoneNumber"));
            customer.setId(resultSet.getInt("customer_id"));
            return customer;
        }
    }
}
