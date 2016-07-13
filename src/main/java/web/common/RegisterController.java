package web.common;

import Domain.Customer;
import Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationForm(ModelMap modelMap) {
        modelMap.addAttribute("customer", customerService.getNewCustomer());
        return "common/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute Customer customer,
                               ModelMap modelMap) {
        customer.setRole(Customer.Role.valueOf("USER"));
        return registerCustomer(customer, modelMap);
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private String registerCustomer(Customer customer, ModelMap modelMap) {
        String password = customer.getPassword();
        customer.setPassword(passwordEncoder.encode(password));
        if (!customerService.registerCustomer(customer)) {
            if (customerService.findCustomerByLogin(
                    customer.getLogin()) != null) {
                modelMap.put("error", "Such login already exists");
            }
            return "common/register";
        }
        return "common/login";
    }
}
