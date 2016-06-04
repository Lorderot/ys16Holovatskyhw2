package Repository;

import Domain.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("orderRepository")
public class InMemOrderRepository implements OrderRepository {
    private List<Order> orders = new ArrayList<>();

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean create(Order order) {
        if (order == null) {
            return false;
        }
        if (getOrderById(order.getId()) != null) {
            return false;
        }
        orders.add(order);
        return true;
    }

    @Override
    public boolean update(Order order) {
        if (order == null) {
            return false;
        }
        Order old = getOrderById(order.getId());
        if (old == null) {
            return false;
        }
        old.setOrderList(order.getOrderList());
        old.setCustomer(order.getCustomer());
        return true;
    }

    @Override
    public boolean delete(Integer orderId) {
        Order oldOrder = getOrderById(orderId);
        if (oldOrder == null) {
            return false;
        }
        return orders.remove(oldOrder);
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order getOrderById(Integer id) {
        Optional<Order> optionalOrder = orders.stream()
                .filter(order -> order.getId().equals(id)).findFirst();
        if (!optionalOrder.isPresent()) {
            return null;
        }
        return optionalOrder.get();
    }
}
