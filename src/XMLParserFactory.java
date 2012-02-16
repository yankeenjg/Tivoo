
public class XMLParserFactory {
	private AbstractXMLParser myXMLParser;
	
	public XMLParserFactory(AbstractXMLParser XMLParser) {
		myXMLParser = XMLParser;
	}
	
	//implement isThisType in XMLParserClasses!
	public boolean isThisType() {
		return myXMLParser.isThisType();
	}
	
	public AbstractXMLParser getXMLParser() {
		return myXMLParser;
	}
}
