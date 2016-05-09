import Domain.Customer;
import Domain.Order;
import Infrastructure.*;
import Repository.OrderRepository;
import Service.PizzaService;
import Service.SimpleOrderService;

class PizzaApp {
    public static ServiceLocator locator;
    public static void main(String[] args) throws Exception{
        Config config = new JavaConfig();
        ApplicationContext context = new JavaConfigApplicationContext(config);
        Customer customer = new Customer(1, "Misha", "Kyiv, Kovalenka 5");
        Order order;

        PizzaService pizzaService = (PizzaService) context.getBean("pizzaService");
        OrderRepository orderRepository = (OrderRepository) context.getBean("orderRepository");
        SimpleOrderService orderService = new SimpleOrderService(pizzaService, orderRepository);
        order = orderService.placeNewOrder(customer, 1, 2, 3);
        System.out.println(order);
    }
}