package com.coder2client.phonebook;


import java.util.List;

public interface ContactAddressService {

    ContactAddressDTO createContact(ContactAddressDTO contactAddressDTO);
    List<ContactAddressDTO> getAllContactAddresses();
    ContactAddressDTO getContactAddress(Long contactId);
    ContactAddressDTO updateContactAddress(ContactAddressDTO contactAddressDTO);
    void deleteContactAddress(Long id);
    List<ContactAddressDTO> findContactAddressByCriteria(String firstName, String lastName, String email, String phoneNumber);
}
