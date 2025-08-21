package com.coder2client.phonebook;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ContactAddressDTO> createContact(@Valid @RequestBody ContactAddressDTO contactAddressDTO,
                                                          Authentication authentication) {
        ContactAddressDTO contact = contactAddressService.createContact(contactAddressDTO, authentication);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

    @GetMapping("/all-contacts")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ContactAddressDTO>> getAllContactAddresses(Authentication authentication) {
        List<ContactAddressDTO> allContactAddresses = contactAddressService.getAllContactAddresses(authentication);
        return new ResponseEntity<>(allContactAddresses, HttpStatus.OK);
    }

    @GetMapping("/admin/all-contacts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactAddressDTO>> getAllContactAddressesAdmin() {
        List<ContactAddressDTO> allContactAddresses = contactAddressService.getAllContactAddressesAdmin();
        return new ResponseEntity<>(allContactAddresses, HttpStatus.OK);
    }

    @GetMapping("/contact-address/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ContactAddressDTO> getContactAddress(@PathVariable Long id,
                                                             Authentication authentication) {
        ContactAddressDTO contactAddress = contactAddressService.getContactAddress(id, authentication);
        return new ResponseEntity<>(contactAddress, HttpStatus.OK);
    }

    @PatchMapping("/update-contact/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ContactAddressDTO> updateContactAddress(@PathVariable Long id,
                                                                @Valid @RequestBody ContactAddressDTO contactAddressDTO,
                                                                Authentication authentication) {
        contactAddressDTO.setId(id);
        ContactAddressDTO updateContactAddress = contactAddressService.updateContactAddress(contactAddressDTO, authentication);
        return new ResponseEntity<>(updateContactAddress, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete-contact/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteContactAddress(@PathVariable Long id,
                                                      Authentication authentication) {
        contactAddressService.deleteContactAddress(id, authentication);
        return new ResponseEntity<>("Successfully deleted.", HttpStatus.OK);
    }

    @GetMapping("/search-contact")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ContactAddressDTO>> searchContactAddress(@RequestParam(required = false) String firstName,
                                                                       @RequestParam(required = false) String lastName,
                                                                       @RequestParam(required = false) String email,
                                                                       @RequestParam(required = false) String phoneNumber,
                                                                       Authentication authentication) {
        List<ContactAddressDTO> contactAddressByCriteria = contactAddressService
                .findContactAddressByCriteria(firstName, lastName, email, phoneNumber, authentication);
        return new ResponseEntity<>(contactAddressByCriteria, HttpStatus.FOUND);
    }
}
