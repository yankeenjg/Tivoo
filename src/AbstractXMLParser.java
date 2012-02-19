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
	
	public chooseParser() {
		//TODO: Implement this method
	}
		
	public abstract List<Event> processEvents();

	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}