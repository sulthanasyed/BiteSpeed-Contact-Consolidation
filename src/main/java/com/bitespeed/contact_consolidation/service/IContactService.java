package com.bitespeed.contact_consolidation.service;

import com.bitespeed.contact_consolidation.dto.IdentifyResponse;

public interface IContactService {
    IdentifyResponse identify(String email, String phoneNumber);
}
