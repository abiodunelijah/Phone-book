package com.coder2client.phonebook;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_addresses")
public class ContactAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Size(max = 50, message = "Other name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s'-]*$", message = "Other name can only contain letters, spaces, hyphens, and apostrophes")
    @Column(name = "other_name", length = 50)
    private String otherName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9\\s()-]{10,20}$", message = "Please provide a valid phone number (10-20 digits, may include +, spaces, hyphens, and parentheses)")
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
