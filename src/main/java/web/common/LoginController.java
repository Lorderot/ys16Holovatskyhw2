package web.common;

import Domain.Customer;
import Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@SessionAttributes
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "common/login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationForm(ModelMap modelMap) {
        modelMap.addAttribute("customer", customerService.getNewCustomer());
        return "common/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute Customer customer,
                               ModelMap modelMap) {
        customer.setRole(Customer.Role.valueOf("USER"));
        if (!customerService.registerCustomer(customer)) {
            if (customerService.findCustomerByLogin(
                    customer.getLogin()) != null) {
                modelMap.put("error", "Such login already exists");
            }
            return "common/register";
        }
        return "common/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String  logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}
