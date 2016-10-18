package controller;

import dao.PeopleList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.File;

public class HealthProfileWriter {

    private static String xmlPath;
    private static Marshaller marshaller;


    // Class constructors

    public HealthProfileWriter() throws JAXBException {
        this("data/peopleOutput.xml");
    }
    public HealthProfileWriter(String outputXmlFilePath) throws JAXBException {
        this.xmlPath = outputXmlFilePath;
        JAXBContext jc = JAXBContext.newInstance(PeopleList.class);
        this.marshaller = jc.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }


    // Marshalling to XML using classes generated with JAXB XJC.

    public void marshal(PeopleList peopleList) throws JAXBException {
        this.marshaller.marshal(peopleList, System.out);
        this.marshaller.marshal(peopleList, new File(this.xmlPath));
    }
    public void marshal(PeopleList peopleList, String xmlOutputFile) throws JAXBException {
        this.marshaller.marshal(peopleList, System.out);
        this.marshaller.marshal(peopleList, new File(xmlOutputFile));
    }
}
