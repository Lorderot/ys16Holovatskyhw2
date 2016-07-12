package Repository;

import Domain.Order;

import java.util.List;

public interface OrderRepository {

    Order create(Order order);

    boolean update(Order order);

    boolean delete(Integer orderId);

    List<Order> getOrders();

    List<Order> getUndoneOrders();

    List<Order> getOrdersByCustomerId(Integer customerId);

    Order getOrderById(Integer id);
}
