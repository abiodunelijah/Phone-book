package com.coder2client.phonebook;

import com.coder2client.phonebook.security.Role;
import com.coder2client.phonebook.security.User;
import com.coder2client.phonebook.security.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactAddressServiceImpl implements ContactAddressService {

    private static final Logger logger = LoggerFactory.getLogger(ContactAddressServiceImpl.class);

    private final ContactAddressRepository contactAddressRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public ContactAddressServiceImpl(ContactAddressRepository contactAddressRepository, 
                                   UserRepository userRepository, 
                                   EntityManager entityManager) {
        this.contactAddressRepository = contactAddressRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    public ContactAddressDTO createContact(ContactAddressDTO contactAddressDTO, Authentication authentication) {
        logger.info("creating contact: {}", contactAddressDTO);

        User user = getCurrentUser(authentication);
        ContactAddress address = ContactAddressMapper.mapToEntity(contactAddressDTO);
        address.setUser(user);
        
        ContactAddress savedAddress = contactAddressRepository.save(address);
        return ContactAddressMapper.mapToDTO(savedAddress);
    }

    @Override
    public List<ContactAddressDTO> getAllContactAddresses(Authentication authentication) {
        User user = getCurrentUser(authentication);
        
        // Regular users can only see their own contacts
        List<ContactAddress> contactAddresses = contactAddressRepository.findByUser(user);
        return contactAddresses.stream()
                .map(ContactAddressMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContactAddressDTO> getAllContactAddressesAdmin() {
        // Admin can see all contacts
        List<ContactAddress> contactAddresses = contactAddressRepository.findAll();
        return contactAddresses.stream()
                .map(ContactAddressMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactAddressDTO getContactAddress(Long contactId, Authentication authentication) {
        User user = getCurrentUser(authentication);
        ContactAddress address = contactAddressRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact Address", "ID", contactId));

        // Check if user owns this contact or is admin
        if (!isAdmin(user) && !address.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You don't have permission to access this contact");
        }

        return ContactAddressMapper.mapToDTO(address);
    }

    @Override
    public ContactAddressDTO updateContactAddress(ContactAddressDTO contactAddressDTO, Authentication authentication) {
        User user = getCurrentUser(authentication);
        Optional<ContactAddress> contactAddressOptional = contactAddressRepository.findById(contactAddressDTO.getId());
        ContactAddress addressToUpdate = contactAddressOptional
                .orElseThrow(() -> new ResourceNotFoundException("Contact Address", "ID", contactAddressDTO.getId()));

        // Check if user owns this contact or is admin
        if (!isAdmin(user) && !addressToUpdate.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You don't have permission to update this contact");
        }

        updateContactAddressEntityToDTO(addressToUpdate, contactAddressDTO);
        ContactAddress addressSaved = contactAddressRepository.save(addressToUpdate);

        return ContactAddressMapper.mapToDTO(addressSaved);
    }

    @Override
    public void deleteContactAddress(Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        
        // Only admins can delete contacts
        if (!isAdmin(user)) {
            throw new AccessDeniedException("You don't have permission to delete contacts");
        }

        if (!contactAddressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact Address", "ID", id);
        }
        contactAddressRepository.deleteById(id);
    }

    @Override
    public List<ContactAddressDTO> findContactAddressByCriteria(String firstName, String lastName, String email, 
                                                              String phoneNumber, Authentication authentication) {
        User user = getCurrentUser(authentication);
        
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ContactAddress> criteriaQuery = criteriaBuilder.createQuery(ContactAddress.class);
        Root<ContactAddress> root = criteriaQuery.from(ContactAddress.class);
        List<Predicate> predicateList = new ArrayList<>();

        // Add user filter for non-admin users
        if (!isAdmin(user)) {
            predicateList.add(criteriaBuilder.equal(root.get("user"), user));
        }

        if (firstName != null && !firstName.isEmpty()) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
        }
        if (lastName != null && !lastName.isEmpty()) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
        }
        if (email != null && !email.isEmpty()) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            predicateList.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("phoneNumber")), "%" + phoneNumber.toLowerCase() + "%"));
        }

        criteriaQuery.where(predicateList.toArray(new Predicate[0]));
        List<ContactAddress> result = entityManager.createQuery(criteriaQuery).getResultList();
        return result.stream().map(ContactAddressMapper::mapToDTO).collect(Collectors.toList());
    }

    private User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    private void updateContactAddressEntityToDTO(ContactAddress addressToUpdate, ContactAddressDTO contactAddressDTO) {
        if (contactAddressDTO.getFirstName() != null) {
            addressToUpdate.setFirstName(contactAddressDTO.getFirstName());
        }
        if (contactAddressDTO.getLastName() != null) {
            addressToUpdate.setLastName(contactAddressDTO.getLastName());
        }
        if (contactAddressDTO.getOtherName() != null) {
            addressToUpdate.setOtherName(contactAddressDTO.getOtherName());
        }
        if (contactAddressDTO.getEmail() != null) {
            addressToUpdate.setEmail(contactAddressDTO.getEmail());
        }
        if (contactAddressDTO.getPhoneNumber() != null) {
            addressToUpdate.setPhoneNumber(contactAddressDTO.getPhoneNumber());
        }
        if (contactAddressDTO.getAddress() != null) {
            addressToUpdate.setAddress(contactAddressDTO.getAddress());
        }
    }
}
