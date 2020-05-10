package com.alexd.AlexPharmacy.service;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import com.alexd.AlexPharmacy.exception.manufacturer.ManufacturerNotFoundException;
import com.alexd.AlexPharmacy.repository.ManufacturerRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {

    /**
     * Spring Data repository for manufacturers table.
     */
    private final ManufacturerRepository manufacturerRepository;

    /**
     * Spring DI constructor with ManufacturerRepository.
     *
     * @param manufacturerRepository Spring Data repository for manufacturers table
     */
    public ManufacturerService(final ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    /**
     * Service method, which returns all presented manufacturers.
     *
     * @return List of manufacturers presented in table
     */
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    /**
     * Service method, which finds Manufacturer specified by id basket record.
     *
     * @param id ID of desired manufacturer
     * @return Manufacturer founded by ID
     */
    public Manufacturer getManufacturerById(final String id) {
        try {
            var longId = NumberUtils.createLong(id);
            var foundedManufacturer = manufacturerRepository.findById(longId);
            return foundedManufacturer.orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException ex) {
            throw new ManufacturerNotFoundException(id);
        }
    }

    /**
     * Service method, which add to DB given manufacturer record.
     *
     * @param newManufacturer New given manufacturer
     */
    public void addNewManufacturer(final Manufacturer newManufacturer) {
        manufacturerRepository.save(newManufacturer);
    }

    /**
     * Service method, which updates given manufacturer record.
     *
     * @param updManufacturer Updating given manufacturer
     */
    public void updateManufacturer(final @NotNull Manufacturer updManufacturer) {
        var manufacturerFromDB = getManufacturerById(String.valueOf(updManufacturer.getId()));
        BeanUtils.copyProperties(updManufacturer, manufacturerFromDB, "id");
        manufacturerRepository.save(manufacturerFromDB);
    }

    /**
     * Service method, which deletes Manufacturer record by ID.
     *
     * @param id Deleting manufacturer ID
     */
    public void deleteManufacturer(final String id) {
        var deletionManufacturer = getManufacturerById(id);
        manufacturerRepository.delete(deletionManufacturer);
    }

}
