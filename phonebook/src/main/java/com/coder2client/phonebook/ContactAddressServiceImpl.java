package com.coder2client.phonebook;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactAddressServiceImpl implements ContactAddressService {

    private static final Logger logger = LoggerFactory.getLogger(ContactAddressServiceImpl.class);

    private final ContactAddressRepository contactAddressRepository;
    private final EntityManager entityManager;

    public ContactAddressServiceImpl(ContactAddressRepository contactAddressRepository, EntityManager entityManager) {
        this.contactAddressRepository = contactAddressRepository;
        this.entityManager = entityManager;
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

    @Override
    public ContactAddressDTO getContactAddress(Long contactId) {
        ContactAddress address = contactAddressRepository.findById(contactId)
                .orElseThrow(()-> new ResourceNotFoundException("Contact Address", "ID", contactId));
        return ContactAddressMapper.mapToDTO(address);
    }

    @Override
    public ContactAddressDTO updateContactAddress(ContactAddressDTO contactAddressDTO) {
        Optional<ContactAddress> contactAddressId = contactAddressRepository.findById(contactAddressDTO.getId());
        ContactAddress addressToUpdate = contactAddressId.orElseThrow(() -> new ResourceNotFoundException("Contact Address", "ID", contactAddressDTO.getId()));
        updateContactAddressEntityToDTO(addressToUpdate, contactAddressDTO);

        ContactAddress addressSaved = contactAddressRepository.save(addressToUpdate);

        return ContactAddressMapper.mapToDTO(addressSaved);
    }

    @Override
    public void deleteContactAddress(Long id) {
        if (!contactAddressRepository.existsById(id)){
            throw new ResourceNotFoundException("Contact Address", "ID", id);
        }
        contactAddressRepository.deleteById(id);
    }

    @Override
    public List<ContactAddressDTO> findContactAddressByCriteria(String firstName, String lastName, String email, String phoneNumber) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactAddress> criteriaQuery = criteriaBuilder.createQuery(ContactAddress.class);
        Root<ContactAddress> root = criteriaQuery.from(ContactAddress.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (firstName !=null && !firstName.isEmpty()){
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%" ));
        }
        if (lastName !=null && !lastName.isEmpty()){
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%" ));
        }
        if (email !=null && !email.isEmpty()){
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%" ));
        }
        if (phoneNumber !=null && !phoneNumber.isEmpty()){
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%" ));
        }

        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        List<ContactAddress> result = entityManager.createQuery(criteriaQuery).getResultList();
        return result.stream().map(ContactAddressMapper::mapToDTO).collect(Collectors.toList());
    }

    private void updateContactAddressEntityToDTO(ContactAddress addressToUpdate, ContactAddressDTO contactAddressDTO) {

        if(contactAddressDTO.getFirstName() != null){
            addressToUpdate.setFirstName(contactAddressDTO.getFirstName());
        }
        if (contactAddressDTO.getLastName() != null){
            addressToUpdate.setLastName(contactAddressDTO.getLastName());
        }
        if (contactAddressDTO.getOtherName() != null){
            addressToUpdate.setOtherName(contactAddressDTO.getOtherName());
        }
        if (contactAddressDTO.getEmail() != null){
            addressToUpdate.setEmail(contactAddressDTO.getEmail());
        }
        if (contactAddressDTO.getPhoneNumber() != null){
            addressToUpdate.setPhoneNumber(contactAddressDTO.getPhoneNumber());
        }
        if (contactAddressDTO.getAddress() != null){
            addressToUpdate.setAddress(contactAddressDTO.getAddress());
        }


    }
}
