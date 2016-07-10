package web.common;

import Domain.Customer;
import Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationForm(ModelMap modelMap) {
        modelMap.addAttribute("customer", customerService.createNewCustomer());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute Customer customer) {
        customer.setRole(Customer.Role.valueOf("USER"));
        try {
            customerService.registerCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return "register";
        }
        return "login";
    }
}
