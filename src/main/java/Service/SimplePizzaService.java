package Service;

import Domain.Pizza;
import Exceptions.NoSuchPizzaException;
import Infrastructure.Benchmark;
import Repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("pizzaService")
public class SimplePizzaService implements PizzaService {
    private PizzaRepository pizzaRepository;

    public SimplePizzaService() {
    }

    @Autowired
    public SimplePizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @Override
    @Benchmark(active = true)
    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = pizzaRepository.getPizzas();
        return makeClone(pizzas);
    }

    @Override
    public List<Pizza> getPizzasByType(Pizza.PizzaType type) {
        List<Pizza> pizzas = pizzaRepository.getPizzas().stream().filter(pizza ->
                pizza.getType().equals(type)).collect(Collectors.toList());
        return makeClone(pizzas);
    }

    @Override
    public Pizza getPizzaById(Integer id) throws NoSuchPizzaException {
        Pizza pizza = pizzaRepository.getPizzaById(id);
        if (pizza == null) {
            throw new NoSuchPizzaException(id);
        }
        return pizza;
    }

    @Override
    public List<Pizza> getPizzasSortedByPrice() {
        List<Pizza> pizzas = pizzaRepository.getPizzas().stream()
                .sorted((firstPizza, secondPizza) -> {
                    if (firstPizza == null || secondPizza == null) {
                        return -1;
                    }
                    if (firstPizza.getPrice() < secondPizza.getPrice()) {
                        return -1;
                    }
                    if (firstPizza.getPrice().equals(secondPizza.getPrice())) {
                        return 0;
                    }
                    return 1;
        }).collect(Collectors.toList());
        return makeClone(pizzas);
    }

    @Override
    public Pizza updatePizzaPriceById(Integer pizzaId, Double price)
            throws NoSuchPizzaException {
        Pizza pizza = pizzaRepository.getPizzaById(pizzaId);
        if (pizza == null) {
            throw new NoSuchPizzaException(pizzaId);
        }
        pizza.setPrice(price);
        pizzaRepository.update(pizza);
        return pizza;
    }

    @Override
    public boolean addPizza(Pizza pizza) {
        return pizzaRepository.create(pizza);
    }

    private List<Pizza> makeClone(List<Pizza> pizzas) {
        List<Pizza> clone = new ArrayList<>(pizzas.size());
        pizzas.forEach((pizza -> clone.add(new Pizza(pizza))));
        return clone;
    }
}
