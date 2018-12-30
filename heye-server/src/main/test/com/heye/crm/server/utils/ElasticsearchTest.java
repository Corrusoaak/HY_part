package com.heye.crm.server.utils;

import com.google.common.io.Files;
import com.heye.crm.common.model.HyUser;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author : lishuming
 */
public class ElasticsearchTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchTest.class);

    @Before
    public void setup() {
    }

    @Test
    public void testElastic1() {
        try {
            Client client = ElasticUtils.getClient("47.106.80.130", 9300).get();

            XContentBuilder source = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("user", "lalala")
                    .field("postDate", new Date())
                    .field("message", "trying to out ElasticSearch")
                    .endObject();

            IndexResponse response = client.prepareIndex("test-1", "test-1", "1").setSource(source).get();
            String index = response.getIndex();
            String type = response.getType();
            String id = response.getId();
            long version = response.getVersion();

            System.out.println(index + " : " + type + ": " + id + ": " + version + ": ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testElastic2() {
        try {
            Client client = ElasticUtils.getClient("47.106.80.130", 9300).get();

            IndexResponse indexResponse = client.prepareIndex("product_index", "product", "1").setSource(XContentFactory.jsonBuilder()
                    .startObject()
                    .field("product_name", "飞利浦电动牙刷 HX6700-1")
                    .field("product_desc", "前 1000 名赠刷头，6 月 1 日 0 点火爆开抢，618 开门红巅峰 48 小时")
                    .field("price", 399.00)
                    .field("created_date_time", new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSZ").format(new Date()))
                    .field("last_modified_date_time", new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSZ").format(new Date()))
                    .field("version", 1)
                    .endObject()).get();

            GetResponse getResponse = client
                    .prepareGet("product_index", "product", "1")
                    .get();
            LOGGER.info("query1 result:" + getResponse.getSourceAsString());

            SearchResponse searchResponse = client.prepareSearch("product_index").setTypes("product")
                    .setQuery(QueryBuilders.matchQuery("product_name", "飞利浦"))
                    .get();

            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                LOGGER.info("query2 result：" + searchHit.getSourceAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testElasticAdminUser() {
        Client client = ElasticUtils.getClient("47.106.80.130", 9300).get();

        try {
            HyUser hyUser = new HyUser();
            IndexResponse indexResponse = client.prepareIndex("heye_elastic", "elastic_hy_user", "1").setSource(XContentFactory.jsonBuilder()
                    .startObject()
                    .field("product_name", "飞利浦电动牙刷 HX6700-1")
                    .field("product_desc", "前 1000 名赠刷头，6 月 1 日 0 点火爆开抢，618 开门红巅峰 48 小时")
                    .field("price", 399.00)
                    .field("created_date_time", new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSZ").format(new Date()))
                    .field("last_modified_date_time", new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSSZ").format(new Date()))
                    .field("version", 1)
                    .endObject()).get();

            GetResponse getResponse = client
                    .prepareGet("product_index", "product", "1")
                    .get();
            LOGGER.info("query1 result:" + getResponse.getSourceAsString());

            SearchResponse searchResponse = client.prepareSearch("product_index").setTypes("product")
                    .setQuery(QueryBuilders.matchQuery("product_name", "飞利浦"))
                    .get();

            for (SearchHit searchHit : searchResponse.getHits().getHits()) {
                LOGGER.info("query2 result：" + searchHit.getSourceAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertIntoHyUserElastic() {
        Client client = ElasticUtils.getClient("47.106.80.130", 9300).get();

        try {
            File hyUserFile = new File("../heye-doc/ddl/heye_user_datas.sql");

            List<String> lines = Files.readLines(hyUserFile, Charset.defaultCharset());
            HyUser hyUser = new HyUser();
            for (String line : lines) {
                System.out.println(line);
                String[] values = line.split("values");
                if (values.length == 2) {
                    String[] v = values[1].split(",");
                    hyUser.setUserId(Long.parseLong(v[0].substring(1, v[0].length())));
                    hyUser.setUserProvince(v[9]);
                    hyUser.setUserCity(v[10]);
                    hyUser.setUserLocation(v[11]);
                    hyUser.setUserDetailAddress(v[12]);
                }
                ElasticUtils.insertHyUserIntoElastic(client, hyUser);
                break;
            }
           // ElasticUtils.insertHyUserIntoElastic(client, );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
