package Repository;

import Domain.Pizza;
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
import java.util.*;

@Repository("pizzaRepository")
public class PizzaRepositoryImpl extends JdbcDaoSupport
        implements PizzaRepository, ApplicationContextAware {
    private ApplicationContext context;
    private PizzaMapper pizzaMapper = new PizzaMapper();

    @Override
    public boolean create(Pizza pizza) {
        if (pizza == null) {
            return false;
        }
        String sql = "INSERT into pizzas(name, price, size, type, available, "
                + "description) values(:name, :price, :size, :type, "
                + ":available, :description);";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", pizza.getName())
                .addValue("price", pizza.getPrice())
                .addValue("size", pizza.getSize().toString())
                .addValue("type", pizza.getType().toString())
                .addValue("available", pizza.isAvailable())
                .addValue("description", pizza.getDescription());
        try {
            new NamedParameterJdbcTemplate(getJdbcTemplate())
                    .update(sql, sqlParameterSource);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Pizza pizza) {
        if (pizza == null) {
            return false;
        }
        String sql = "UPDATE pizzas SET name=:name, price=:price, size=:size, "
                + "type=:type, available=:available, description="
                + ":description WHERE pizza_id=:id;";
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", pizza.getName())
                .addValue("price", pizza.getPrice())
                .addValue("size", pizza.getSize().toString())
                .addValue("type", pizza.getType().toString())
                .addValue("available", pizza.isAvailable())
                .addValue("description", pizza.getDescription())
                .addValue("id", pizza.getId());
        Integer updated = new NamedParameterJdbcTemplate(getJdbcTemplate())
                .update(sql, sqlParameterSource);
        return !updated.equals(0);
    }

    @Override
    public boolean delete(Integer pizzaId) {
        String sql = "UPDATE pizzas set available=false WHERE pizza_id=?";
        Integer deleted = getJdbcTemplate().update(sql, pizzaId);
        return !deleted.equals(0);
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        String sql = "SELECT * FROM pizzas WHERE pizza_id=?;";
        try {
            return getJdbcTemplate().queryForObject(
                    sql, new Object[]{id}, pizzaMapper);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pizza> getPizzas() {
        String sql = "SELECT * FROM pizzas;";
        return getJdbcTemplate().query(sql, pizzaMapper);
    }

    @Override
    public List<Pizza> getPizzasById(List<Integer> pizzasId) {
        if (pizzasId.size() == 0) {
            return new ArrayList<>();
        }
        String sql = "SELECT * FROM pizzas WHERE pizza_id IN (:ids);";
        Set<Integer> setOfId = new HashSet<>();
        setOfId.addAll(pizzasId);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("ids", setOfId);
        return new NamedParameterJdbcTemplate(getJdbcTemplate())
                .query(sql, sqlParameterSource, pizzaMapper);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    private final class PizzaMapper implements RowMapper<Pizza> {
        @Override
        public Pizza mapRow(ResultSet resultSet, int i) throws SQLException {
            Pizza pizza = (Pizza)context.getBean("pizza");
            pizza.setId(resultSet.getInt("pizza_id"));
            pizza.setName(resultSet.getString("name"));
            pizza.setPrice(resultSet.getDouble("price"));
            pizza.setSize(Pizza.PizzaSize.valueOf(resultSet.getString("size")));
            pizza.setType(Pizza.PizzaType.valueOf(resultSet.getString("type")));
            pizza.setAvailable(resultSet.getBoolean("available"));
            pizza.setDescription(resultSet.getString("description"));
            return pizza;
        }
    }
}
