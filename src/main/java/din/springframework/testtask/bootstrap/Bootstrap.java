package din.springframework.testtask.bootstrap;

import din.springframework.testtask.model.ValCurs;
import din.springframework.testtask.model.Valute;
import din.springframework.testtask.repositories.ValCursRepository;
import din.springframework.testtask.repositories.ValuteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;

@Transactional
@Component
public class Bootstrap implements CommandLineRunner {

    private final ValCursRepository valCursRepository;
    private final ValuteRepository valuteRepository;

    public Bootstrap(ValCursRepository valCursRepository, ValuteRepository valuteRepository) {
        this.valCursRepository = valCursRepository;
        this.valuteRepository = valuteRepository;
    }

    public static final String DAILY_ASP = "http://www.cbr.ru/scripts/XML_daily.asp";

    @Override
    public void run(String... args) throws Exception {
        convertXMLtoPojo();
    }

    public void convertXMLtoPojo() {

        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ValCurs.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            ValCurs valCurs = (ValCurs) jaxbUnmarshaller.unmarshal(new URL(DAILY_ASP));
            valCurs
                    .getValute()
                    .stream()
                    .iterator()
                    .forEachRemaining(valute -> valute.setValcurs(valCurs));

            if (valCursRepository.getValCursByDateEquals(valCurs.getDate()) == null) {
                valCursRepository.save(valCurs);
            }
            System.out.println(valuteRepository.findValutesByValcurs_Date(valCurs.getDate()).toString());

            System.out.println(valCurs);

        }
        catch (JAXBException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
