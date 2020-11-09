package din.springframework.testtask.controllers;

import din.springframework.testtask.model.User;
import din.springframework.testtask.services.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RegistrationController {

    public static final String REGISTRATION_PAGE = "registration";
    private final UserServiceImpl userServiceImpl;

    public RegistrationController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    /**
     * GET method for registration page, creates new User and adds it to model as userForm.
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return REGISTRATION_PAGE;
    }

    /**
     * Registration POST method, utilizes userForm created in GET method. Redirects to index page and authorizes user.
     * @param userForm contains user data from registration.html form
     * @param bindingResult used to validate user data (username and password in our case)
     * @param attributes used to show message about successful registration on index page
     * @return redirects to index page
     */
    @PostMapping("/registration")
    public String addUser(@Valid @ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model,
                          RedirectAttributes attributes) {
        if (bindingResult.hasErrors()) {
            return REGISTRATION_PAGE;
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return REGISTRATION_PAGE;
        }
        if (!userServiceImpl.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return REGISTRATION_PAGE;
        }
        attributes.addFlashAttribute("successfulRegistration", true);
        authWithoutPassword(userServiceImpl.findByUsername(userForm.getUsername()));
        return "redirect:/";
    }


    /**
     * Method for authorization after registration.
     * @param user - user entity
     */
    public void authWithoutPassword(User user) {
        List<GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(p -> new SimpleGrantedAuthority(p.getAuthority()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
