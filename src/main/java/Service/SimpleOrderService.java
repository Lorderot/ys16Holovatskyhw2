package Service;

import Domain.Customer;
import Domain.Order;
import Domain.Pizza;
import Exceptions.NoSuchOrderException;
import Exceptions.NoSuchPizzaException;
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
    public Order placeNewOrder(Customer customer, Integer ... pizzasID)
            throws NoSuchPizzaException {
        List<Pizza> pizzas = pizzasListFromArrayOfIds(pizzasID);
        Order newOrder = getNewOrder();
        newOrder.setCustomer(customer);
        newOrder.setOrderList(pizzas);
        orderRepository.create(newOrder);
        return new Order(newOrder);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.getOrders();
        return makeClone(orders);
    }

    @Override
    public Order addPizzaToOrder(Integer orderId, Integer pizzaId)
            throws NoSuchOrderException, NoSuchPizzaException {
        Order order = orderRepository.getOrderById(orderId);
        Pizza pizza = pizzaService.getPizzaById(pizzaId);
        if (order == null) {
            throw new NoSuchOrderException(orderId);
        }
        order.addPizza(pizza);
        orderRepository.update(order);
        return new Order(order);
    }

    @Override
    public Order removePizzaFromOrder(Integer orderId, Integer pizzaId)
            throws NoSuchOrderException, NoSuchPizzaException  {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new NoSuchOrderException(orderId);
        }
        order.removePizza(pizzaId);
        orderRepository.update(order);
        return new Order(order);
    }

    @Override
    public Order updateOrder(Order order) throws NoSuchOrderException {
        if (!orderRepository.update(order)) {
            throw new NoSuchOrderException(order.getId());
        }
        return order;
    }

    @Override
    public boolean addOrder(Order order) {
        return orderRepository.create(order);
    }

    @Override
    public Order getOrderById(Integer id) throws NoSuchOrderException {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new NoSuchOrderException(id);
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

    private List<Pizza> pizzasListFromArrayOfIds(Integer[] pizzasID)
            throws NoSuchPizzaException {
        List<Pizza> pizzas = new ArrayList<>();
        List<Integer> noSuchPizzas = new ArrayList<>();
        for(Integer id : pizzasID){
            Pizza pizza = null;
            try {
                pizza = pizzaService.getPizzaById(id);
            } catch (NoSuchPizzaException e) {
                noSuchPizzas.add(id);
            }
            pizzas.add(pizza);
        }
        if (!noSuchPizzas.isEmpty()) {
            throw new NoSuchPizzaException(noSuchPizzas);
        }
        return pizzas;
    }

    private List<Order> makeClone(List<Order> orders) {
        List<Order> cloneOrders = new ArrayList<>(orders.size());
        orders.forEach((order) -> cloneOrders.add(new Order(order)));
        return cloneOrders;
    }
}
