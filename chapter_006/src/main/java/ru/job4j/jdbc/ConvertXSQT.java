package ru.job4j.jdbc;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Обработка файла XML.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ConvertXSQT {
    /**
     * Преобразование формата данных
     * @param source источник
     * @param dest результат преобразования
     * @param scheme шаблон преобразования
     */
    public void convert(File source, File dest, File scheme) throws IOException,
            TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(
                new StreamSource(
                        new ByteArrayInputStream(
                                Files.readAllBytes(scheme.toPath())))
        );
        transformer.transform(new StreamSource(
                        new ByteArrayInputStream(
                                Files.readAllBytes(source.toPath()))),
                new StreamResult(dest)
        );
    }
}
