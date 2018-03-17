package com.muthukumaranpk;

import com.muthukumaranpk.entity.Contact;
import com.muthukumaranpk.exception.ContactAlreadyExistsException;
import com.muthukumaranpk.exception.ContactNotFoundException;
import com.muthukumaranpk.exception.InvalidContactFieldException;
import com.muthukumaranpk.repository.ContactsRepository;
import com.muthukumaranpk.service.ContactsService;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactsBackendApplicationTests {

	@Autowired
	private ContactsService contactsService;

	private static Contact contactWithInvalidName;
	private static Contact contactWithInvalidPhoneNumber;
	private static Contact contactWithInvalidAddress;
	private static Contact contactWithInvalidEmail;
	private static Contact contactWithMissingName;
	private static Contact contactWithMissingPhoneNumber;
	private static Contact contactWithMissingAddress;
	private static Contact contactWithMissingEmail;


	@BeforeClass
	public static void setup() {
		initializeInvalidContacts();
		initializeMissingFieldContacts();
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest1() {
		contactsService.createContact(contactWithInvalidName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest2() {
		contactsService.createContact(contactWithInvalidPhoneNumber);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest3() {
		contactsService.createContact(contactWithInvalidAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest4() {
		contactsService.createContact(contactWithInvalidEmail);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest1() {
		contactsService.createContact(contactWithMissingName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest2() {
		contactsService.createContact(contactWithMissingPhoneNumber);

	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest3() {
		contactsService.createContact(contactWithMissingAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest4() {
		contactsService.createContact(contactWithMissingEmail);
	}

	@Test
	public void createValidContact() {
		Contact validContact = new Contact();
		validContact.setName("name1");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#4, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.createContact(validContact);
		assertEquals(validContact, contactsService.getContact(validContact.getName()));
		contactsService.deleteContact(validContact.getName());
	}

	@Test(expected = ContactAlreadyExistsException.class)
	public void createDuplicateContact() {
		Contact validContact = new Contact();
		validContact.setName("name2");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#4, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.createContact(validContact);
		contactsService.createContact(validContact);
		contactsService.deleteContact(validContact.getName());
	}

	@Test(expected = ContactNotFoundException.class)
	public void getNotExistingContact() {
		contactsService.getContact("name1000");
	}

	@Test
	public void getValidContact() {
		Contact validContact = new Contact();
		validContact.setName("name3");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#4, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.createContact(validContact);
		assertEquals(validContact, contactsService.getContact(validContact.getName()));
		contactsService.deleteContact(validContact.getName());
	}


	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest1() {
		contactsService.updateContact(contactWithInvalidName.getName(), contactWithInvalidName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest2() {
		contactsService.updateContact(contactWithInvalidPhoneNumber.getName(), contactWithInvalidPhoneNumber);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest3() {
		contactsService.updateContact(contactWithInvalidAddress.getName(), contactWithInvalidAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest4() {
		contactsService.updateContact(contactWithInvalidEmail.getName(), contactWithInvalidEmail);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest1() {
		contactsService.updateContact(contactWithMissingName.getName(), contactWithMissingName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest2() {
		contactsService.updateContact(contactWithMissingPhoneNumber.getName(), contactWithMissingPhoneNumber);

	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest3() {
		contactsService.updateContact(contactWithInvalidAddress.getName(), contactWithMissingAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest4() {
		contactsService.updateContact(contactWithMissingEmail.getName(), contactWithMissingEmail);
	}

	@Test(expected = ContactNotFoundException.class)
	public void updateNotExistingContact() {
		Contact validContact = new Contact();
		validContact.setName("name1000");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#4, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.updateContact(validContact.getName(), validContact);
	}

	@Test
	public void updateValidContact() {
		Contact validContact = new Contact();
		validContact.setName("name4");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#4, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.createContact(validContact);
		Contact updatedContact = new Contact();
		updatedContact.setName("name4");
		updatedContact.setPhoneNumber(2222222222L);
		updatedContact.setAddress("#4, east village, NY");
		updatedContact.setEmail("email1@email.com");
		contactsService.updateContact(validContact.getName(), updatedContact);
		assertEquals(updatedContact, contactsService.getContact(validContact.getName()));
		contactsService.deleteContact(validContact.getName());
	}

	@Test(expected = ContactNotFoundException.class)
	public void deleteNonExistingContact() {
		contactsService.deleteContact("name1000");
	}

	@Test(expected = ContactNotFoundException.class)
	public void deleteValidContact() {
		Contact validContact = new Contact();
		validContact.setName("name5");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#4, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.createContact(validContact);
		contactsService.deleteContact(validContact.getName());
		contactsService.getContact(validContact.getName());
	}

	private static void initializeInvalidContacts() {
		contactWithInvalidName = new Contact();
		contactWithInvalidName.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		contactWithInvalidName.setPhoneNumber(1010101010L);
		contactWithInvalidName.setAddress("bbbbb");
		contactWithInvalidName.setEmail("abc@abc.com");

		contactWithInvalidPhoneNumber = new Contact();
		contactWithInvalidPhoneNumber.setName("aaaaa");
		contactWithInvalidPhoneNumber.setPhoneNumber(101010L);
		contactWithInvalidPhoneNumber.setAddress("bbbbb");
		contactWithInvalidPhoneNumber.setEmail("abc@abc.com");

		contactWithInvalidAddress = new Contact();
		contactWithInvalidAddress.setName("aaaaa");
		contactWithInvalidAddress.setPhoneNumber(1010101010L);
		contactWithInvalidAddress.setAddress("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		contactWithInvalidAddress.setEmail("abc@abc.com");

		contactWithInvalidEmail = new Contact();
		contactWithInvalidEmail.setName("aaaaa");
		contactWithInvalidEmail.setPhoneNumber(1010101010L);
		contactWithInvalidEmail.setAddress("bbbbb");
		contactWithInvalidEmail.setEmail("abc");
	}

	private static void initializeMissingFieldContacts() {
		contactWithMissingName = new Contact();
		contactWithMissingName.setPhoneNumber(1010101010L);
		contactWithMissingName.setAddress("bbbbb");
		contactWithMissingName.setEmail("abc@abc.com");

		contactWithMissingPhoneNumber = new Contact();
		contactWithMissingPhoneNumber.setName("aaaaa");
		contactWithMissingPhoneNumber.setAddress("bbbbb");
		contactWithMissingPhoneNumber.setEmail("abc@abc.com");

		contactWithMissingAddress = new Contact();
		contactWithMissingAddress.setName("aaaaa");
		contactWithMissingAddress.setPhoneNumber(1010101010L);
		contactWithMissingAddress.setEmail("abc@abc.com");

		contactWithMissingEmail = new Contact();
		contactWithMissingEmail.setName("aaaaa");
		contactWithMissingEmail.setPhoneNumber(1010101010L);
		contactWithMissingEmail.setAddress("bbbbb");
	}

}