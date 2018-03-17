package com.muthukumaranpk.repository;

import com.muthukumaranpk.entity.Contact;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by muthukumaran on 3/15/18.
 */
@Repository
public class ContactsRepository {

    @Autowired
    private TransportClient client;
//    private static final String HOST_NAME = "localhost";
//    private static final int PORT_NUMBER = 9300;
//    private static final String CLUSTER_NAME = "elasticsearch";

//    public ContactsRepository() {
//        final Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();
//        try {
//            client = new PreBuiltTransportClient(settings)
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_NAME), PORT_NUMBER));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//    }

    public List<Contact> searchContacts(int from, int size, String query) {
        final QueryBuilder qb = QueryBuilders.queryStringQuery(query);
        final SearchRequestBuilder searchRequestBuilder = client.prepareSearch()
                .setIndices("contacts")
                .setQuery(qb)
                .setFrom(from)
                .setSize(size);

        SearchResponse response = null;
        try {
            response = searchRequestBuilder.execute().get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        final SearchHit[] searchHits = response.getHits().getHits();
        final List<Contact> resultList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            final String name = searchHit.getId();
            final Map<String, Object> map = searchHit.getSourceAsMap();
            final long phoneNumber = Long.valueOf(map.get("phoneNumber").toString());
            final String address = map.get("address").toString();
            final String email = map.get("emailAddress").toString();

            final Contact newContact = new Contact();
            newContact.setName(name);
            newContact.setEmail(email);
            newContact.setAddress(address);
            newContact.setPhoneNumber(phoneNumber);

            resultList.add(newContact);
        }
        return resultList;
    }

    public Contact createContact(Contact contact) {
        final IndexResponse indexResponse = client.prepareIndex("contacts", "name", contact.getName())
                .setSource(new JSONObject()
                        .put("phoneNumber", contact.getPhoneNumber())
                        .put("address", contact.getAddress())
                        .put("emailAddress", contact.getEmail())
                        .toString()).get();

        return getContact(contact.getName());
    }


    public Contact getContact(String name) {
        final GetResponse getResponse = client.prepareGet("contacts", "name", name).get();
        final Map<String, Object> resultMap = getResponse.getSource();
        Contact contact = null;
        if (resultMap != null) {
            contact = new Contact();
            contact.setName(name);
            contact.setAddress(resultMap.get("address").toString());
            contact.setEmail(resultMap.get("emailAddress").toString());
            contact.setPhoneNumber(Long.valueOf(resultMap.get("phoneNumber").toString()));
        }
        return contact;
    }

    public Contact updateContact(Contact contact) {
        final UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("contacts")
                .type("name")
                .id(contact.getName())
                .doc(new JSONObject()
                    .put("phoneNumber", contact.getPhoneNumber())
                    .put("address", contact.getAddress())
                    .put("emailAddress", contact.getEmail()).toString());
        try {
            final UpdateResponse updateResponse = client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getContact(contact.getName());
    }


    public Contact deleteContact(String name) {
        final Contact contact = getContact(name);
        final DeleteResponse deleteResponse = client.prepareDelete("contacts", "name", name).get();

        return contact;
    }

    public boolean contactExists(String name) {
        final Contact contact = getContact(name);
        return contact != null;
    }

}
