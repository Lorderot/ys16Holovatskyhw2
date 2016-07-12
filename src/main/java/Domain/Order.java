package Domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Scope(scopeName = "prototype")
public class Order {
    private Integer id;
    private Date creationDate;
    private Date updateDate;
    private Date finishDate;
    private List<Pizza> orderList;
    private Customer customer;
    private boolean cancelled;
    private Double totalPrice;

    public Order() {
        this.cancelled = false;
        this.orderList = new ArrayList<>();
        totalPrice = 0.0;
    }

    public Order(List<Pizza> orderList, Customer customer) {
        this();
        this.orderList = orderList;
        this.customer = customer;
        updateTotalPrice();
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

    public void addPizza(Pizza pizza) {
        orderList.add(pizza);
        totalPrice += pizza.getPrice();
    }

    public Pizza removePizza(Integer pizzaId) {
        for (Pizza pizza : orderList) {
            if (pizza.getId().equals(pizzaId)) {
                orderList.remove(pizza);
                totalPrice -= pizza.getPrice();
                return pizza;
            }
        }
        return null;
    }

    public List<Pizza> getOrderList() {
        return orderList;
    }

    public void clear() {
        orderList.clear();
        updateTotalPrice();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    private void updateTotalPrice() {
        totalPrice = orderList.stream().mapToDouble(Pizza::getPrice).sum();
    }
}
