package din.springframework.testtask.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    public static final String INDEX_PAGE = "index";
    public static final String LOGIN_PAGE = "login";

    /**
     * Main page
     */
    @SuppressWarnings("SameReturnValue")
    @RequestMapping({"/index", "/"})
    public String root() {
        return INDEX_PAGE;
    }

    /**
     * Login page
     */
    @SuppressWarnings("SameReturnValue")
    @RequestMapping("/login")
    public String login() {
        return LOGIN_PAGE;
    }

    /**
     * Login form error handling
     */
    @SuppressWarnings("SameReturnValue")
    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return LOGIN_PAGE;
    }
}
