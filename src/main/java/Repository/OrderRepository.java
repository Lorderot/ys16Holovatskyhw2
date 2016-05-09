package Repository;

import Domain.Order;

import java.util.List;

public interface OrderRepository {

    void create(Order order);

    boolean update(Order order);

    boolean delete(Order order);

    List<Order> getOrders();
}
