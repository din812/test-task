package din.springframework.testtask.controllers;

import din.springframework.testtask.model.Currency;
import din.springframework.testtask.model.User;
import din.springframework.testtask.services.CurrencyService;
import din.springframework.testtask.services.UserServiceImpl;
import din.springframework.testtask.services.CurrencyConverterHistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final CurrencyConverterHistoryServiceImpl historyService;
    private final CurrencyService currencyService;

    public AdminController(UserServiceImpl userServiceImpl, CurrencyConverterHistoryServiceImpl historyService, CurrencyService currencyService) {
        this.userServiceImpl = userServiceImpl;
        this.historyService = historyService;
        this.currencyService = currencyService;
    }

    @ModelAttribute("currencyList")
    public List<Currency> currencyList() {
        return (List<Currency>) currencyService.findAll();
    }

    @GetMapping("/admin")
    public String userList(@PageableDefault(sort = "id") @Qualifier("allUsersPage") Pageable pageableUsers,
                                                                                                        Model model) {
        model.addAttribute("allUsersPage", userServiceImpl.findAll(pageableUsers));
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userServiceImpl.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/admin/get/")
    public String getUser(@Qualifier("userHistory") Pageable pageableHistory,
                           @PageableDefault(sort = "id") @Qualifier("allUsersPage") Pageable pageableUsers,
                           @ModelAttribute("userId") String userId, Model model) {
        model.addAttribute("allUsersPage", userServiceImpl.findAll(pageableUsers));
        try {
            User userFound = userServiceImpl.findUserById(Long.valueOf(userId));

            if (userFound.getId() == null) {
                model.addAttribute("idNotFound", true);
            } else {
                model.addAttribute("user", userFound);
                model.addAttribute("userHistory",
                        historyService.findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(Long.valueOf(userId),
                                                                                                    pageableHistory));
            }
        } catch (NumberFormatException e) {
            model.addAttribute("requestError", true);
        }

        return "admin";
    }
}