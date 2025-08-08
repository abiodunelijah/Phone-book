package com.coder2client.phonebook;


public class ContactAddressMapper {

    public ContactAddressDTO mapToDTO(ContactAddress contactAddress){
        return ContactAddressDTO.builder()
                .firstName(contactAddress.getFirstName())
                .lastName(contactAddress.getLastName())
                .otherName(contactAddress.getOtherName())
                .email(contactAddress.getEmail())
                .phoneNumber(contactAddress.getPhoneNumber())
                .address(contactAddress.getAddress())
                .build();
    }

    public ContactAddress mapToEntity(ContactAddressDTO contactAddressDTO){
        return ContactAddress.builder()
                .firstName(contactAddressDTO.getFirstName())
                .lastName(contactAddressDTO.getLastName())
                .otherName(contactAddressDTO.getOtherName())
                .email(contactAddressDTO.getEmail())
                .phoneNumber(contactAddressDTO.getPhoneNumber())
                .address(contactAddressDTO.getAddress())
                .build();
    }
}
