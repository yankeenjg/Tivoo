package output;
import model.Event;
import com.hp.gagawa.java.Node;
import java.io.*;
import java.util.*;

public abstract class AbstractHtmlOutput {
	
	protected static final String FILE_EXT = ".html";
    
    public abstract void writeEventList(List<Event> events);

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
