package Service;

import Domain.Customer;
import Domain.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order placeNewOrder(Customer customer, Integer... pizzasID);

    Order addPizzaToOrder(Order order, Integer pizzaId);

    Order removePizzaFromOrder(Order order, Integer pizzaId);
}
