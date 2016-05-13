package Service;

import Domain.Pizza;
import Infrastructure.Benchmark;
import Repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        List<Pizza> clonePizzas = makeClone(pizzas);
        return clonePizzas;
    }

    @Override
    public List<Pizza> getPizzasByType(Pizza.PizzaType type) {
        List<Pizza> pizzas = pizzaRepository.getPizzas().stream().filter(pizza ->
                pizza.getType().equals(type)).collect(Collectors.toList());
        List<Pizza> clonePizzas = makeClone(pizzas);
        return clonePizzas;
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        Pizza pizza = pizzaRepository.getPizzas().stream().filter(pizza1 ->
                pizza1.getId().equals(id)).findFirst().get();
        Pizza clonePizza = new Pizza(pizza);
        return clonePizza;
    }

    @Override
    public List<Pizza> getPizzasSortedByPrice() {
        List<Pizza> pizzas = pizzaRepository.getPizzas().stream()
                .sorted(new Comparator<Pizza>() {
            @Override
            public int compare(Pizza o1, Pizza o2) {
                if (o1 == null || o2 == null) {
                    return -1;
                }
                if (o1.getPrice() < o2.getPrice()) {
                    return -1;
                }
                if (o1.getPrice() == o2.getPrice()) {
                    return 0;
                }
                return 1;
            }
        }).collect(Collectors.toList());
        List<Pizza> clonePizzas = makeClone(pizzas);
        return clonePizzas;
    }

    private List<Pizza> makeClone(List<Pizza> pizzas) {
        List<Pizza> clone = new ArrayList<>(pizzas.size());
        for (Pizza pizza : pizzas) {
            clone.add(new Pizza(pizza));
        }
        return clone;
    }
}
