package Service;

import Domain.Customer;
import Domain.Order;
import Domain.Pizza;
import Infrastructure.Benchmark;
import Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    @Benchmark(active = true)
    public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
        List<Pizza> pizzas = pizzasListFromArrayOfIds(pizzasID);
        Order newOrder = getNewOrder();
        newOrder.setCustomer(customer);
        newOrder.setOrderList(pizzas);
        orderRepository.create(newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.getOrders();
        List<Order> cloneOrders = makeClone(orders);
        return cloneOrders;
    }

    @Override
    public Order addPizzaToOrder(Order order, Integer pizzaId) {
        List<Pizza> pizzas = order.getOrderList();
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if (pizza != null) {
            pizzas.add(pizza);
        }
        return order;
    }

    @Override
    public Order removePizzaFromOrder(Order order, Integer pizzaId) {
        List<Pizza> pizzas = order.getOrderList();
        Pizza pizza = pizzas.stream().filter(pizza1 -> pizza1.getId()
                .equals(pizzaId)).findFirst().get();
        if (pizza != null) {
            pizzas.remove(pizza);
        }
        return order;
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
    protected Order getNewOrder(){
        return null;
    }

    private List<Pizza> pizzasListFromArrayOfIds(Integer[] pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for(Integer id : pizzasID){
            pizzas.add(pizzaService.getPizzaById(id));
        }
        return pizzas;
    }

    private List<Order> makeClone(List<Order> orders) {
        List<Order> cloneOrders = new ArrayList<>(orders.size());
        for (Order order : orders) {
            cloneOrders.add(new Order(order));
        }
        return cloneOrders;
    }
}
