package com.coder2client.phonebook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactAddressRepository extends JpaRepository<ContactAddress, Long> {
}
