package web.user;

import Domain.Customer;
import Domain.Order;
import Service.CustomerService;
import Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserOrderController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list-orders", method = RequestMethod.GET)
    public String getListOfOrders(ModelMap modelMap) {
        Customer customer = getLoggedInCustomer();
        List<Order> listOfOrders = orderService.sortOrderListByDate(
                orderService.getOrdersByCustomerId(customer.getId()));
        modelMap.addAttribute("orders", listOfOrders);
        return "user/list-orders";
    }

    @RequestMapping(value = "/orderList")
    public String showOrderList(@RequestParam Integer orderId, ModelMap modelMap) {
        Order order = orderService.getOrderById(orderId);
        modelMap.addAttribute("pizzas", order.getOrderList());
        return "user/showOrderList";
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
