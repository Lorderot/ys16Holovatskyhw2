package web;

import Domain.Pizza;
import Service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @RequestMapping(value = "/pizza", method = RequestMethod.GET)
    public List<Pizza> getAllPizzas() {
        return pizzaService.getAllPizzas();
    }


}
