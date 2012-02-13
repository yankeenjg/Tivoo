import java.util.*;
import org.jdom.*;
import org.jdom.input.*;

public abstract class AbstractXMLParser {
	
	protected Document doc;
	protected Element eventsRoot;
	
	public final void loadFile(String filename){
		try{
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(filename);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	abstract public Element parseGetEventsRoot();
	
	abstract public List<Event> processEvents();
}