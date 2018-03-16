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
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * Created by muthukumaran on 3/15/18.
 */
@Repository
public class ContactsRepository {

    private TransportClient client;

    public ContactsRepository() {
        // TODO : insert client as a bean
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void searchContacts(String query) {
        query = "{" +
                "    \"query\": {" +
                "        \"query_string\" : {" +
                "            \"default_field\" : \"name\"," +
                "            \"query\" : \"muthu\"" +
                "        }" +
                "    }" +
                "}";
        QueryBuilder qb = QueryBuilders.queryStringQuery(query);
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch()
                .setIndices("contacts")
                .setQuery(qb);

        SearchResponse response = searchRequestBuilder.execute().actionGet();
        System.out.println("hi3");
        System.out.println(response);
    }

    public Contact createContact(Contact contact) {
        IndexResponse indexResponse = client.prepareIndex("contacts", "name", contact.getName())
                .setSource(new JSONObject()
                        .put("phoneNumber", contact.getPhoneNumber())
                        .put("address", contact.getAddress())
                        .put("emailAddress", contact.getEmail())
                        .toString()).get();

        return getContact(contact.getName());
    }


    public Contact getContact(String name) {
        GetResponse getResponse = client.prepareGet("contacts", "name", name).get();
        Map<String, Object> resultMap = getResponse.getSource();
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
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("contacts")
                .type("name")
                .id(contact.getName())
                .doc(new JSONObject()
                    .put("phoneNumber", contact.getPhoneNumber())
                    .put("address", contact.getAddress())
                    .put("emailAddress", contact.getEmail()).toString());
        try {
            UpdateResponse updateResponse = client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getContact(contact.getName());
    }


    public Contact deleteContact(String name) {
        Contact contact = getContact(name);
        DeleteResponse deleteResponse = client.prepareDelete("contacts", "name", name).get();

        return contact;
    }

    public boolean contactExists(String name) {
        Contact contact = getContact(name);
        return contact != null;
    }

}
