package Repository;

import Domain.Pizza;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("pizzaRepository")
public class InMemPizzaRepository implements PizzaRepository {
    private List<Pizza> pizzas = new ArrayList<>();

    public void init() {
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public void create(Pizza pizza) {
        pizzas.add(pizza);
    }

    @Override
    public boolean update(Pizza pizza) {
        Pizza old = getPizzaById(pizza.getId());
        if (old == null) {
            return false;
        }
        old.setName(pizza.getName());
        old.setPrice(pizza.getPrice());
        old.setType(pizza.getType());
        return true;
    }

    @Override
    public boolean delete(Pizza pizza) {
        Pizza old = getPizzaById(pizza.getId());
        if (old == null) {
            return false;
        }
        return pizzas.remove(pizza);
    }

    private Pizza getPizzaById(Integer id) {
        return pizzas.stream().filter(pizza ->
                pizza.getId().equals(id)).findFirst().get();
    }
}
