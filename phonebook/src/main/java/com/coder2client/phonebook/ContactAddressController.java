package com.coder2client.phonebook;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactAddressController {

    private final ContactAddressService contactAddressService;

    public ContactAddressController(ContactAddressService contactAddressService) {
        this.contactAddressService = contactAddressService;
    }

    @PostMapping("/create-contact")
    public ResponseEntity<ContactAddressDTO> createContact(@Valid @RequestBody ContactAddressDTO contactAddressDTO){
        ContactAddressDTO contact = contactAddressService.createContact(contactAddressDTO);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @GetMapping("/all-contacts")
    public ResponseEntity<List<ContactAddressDTO>> getAllContactAddresses(){
        List<ContactAddressDTO> allContactAddresses = contactAddressService.getAllContactAddresses();
        return new ResponseEntity<>(allContactAddresses, HttpStatus.OK);
    }

    @GetMapping("/contact-address/{id}")
    public ResponseEntity<ContactAddressDTO> getContactAddress(@PathVariable Long id){
        ContactAddressDTO contactAddress = contactAddressService.getContactAddress(id);
        return new ResponseEntity<>(contactAddress, HttpStatus.OK);
    }

    @PatchMapping("/update-contact/{id}")
    public ResponseEntity<ContactAddressDTO> updateContactAddress(@PathVariable Long id, @Valid @RequestBody ContactAddressDTO contactAddressDTO){
        contactAddressDTO.setId(id);

        ContactAddressDTO updateContactAddress = contactAddressService.updateContactAddress(contactAddressDTO);
        return new ResponseEntity<>(updateContactAddress, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-contact/{id}")
    public ResponseEntity<String> deleteContactAddress(@PathVariable Long id){

        contactAddressService.deleteContactAddress(id);

        return new ResponseEntity<>("Successfully deleted.", HttpStatus.OK);
    }

    @GetMapping("/search-contact")
    public ResponseEntity<List<ContactAddressDTO>> searchContactAddress(@RequestParam(required = false) String firstName,
                                                                        @RequestParam(required = false) String lastName,
                                                                        @RequestParam(required = false) String email,
                                                                        @RequestParam(required = false) String phoneNumber){

        List<ContactAddressDTO> contactAddressByCriteria = contactAddressService.findContactAddressByCriteria(firstName, lastName, email, phoneNumber);
        return new ResponseEntity<>(contactAddressByCriteria, HttpStatus.FOUND);
    }
}
