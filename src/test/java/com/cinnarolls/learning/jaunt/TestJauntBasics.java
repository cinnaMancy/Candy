package com.cinnarolls.learning.jaunt;

import com.jaunt.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class TestJauntBasics {

    @Test
    public void scrapesJSONandXMLandHTTP() {
        final String HTTP_PAGE = "https://www.booking.com";
        final String XML_PAGE = "https://rule34.xxx/index.php?page=dapi&s=post&q=index";
        final String JSON_PAGE = "https://swapi.dev/api/people";
        try {
            UserAgent userAgent = new UserAgent();

            //  HTTP and XML are parsed much in the same way
            userAgent.visit(HTTP_PAGE);
            log.info("Data from an HTTP Request:");
            //  Have to use userAgent.doc for HTML/XML
            log.info(userAgent.doc.findFirst("<img class=bui-card__image>").getAt("src"));
            assertNotNull(userAgent.doc);

            userAgent.visit(XML_PAGE);
            log.info("Data from an XML Request:");
            log.info(userAgent.doc.findFirst("<post>").getAt("file_url"));
            assertNotNull(userAgent.doc);

            userAgent.visit(JSON_PAGE);
            //  userAgent.sendGET() also works for JSON
            log.info("Data from an JSON Request:");
            //  Have to use userAgent.json for JSON
            log.info(userAgent.json.findFirst("name").toString());
            assertNotNull(userAgent.json);

        } catch (JauntException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void scrapesWebContent() {
        final String E621_API = "http://www.e621.net/posts.json";
        try {
            UserAgent userAgent = new UserAgent();

            userAgent.sendGET(E621_API);
            JNode jNode = userAgent.json.findFirst("posts").get(0);

            log.info("You can easily scape the web with Jaunt!");
            log.info("For example, the latest post on e621 is:");
            log.info(jNode.toString());
            assertNotNull(jNode);
        } catch (JauntException e) {
            e.printStackTrace();
        }
    }
}
