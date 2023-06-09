package io.mkolodziejczyk92.data.service;

import io.mkolodziejczyk92.data.entity.Address;

import java.util.List;
import java.util.Optional;

import io.mkolodziejczyk92.data.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Optional<Address> get(Long id) {
        return repository.findById(id);
    }

    public Address saveNewAddress(Address entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Address> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Address> list(Pageable pageable, Specification<Address> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public List<Address> allAddresses(){ return repository.findAll();}

    public List<Address> clientAddresses(Long clientId){
        return repository.findAddressesByClientId(clientId);
    }

    public boolean isExist(Long addressId) {
        return repository.existsById(addressId);
    }

    public Address update(Address address) {
        return repository.save(address);
    }

    public Address save(Address address) {
        return repository.save(address);
    }
}
