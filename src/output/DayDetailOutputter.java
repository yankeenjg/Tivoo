package output;
import java.io.File;
import java.util.*;
import model.Event;
import org.joda.time.DateTime;
import com.hp.gagawa.java.elements.*;

/**
 * Condensed calendar output for a single day
 * The day in question is the day of the earliest event
 * in the given list
 * @author herio
 *
 */
public class DayDetailOutputter extends DetailOutputter{

	public File writeEvents(List<Event> events) {
        DateTime dt = new DateTime();
        initDtEvents(dt, events);
            
        String filepath = "DayDetail_of_" + dt.toString("MMMdd");
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        H4 h4 = new H4();
        h4.appendChild(new Text(dt.toString("MMM dd, YYYY")));
        body.appendChild(h4);
        
        P p = new P();
        body.appendChild(p);
        writeEventP(p, events, dt, filepath);
        
        return writeHtmlFile(html, filepath + FILE_EXT);
    }
    
    
    
    /*public static void main (String[] args){
    	AbstractHtmlOutputter ho = new DayDetailOutputter();
    	DateTime dt1 = new DateTime(2012, 2, 24, 11, 15);
    	DateTime dt2 = new DateTime(2012, 2, 24, 11, 30);
    	DateTime dt3 = new DateTime(2012, 2, 24, 11, 45);
    	DateTime dt4 = new DateTime(2012, 2, 24, 12, 00);
    	DateTime dt5 = new DateTime(2012, 2, 24, 12, 15);
    	DateTime dt6 = new DateTime(2012, 2, 24, 12, 30);
    	DateTime dt7 = new DateTime(2012, 2, 24, 12, 45);
    	DateTime dt8 = new DateTime(2012, 2, 24, 13, 00);
    	List<String> actor = new ArrayList<String>();
    	actor.add("actor");
    	Event e1 = new Event("Title", dt1, dt2, "Description", "Location", true, null);
    	Event e2 = new Event("Title2", dt3, dt4, "Description2", "Location", false, null);
    	Event e3 = new Event("Title3", dt5, dt6, "Description3", "Location2.333", false, null);
    	Event e4 = new Event("Title4", dt7, dt8, "Description 4", "Location4", false, null);
    	List<Event> l = new ArrayList<Event>();
    	l.add(e2);
    	l.add(e1);
    	l.add(e3);
    	l.add(e4);
    	ho.writeEvents(l);
    }*/

}
