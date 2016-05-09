package Service;

import Domain.Pizza;

import java.util.List;

public interface PizzaService {
    List<Pizza> getAllPizzas();

    List<Pizza> getPizzasByType(Pizza.PizzaType type);

    Pizza getPizzaById(Integer id);

    List<Pizza> getPizzasSortedByPrice();
}
