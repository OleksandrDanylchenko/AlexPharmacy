package com.alexd.AlexPharmacy.service;

import com.alexd.AlexPharmacy.domain.Drug;
import com.alexd.AlexPharmacy.repository.DrugRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy
public class DrugService {

    /**
     * Drugs table repository.
     */
    private final DrugRepository drugRepository;

    /**
     * Spring DI constructor for DrugRepository.
     *
     * @param drugRepository Drugs table repository
     */
    public DrugService(final DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    /**
     * Знайти назви препаратів, придбаних клієнтом з ім'ям clientFirstName та датою народження clientBirthday.
     *
     * @param firstName First name of specified client
     * @param birthday  Birthday of specified client
     * @return List of drug names, which was bought by specified client
     */
    public List<String> getBoughtDrugNamesByClient(final String firstName, final LocalDate birthday) {
        var drugs = drugRepository.findDrugBoughtByClientWithFirstNameAndBirthday(firstName, birthday);
        return drugs.stream().map(Drug::getName).collect(Collectors.toList());
    }
}
