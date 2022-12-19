package com.kowalski;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Settings {

    private Boolean enabled = false;
    private String fileName;
    private String format;

    public Settings(String setting) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));
            NodeList elements = doc.getElementsByTagName(setting);

            this.enabled = elements.item(0).getChildNodes().item(1).getFirstChild().getNodeValue().equals("true");
            this.fileName = elements.item(0).getChildNodes().item(3).getFirstChild().getNodeValue();
            if (elements.item(0).getChildNodes().item(5) != null) {
                if (elements.item(0).getChildNodes().item(5).getFirstChild().getNodeValue().equals("txt"))
                    this.format = ".txt";
                else if (elements.item(0).getChildNodes().item(5).getFirstChild().getNodeValue().equals("json")) {
                    this.format = ".json";
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFormat() {
        return format;
    }
}
