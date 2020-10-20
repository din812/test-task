package din.springframework.testtask.bootstrap;

import din.springframework.testtask.model.CursValute;
import din.springframework.testtask.model.ValCurs;
import din.springframework.testtask.model.ValuteConverterHistory;
import din.springframework.testtask.repositories.CursValuteRepository;
import din.springframework.testtask.repositories.ValuteConverterHistoryRepository;
import din.springframework.testtask.repositories.ValuteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@Component
public class Bootstrap implements CommandLineRunner {

    private final CursValuteRepository cursValuteRepository;
    private final ValuteRepository valuteRepository;
    public final ValuteConverterHistoryRepository valuteConverterHistoryRepository;


    public static final String DAILY_ASP = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=16/10/2020";

    public Bootstrap(CursValuteRepository cursValuteRepository, ValuteRepository valuteRepository, ValuteConverterHistoryRepository valuteConverterHistoryRepository) {
        this.cursValuteRepository = cursValuteRepository;
        this.valuteRepository = valuteRepository;
        this.valuteConverterHistoryRepository = valuteConverterHistoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        convertXMLtoPojo();
        mockData();
    }

    public void convertXMLtoPojo() {

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            ValCurs valCurs = (ValCurs) jaxbUnmarshaller.unmarshal(new URL(DAILY_ASP));

            if (!cursValuteRepository.existsByDate(valCurs.getDate())) {
                valCurs.getValute()
                        .forEach(valute -> {
                            CursValute cursValute = new CursValute();
                                cursValute.setDate(valCurs.getDate());
                                cursValute.setNominal(valute.getNominal());
                                cursValute.setValue(valute.getValue());
                                cursValute.setValute(valute);

                                System.out.println(cursValute.toString());

                                valuteRepository.save(valute);
                                cursValuteRepository.save(cursValute);
                });
            }

            System.out.println(cursValuteRepository.getCursValuteByDateAndValute_Id("20.10.2020", "R01060"));
        }
        catch (JAXBException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void mockData() {
        ValuteConverterHistory converterHistory = new ValuteConverterHistory();
        converterHistory.setInitialValute("первоначальная валюта");
        converterHistory.setGoalValute("целевая валюта");
        converterHistory.setInitialSum("35.535");
        converterHistory.setGoalSum("353355.59909");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDateTo = LocalDate.parse("01.10.2020", formatter);

        converterHistory.setQueryDate(localDateTo);

        valuteConverterHistoryRepository.save(converterHistory);

        System.out.println(valuteConverterHistoryRepository
                .findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate.parse("20.10.2020", formatter),
                        LocalDate.parse("15.10.2020", formatter)));
    }
}
