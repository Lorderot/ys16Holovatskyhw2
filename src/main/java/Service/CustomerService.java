package Service;

import Domain.Customer;

public interface CustomerService {
    Customer getNewCustomer();

    boolean registerCustomer(Customer customer);

    Customer findCustomerByLogin(String login);

    Customer findCustomerById(Integer id);

    boolean deleteCustomer(Customer customer);

    boolean updateCustomer(Customer customer);
}
