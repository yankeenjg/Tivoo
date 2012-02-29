package output;

import model.Event;
import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.B;
import com.hp.gagawa.java.elements.P;
import com.hp.gagawa.java.elements.Text;
import java.io.*;
import java.util.*;
import org.joda.time.DateTime;
import sorting.StartTimeSorter;

/**
 * Abstract class for outputting html files that provides a 
 * few universal helper methods.  I guess not all subclasses
 * use all the methods, but that depends largely in part on
 * the individual class, and there are always some at each
 * level that do
 * @author herio
 *
 */
public abstract class AbstractHtmlOutputter {
	
	/**
	 * The file extension we want to use for output files
	 */
	protected static final String FILE_EXT = ".html";
	
	/**
	 * The time format we want to use when start and end times
	 * are paired together
	 */
	private static final String SE_TIME_FORMAT = "HH:mm MM/dd";
    
	/**
	 * Primary method that directs the construction of html
	 * element nodes to write the html pages
	 * @param events List of events to be output
	 */
    public abstract void writeEvents(List<Event> events);
    
    /**
     * Initalizes the reference DateTime object and sorts
     * the list of events for future use
     * @param dt Reference DateTime
     * @param events Given list to the sorted by start time
     */
    protected void initDtEvents(DateTime dt, List<Event> events){
    	if(!events.isEmpty()){
        	StartTimeSorter sts = new StartTimeSorter();
        	events = sts.sort(events);
            dt = new DateTime(events.get(0).getStartTime());
        }
    }

    /**
     * Handles all the actual file writing and related
     * exception checking
     * @param head Head of the html node tree
     * @param dir File path and file name to write to
     */
    protected void writeHtmlFile(Node head, String dir){
        File f = new File(dir);
        if(dir.contains("/") && !f.getParentFile().exists())
            f.getParentFile().mkdir();
        
        BufferedWriter out;
        try{
            out = new BufferedWriter(new FileWriter(dir));
            out.write(head.write());
            out.close();
        }catch(IOException e1){
            e1.printStackTrace();
        }
    }
    
    /**
     * Appends an event's title and start/end times
     * @param e Event in question
     * @param p P element to which to append
     */
    protected void appendTitleTimes(Event e, P p){
		B b = new B();
		b.appendChild(new Text(e.getTitle()));
		p.appendChild(b);
        appendTimes(e, p);
	}
    
    /**
     * Appends an event's start/end times
     * @param e Event in question
     * @param p P element to which to append
     */
    protected void appendTimes(Event e, P p){
    	if(e.isAllDay()){
            p.appendChild(new Text("<br/> All day "+e.getStartTime().toString("MM/dd")));
        }else{
            p.appendChild(new Text("<br/> Start: "+e.getStartTime().toString(SE_TIME_FORMAT)+"<br/>"));
            p.appendChild(new Text("End: "+e.getEndTime().toString(SE_TIME_FORMAT)+"<br/>"));
        }
    }
    
    /**
     * Checks if two DateTimes are in the same year
     * @param dt1 First DateTime
     * @param dt2 Second DateTime
     * @return If the two share the same year
     */
    protected boolean isSameYear(DateTime dt1, DateTime dt2){
    	if(dt1.getYear()==dt2.getYear())
    		return true;
    	return false;
    }
    
    /**
     * Checks if two DateTimes are the same date
     * (same MM/dd/YYY)
     * @param dt1 First DateTime
     * @param dt2 Second DateTime
     * @return If the two are the same date
     */
    protected boolean isSameDate(DateTime dt1, DateTime dt2){
    	if(dt1.getDayOfYear()==dt2.getDayOfYear() && isSameYear(dt1, dt2))
    		return true;
    	return false;
    }

}
