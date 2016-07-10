package Service;

import Domain.Customer;
import Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("userDetailsService")
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        Customer customer = customerRepository.getCustomerByLogin(login);
        if (customer == null) {
            throw new UsernameNotFoundException("Register, please!");
        }
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"
                + customer.getRole().toString());
        return new User(customer.getLogin(), customer.getPassword(),
                true, true, true, true, Arrays.asList(authority));
    }
}
