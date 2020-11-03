package din.springframework.testtask.controllers;

import din.springframework.testtask.exceptions.ConvertException;
import din.springframework.testtask.model.Currency;
import din.springframework.testtask.model.CurrencyConverterHistory;
import din.springframework.testtask.model.User;
import din.springframework.testtask.services.ConverterService;
import din.springframework.testtask.services.UserServiceImpl;
import din.springframework.testtask.services.CurrencyConverterHistoryServiceImpl;
import din.springframework.testtask.services.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

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

    @ModelAttribute("currencyList")
    public List<Currency> currencyList() {
        return (List<Currency>) currencyService.findAll();
    }

    @ModelAttribute("userHistory")
    public Page<CurrencyConverterHistory> userHistory(@AuthenticationPrincipal UserDetails currentUser,
                    @Qualifier("userHistory") @PageableDefault(size = 5, sort = "queryDate") Pageable pageableHistory) {
        User user = (User) userService.findByUsername(currentUser.getUsername());
        return historyService.findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(user.getId(),
                        pageableHistory);
    }

    @GetMapping("/converter")
    public String converterIndex() {
        return "converter";
    }

    @GetMapping("/converter-history")
    public String converterHistory() {
        return "converter-history";
    }

    @PostMapping("/converter")
    public String convert(@RequestParam("initialCurrencyId") String iniCurrencyId,
                          @RequestParam("goalCurrencyId") String goalCurrencyId,
                          @RequestParam("initialValue") String iniValue,
                          @AuthenticationPrincipal UserDetails currentUser,
                          RedirectAttributes attributes) {

        User user = (User) userService.findByUsername(currentUser.getUsername());

        attributes.addFlashAttribute("initialCurrencyId", iniCurrencyId);
        attributes.addFlashAttribute("goalCurrencyId", goalCurrencyId);
        attributes.addFlashAttribute("initialValue", iniValue);
        try {
            attributes.addFlashAttribute("goalValue", converterService.convert(iniCurrencyId, goalCurrencyId,
                                                                                new BigDecimal(iniValue), user));
        } catch (NumberFormatException | ConvertException e) {
            attributes.addFlashAttribute("requestError", true);
        }


        return "redirect:converter";
    }

}
