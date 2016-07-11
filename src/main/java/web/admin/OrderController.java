package web.admin;

import Domain.Order;
import Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/list-orders", method = RequestMethod.GET)
    public String showListOfOrders(ModelMap modelMap) {
        modelMap.addAttribute("orders", orderService.getUndoneOrdersSortedByDate());
        return "admin/list-orders";
    }

    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    public String showOrderList(
            ModelMap modelMap, @RequestParam Integer orderId) {
        Order order = orderService.getOrderById(orderId);
        modelMap.addAttribute("pizzas", order.getOrderList());
        return "admin/showOrderList";
    }

    @RequestMapping(value = "/completeOrder", method = RequestMethod.GET)
    public String completeOrder(@RequestParam Integer orderId) {
        orderService.completeOrder(orderId);
        return "redirect:/admin/list-orders";
    }


    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
