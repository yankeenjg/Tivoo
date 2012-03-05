import javax.swing.JFrame;


public class GUIMain {

    public static final String TITLE = "TiVoo GUI";

    public static void main (String[] args)
    {
    	// create program specific components
        MainModel mmodel = new MainModel();
    	GUIModel gmodel = new GUIModel(mmodel);
        GUIViewer display = new GUIViewer(gmodel);
        // create container that will work with Window manager
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add our user interface components to Frame and show it
        frame.getContentPane().add(display);
        frame.pack();
        frame.setVisible(true);
    }
}
