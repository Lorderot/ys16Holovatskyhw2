package Service;

import Domain.Pizza;
import Exceptions.NoSuchPizzaException;

import java.util.List;

public interface PizzaService {
    List<Pizza> getAllPizzas();

    List<Pizza> getPizzasByType(Pizza.PizzaType type);

    Pizza getPizzaById(Integer id) throws NoSuchPizzaException;

    List<Pizza> getPizzasSortedByPrice();

    Pizza updatePizzaPriceById(Integer pizzaId, Double price)
            throws NoSuchPizzaException;

    boolean addPizza(Pizza pizza);
}
