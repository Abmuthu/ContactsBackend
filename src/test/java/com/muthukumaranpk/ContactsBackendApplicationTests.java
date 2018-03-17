package com.muthukumaranpk;

import com.muthukumaranpk.entity.Contact;
import com.muthukumaranpk.exception.ContactAlreadyExistsException;
import com.muthukumaranpk.exception.ContactNotFoundException;
import com.muthukumaranpk.exception.InvalidContactFieldException;
import com.muthukumaranpk.repository.ContactsRepository;
import com.muthukumaranpk.service.ContactsService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


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

	private static Contact validContact1;
	private static Contact validContact2;

	@BeforeClass
	public static void setup() {
		initializeInvalidContacts();
		initializeMissingFieldContacts();
		initializeValidContacts();
	}

	@Before
	public void createValidContacts() {
		contactsService.createContact(validContact1);
		contactsService.createContact(validContact2);
	}

	@After
	public void deleteValidContacts() {
		contactsService.deleteContact(validContact1.getName());
		contactsService.deleteContact(validContact2.getName());
	}


	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest_invalid_name() {
		contactsService.createContact(contactWithInvalidName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest_invalid_phonenumber() {
		contactsService.createContact(contactWithInvalidPhoneNumber);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest_invalid_address() {
		contactsService.createContact(contactWithInvalidAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactInvalidTest_invalid_email() {
		contactsService.createContact(contactWithInvalidEmail);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest_missing_name() {
		contactsService.createContact(contactWithMissingName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest_missing_phonenumber() {
		contactsService.createContact(contactWithMissingPhoneNumber);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest_missing_address() {
		contactsService.createContact(contactWithMissingAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void createContactMissingFieldTest_missing_email() {
		contactsService.createContact(contactWithMissingEmail);
	}


	@Test
	public void createValidContact() {
		Contact validContact = new Contact();
		validContact.setName("name3");
		validContact.setPhoneNumber(3333333333L);
		validContact.setAddress("#3, east village, NY");
		validContact.setEmail("three@three.com");
		contactsService.createContact(validContact);
		assertEquals(validContact, contactsService.getContact(validContact.getName()));
		contactsService.deleteContact(validContact.getName());
	}

	@Test(expected = ContactAlreadyExistsException.class)
	public void createDuplicateContact() {
		contactsService.createContact(validContact2);
	}

	@Test(expected = ContactNotFoundException.class)
	public void getNotExistingContact() {
		contactsService.getContact("name1000");
	}

	@Test
	public void getValidContact() {
		assertEquals(validContact1, contactsService.getContact(validContact1.getName()));
	}


	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest_invalid_name() {
		contactsService.updateContact(contactWithInvalidName.getName(), contactWithInvalidName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest_invalid_phonenumber() {
		contactsService.updateContact(contactWithInvalidPhoneNumber.getName(), contactWithInvalidPhoneNumber);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest_invalid_address() {
		contactsService.updateContact(contactWithInvalidAddress.getName(), contactWithInvalidAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactInvalidTest_invalid_email() {
		contactsService.updateContact(contactWithInvalidEmail.getName(), contactWithInvalidEmail);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest_missing_name() {
		contactsService.updateContact(contactWithMissingName.getName(), contactWithMissingName);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest_missing_phonenumber() {
		contactsService.updateContact(contactWithMissingPhoneNumber.getName(), contactWithMissingPhoneNumber);

	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest_missing_address() {
		contactsService.updateContact(contactWithInvalidAddress.getName(), contactWithMissingAddress);
	}

	@Test(expected = InvalidContactFieldException.class)
	public void updateContactMissingFieldTest_missing_email() {
		contactsService.updateContact(contactWithMissingEmail.getName(), contactWithMissingEmail);
	}

	@Test(expected = ContactNotFoundException.class)
	public void updateNotExistingContact() {
		Contact validContact = new Contact();
		validContact.setName("name1000");
		validContact.setPhoneNumber(1111111111L);
		validContact.setAddress("#1000, east village, NY");
		validContact.setEmail("email1@email.com");
		contactsService.updateContact(validContact.getName(), validContact);
	}

	@Test
	public void updateValidContact() {
		Contact updatedContact = new Contact();
		updatedContact.setName("name1");
		updatedContact.setPhoneNumber(6666666666L);
		updatedContact.setAddress("#4, east village, NY");
		updatedContact.setEmail("four@four.com");
		contactsService.updateContact(validContact1.getName(), updatedContact);
		assertEquals(updatedContact, contactsService.getContact(validContact1.getName()));
	}

	@Test(expected = ContactNotFoundException.class)
	public void deleteNonExistingContact() {
		contactsService.deleteContact("name1000");
	}

	@Test(expected = ContactNotFoundException.class)
	public void deleteValidContact() {
		Contact validContact = new Contact();
		validContact.setName("name5");
		validContact.setPhoneNumber(5555555555L);
		validContact.setAddress("#5, east village, NY");
		validContact.setEmail("five@five.com");
		contactsService.createContact(validContact);
		contactsService.deleteContact(validContact.getName());
		contactsService.getContact(validContact.getName());
	}

	@Test
	public void searchContactTest() {
		List<Contact> contactList = contactsService.searchContacts(10, 1, "west");
		assertTrue(contactList.size() == 1);
		assertEquals(validContact2, contactList.get(0));
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

	private static void initializeValidContacts() {
		validContact1 = new Contact();
		validContact1.setName("name1");
		validContact1.setPhoneNumber(1111111111L);
		validContact1.setAddress("#1, east village, NY");
		validContact1.setEmail("one@one.com");

		validContact2 = new Contact();
		validContact2.setName("name2");
		validContact2.setPhoneNumber(2222222222L);
		validContact2.setAddress("#2, west village, NY");
		validContact2.setEmail("two@two.com");
	}

}
