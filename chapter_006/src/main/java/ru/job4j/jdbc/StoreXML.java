package ru.job4j.jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.List;

/**
 * Генерация XML из данных базы.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StoreXML {
    /** Файл, куда будут сохраняться данные */
    private File target;

    StoreXML(File target) {
        this.target = target;
    }

    @XmlRootElement
    public static class Entries {
        private List<Entry> entries;

        public Entries() {
        }

        public Entries(List<Entry> entry) {
            this.entries = entry;
        }

        @XmlElement(name = "entry")
        public List<Entry> getEntries() {
            return entries;
        }

        public void setEntries(List<Entry> entries) {
            this.entries = entries;
        }
    }

    public void save(List<Entry> list) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Entries.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        Entries entries = new Entries(list);
        jaxbMarshaller.marshal(entries, this.target);
    }
}
