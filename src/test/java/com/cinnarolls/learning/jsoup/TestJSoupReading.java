package com.cinnarolls.learning.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestJSoupReading {

    @Value("classpath:htmlText.txt")
    private Resource localHtmlText;

    //  https://jsoup.org/cookbook/input/parse-document-from-string
    @Test
    public void JSoupParsesHTML() {
        String html = "<html><head><title>First parse</title></head>" +
                "<body><p>Hello World.</p></body></html>";

        Document doc = Jsoup.parse(html);
        String body = doc.body().outerHtml();

        assertEquals("""
                        <body>
                         <p>Hello World.</p>
                        </body>""",
                body);
    }

    //  https://jsoup.org/cookbook/input/parse-body-fragment
    @Test
    public void JSoupParsesFragmentsOfHtml() {
        String htmlFragment = "<div><p>It's just a div bro, why you have to be mad?</p>";

        Document doc = Jsoup.parseBodyFragment(htmlFragment);
        String div = doc.body().html();

        assertEquals("""
                <div>
                 <p>It's just a div bro, why you have to be mad?</p>
                </div>""", div);
    }

    //  https://jsoup.org/cookbook/input/load-document-from-url
    @Test
    public void JSoupGetsDataFromWeb() throws IOException {
        Connection connect = Jsoup.connect("https://github.com/");
        Document doc = new Document("");
        assertNull(doc.body());

        doc = connect.get();
        assertNotNull(doc.body());
    }

    @Test
    public void JSoupConnectionCustomizable() throws IOException {
        Connection connect = Jsoup.connect("https://www.foaas.com/asshole/Ricky")
                .header("Content-Type", "text/html")
                .timeout(1000)
                .userAgent("Mozilla")
                .data("some-data-here", "...");

        Document doc = connect.get();

        assertTrue(doc.text().contains("Fuck you, asshole. - Ricky"));
    }

    //  https://jsoup.org/cookbook/input/load-document-from-file
    @Test
    public void JSoupEvenWorksWithFiles() throws IOException {
        File file = new ClassPathResource("htmlText.txt").getFile();

        Document doc = Jsoup.parse(
                file,
                "UTF-8"
        );

        assertTrue(doc.text().contains("Montclair"));
    }
}
