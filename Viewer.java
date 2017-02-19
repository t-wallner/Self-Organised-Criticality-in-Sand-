// Imports
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;

// class to visually display the simulation
public class Viewer extends JFrame {

    // variables
    private BufferedImage BI;

    // constructors
    public Viewer()
    {
	BI = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
	addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
	
    }

    // methods
    public void paint(Graphics g)
    {
	g.drawImage(BI,0,24,null);
    }
    public BufferedImage getBI()
    {
	return BI;
    }
    public void drawImage()
    {
	repaint();
    }

}
