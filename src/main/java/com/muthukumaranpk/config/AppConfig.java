package com.muthukumaranpk.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by muthukumaran on 3/17/18.
 */
@Configuration
public class AppConfig {
    private static final String HOST_NAME = "localhost";
    private static final int PORT_NUMBER = 9300;
    private static final String CLUSTER_NAME = "elasticsearch";

    @Bean
    public TransportClient toolFactory() {
        final Settings settings = Settings.builder().put("cluster.name", CLUSTER_NAME).build();
        TransportClient client = null;
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_NAME), PORT_NUMBER));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }
}
