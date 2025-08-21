package com.coder2client.phonebook;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface ContactAddressService {

    ContactAddressDTO createContact(ContactAddressDTO contactAddressDTO, Authentication authentication);
    List<ContactAddressDTO> getAllContactAddresses(Authentication authentication);
    List<ContactAddressDTO> getAllContactAddressesAdmin(); // Admin only
    ContactAddressDTO getContactAddress(Long contactId, Authentication authentication);
    ContactAddressDTO updateContactAddress(ContactAddressDTO contactAddressDTO, Authentication authentication);
    void deleteContactAddress(Long id, Authentication authentication);
    List<ContactAddressDTO> findContactAddressByCriteria(String firstName, String lastName, String email, String phoneNumber, Authentication authentication);
}
