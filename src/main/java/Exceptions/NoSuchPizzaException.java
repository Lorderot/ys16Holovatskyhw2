package Exceptions;

import java.util.*;

public class NoSuchPizzaException extends Exception {
    private List<Integer> pizzasId = new ArrayList<>();

    public NoSuchPizzaException(Integer ...pizzaId) {
        super("No such pizzas were found: " + Arrays.toString(pizzaId));
        Collections.addAll(pizzasId, pizzaId);
    }

    public NoSuchPizzaException(List<Integer> pizzaId) {
        super("No such pizzas were found: " + pizzaId);
        pizzasId = pizzaId;
    }

    public List<Integer> getPizzasId() {
        return pizzasId;
    }
}
