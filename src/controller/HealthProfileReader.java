package controller;

import dao.PeopleList;
import model.HealthProfile;
import model.Person;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HealthProfileReader {  	
	// public static PeopleStore people = new PeopleStore();

    static String xmlPath = "data/people.xml";
    static Document doc;
    static XPathFactory factory;
    static XPath xpath;

    static Unmarshaller um;

    public HealthProfileReader() throws ParserConfigurationException, SAXException,
            IOException, JAXBException {

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();

        // System.out.println("Loading people.xml...");
        this.doc = builder.parse(xmlPath);
        this.factory = XPathFactory.newInstance();
        this.xpath = this.factory.newXPath();

        JAXBContext jc = JAXBContext.newInstance(PeopleList.class);
        this.um = jc.createUnmarshaller();
    }


    // Based on Lab 3

    // 1. Use xpath to implement methods like getWeight and getHeight
    //    (getWeight(personID) returns weight of a given person, the same for getHeight)

    public String getWeight(String personID) throws XPathExpressionException {
        String searchExpr = "/people/person[@id='" + personID + "']";
        XPathExpression expr = this.xpath.compile(searchExpr);
        Object result = expr.evaluate(this.doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            NodeList weightNodes = e.getElementsByTagName("weight");
            return weightNodes.item(0).getChildNodes().item(0).getNodeValue();
        }
        return "";
    }
    public String getHeight(String personID) throws XPathExpressionException {
        String searchExpr = "/people/person[@id='" + personID + "']";
        XPathExpression expr = this.xpath.compile(searchExpr);
        Object result = expr.evaluate(this.doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            NodeList weightNodes = e.getElementsByTagName("height");
            return weightNodes.item(0).getChildNodes().item(0).getNodeValue();
        }
        return "";
    }


    private Person readPersonFromXmlElement(Element e) {
        String personId = e.getAttribute("id");

        NodeList firstnameNodes = e.getElementsByTagName("firstname");
        NodeList lastnameNodes = e.getElementsByTagName("lastname");
        NodeList birthdateNodes = e.getElementsByTagName("birthdate");
        NodeList weightNodes = e.getElementsByTagName("weight");
        NodeList heightNodes = e.getElementsByTagName("height");

        String firstname = firstnameNodes.item(0).getChildNodes().item(0).getNodeValue();
        String lastname = lastnameNodes.item(0).getChildNodes().item(0).getNodeValue();
        String birthdate = birthdateNodes.item(0).getChildNodes().item(0).getNodeValue();
        double weight = Double.parseDouble(weightNodes.item(0).getChildNodes().item(0).getNodeValue());
        double height = Double.parseDouble(heightNodes.item(0).getChildNodes().item(0).getNodeValue());

        return new Person(Long.parseLong(personId), firstname, lastname, birthdate, new HealthProfile(weight, height));
    }


    // 2. Make a function that prints all people in the list with detail

    public List<Person> listAllPeople() throws  XPathExpressionException {
        List<Person> results = new ArrayList<Person>();
        String searchExpr = "/people/person";
        XPathExpression expr = this.xpath.compile(searchExpr);
        Object result = expr.evaluate(this.doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            results.add(readPersonFromXmlElement(e));
        }
        return results;
    }


    // 3. A function that accepts id as parameter and prints the HealthProfile of the person with that id

    public HealthProfile getHealthProfileByPersonId(String personID) throws XPathExpressionException {
        String searchExpr = "/people/person[@id='" + personID + "']";
        XPathExpression expr = this.xpath.compile(searchExpr);
        Object result = expr.evaluate(this.doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            return readPersonFromXmlElement(e).getHealthProfile();
        }
        return new HealthProfile(0, 0);
    }


    // 4. A function which accepts a weight and an operator (=, > , <) as parameters
    //    and prints people that fulfill that condition (i.e., >80Kg, =75Kg, etc.).

    public List<Person> getPersonByWeight(double weight, String operator) throws XPathExpressionException {
        List<Person> results = new ArrayList<Person>();

        String searchExpr = "/people/person[healthprofile/weight" + operator + weight + "]";
        XPathExpression expr = this.xpath.compile(searchExpr);
        Object result = expr.evaluate(this.doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);
            results.add(readPersonFromXmlElement(e));
        }

        return results;
    }


}
