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

    public List<List<Contact>> searchContacts(int pageSize, int pagesCount, String query) {
        contactsRepository.searchContacts(query);
        return null;
    }

    public Contact createContact(Contact contact) {
        // TODO: check if already exists using name
        if (contactsRepository.contactExists(contact.getName())) {
            throw new ContactAlreadyExistsException(contact.getName());
        }
        Validator.validateContact(contact);
        return contactsRepository.createContact(contact);
    }

    public Contact getContact(String name) {
        Contact contact = contactsRepository.getContact(name);
        if (contact == null) {
            throw new ContactNotFoundException(name);
        }

        return contactsRepository.getContact(name);
    }

    public Contact updateContact(String name, Contact contact) {
        if (!contactsRepository.contactExists(name)) {
            throw new ContactNotFoundException(name);
        }
        Validator.validateContact(contact);
        return contactsRepository.updateContact(contact);
    }

    public Contact deleteContact(String name) {
        if (!contactsRepository.contactExists(name)) {
            throw new ContactNotFoundException(name);
        }
        return contactsRepository.deleteContact(name);
    }


}
