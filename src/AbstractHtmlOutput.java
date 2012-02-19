import com.hp.gagawa.java.Node;
import com.hp.gagawa.java.elements.*;
import java.io.*;
import java.util.*;

public abstract class AbstractHtmlOutput {
    
    public abstract void writeEventList(List<Event> events);

    public void writeHtmlFile(Node head, String dir){
        File f = new File(dir);
        if(!f.getParentFile().exists())
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
