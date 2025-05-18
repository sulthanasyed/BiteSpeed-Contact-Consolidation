package com.bitespeed.contact_consolidation.service;

import com.bitespeed.contact_consolidation.dto.ContactResponseDTO;
import com.bitespeed.contact_consolidation.dto.IdentifyResponse;
import com.bitespeed.contact_consolidation.exception.InvalidRequestException;
import com.bitespeed.contact_consolidation.model.Contact;
import com.bitespeed.contact_consolidation.model.LinkPrecedence;
import com.bitespeed.contact_consolidation.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ContactServiceImpl implements IContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public IdentifyResponse identify(String email, String phoneNumber) {
        if ((email == null || email == "") && (phoneNumber == null || phoneNumber == "")) {
            throw new InvalidRequestException("At least one of email or phoneNumber must be provided");
        }
        List<Contact> matchingContacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);
        Contact savedContact;
        Contact primaryContact = null;
        if(!CollectionUtils.isEmpty(matchingContacts)) {
            primaryContact = determinePrimaryContact(matchingContacts);
            updateLinkPrecedence(matchingContacts, primaryContact);
            Contact exactMatch = contactRepository.findByEmailAndPhoneNumber(email, phoneNumber);
            if(exactMatch == null) {
                savedContact = createContact(email, phoneNumber, LinkPrecedence.secondary, primaryContact.getId());
                matchingContacts.add(savedContact);
            }
        } else {
            savedContact = createContact(email, phoneNumber, LinkPrecedence.primary, 0);
            primaryContact = savedContact;
            matchingContacts.add(savedContact);
        }
        return generateResponse(primaryContact, matchingContacts);
    }

    private Contact determinePrimaryContact(List<Contact> contacts) {
        return contacts.stream()
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElseThrow(() -> new IllegalStateException("No contacts found"));
    }

    private void updateLinkPrecedence(List<Contact> contacts, Contact primaryContact) {
        for (Contact contact : contacts) {
            if (contact.getId() != primaryContact.getId()) {
                contact.setLinkPrecedence(LinkPrecedence.secondary);
                contact.setLinkedId(primaryContact.getId());
            } else {
                contact.setLinkPrecedence(LinkPrecedence.primary);
                contact.setLinkedId(0);
            }
        }
        contactRepository.saveAll(contacts);
    }

    private Contact createContact(String email, String phoneNumber, LinkPrecedence precedence, long linkedId) {
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhoneNumber(phoneNumber);
        contact.setLinkPrecedence(precedence);
        contact.setLinkedId(linkedId);
        return contactRepository.save(contact);
    }

    private IdentifyResponse generateResponse(Contact primaryContact, List<Contact> allRelatedContacts) {
        Set<String> emails = new LinkedHashSet<>();
        Set<String> phoneNumbers = new LinkedHashSet<>();
        List<Long> secondaryIds = new ArrayList<>();

        for (Contact contact : allRelatedContacts) {
            if (contact.getEmail() != null)
                emails.add(contact.getEmail());
            if (contact.getPhoneNumber() != null)
                phoneNumbers.add(contact.getPhoneNumber());
            if (contact.getId() != primaryContact.getId()) {
                secondaryIds.add(contact.getId());
            }
        }

        ContactResponseDTO contactResponseDTO = new ContactResponseDTO(
                primaryContact.getId(),
                new ArrayList<>(emails),
                new ArrayList<>(phoneNumbers),
                secondaryIds
        );

        return new IdentifyResponse(contactResponseDTO);
    }

}
