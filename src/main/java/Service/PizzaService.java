package Service;

import Domain.Pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> getAllPizzas();

    List<Pizza> getPizzasByType(Pizza.PizzaType type);

    Pizza getPizzaById(Integer id);

    List<Pizza> getPizzasById(Integer ...pizzasId);

    List<Pizza> getPizzasSortedByPrice();

    Pizza updatePizzaPriceById(Integer pizzaId, Double price);

    boolean addPizza(Pizza pizza);
}
