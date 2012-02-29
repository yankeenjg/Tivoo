import javax.swing.JFrame;


public class GUIMain {
	  // convenience constants
    public static final String TITLE = "TIVOO";

    public static void main (String[] args)
    {
    	// create program specific components
        GUIModel model = new GUIModel();
        GUIViewer display = new GUIViewer(model);
        // create container that will work with Window manager
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(display);
        frame.pack();
        frame.setVisible(true);
    }
  
}
