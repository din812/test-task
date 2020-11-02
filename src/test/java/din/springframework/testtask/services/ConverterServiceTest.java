package din.springframework.testtask.services;

import din.springframework.testtask.model.ExchangeRate;
import din.springframework.testtask.model.Role;
import din.springframework.testtask.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class ConverterServiceTest {

    ConverterService converterService;

    @Mock
    ExchangeRateServiceImpl exchangeRateService;

    @Mock
    CurrencyServiceImpl currencyService;

    @Mock
    CurrencyConverterHistoryServiceImpl converterHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        converterService = new ConverterService(exchangeRateService, currencyService, converterHistoryService);
    }

    @Test
    void convert() {
        Role role = new Role();
        role.setId(5L);
        role.setName("TestRole");

        User user = new User();
        user.setId(1L);
        user.setUsername("TestUser");
        user.getRoles().add(role);

        role.getUsers().add(user);

        ExchangeRate iniCurrency = new ExchangeRate();
        iniCurrency.setValue("90.4489");
        iniCurrency.setNominal("1");

        ExchangeRate goalCurrency = new ExchangeRate();
        goalCurrency.setValue("76.4443");
        goalCurrency.setNominal("1");

        when(exchangeRateService.findFirstByCurrency_IdOrderByDateDesc("R01235")).thenReturn(iniCurrency);
        when(exchangeRateService.findFirstByCurrency_IdOrderByDateDesc("R01239")).thenReturn(goalCurrency);

        String result = converterService.convert("R01235", "R01239", new BigDecimal("39"),
                                                                                                                  user);

        System.out.println(result);
    }
}