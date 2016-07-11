package Service;

import Domain.Pizza;

import java.util.List;

public interface PizzaService {
    Pizza getNewPizza();

    List<Pizza> getAllPizzas();

    List<Pizza> getPizzasByType(Pizza.PizzaType type);

    Pizza getPizzaById(Integer id);

    List<Pizza> getPizzasById(List<Integer> pizzasId);

    List<Pizza> getPizzasSortedByPrice();

    Pizza updatePizzaPriceById(Integer pizzaId, Double price);

    boolean updatePizza(Pizza pizza);

    boolean addPizza(Pizza pizza);

    boolean deletePizza(Integer pizzaId);
}
