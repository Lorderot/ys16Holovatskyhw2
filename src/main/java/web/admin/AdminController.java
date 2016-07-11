package web.admin;

import Domain.Customer;
import Domain.Pizza;
import Service.CustomerService;
import Service.OrderService;
import Service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String showMainPage(ModelMap modelMap) {
        modelMap.addAttribute("customer", getLoggedInCustomer());
        return "admin/welcome";
    }

    @RequestMapping(value = "/list-pizzas", method = RequestMethod.GET)
    public String showPizzasList(ModelMap modelMap) {
        modelMap.addAttribute("pizzas", pizzaService.getPizzasSortedByPrice());
        return "admin/list-pizzas";
    }

    @RequestMapping(value = "/update-pizza", method = RequestMethod.GET)
    public String showPizzaForm(ModelMap modelMap,
                                      @RequestParam Integer pizzaId) {
        modelMap.addAttribute("pizza", pizzaService.getPizzaById(pizzaId));
        return "admin/addPizzaForm";
    }

    @RequestMapping(value = "/update-pizza", method = RequestMethod.POST)
    public String showUpdatePizzaPage(@ModelAttribute Pizza pizza) {
        pizzaService.updatePizza(pizza);
        return "redirect:/admin/list-pizzas";
    }

    @RequestMapping(value = "/delete-pizza")
    public String deletePizza(@RequestParam Integer pizzaId) {
        pizzaService.deletePizza(pizzaId);
        return "redirect:/admin/list-pizzas";
    }

    @RequestMapping(value = "/add-pizza", method = RequestMethod.GET)
    public String showPizzaForm(ModelMap modelMap) {
        modelMap.addAttribute(pizzaService.getNewPizza());
        return "admin/addPizzaForm";
    }

    @RequestMapping(value = "/add-pizza", method = RequestMethod.POST)
    public String addPizza(@ModelAttribute Pizza pizza) {
        pizzaService.addPizza(pizza);
        return "redirect:/admin/list-pizzas";
    }

    public void setPizzaService(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    private Customer getLoggedInCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        return customerService.findCustomerByLogin(user.getUsername());
    }
}
