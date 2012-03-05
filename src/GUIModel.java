import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class GUIModel {
	
	private MainModel myParser;
	
	private String myHome;
	private String myCurrentURL;
	private List<String> myHistory;
	private int myCurrentIndex;
	
	public GUIModel(MainModel parser){
		myParser = parser;
		myHome = "";
		myCurrentURL = "";
		myHistory = new ArrayList<String>();
	}
	
	public String getFileRoot(){
		URL url = Main.class.getProtectionDomain().getCodeSource().getLocation();
	    String root=null;
		try {
			root = (new File(url.toURI())).getParent();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "file://"+root+"/";
	}
	
	public String loadFile(File file){
	    String filename = myParser.loadFile(file);
	    return getFileRoot()+filename;
	}
	
	public String chooseKeywordFilter(String keyword){
		myParser.setFilterParameters(keyword);
		return getFileRoot()+myParser.chooseFilter("KeywordFilter");
	}
	
	public String chooseLocationFilter(String keyword){
		myParser.setFilterParameters(keyword);
		return getFileRoot()+myParser.chooseFilter("LocationFilter");
	}
	
    public void go (String url)
    {
        myCurrentURL = url;
        if (hasNext())
        {
            myHistory = myHistory.subList(0, myCurrentIndex + 1); 
        }
        myHistory.add(url);
        myCurrentIndex++;
    }
	
	public String next(){
		if(hasNext())
		{
			myCurrentIndex++;
			return myHistory.get(myCurrentIndex);
		}
		return null;
	}
	
	public String back(){
		if(hasPrevious())
		{
			myCurrentIndex--;
			return myHistory.get(myCurrentIndex);
		}
		return null;
	}
	
	public void reset(){
		myHistory.clear();
		myParser.updateView();
	}
	
	public String getHome(){
		return myHome;
	}
	
	public void setHome(String home){
		myHome = home;
	}
	
	public boolean hasNext(){
		return myCurrentIndex < (myHistory.size()-1);
	}
	
	public boolean hasPrevious(){
		return myCurrentIndex > 0;
	}
	
	
	
}
