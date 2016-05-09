package Domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
        this.orderList = orderList;
        this.customer = customer;
        updateTotalPrice();
    }
    public Order(Order order) {
        this(order.customer, order.orderList);
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
        this.orderList = orderList;
        updateTotalPrice();
    }

    public List<Pizza> getOrderList() {
        return orderList;
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
}
