package din.springframework.testtask.controllers;

import din.springframework.testtask.model.Currency;
import din.springframework.testtask.model.User;
import din.springframework.testtask.services.CurrencyConverterHistoryServiceImpl;
import din.springframework.testtask.services.CurrencyService;
import din.springframework.testtask.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class AdminController {

    public static final String ADMIN_PAGE = "admin";
    private final UserServiceImpl userServiceImpl;
    private final CurrencyConverterHistoryServiceImpl historyService;
    private final CurrencyService currencyService;

    public AdminController(UserServiceImpl userServiceImpl, CurrencyConverterHistoryServiceImpl historyService, CurrencyService currencyService) {
        this.userServiceImpl = userServiceImpl;
        this.historyService = historyService;
        this.currencyService = currencyService;
    }

    /**
     * Utility method, loads on AdminController call, used for generating currency name in tables.
     * @return returns list of Currencies data.
     */
    @ModelAttribute("currencyList")
    public List<Currency> currencyList() {
        return (List<Currency>) currencyService.findAll();
    }

    /**
     * Main admin GET method, loads pageableUsers in a table and sorts when by ID.
     * @param pageableUsers used for paging table with users.
     * @return loads admin.html
     */
    @GetMapping("/admin")
    public String userList(@PageableDefault(sort = "id") @Qualifier("allUsersPage") Pageable pageableUsers,
                                                                                                        Model model) {
        model.addAttribute("allUsersPage", userServiceImpl.findAll(pageableUsers));
        return ADMIN_PAGE;
    }

    /**
     * Post method used to find user by ID in database and retrieve his data and convert history into pageable table.
     * Implemented some error handling.
     * @param pageableHistory used for paging table with user history.
     * @param pageableUsers used for paging table with users.
     * @param userId ID submitted from admin.html, used to find users history with userServiceImpl
     * @return loads admin.html
     */
    @GetMapping("/admin/get/")
    public String getUser(@PageableDefault(sort = "queryDate") @Qualifier("userHistory") Pageable pageableHistory,
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
        return ADMIN_PAGE;
    }
}