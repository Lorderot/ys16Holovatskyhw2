package Repository;

import Domain.Customer;

import java.util.List;

public interface CustomerRepository {
    boolean create(Customer customer);

    boolean update(Customer customer);

    boolean delete(Integer customerId);

    List<Customer> getCustomers();

    Customer getCustomerById(Integer customerId);

    Customer getCustomerByLogin(String login);
}
