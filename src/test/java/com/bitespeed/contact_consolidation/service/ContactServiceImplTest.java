package com.bitespeed.contact_consolidation.service;

import com.bitespeed.contact_consolidation.dto.IdentifyResponse;
import com.bitespeed.contact_consolidation.model.Contact;
import com.bitespeed.contact_consolidation.model.LinkPrecedence;
import com.bitespeed.contact_consolidation.repository.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class ContactServiceImplTest {

    @Mock
    private ContactRepository  contactRepository;

    @InjectMocks
    private ContactServiceImpl contactService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIdentify_WhenNoMatches_CreatesPrimaryContact(){

        String email = "ram@gmail.com";
        String phoneNumber = "987654321";

        when(contactRepository.findByEmailOrPhoneNumber(email,phoneNumber))
                .thenReturn(new ArrayList<>());

        Contact newContact = new Contact();
        newContact.setId(1);
        newContact.setPhoneNumber(phoneNumber);
        newContact.setEmail(email);
        newContact.setLinkPrecedence(LinkPrecedence.primary);
        newContact.setLinkedId(0);
        newContact.setCreatedAt(LocalDateTime.now());
        newContact.setUpdatedAt(LocalDateTime.now());
        newContact.setDeletedAt(null);

        when(contactRepository.save(any(Contact.class))).thenReturn(newContact);

        IdentifyResponse response = contactService.identify(email,phoneNumber);

        assertNotNull(response);
        assertNotNull(response.getContact());
        assertEquals(1,response.getContact().getPrimaryContactId());
        assertTrue(response.getContact().getEmails().contains(email));
        assertTrue(response.getContact().getPhoneNumbers().contains(phoneNumber));
        assertTrue(response.getContact().getSecondaryContactIds().isEmpty());

    }


    @Test
    void  testIdentify_WhenContactMatches_SavesAsSecondaryContact(){

        String email = "ram@gmail.com";
        String phoneNumber = "987654321";
        LocalDateTime now = LocalDateTime.now();

        Contact oldContact =new Contact();
        oldContact.setId(1);
        oldContact.setPhoneNumber(null);
        oldContact.setEmail(email);
        oldContact.setLinkPrecedence(LinkPrecedence.primary);
        oldContact.setLinkedId(0);
        oldContact.setCreatedAt(now.minusDays(1));
        oldContact.setUpdatedAt(now.minusDays(1));
        oldContact.setDeletedAt(null);

        List<Contact> matchingContacts = new ArrayList<>();
        matchingContacts.add(oldContact);

        when(contactRepository.findByEmailOrPhoneNumber(email,phoneNumber)).thenReturn(matchingContacts);
        when(contactRepository.findByEmailAndPhoneNumber(email,phoneNumber)).thenReturn(null);

        Contact newContact = new Contact();
        newContact.setId(2);
        newContact.setPhoneNumber(phoneNumber);
        newContact.setEmail(email);
        newContact.setLinkPrecedence(LinkPrecedence.secondary);
        newContact.setLinkedId(1);
        newContact.setCreatedAt(LocalDateTime.now());
        newContact.setUpdatedAt(LocalDateTime.now());
        newContact.setDeletedAt(null);

        when(contactRepository.save(any(Contact.class))).thenReturn(newContact);

        IdentifyResponse response = contactService.identify(email,phoneNumber);

        assertNotNull(response);
        assertEquals(1,response.getContact().getPrimaryContactId());
        assertTrue(response.getContact().getEmails().contains(email));
        assertTrue(response.getContact().getPhoneNumbers().contains(phoneNumber));
        assertTrue(response.getContact().getSecondaryContactIds().contains(2l));

    }
}
