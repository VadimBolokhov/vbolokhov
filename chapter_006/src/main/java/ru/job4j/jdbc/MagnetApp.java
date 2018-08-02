package ru.job4j.jdbc;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.List;

/**
 * Обработка данных базы SQLite.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MagnetApp {

    public static void main(String[] args) throws Exception {
        new File("./target/db").mkdirs();
        String fileName = "./target/db/storesql.db";
        int n = Integer.parseInt(args[0]);
        Config config = new Config(fileName);
        StoreSQL store = new StoreSQL(config);
        store.generate(n);
        List<Entry> entries = store.getAllEntries();

        File storeXML = new File("./target/db/storexml.xml");
        new StoreXML(storeXML).save(entries);

        File destXML = new File("./target/db/dest.xml");
        File scheme = new File("./sqlite/convert.xsl");
        new ConvertXSQT().convert(storeXML, destXML, scheme);

        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser saxParser = spf.newSAXParser();
        SAXProcessor sum = new SAXProcessor();
        saxParser.parse(destXML, sum);
    }


}
