package com.SoftwareMatrix;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class XMLParseTest {

    @Test
    public void URLTest () {
        InputStream is = this.getClass().getResourceAsStream("string.xml");
        String url = null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(is);
            NodeList nl = doc.getElementsByTagName("Tutorial");
            Node node = nl.item(0);
            url = node.getTextContent();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertNotNull(url);
        Assert.assertEquals(url, "https://csed332.postech.ac.kr/team3/team_project/wikis/Tutorial");
    }
}
