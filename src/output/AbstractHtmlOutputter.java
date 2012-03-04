package output;

import model.Event;
import com.hp.gagawa.java.*;
import com.hp.gagawa.java.elements.*;
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
    public String writeEvents(List<Event> events){
    	DateTime dt = initDtEvents(events);
        
        Html html = new Html();
        Body body = new Body();
        html.appendChild(body);
        
        String filepath = appendFormatting(events, dt, body) + FILE_EXT;
        
        writeHtmlFile(html, filepath);
        return filepath;
    }
    
    /**
     * Adds all the specific event formatting for the respective outputter
     * to the provided Body object
     * @param events Events to print
     * @param dt Reference DateTime
     * @param body Body object to which to append
     * @return The name of the html file to write to
     */
    protected abstract String appendFormatting(List<Event> events, DateTime dt, Body body);
    
    /**
     * Initalizes the reference DateTime object and sorts
     * the list of events for future use
     * @param events Given list to the sorted by start time
     */
    private DateTime initDtEvents(List<Event> events){
    	if(!events.isEmpty()){
        	StartTimeSorter sts = new StartTimeSorter();
        	events = sts.sort(events);
            return events.get(0).getStartTime();
        }else
        	return new DateTime();
            
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
     * Checks if two DateTimes are the same date
     * (same MM/dd/YYY)
     * @param dt1 First DateTime
     * @param dt2 Second DateTime
     * @return If the two are the same date
     */
    protected boolean isSameDate(DateTime dt1, DateTime dt2){
    	if(dt1.getDayOfYear()==dt2.getDayOfYear() && dt1.getYear()==dt2.getYear())
    		return true;
    	return false;
    }

}
