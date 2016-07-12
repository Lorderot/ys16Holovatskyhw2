package Service;

import Domain.Customer;
import Domain.Order;

import java.util.List;

public interface OrderService {
    Order getNewOrder();

    List<Order> getAllOrders();

    List<Order> getUndoneOrders();

    List<Order> getOrdersByCustomerId(Integer customerId);

    Order placeNewOrder(Customer customer, Integer... pizzasID);

    Order addPizzaToOrder(Integer orderId, Integer pizzaId);

    Order removePizzaFromOrder(Integer orderId, Integer pizzaId);

    Order getOrderById(Integer id);

    void updateOrder(Order order);

    public List<Order> sortOrderListByDate(List<Order> orderList);

    Order addOrder(Order order);

    Order getUnfinishedOrder(Integer customerId);

    Order addUnfinishedOrder(Order order);

    boolean finishOrder(Order order);

    boolean cancelOrder(Integer orderId);

    void completeOrder(Integer orderId);
}
