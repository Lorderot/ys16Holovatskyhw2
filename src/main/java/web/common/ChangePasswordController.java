package web.common;

import Domain.Customer;
import Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChangePasswordController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{role:user|admin}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable String role,
                                         ModelMap modelMap) {
        String url = "/" + role + "/changePassword";
        modelMap.put("url", url);
        return "common/passwordForm";
    }

    @RequestMapping(value = "/{role:user|admin}/changePassword", method = RequestMethod.POST)
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 ModelMap modelMap,
                                 @PathVariable String role) {
        return passwordValidation(oldPassword, newPassword,
                confirmPassword, modelMap, role);
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private String passwordValidation(String oldPassword,
                                      String newPassword,
                                      String confirmPassword,
                                      ModelMap modelMap,
                                      String role) {
        Customer customer = getLoggedInCustomer();
        if (newPassword == null || newPassword.length() < 6) {
            modelMap.put("error",
                    "Password should contain at least 6 characters");
            return "common/passwordForm";
        }
        if (!passwordEncoder.matches(oldPassword, customer.getPassword())){
            modelMap.put("error", "Invalid password");
            return "common/passwordForm";
        }
        if (!newPassword.equals(confirmPassword)) {
            modelMap.put("error", "Invalid confirm password");
            return "common/passwordForm";
        }
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerService.updateCustomer(customer);
        return "redirect:/"+role+"/profile";
    }

    private Customer getLoggedInCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        return customerService.findCustomerByLogin(user.getUsername());
    }
}
