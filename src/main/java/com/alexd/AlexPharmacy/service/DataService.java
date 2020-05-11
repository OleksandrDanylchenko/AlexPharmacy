package com.alexd.AlexPharmacy.service;

import com.alexd.AlexPharmacy.domain.PharmacyDomain;
import com.alexd.AlexPharmacy.exception.RecordExistException;
import com.alexd.AlexPharmacy.exception.RecordNotFoundException;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    /**
     * Service method, which returns all presented records of certain repository.
     *
     * @param repository Repository with all needed records
     * @return List of specified repository records presented in table
     */
    public List<? extends PharmacyDomain> getAllRecords(
            @NotNull final JpaRepository<? extends PharmacyDomain, Long> repository) {
        return repository.findAll();
    }

    /**
     * Service method, which finds record in repository specified by id basket record.
     *
     * @param repository Repository with all needed records
     * @param id         ID of desired record
     * @return Record founded by ID
     */
    public PharmacyDomain getRecordById(@NotNull final JpaRepository<? extends PharmacyDomain, Long> repository,
                                        final String id) {
        try {
            var longId = NumberUtils.createLong(id);
            var foundedRecord = repository.findById(longId);
            return foundedRecord.orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException ex) {
            throw new RecordNotFoundException(id);
        }
    }

    /**
     * Service method, which saves given record into DB.
     *
     * @param repository Adding repository
     * @param record     Saving record
     */
    public void saveRecord(@NotNull final JpaRepository repository, final PharmacyDomain record) {
        try {
            repository.save(record);
        } catch (IllegalArgumentException | DataIntegrityViolationException ignored) {
            throw new RecordExistException("Запис уже присутній у базі даних");
        }
    }

    /**
     * Service method, which updates given manufacturer record.
     *
     * @param repository Updating repository
     * @param updRecord  Updating record
     */
    public void updateRecord(@NotNull final JpaRepository repository, @NotNull final PharmacyDomain updRecord) {
        var stringId = String.valueOf(updRecord.getId());
        var recordFromDB = getRecordById(repository, stringId);
        BeanUtils.copyProperties(updRecord, recordFromDB, "id");
        repository.save(recordFromDB);
    }

    /**
     * Service method, which deletes record by ID.
     *
     * @param repository Deleting repository
     * @param id         ID of deleting record
     */
    public void deleteRecord(final JpaRepository repository, final String id) {
        var recordFromDB = getRecordById(repository, id);
        repository.delete(recordFromDB);
    }

}
