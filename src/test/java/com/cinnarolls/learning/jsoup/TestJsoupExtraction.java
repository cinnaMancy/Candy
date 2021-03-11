package com.cinnarolls.learning.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestJsoupExtraction {

    public static final String testHtml = """
            <p>I can make you dance</p>
            <p class=fancy>Oh yeah, I can make you dance!</p>
            
            <a href="www.dance.com" id=dance-link class=fancy><p>If you want me to</p></a>""";
    public static final Document testDoc = Jsoup.parse(testHtml);

    //  https://jsoup.org/cookbook/extracting-data/attributes-text-html
    @Test
    public void JSoupCanExtractsLotsOfWays() {
        Element firstLink = testDoc.select("a").first();
        String outerHtml = firstLink.outerHtml();
        String innerHtml = firstLink.html();
        String linkHref = firstLink.attr("href");
        String linkText = firstLink.text();
        String linkId = firstLink.id();
        boolean hasClassFancy = firstLink.hasClass("fancy");

        assertEquals("<a href=\"www.dance.com\" id=\"dance-link\" class=\"fancy\">" +
                "<p>If you want me to</p></a>", outerHtml);
        assertEquals("<p>If you want me to</p>", innerHtml);
        assertEquals("www.dance.com", linkHref);
        assertEquals("If you want me to", linkText);
        assertEquals("dance-link", linkId);
        assertTrue(hasClassFancy);
    }
}
