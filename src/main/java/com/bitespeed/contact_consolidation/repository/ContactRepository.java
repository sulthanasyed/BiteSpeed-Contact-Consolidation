package com.bitespeed.contact_consolidation.repository;

import com.bitespeed.contact_consolidation.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
