package com.coder2client.phonebook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactAddressServiceImpl implements ContactAddressService {

    private static final Logger logger = LoggerFactory.getLogger(ContactAddressServiceImpl.class);

    private final ContactAddressRepository contactAddressRepository;

    public ContactAddressServiceImpl(ContactAddressRepository contactAddressRepository) {
        this.contactAddressRepository = contactAddressRepository;
    }


    @Override
    public ContactAddressDTO createContact(ContactAddressDTO contactAddressDTO) {

        logger.info("creating contact: {}", contactAddressDTO);

        ContactAddress address = ContactAddressMapper.mapToEntity(contactAddressDTO);
        ContactAddress savedAddress = contactAddressRepository.save(address);

        return ContactAddressMapper.mapToDTO(savedAddress);
    }

    @Override
    public List<ContactAddressDTO> getAllContactAddresses() {
        List<ContactAddress> contactAddresses = contactAddressRepository.findAll();
        return contactAddresses.stream()
                .map(ContactAddressMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
