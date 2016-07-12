package Service;

import Domain.Customer;
import Domain.Order;

import java.util.List;

public interface OrderService {
    Order getNewOrder();

    List<Order> getAllOrders();

    List<Order> getUndoneOrdersSortedByDate();

    List<Order> getOrdersByCustomerId(Integer customerId);

    Order placeNewOrder(Customer customer, Integer... pizzasID);

    Order addPizzaToOrder(Integer orderId, Integer pizzaId);

    Order removePizzaFromOrder(Integer orderId, Integer pizzaId);

    Order getOrderById(Integer id);

    void updateOrder(Order order);

    boolean addOrder(Order order);

    boolean cancelOrder(Integer orderId);

    void completeOrder(Integer orderId);
}
