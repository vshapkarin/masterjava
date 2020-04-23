package ru.javaops.masterjava.xml.util;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import java.io.*;


/**
 * Marshalling/Unmarshalling JAXB helper
 * XML Facade
 */
public class JaxbParser {

    private JAXBContext jaxbContext;
    protected Schema schema;

    public JaxbParser(Class... classesToBeBound) {
        try {
            jaxbContext = JAXBContext.newInstance(classesToBeBound);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

    //    http://stackoverflow.com/questions/30643802/what-is-jaxbcontext-newinstancestring-contextpath
    public JaxbParser(String context) {
        try {
            jaxbContext = JAXBContext.newInstance(context);
        } catch (JAXBException e) {
            throw new IllegalArgumentException(e);
        }
    }

//    private void init(JAXBContext ctx) throws JAXBException {
//        jaxbMarshaller = new JaxbMarshaller(ctx);
//        jaxbUnmarshaller = new JaxbUnmarshaller(ctx);
//    }

    // Unmarshaller
    public <T> T unmarshal(InputStream is) throws JAXBException {
        JaxbUnmarshaller jaxbUnmarshaller = createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(is);
    }

    public <T> T unmarshal(Reader reader) throws JAXBException {
        JaxbUnmarshaller jaxbUnmarshaller = createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(reader);
    }

    public <T> T unmarshal(String str) throws JAXBException {
        JaxbUnmarshaller jaxbUnmarshaller = createUnmarshaller();
        return (T) jaxbUnmarshaller.unmarshal(str);
    }

    public <T> T unmarshal(XMLStreamReader reader, Class<T> elementClass) throws JAXBException {
        JaxbUnmarshaller jaxbUnmarshaller = createUnmarshaller();
        return jaxbUnmarshaller.unmarshal(reader, elementClass);
    }

    // Marshaller
    public void setMarshallerProperty(String prop, Object value) throws JAXBException {
        try {
            JaxbMarshaller jaxbMarshaller = createMarshaller();
            jaxbMarshaller.setProperty(prop, value);
        } catch (PropertyException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String marshal(Object instance) throws JAXBException {
        JaxbMarshaller jaxbMarshaller = createMarshaller();
        return jaxbMarshaller.marshal(instance);
    }

    public void marshal(Object instance, Writer writer) throws JAXBException {
        JaxbMarshaller jaxbMarshaller = createMarshaller();
        jaxbMarshaller.marshal(instance, writer);
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public void validate(String str) throws IOException, SAXException {
        validate(new StringReader(str));
    }

    public void validate(Reader reader) throws IOException, SAXException {
        schema.newValidator().validate(new StreamSource(reader));
    }

    private JaxbMarshaller createMarshaller() throws JAXBException {
        JaxbMarshaller jaxbMarshaller = new JaxbMarshaller(jaxbContext);
        jaxbMarshaller.setSchema(schema);
        return jaxbMarshaller;
    }

    private JaxbUnmarshaller createUnmarshaller() throws JAXBException {
        JaxbUnmarshaller jaxbUnmarshaller = new JaxbUnmarshaller(jaxbContext);
        jaxbUnmarshaller.setSchema(schema);
        return jaxbUnmarshaller;
    }
}
