package din.springframework.testtask.bootstrap;

import din.springframework.testtask.model.ExchangeRate;
import din.springframework.testtask.model.ValCurs;
import din.springframework.testtask.repositories.ExchangeRateRepository;
import din.springframework.testtask.repositories.CurrencyRepository;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for pulling data on start up and on demand if needed.
 */

@Component
public class DataSourceParser {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public DataSourceParser(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;

        convertSourcePojoStructure(pullAndConvertXmlToPojo());
    }

    /**
     * Specific date can be requested with addition of ?date_req=18/10/2020 to the end of string.
     */
    public static final String DAILY_ASP = "http://www.cbr.ru/scripts/XML_daily.asp";

    /**
     * Basic JAXB parsing.
     * @return returns data with original structure.
     */
    public static ValCurs pullAndConvertXmlToPojo() {
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (ValCurs) jaxbUnmarshaller.unmarshal(new URL(DAILY_ASP));
        }
        catch (JAXBException | MalformedURLException e) {
            e.printStackTrace();
        }
        return new ValCurs();
    }

    /**
     * Converting source structure and data format into our POJOs for easier use.
     * @param valCurs - main part of source POJO, contains ValCurs and Valute (Currency) data.
     */
    public void convertSourcePojoStructure(ValCurs valCurs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (!exchangeRateRepository.existsByDate(LocalDate.parse(valCurs.getDate(), formatter))) {
            valCurs.getCurrency()
                    .forEach(currency -> {
                        ExchangeRate exchangeRate = new ExchangeRate();
                        exchangeRate.setDate(LocalDate.parse(valCurs.getDate(), formatter));
                        exchangeRate.setNominal(currency.getNominal().replaceAll(",", "."));
                        exchangeRate.setValue(currency.getValue().replaceAll(",", "."));
                        exchangeRate.setCurrency(currency);

                        currencyRepository.save(currency);
                        exchangeRateRepository.save(exchangeRate);
                    });
        }
    }
}
