package din.springframework.testtask.services;

import din.springframework.testtask.model.CursValute;
import din.springframework.testtask.repositories.CursValuteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CursValuteServiceImpl implements CursValuteService {

    private final CursValuteRepository cursValuteRepository;

    public CursValuteServiceImpl(CursValuteRepository cursValuteRepository) {
        this.cursValuteRepository = cursValuteRepository;
    }

    @Override
    public boolean existsByDate(String date) {
        return cursValuteRepository.existsByDate(date);
    }

    @Override
    public void deleteAllByDate(String date) {
        cursValuteRepository.deleteAllByDate(date);
    }

    @Override
    public Optional<CursValute> getCursValuteByDateAndId(String date, String id) {
        return null; //cursValuteRepository.getCursValuteByDateAndId(date, id);
    }
}
