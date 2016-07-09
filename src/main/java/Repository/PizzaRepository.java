package Repository;

import Domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    boolean create(Pizza pizza);

    boolean update(Pizza pizza);

    boolean delete(Integer pizzaId);

    List<Pizza> getPizzas();

    Pizza getPizzaById(Integer id);

    List<Pizza> getPizzasById(Integer ...pizzasId);
}
