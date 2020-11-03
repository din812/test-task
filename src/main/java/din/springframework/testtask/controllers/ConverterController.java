package din.springframework.testtask.controllers;

import din.springframework.testtask.model.User;
import din.springframework.testtask.services.ConverterService;
import din.springframework.testtask.services.UserServiceImpl;
import din.springframework.testtask.services.CurrencyConverterHistoryServiceImpl;
import din.springframework.testtask.services.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class ConverterController {

    private final CurrencyServiceImpl currencyService;
    private final CurrencyConverterHistoryServiceImpl historyService;
    private final UserServiceImpl userService;
    private final ConverterService converterService;

    public ConverterController(CurrencyServiceImpl currencyService,
                               CurrencyConverterHistoryServiceImpl historyService, UserServiceImpl userService, ConverterService converterService) {
        this.currencyService = currencyService;
        this.historyService = historyService;
        this.userService = userService;
        this.converterService = converterService;
    }


    @GetMapping("/converter")
    public String converterIndex(Model model,
                                 @Qualifier("userHistory") @PageableDefault(size = 5) Pageable pageableHistory,
                                 @AuthenticationPrincipal UserDetails currentUser) {
        User user = (User) userService.findByUsername(currentUser.getUsername());
        model.addAttribute("currencyList", currencyService.findAll());
        model.addAttribute("userHistory",
                historyService.findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(user.getId(),
                        pageableHistory));
        return "converter";
    }

    @PostMapping("/converter")
    public String convert(@RequestParam("initialCurrencyId") String iniCurrencyId,
                          @RequestParam("goalCurrencyId") String goalCurrencyId,
                          @RequestParam("initialValue") String iniValue,
                          @AuthenticationPrincipal UserDetails currentUser,
                          Model model, @Qualifier("userHistory") @PageableDefault(size = 5) Pageable pageableHistory) {
        User user = (User) userService.findByUsername(currentUser.getUsername());
        model.addAttribute("currencyList", currencyService.findAll());
        model.addAttribute("initialCurrencyId", iniCurrencyId);
        model.addAttribute("goalCurrencyId", goalCurrencyId);
        model.addAttribute("initialValue", iniValue);
        try {
            model.addAttribute("goalValue", converterService.convert(iniCurrencyId, goalCurrencyId,
                                                                                new BigDecimal(iniValue), user));
            model.addAttribute("userHistory",
                    historyService.findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(user.getId(),
                            pageableHistory));
        } catch (NumberFormatException e) {
            model.addAttribute("requestError", true);
        }
        return "converter";
    }

}
