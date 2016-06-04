package app;

import Domain.Customer;
import Domain.Order;
import Service.OrderService;
import Service.PizzaService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class SpringPizzaApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext repositoryContext =
                new ClassPathXmlApplicationContext("repositoryContext.xml");
        ConfigurableApplicationContext appContext =
                new ClassPathXmlApplicationContext(
                        new String[] {"appContext.xml"}, repositoryContext);
        PizzaService pizzaService = appContext
                .getBean("pizzaService", PizzaService.class);
        OrderService orderService = appContext
                .getBean("orderService", OrderService.class);
        Order order = null;
        try {
            order = orderService.placeNewOrder(
                    new Customer(0, "Kolia", "Kyiv, Kovalskogo 5"), 0, 1);
            System.out.println("GOT PIZZA BY ID = 0: " + pizzaService.getPizzaById(0));
            order = orderService.addPizzaToOrder(order.getId(), 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Price: " + order.getTotalPrice());
        List<Order> orders = orderService.getAllOrders();
        orders.stream().forEach(System.out::println);
    }
}
