package web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
public class UserPizzaController {
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String showMainPage() {
        return "user/welcome";
    }
}
