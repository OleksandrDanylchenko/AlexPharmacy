package com.alexd.AlexPharmacy.service;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import com.alexd.AlexPharmacy.repository.ManufacturerRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Lazy
public class ManufacturerService {

    /**
     * Manufacturers table repository.
     */
    private final ManufacturerRepository manufacturerRepository;

    /**
     * Spring DI constructor for ManufacturerRepository.
     *
     * @param manufacturerRepository Manufacturers table repository
     */
    public ManufacturerService(final ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    /**
     * Method for getting all founded trademarks of manufacturers, which supply drug with drugName.
     *
     * @param drugName Name of drug, which supplied by manufacturer
     * @return List of trademarks of manufacturers, which supply drug with drugName
     */
    public List<String> getTrademarksBySupplyDrug(final String drugName) {
        var suppliers = manufacturerRepository.findManufacturersBySupplyDrug(drugName);
        return suppliers.stream().map(Manufacturer::getTrademark).collect(Collectors.toList());
    }

}
