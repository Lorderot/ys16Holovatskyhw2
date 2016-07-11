package Repository;

import Domain.Order;

import java.util.List;

public interface OrderRepository {

    boolean create(Order order);

    boolean update(Order order);

    boolean delete(Integer orderId);

    List<Order> getOrders();

    List<Order> getUndoneOrders();

    Order getOrderById(Integer id);
}
