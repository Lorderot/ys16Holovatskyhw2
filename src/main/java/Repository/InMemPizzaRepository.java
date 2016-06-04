package Repository;

import Domain.Pizza;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("pizzaRepository")
public class InMemPizzaRepository implements PizzaRepository {
    private List<Pizza> pizzas = new ArrayList<>();

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    @Override
    public boolean create(Pizza pizza) {
        if (pizza == null) {
            return false;
        }
        if (getPizzaById(pizza.getId()) != null) {
            return false;
        }
        pizzas.add(pizza);
        return true;
    }

    @Override
    public boolean update(Pizza pizza) {
        if (pizza == null) {
            return false;
        }
        Pizza oldPizza = getPizzaById(pizza.getId());
        if (oldPizza == null) {
            return false;
        }
        oldPizza.setName(pizza.getName());
        oldPizza.setPrice(pizza.getPrice());
        oldPizza.setType(pizza.getType());
        return true;
    }

    @Override
    public boolean delete(Integer pizzaId) {
        Pizza oldPizza = getPizzaById(pizzaId);
        return oldPizza != null && pizzas.remove(oldPizza);
    }

    @Override
    public Pizza getPizzaById(Integer id) {
        Optional<Pizza> optionalPizza = pizzas.stream().filter(pizza ->
                pizza.getId().equals(id)).findFirst();
        if (!optionalPizza.isPresent()) {
            return null;
        }
        return optionalPizza.get();
    }
}
