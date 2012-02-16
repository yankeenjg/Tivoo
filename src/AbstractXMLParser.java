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
	
	public static List<XMLParserFactory> makeXMLParserList() {
		List<XMLParserFactory> XMLParserList = new ArrayList<XMLParserFactory>();
		XMLParserList.add(DukeXMLParser.getFactory());
		XMLParserList.add(GoogleXMLParser.getFactory());
		return XMLParserList;
	}
		
	public abstract List<Event> processEvents();

	//implement this is subclasses
	//public abstract boolean isThisType(String URL);
	
}