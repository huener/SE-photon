// Java GUI window setup consists of a navigation panel and a gameplay panel. The gameplay panel shifts between player entry, gameplay, and game over screens as necessary.

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;

class View extends JPanel
{
	BufferedImage splash_image;
   	int splashFrames = 0;
    	boolean splash = false;

    	View(Controller c)
	{
		c.setView(this); 	//sets the controller's view to this view instance in order for the two to be able to communicate
        
        	// loads the picture for the splash screen.
        	splash_image = loadImage("logo1.jpg");    // logo1.jpg is 960x623. Black background at the bottom
        	// of the window matches best.
	}
    	// window size is set to 960 x 720 in main

	public void paintComponent(Graphics g)
	{
		//background color
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        	if(splash == false) {
            	g.drawImage(splash_image, 0, 0, null);
            	//splash = true;
        	}
		
	}

	//static method to load an image with a string input of its name
	public static BufferedImage loadImage(String imageName)
	{
		BufferedImage temp = null;
		try
		{
			temp = ImageIO.read(new File(imageName));
		}
		catch(Exception e) 
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		return temp;
	}

}
