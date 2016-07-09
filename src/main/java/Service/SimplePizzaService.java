package Service;

import Domain.Pizza;
import Repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.getPizzas();
    }

    @Override
    public List<Pizza> getPizzasByType(Pizza.PizzaType type) {
        return pizzaRepository.getPizzas().stream().filter(pizza ->
                pizza.getType().equals(type)).collect(Collectors.toList());
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        return pizzaRepository.getPizzaById(id);
    }

    @Override
    public List<Pizza> getPizzasSortedByPrice() {
        return pizzaRepository.getPizzas().stream()
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
    }

    @Override
    public Pizza updatePizzaPriceById(Integer pizzaId, Double price) {
        Pizza pizza = pizzaRepository.getPizzaById(pizzaId);
        if (pizza == null) {
            return null;
        }
        pizza.setPrice(price);
        pizzaRepository.update(pizza);
        return pizza;
    }

    @Override
    public boolean addPizza(Pizza pizza) {
        return pizzaRepository.create(pizza);
    }

    @Override
    public List<Pizza> getPizzasById(Integer ...pizzasId) {
        return pizzaRepository.getPizzasById(pizzasId);
    }
}
