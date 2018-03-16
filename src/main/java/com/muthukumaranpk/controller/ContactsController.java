package com.muthukumaranpk.controller;

import com.muthukumaranpk.entity.Contact;
import com.muthukumaranpk.exception.ContactAlreadyExistsException;
import com.muthukumaranpk.exception.ContactNotFoundException;
import com.muthukumaranpk.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by muthukumaran on 3/15/18.
 */

@RestController
@RequestMapping("/contact")
public class ContactsController {

    @Autowired
    private ContactsService contactsService;

    //GET /contact?pageSize={}&page={}&query={}
    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<List<Contact>> getContacts(@RequestParam(value = "pageSize", required = false, defaultValue = "-1") int pageSize,
                                      @RequestParam(value = "page", required = false, defaultValue = "1") int pagesCount,
                                      @RequestParam(value = "query", required = false, defaultValue = "") String query) {
//        System.out.println("PageSize" + pageSize + "Page" + page + "Query" + query);
        return contactsService.searchContacts(pageSize, pagesCount, query);
    }


    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Contact createContact(@RequestBody Contact contact) {
        return contactsService.createContact(contact);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.GET,
                produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Contact getContact(@PathVariable("name") String name) {
        return contactsService.getContact(name);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Contact updateContact(@PathVariable("name") String name,
            @RequestBody Contact contact) {
        return contactsService.updateContact(name, contact);
    }


    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Contact deleteContact(@PathVariable("name") String name) {
        return contactsService.deleteContact(name);
    }


    @ExceptionHandler(ContactNotFoundException.class)
    public void handleContactNotFoundException(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }


    @ExceptionHandler(ContactAlreadyExistsException.class)
    public void handleContactAlreadyExistsException(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.CONFLICT.value());
    }
}
