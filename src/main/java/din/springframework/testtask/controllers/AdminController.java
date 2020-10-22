package din.springframework.testtask.controllers;

import din.springframework.testtask.model.User;
import din.springframework.testtask.services.UserServiceImpl;
import din.springframework.testtask.services.ValuteConverterHistoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class AdminController {

    private final UserServiceImpl userServiceImpl;
    private final ValuteConverterHistoryServiceImpl historyService;

    public AdminController(UserServiceImpl userServiceImpl, ValuteConverterHistoryServiceImpl historyService) {
        this.userServiceImpl = userServiceImpl;
        this.historyService = historyService;
    }

    @GetMapping("/admin")
    public String userList(@PageableDefault(size = 1) @Qualifier("allUsersPage") Pageable pageableUsers, Model model) {
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
    public String  getUser(@PageableDefault(size = 3) @Qualifier("allUsersPage") Pageable pageableUsers,
                           @Qualifier("userHistory") Pageable pageableHistory,
                           @ModelAttribute("userId") String userId, Model model) {
        model.addAttribute("allUsersPage", userServiceImpl.findAll(pageableUsers));
        try {
            User userFound = userServiceImpl.findUserById(Long.valueOf(userId));

            if (userFound.getId() == null) {
                model.addAttribute("idNotFound", true);
            } else {
                model.addAttribute("user", userFound);
                model.addAttribute("userHistory",
                        historyService.findAllByUserIdOrderByUuid(Long.valueOf(userId), pageableHistory));
                System.out.println(pageableHistory.getPageSize());
            }
        } catch (NumberFormatException e) {
            model.addAttribute("requestError", true);
        }

        return "admin";
    }
}