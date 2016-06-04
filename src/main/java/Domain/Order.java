package Domain;

import Exceptions.NoSuchPizzaException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Scope(scopeName = "prototype")
public class Order {
    private static int count = 1;
    private Integer id;
    private Date date;
    private List<Pizza> orderList;
    private Customer customer;
    private Double totalPrice;
    private String name;

    public Order() {
        id = count++;
        this.date = new Date();
        name = date.toString() + " id = " + id;
    }

    public Order(Customer customer, List<Pizza> orderList) {
        this();
        this.orderList = makeClone(orderList);
        this.customer = customer;
        updateTotalPrice();
    }
    public Order(Order order) {
        this.id = order.id;
        this.date = order.date;
        this.orderList = order.getOrderList();
        this.customer = new Customer(order.customer);
        this.totalPrice = order.totalPrice;
    }

    @Override
    public String toString() {
        return customer + "\nOrder{" + name + ", pizzas=" + orderList + '}';
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderList(List<Pizza> orderList) {
        this.orderList = makeClone(orderList);
        updateTotalPrice();
    }

    public void addPizza(Pizza pizza) {
        if (pizza == null) {
            throw new NullPointerException();
        }
        orderList.add(new Pizza(pizza));
        totalPrice += pizza.getPrice();
    }

    public void removePizza(Integer pizzaId) throws NoSuchPizzaException {
        int sizeBeforeRemoving = orderList.size();
        for (Pizza pizza : orderList) {
            if (pizza.getId().equals(pizzaId)) {
                orderList.remove(pizza);
                totalPrice -= pizza.getPrice();
                break;
            }
        }
        if (orderList.size() == sizeBeforeRemoving) {
            throw new NoSuchPizzaException(pizzaId);
        }
    }

    public List<Pizza> getOrderList() {
        return makeClone(orderList);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private void updateTotalPrice() {
        totalPrice = orderList.stream().mapToDouble(Pizza::getPrice).sum();
    }

    private List<Pizza> makeClone(List<Pizza> pizzas) {
        if (pizzas == null) {
            return null;
        }
        List<Pizza> clone = new ArrayList<>(pizzas.size());
        pizzas.forEach((pizza -> clone.add(new Pizza(pizza))));
        return clone;
    }
}
