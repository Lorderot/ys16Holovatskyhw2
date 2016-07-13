package web.user;

import Domain.Customer;
import Domain.Order;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserPizzaController {
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    private List<Pizza> pizzaMenu;

    @PostConstruct
    public void init() {
        pizzaMenu = pizzaService.getPizzasSortedByPrice();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showMainPage(ModelMap modelMap) {
        modelMap.addAttribute("customer", getLoggedInCustomer());
        return "user/Profile";
    }

    @RequestMapping(value = "/create-order")
    public String createOrder() {
        Order order = orderService
                .getUnfinishedOrder(getLoggedInCustomer().getId());
        if (order == null) {
            return "redirect:/user/list-pizzas";
        }
        orderService.finishOrder(order);
        return "redirect:/user/list-orders";
    }

    @RequestMapping(value = "/list-pizzas", method = RequestMethod.GET)
    public String showPizzasMenu(ModelMap modelMap) {
        Order order = orderService.getUnfinishedOrder(
                getLoggedInCustomer().getId());
        if (order == null) {
            order = orderService.getNewOrder();
            order.setCustomer(getLoggedInCustomer());
            orderService.addUnfinishedOrder(order);
        }
        modelMap.addAttribute("totalPrice", order.getTotalPrice());
        modelMap.addAttribute("pizzas", pizzaMenu);
        modelMap.addAttribute("orderList", order.getOrderList());
        return "user/pizzaMenu";
    }

    @RequestMapping(value = "/list-pizzas", method = RequestMethod.POST)
    public String addPizzaToOrder(@RequestParam Integer pizzaId) {
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        Order order = orderService.getUnfinishedOrder(
                getLoggedInCustomer().getId());
        order.addPizza(pizza);
        orderService.updateOrder(order);
        return "redirect:/user/list-pizzas";
    }

    @RequestMapping(value = "/delete-pizza", method = RequestMethod.POST)
    public String deletePizzaFromOrder(@RequestParam Integer pizzaId) {
        Order order = orderService.getUnfinishedOrder(
                getLoggedInCustomer().getId());
        order.removePizza(pizzaId);
        orderService.updateOrder(order);
        return "redirect:/user/list-pizzas";
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setPizzaService(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    private Customer getLoggedInCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        return customerService.findCustomerByLogin(user.getUsername());
    }
}