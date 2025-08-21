package com.coder2client.phonebook;

import com.coder2client.phonebook.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactAddressRepository extends JpaRepository<ContactAddress, Long> {
    
    List<ContactAddress> findByUser(User user);
    
    List<ContactAddress> findByUserAndFirstNameContainingIgnoreCase(User user, String firstName);
    
    List<ContactAddress> findByUserAndLastNameContainingIgnoreCase(User user, String lastName);
    
    List<ContactAddress> findByUserAndEmailContainingIgnoreCase(User user, String email);
    
    List<ContactAddress> findByUserAndPhoneNumberContaining(User user, String phoneNumber);
}
