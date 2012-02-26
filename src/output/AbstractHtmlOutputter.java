package output;

import model.Event;
import com.hp.gagawa.java.Node;
import java.io.*;
import java.util.*;

/*
 * still an abstract class because it makes more sense to have
 * common methods in here as opposed to having an interface and
 * a static method in two different files that serve the same
 * purpose
 */
public abstract class AbstractHtmlOutputter {
	
	protected static final String FILE_EXT = ".html";
    
	/*
	 * at some point this should also take a file path to output to
	 * for the UI
	 */
    public abstract void writeEvents(List<Event> events);

    /*
     * creates an html page based on the given head node
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

}
