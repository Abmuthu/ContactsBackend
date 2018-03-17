package com.muthukumaranpk.service;

import com.muthukumaranpk.entity.Contact;
import com.muthukumaranpk.exception.ContactAlreadyExistsException;
import com.muthukumaranpk.exception.ContactNotFoundException;
import com.muthukumaranpk.repository.ContactsRepository;
import com.muthukumaranpk.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by muthukumaran on 3/15/18.
 */
@Service
public class ContactsService {
    @Autowired
    ContactsRepository contactsRepository;

    public List<Contact> searchContacts(int pageSize, int page, String query) {
        final int from = (page - 1) * pageSize; // calculate
        return contactsRepository.searchContacts(from, pageSize, query);
    }

    public Contact createContact(Contact contact) {
        Validator.validateContact(contact);
        if (contactsRepository.contactExists(contact.getName())) {
            throw new ContactAlreadyExistsException(contact.getName());
        }
        return contactsRepository.createContact(contact);
    }

    public Contact getContact(String name) {
        Validator.validateName(name);
        final Contact contact = contactsRepository.getContact(name);
        if (contact == null) {
            throw new ContactNotFoundException(name);
        }

        return contactsRepository.getContact(name);
    }

    public Contact updateContact(String name, Contact contact) {
        Validator.validateContact(contact);
        if (!contactsRepository.contactExists(name)) {
            throw new ContactNotFoundException(name);
        }
        return contactsRepository.updateContact(contact);
    }

    public Contact deleteContact(String name) {
        Validator.validateName(name);
        if (!contactsRepository.contactExists(name)) {
            throw new ContactNotFoundException(name);
        }
        return contactsRepository.deleteContact(name);
    }


}
