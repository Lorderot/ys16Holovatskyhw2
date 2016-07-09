package web;

import Domain.Pizza;
import Service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @RequestMapping(value = "pizza", method = RequestMethod.GET)
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello from PizzaController";
    }

    @RequestMapping(value = "pizza/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pizza> pizzaById(@PathVariable("id")int id) {
        Pizza pizza = pizzaService.getPizzaById(id);
        if (pizza == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pizza,
                HttpStatus.OK);
    }

    @RequestMapping(value = "pizza/{id}/price", method = RequestMethod.GET)
    public ResponseEntity<Double> getPizzaPrice(@PathVariable("id")int id) {
        Pizza pizza = pizzaService.getPizzaById(id);
        if (pizza == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pizza.getPrice(), HttpStatus.OK);
    }

    @RequestMapping(value = "pizza/{id}/price/{price}", method = RequestMethod.PUT)
    public ResponseEntity updatePizzaPrice(@PathVariable("id") int id,
                                       @PathVariable("price") double price) {
        Pizza pizza = pizzaService.updatePizzaPriceById(id, price);
        if (pizza == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "pizza",
            method = RequestMethod.POST,
            headers = "Content-Type=application/json")
    public ResponseEntity<Integer> addPizza(@RequestBody Pizza pizza) {
        System.out.println("Created " + pizza);
        if (!pizzaService.addPizza(pizza)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(pizza.getId(), HttpStatus.CREATED);
    }
}
