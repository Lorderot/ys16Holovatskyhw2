package Repository;

import Domain.Pizza;

import java.util.List;

public interface PizzaRepository {
    void create(Pizza pizza);

    boolean update(Pizza pizza);

    boolean delete(Pizza pizza);

    List<Pizza> getPizzas();
}
