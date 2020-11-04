package din.springframework.testtask.controllers;

import din.springframework.testtask.exceptions.ConvertException;
import din.springframework.testtask.model.Currency;
import din.springframework.testtask.model.CurrencyConverterHistory;
import din.springframework.testtask.model.User;
import din.springframework.testtask.services.ConverterService;
import din.springframework.testtask.services.CurrencyConverterHistoryServiceImpl;
import din.springframework.testtask.services.CurrencyServiceImpl;
import din.springframework.testtask.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ConverterController {

    public static final String CONVERTER_PAGE = "converter";
    public static final String CONVERTER_HISTORY_PAGE = "converter-history";
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

    /**
     * Utility method, loads on AdminController call, used for generating currency name in tables.
     * @return returns list of Currencies data.
     */
    @ModelAttribute("currencyList")
    public List<Currency> currencyList() {
        return (List<Currency>) currencyService.findAll();
    }

    /**
     * Utility method, loads on AdminController call, used for loading pageable convert history for current user.
     * @return returns pageable convert history.
     */
    @ModelAttribute("userHistory")
    public Page<CurrencyConverterHistory> userHistory(@AuthenticationPrincipal UserDetails currentUser,
                    @Qualifier("userHistory") @PageableDefault(size = 5, sort = "queryDate") Pageable pageableHistory) {
        User user = userService.findByUsername(currentUser.getUsername());
        return historyService.findAllByUserIdOrderByQueryDateDescUuidDescInitialSumDesc(user.getId(),
                        pageableHistory);
    }

    /**
     * Main page of converter. Contains last 5 conversions and form for conversion.
     * @return loads converter.html
     */
    @SuppressWarnings("SameReturnValue")
    @GetMapping("/converter")
    public String converterIndex() {
        return CONVERTER_PAGE;
    }

    /**
     * Shows complete history for current user.
     * @return loads converter-history.html
     */
    @SuppressWarnings("SameReturnValue")
    @GetMapping("/converter-history")
    public String converterHistory() {
        return CONVERTER_HISTORY_PAGE;
    }

    /**
     * Method used to get ID's of initial and goal currency and amount of currency user wants to exchange. Redirects
     * back to converter page with FlashAttributes to populate form with submitted data or else it will be set to
     * default values. Implements basic error handling.
     * @param iniCurrencyId ID of initial currency
     * @param goalCurrencyId ID of goal currency
     * @param iniValue amount of money
     * @param currentUser used for saving conversion in database with ID of a user.
     * @param attributes needed for redirecting attributes.
     * @return redirects to converter.html
     */
    @PostMapping("/converter")
    public String convert(@RequestParam("initialCurrencyId") String iniCurrencyId,
                          @RequestParam("goalCurrencyId") String goalCurrencyId,
                          @RequestParam("initialValue") String iniValue,
                          @AuthenticationPrincipal UserDetails currentUser,
                          RedirectAttributes attributes) {
        User user = userService.findByUsername(currentUser.getUsername());

        attributes.addFlashAttribute("initialCurrencyId", iniCurrencyId);
        attributes.addFlashAttribute("goalCurrencyId", goalCurrencyId);
        attributes.addFlashAttribute("initialValue", iniValue);
        try {
            attributes.addFlashAttribute("goalValue", converterService.convert(iniCurrencyId, goalCurrencyId,
                                                                                new BigDecimal(iniValue), user));
        } catch (NumberFormatException | ConvertException e) {
            attributes.addFlashAttribute("requestError", true);
        }
        return "redirect:" + CONVERTER_PAGE;
    }

}
