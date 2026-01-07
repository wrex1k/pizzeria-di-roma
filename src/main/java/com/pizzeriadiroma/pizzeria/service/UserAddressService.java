package com.pizzeriadiroma.pizzeria.service;

import com.pizzeriadiroma.pizzeria.entity.User;
import com.pizzeriadiroma.pizzeria.entity.UserAddress;
import com.pizzeriadiroma.pizzeria.repository.UserAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserAddressService {

    private final UserAddressRepository addressRepository;

    public UserAddressService(UserAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }
    @Transactional(readOnly = true)
    public List<UserAddress> getAddressesByUser(User user) {
        return addressRepository.findByUserOrderByIsDefaultDescCreatedAtDesc(user);
    }
    @Transactional(readOnly = true)
    public Optional<UserAddress> getDefaultAddress(User user) {
        return addressRepository.findByUserAndIsDefaultTrue(user);
    }

    @Transactional(readOnly = true)
    public Optional<UserAddress> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Transactional
    public UserAddress saveAddress(UserAddress address) {
        long count = addressRepository.countByUser(address.getUser());
        if (count == 0) {
            address.setIsDefault(true);
        }

        if (address.getIsDefault() != null && address.getIsDefault()) {
            clearAllDefaultsForUser(address.getUser());
        }

        return addressRepository.save(address);
    }

    @Transactional
    public void setDefaultAddress(User user, UserAddress newDefaultAddress) {
        if (!newDefaultAddress.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Adresa nepatrí danému používateľovi");
        }

        clearAllDefaultsForUser(user);

        newDefaultAddress.setIsDefault(true);
        addressRepository.save(newDefaultAddress);
    }

    @Transactional
    public void setAsDefault(Long addressId, User user) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Adresa s ID " + addressId + " neexistuje"));

        setDefaultAddress(user, address);
    }

    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    private void clearAllDefaultsForUser(User user) {
        List<UserAddress> defaultAddresses = addressRepository.findByUserAndIsDefault(user, true);
        for (UserAddress address : defaultAddresses) {
            address.setIsDefault(false);
            addressRepository.save(address);
        }
    }
}
