package Service;

import Domain.Customer;
import Domain.Order;
import Exceptions.NoSuchOrderException;
import Exceptions.NoSuchPizzaException;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order placeNewOrder(Customer customer, Integer... pizzasID)
            throws NoSuchPizzaException;

    Order addPizzaToOrder(Integer orderId, Integer pizzaId)
            throws NoSuchOrderException, NoSuchPizzaException;

    Order removePizzaFromOrder(Integer orderId, Integer pizzaId)
            throws NoSuchOrderException, NoSuchPizzaException;

    Order getOrderById(Integer id) throws NoSuchOrderException;

    Order updateOrder(Order order) throws NoSuchOrderException;

    boolean addOrder(Order order);
}
