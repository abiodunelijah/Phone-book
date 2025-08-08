package com.coder2client.phonebook;


public class ContactAddressMapper {

    public static ContactAddressDTO mapToDTO(ContactAddress contactAddress){
        return ContactAddressDTO.builder()
                .id(contactAddress.getId())
                .firstName(contactAddress.getFirstName())
                .lastName(contactAddress.getLastName())
                .otherName(contactAddress.getOtherName())
                .email(contactAddress.getEmail())
                .phoneNumber(contactAddress.getPhoneNumber())
                .address(contactAddress.getAddress())
                .build();
    }

    public static ContactAddress mapToEntity(ContactAddressDTO contactAddressDTO){
        return ContactAddress.builder()
                .id(contactAddressDTO.getId())
                .firstName(contactAddressDTO.getFirstName())
                .lastName(contactAddressDTO.getLastName())
                .otherName(contactAddressDTO.getOtherName())
                .email(contactAddressDTO.getEmail())
                .phoneNumber(contactAddressDTO.getPhoneNumber())
                .address(contactAddressDTO.getAddress())
                .build();
    }
}
