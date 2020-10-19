package din.springframework.testtask.services;

import din.springframework.testtask.model.ValuteConverterHistory;
import din.springframework.testtask.repositories.ValuteConverterHistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class ValuteConverterHistoryServiceImpl implements ValuteConverterHistoryService {

    private final ValuteConverterHistoryRepository valuteConverterHistoryRepository;

    public ValuteConverterHistoryServiceImpl(ValuteConverterHistoryRepository valuteConverterHistoryRepository) {
        this.valuteConverterHistoryRepository = valuteConverterHistoryRepository;
    }

    /*@Override
    public Set<ValuteConverterHistory> getValuteConverterHistoriesByQueryDateIsBetween(Date dateFrom, Date dateTo,
                                                                                       int dayDifference) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate localDateTo = LocalDate.parse(dateTo, formatter);
        localDateTo = localDateTo.minusDays(dayDifference);
        String formattedLocalDateTo = localDateTo.format(formatter);
        return valuteConverterHistoryRepository.getValuteConverterHistoriesByQueryDateIsBetween(dateFrom,
                Date.valueOf(localDateTo));
    }*/
}
