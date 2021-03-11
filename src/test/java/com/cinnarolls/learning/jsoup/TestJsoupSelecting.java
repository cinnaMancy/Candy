package com.cinnarolls.learning.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestJsoupSelecting {

    public static final String testHtml = """
            <p>A simple paragraph</p>
            <p class=fancy>A classed paragraph</p>
            <a href=#>An anchor</p>
            <img src="hello.png" alt="Hello">""";
    public static final Document testDoc = Jsoup.parse(testHtml);

    //  https://jsoup.org/cookbook/extracting-data/dom-navigation
    @Test
    public void JSoupCanUseDOMMethods() {
        Elements byTag = testDoc.getElementsByTag("p");
        assertEquals("A simple paragraph", byTag.get(0).text());
        assertEquals("A classed paragraph", byTag.get(1).text());

        Element byAttribute = testDoc.getElementsByAttribute("href").first();
        assertEquals("An anchor", byAttribute.text());
        assertEquals("#", byAttribute.attr("href"));

        //  Loads more are available, see the provided link! - Stan
    }

    //  https://jsoup.org/cookbook/extracting-data/selector-syntax
    @Test
    public void JSoupCanUseSelectorSyntax() {
        Element pngImage = testDoc.select("img[src$=.png]").first();

        assertEquals("<img src=\"hello.png\" alt=\"Hello\">", pngImage.outerHtml());

        //  Just about all CSS selectors are included, again, see provided link! - Stan
    }

}
