package web.Rest;

import Domain.Customer;

public class RequestOrderWrapper {
    private Customer customer;
    private Integer[] pizzasID;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer[] getPizzasID() {
        return pizzasID;
    }

    public void setPizzasID(Integer[] pizzasID) {
        this.pizzasID = pizzasID;
    }
}
