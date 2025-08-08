package com.coder2client.phonebook;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactAddressDTO {

    private String firstName;
    private String lastName;
    private String otherName;
    private String email;
    private String phoneNumber;
    private String address;
}
