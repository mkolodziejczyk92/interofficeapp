package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Manufacturer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService {
    private final ManufacturerRepository repository;

    public ManufacturerService(ManufacturerRepository repository) {
        this.repository = repository;
    }

    public List<Manufacturer> allManufacturers() {
        return repository.findAll();
    }

    public void delete(Long manufacturerId) {
        repository.deleteById(manufacturerId);
    }

    public void saveNewManufacturer(Manufacturer manufacturer) {
        repository.save(manufacturer);
    }
}
