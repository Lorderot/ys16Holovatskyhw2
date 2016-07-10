package Service;

import Domain.Customer;
import Repository.CustomerRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service("customerService")
public class SimpleCustomerService implements CustomerService, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean updateCustomer(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Customer findCustomerById(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Customer findCustomerByLogin(String login) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean registerCustomer(Customer customer) {
        return customerRepository.create(customer);
    }

    @Override
    public Customer createNewCustomer() {
        return (Customer)applicationContext.getBean("customer");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}
