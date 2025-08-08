package com.coder2client.phonebook;


import java.util.List;

public interface ContactAddressService {

    ContactAddressDTO createContact(ContactAddressDTO contactAddressDTO);
    List<ContactAddressDTO> getAllContactAddresses();
    ContactAddressDTO getContactAddress(Long contactId);
}
