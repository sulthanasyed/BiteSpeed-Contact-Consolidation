package com.bitespeed.contact_consolidation.controller;

import com.bitespeed.contact_consolidation.dto.IdentifyRequest;
import com.bitespeed.contact_consolidation.dto.IdentifyResponse;
import com.bitespeed.contact_consolidation.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/identify")
public class ContactController {

    @Autowired
    IContactService contactService;

    @PostMapping
    public ResponseEntity<IdentifyResponse> identifyContact(@RequestBody IdentifyRequest request) {
        IdentifyResponse response = contactService.identify(request.getEmail(), request.getPhoneNumber());
        return ResponseEntity.ok(response);
    }
}
