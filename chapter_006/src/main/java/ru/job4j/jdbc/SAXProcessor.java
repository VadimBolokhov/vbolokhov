package ru.job4j.jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Обработка данных из XML файла.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SAXProcessor extends DefaultHandler {
    /** Сумма значений атрибутов */
    private int sum;

    @Override
    public void startDocument() throws SAXException {
        this.sum = 0;
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println(this.sum);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("Entry")) {
            int value = Integer.parseInt(attributes.getValue("field"));
            this.sum += value;
        }
    }
}
