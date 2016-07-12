package Service;

import Domain.Customer;
import Domain.Order;
import Domain.Pizza;
import Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("orderService")
public class SimpleOrderService implements OrderService {
    private PizzaService pizzaService;
    private OrderRepository orderRepository;

    public SimpleOrderService() {
    }

    @Autowired
    public SimpleOrderService(PizzaService pizzaService, OrderRepository orderRepository) {
        this.pizzaService = pizzaService;
        this.orderRepository = orderRepository;
    }

    public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
        List<Pizza> pizzas = pizzaService.getPizzasById(Arrays.asList(pizzasID));
        Order newOrder = getNewOrder();
        newOrder.setCustomer(customer);
        newOrder.setOrderList(pizzas);
        orderRepository.create(newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.getOrders();
    }

    @Override
    public Order addPizzaToOrder(Integer orderId, Integer pizzaId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            return null;
        }
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if (pizza == null) {
            return order;
        }
        order.addPizza(pizza);
        orderRepository.update(order);
        return order;
    }

    @Override
    public Order removePizzaFromOrder(Integer orderId, Integer pizzaId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            return null;
        }
        Pizza pizza = order.removePizza(pizzaId);
        if (pizza != null) {
            orderRepository.update(order);
        }
        return order;
    }

    @Override
    public void updateOrder(Order order) {
        orderRepository.update(order);
    }

    @Override
    public boolean addOrder(Order order) {
        return orderRepository.create(order);
    }

    @Override
    public Order getOrderById(Integer id) {
        return orderRepository.getOrderById(id);
    }

    @Override
    public void completeOrder(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);
        order.setFinishDate(new Date());
        orderRepository.update(order);
    }

    @Override
    public boolean cancelOrder(Integer orderId) {
        Order order = orderRepository.getOrderById(orderId);
        order.setCancelled(true);
        return orderRepository.update(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.getOrdersByCustomerId(customerId);
    }

    @Override
    public List<Order> getUndoneOrdersSortedByDate() {
        List<Order> orderList = orderRepository.getUndoneOrders();
        Collections.sort(orderList, (order1, order2) -> {
            if (order1 == null && order2 == null) {
                return 0;
            }
            if (order1 == null) {
                return -1;
            }
            if (order2 == null) {
                return 1;
            }
            long date1 = order1.getCreationDate().getTime();
            long date2 = order2.getCreationDate().getTime();
            if (date1 > date2) {
                return -1;
            }
            if (date1 == date2) {
                return 0;
            }
            return 1;
        });
        return orderList;
    }

    public PizzaService getPizzaService() {
        return pizzaService;
    }

    public void setPizzaService(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Lookup
    public Order getNewOrder(){
        return null;
    }
}
