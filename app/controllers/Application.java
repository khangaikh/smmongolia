package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

import models.Person;

import play.data.Form;

import java.util.List;

import play.db.ebean.Model;

import static play.libs.Json.*;

import java.net.URL;  
import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList; 

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result addPerson() {
    	Person person = Form.form(Person.class).bindFromRequest().get();
    	person.save();
    	return redirect(routes.Application.index());
    	
    }

    public static Result getPersons() {
        try {  
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();  
            Document doc = builder.parse("http://www.ikon.mn/rss");  
    
             NodeList items = doc.getElementsByTagName("item");  
    
            for (int i = 0; i < items.getLength(); i++) {  
             
                Element item = (Element)items.item(i);  
                Person person = new Person();
                person.name = item.getAttribute("title");
                person.save();
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        
    	List<Person> persons = new Model.Finder(String.class, Person.class).all();
    	return ok(toJson(persons));
    }
}
