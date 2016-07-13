package Service;

import Domain.Customer;
import Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

@Service("customerService")
public class SimpleCustomerService implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean updateCustomer(Customer customer) {
        return customerRepository.update(customer);
    }

    @Override
    public boolean deleteCustomer(Integer customerId) {
        return customerRepository.delete(customerId);
    }

    @Override
    public Customer findCustomerById(Integer id) {
        return customerRepository.getCustomerById(id);
    }

    @Override
    public Customer findCustomerByLogin(String login) {
        return customerRepository.getCustomerByLogin(login);
    }

    @Override
    public boolean registerCustomer(Customer customer) {
        return customerRepository.create(customer);
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Lookup
    public Customer getNewCustomer() {
        return null;
    }
}
