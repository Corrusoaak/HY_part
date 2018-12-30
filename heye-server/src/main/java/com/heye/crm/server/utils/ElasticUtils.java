package com.heye.crm.server.utils;

import com.heye.crm.common.model.HyUser;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Optional;

/**
 * @author : lishuming
 */
public class ElasticUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticUtils.class);

    public static Optional<Client> getClient(String host, int port) {
        try {
            Settings.Builder setting = Settings.builder()
                    .put("client.transport.sniff", false)
                    .put("cluster.name", "heye-elastic")
                    .put("client.transport.ignore_cluster_name", true);

            TransportClient client = new PreBuiltTransportClient(setting.build())
                    .addTransportAddress(new TransportAddress(InetAddress.getByName(host), port));

            return Optional.of(client);
        } catch (Exception e) {
            LOGGER.warn("init elasticsearch failed:", e);
            return Optional.empty();
        }
    }

    public static void insertHyUserIntoElastic(Client client, HyUser hyUser) {
        try {
            String userAddress = hyUser.getUserProvince() +  hyUser.getUserCity() + hyUser.getUserLocation()
                    + hyUser.getUserDetailAddress();
            IndexResponse indexResponse = client.prepareIndex("heye_elastic", "elastic_hy_user",
                    String.valueOf(hyUser.getUserId())).setSource(XContentFactory.jsonBuilder()
                    .startObject()
                    .field("user_province", hyUser.getUserProvince())
                    .field("user_city", hyUser.getUserCity())
                    .field("user_location", hyUser.getUserLocation())
                    .field("user_detail_address", hyUser.getUserDetailAddress())
                    .field("user_address", userAddress)
                    .field("version", 1)
                    .endObject()).get();
        } catch (Exception e) {
            LOGGER.warn("insert into hy_user elastic failed:", e);
            throw new RuntimeException("Insert into elastic failed");
        }
    }
}
