package din.springframework.testtask.services;

import din.springframework.testtask.bootstrap.DataSourceParser;
import din.springframework.testtask.exceptions.ConvertException;
import din.springframework.testtask.model.ExchangeRate;
import din.springframework.testtask.model.User;
import din.springframework.testtask.model.CurrencyConverterHistory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
public class ConverterService {

    private final ExchangeRateServiceImpl exchangeCurrencyService;
    private final CurrencyServiceImpl currencyService;
    private final CurrencyConverterHistoryServiceImpl converterHistoryService;

    public ConverterService(ExchangeRateServiceImpl exchangeCurrencyService, CurrencyServiceImpl currencyService,
                            CurrencyConverterHistoryServiceImpl converterHistoryService) {
        this.exchangeCurrencyService = exchangeCurrencyService;
        this.currencyService = currencyService;
        this.converterHistoryService = converterHistoryService;
    }

    /**
     * Converting one currency to another. First we get actual exchange rates for Rubles (CRB.RU saves their rate with
     * nominal, so we have to do "value / nominal" to get it). When we exchange INITIAL currency for rubles and when
     * exchange rubles for our GOAL currency. The reason for this is because CRB.RU doesn't provide exchange rates
     * between every currency and the only option i though of was "INITIAL CURRENCY -> RUBLES -> GOAL CURRENCY".
     * Used BigDecimal with RoundingMode.HALF_EVEN, but CRB.RU uses different one, not wasting time on figuring this
     * one out, a lot of stackoverflow posts recommend HALF_EVEN for currency.
     * @param iniCurrencyId  ID of "initial" currency which will be converted FROM.
     * @param goalCurrencyId ID of "goal" currency which will be converted TO.
     * @param iniValue  value of converted currency.
     */
    public String convert(String iniCurrencyId, String goalCurrencyId, BigDecimal iniValue, User user) throws ConvertException {
        LocalDate localDate = LocalDate.now();

        updateExchangeRateIfNeeded(localDate);

        ExchangeRate iniCurrency = exchangeCurrencyService.findFirstByCurrency_IdOrderByDateDesc(iniCurrencyId);
        ExchangeRate goalCurrency = exchangeCurrencyService.findFirstByCurrency_IdOrderByDateDesc(goalCurrencyId);

        if (iniCurrency == null) {
            log.error("Initial currency for convert isn't present in DB!");
            throw new ConvertException();
        }
        if (goalCurrency == null) {
            log.error("Goal currency for convert isn't present in DB!");
            throw new ConvertException();
        }
        if (iniValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new ConvertException();
        }
        BigDecimal bigIniValue = new BigDecimal(iniCurrency.getValue());
        BigDecimal bigIniNominal = new BigDecimal(iniCurrency.getNominal());

        BigDecimal bigGoalValue = new BigDecimal(goalCurrency.getValue());
        BigDecimal bigGoalNominal = new BigDecimal(goalCurrency.getNominal());

        //Getting exchange rates
        BigDecimal IniValExchangeRateToRubles = bigIniValue.divide(bigIniNominal, 4, RoundingMode.HALF_EVEN);
        BigDecimal exchangeRateFromRUBToGoalVal = bigGoalValue.divide(bigGoalNominal, 4, RoundingMode.HALF_EVEN);

        BigDecimal valueInRubles = iniValue.multiply(IniValExchangeRateToRubles);

        BigDecimal valueInGoalCurrency = valueInRubles.divide(exchangeRateFromRUBToGoalVal, 4,
                                                                                                RoundingMode.HALF_EVEN);

        saveHistoryInDB(iniCurrencyId, goalCurrencyId, iniValue, valueInGoalCurrency, user);

        return valueInGoalCurrency.toString();
    }

    private void saveHistoryInDB(String iniCurrencyId, String goalCurrencyId, BigDecimal iniValue, BigDecimal goalValue,
                                                                                                        User user) {
        CurrencyConverterHistory convertHistory = new CurrencyConverterHistory();
        convertHistory.setQueryDate(LocalDateTime.now());
        convertHistory.setInitialCurrency(iniCurrencyId);
        convertHistory.setGoalCurrency(goalCurrencyId);
        convertHistory.setInitialSum(String.valueOf(iniValue));
        convertHistory.setGoalSum(String.valueOf(goalValue));
        convertHistory.setUser(user);

        converterHistoryService.save(convertHistory);
    }

    private void updateExchangeRateIfNeeded(LocalDate localDate) {
        if (!exchangeCurrencyService.existsByDate(localDate)) {
            new DataSourceParser(exchangeCurrencyService, currencyService); //task doesn't require error handling
        }
    }
}
