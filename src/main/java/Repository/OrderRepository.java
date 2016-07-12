package Repository;

import Domain.Order;

import java.util.List;

public interface OrderRepository {

    Order create(Order order);

    boolean update(Order order);

    boolean delete(Integer orderId);

    List<Order> getFinishedOrders();

    List<Order> getUndoneOrders();

    List<Order> getFinishedOrdersByCustomerId(Integer customerId);

    Order getOrderById(Integer id);

    Order getUnfinishedOrder(Integer customerId);
}
