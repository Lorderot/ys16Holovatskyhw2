package Repository;

import Domain.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("orderRepository")
public class InMemOrderRepository implements OrderRepository {
    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void create(Order order) {
        orders.add(order);
    }

    @Override
    public boolean update(Order order) {
        Order old = getOrderById(order.getId());
        if (old == null) {
            return false;
        }
        old.setOrderList(order.getOrderList());
        old.setCustomer(order.getCustomer());
        return true;
    }

    @Override
    public boolean delete(Order order) {
        Order old = getOrderById(order.getId());
        if (old == null) {
            return false;
        }
        return orders.remove(order);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    private Order getOrderById(Integer id) {
        return orders.stream().filter(order -> order.getId().equals(id))
                .findFirst().get();
    }
}
