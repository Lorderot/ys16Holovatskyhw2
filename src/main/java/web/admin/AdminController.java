package web.admin;

import Domain.Customer;
import Service.CustomerService;
import Service.OrderService;
import Service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
    public String showMainPage() {
        return "admin/welcome";
    }

    @RequestMapping(value = "/list-pizzas", method = RequestMethod.GET)
    public String showPizzasList(ModelMap modelMap) {
        modelMap.addAttribute("pizzas", pizzaService.getAllPizzas());
        return "admin/list-pizzas";
    }

    @RequestMapping(value = "/update-pizza")
    public String showUpdatePizzaPage(ModelMap modelMap,
                                      @RequestParam Integer pizzaId) {
        modelMap.addAttribute("pizza", pizzaService.getPizzaById(pizzaId));
        return "";
    }

    @RequestMapping(value = "/delete-pizza")
    public String deletePizza(@RequestParam Integer pizzaId) {
        pizzaService.deletePizza(pizzaId);
        return "redirect:/list-pizzas";
    }

    @RequestMapping(value = "/add-pizza")
    public String showAddPizzaPage() {
        return "";
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
