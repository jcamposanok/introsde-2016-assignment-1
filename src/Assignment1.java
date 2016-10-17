import controller.HealthProfileReader;
import model.HealthProfile;
import model.Person;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Assignment1 {

    private static void printPerson(Person p) {
        System.out.println();
        System.out.println("ID: " + p.getPersonId().toString());
        System.out.println("Full name: " + p.getFirstname() + " " + p.getLastname());
        System.out.println("Date of birth: " + p.getBirthdate());
        System.out.println("Health profile: " + p.getHealthProfile().toString());
    }

    public static void main(String[] args) throws ParserConfigurationException, JAXBException, SAXException, IOException, XPathExpressionException {
        HealthProfileReader reader = new HealthProfileReader();

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("1. Running instruction 2 based on Lab 3");
        List<Person> allPeople = reader.listAllPeople();
        for (Iterator<Person> i = allPeople.iterator(); i.hasNext();) {
            Person p = i.next();
            printPerson(p);
        }

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("2. Running instruction 3 based on Lab 3 with id = 5");
        String personID = "0005";
        HealthProfile hp = reader.getHealthProfileByPersonId(personID);
        System.out.println(hp.toString());

        System.out.println();
        System.out.println("------------------------------------------");
        System.out.println("3. Running instruction 4 based on Lab 3 with weight > 90");
        String operator = ">";
        double searchWeight  = 90;
        List<Person> peopleByWeight = reader.getPersonByWeight(searchWeight, operator);
        for (Iterator<Person> i = peopleByWeight.iterator(); i.hasNext();) {
            Person p = i.next();
            printPerson(p);
        }
    }

}