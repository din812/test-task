package din.springframework.testtask.bootstrap;

import din.springframework.testtask.model.*;
import din.springframework.testtask.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Transactional
@Component
public class Bootstrap implements CommandLineRunner {

    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyConverterHistoryRepository currencyConverterHistoryRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Bootstrap(ExchangeRateRepository exchangeRateRepository, CurrencyRepository currencyRepository, CurrencyConverterHistoryRepository currencyConverterHistoryRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.currencyRepository = currencyRepository;
        this.currencyConverterHistoryRepository = currencyConverterHistoryRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        new DataSourceParser(exchangeRateRepository, currencyRepository); //pull today's data from CBR.ru site
        //mockData();
    }

    public void mockData() {
        Role adminRole = new Role(2L, "ADMIN");
        Role userRole = new Role(1L, "USER");

        roleRepository.saveAll(Arrays.asList(adminRole, userRole));

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User admin = new User();
        admin.setId(1L);
        admin.setPassword(bCryptPasswordEncoder.encode("qwerty"));
        admin.setPasswordConfirm(bCryptPasswordEncoder.encode("qwerty"));
        admin.getRoles().add(adminRole);
        admin.setUsername("Admin");

        userRepository.save(admin);

        /*System.out.println(valuteConverterHistoryRepository.findAllByUser_Id(1L));*/


        //System.out.println(roleRepository.findAll().toString());

        CurrencyConverterHistory converterHistory = new CurrencyConverterHistory();
        converterHistory.setInitialCurrency("первоначальная валюта");
        converterHistory.setGoalCurrency("целевая валюта");
        converterHistory.setInitialSum("35.535");
        converterHistory.setGoalSum("353355.59909");
        converterHistory.setUser(userRepository.findByUsername("Admin"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate localDateTo = LocalDate.parse("11.10.2020", formatter);

        converterHistory.setQueryDate(localDateTo);

        currencyConverterHistoryRepository.save(converterHistory);

        /*System.out.println(valuteConverterHistoryRepository
               .findAllByQueryDateLessThanEqualAndQueryDateGreaterThanEqual(LocalDate.parse("26.10.2020", formatter),
                        LocalDate.parse("15.10.2020", formatter)));*/

        System.out.println(currencyRepository.findAll().toString());
    }
}
