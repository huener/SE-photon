// Java GUI window setup consists of a navigation panel and a gameplay panel. The gameplay panel shifts between player entry, gameplay, and game over screens as necessary.

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.BoxLayout;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.border.Border; 
import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import java.awt.Dimension;

class View extends JPanel
{
	Data data;

	//Splash Screen
	BufferedImage splash_image;
   	int splashFrames = 0;
    boolean splash = false;

	//Panels Framework
	JPanel titlePanel;
	JPanel mainPanel;
	JPanel navPanel;
	JPanel bottomPanel;

	JTextField bottomText;

    View(Controller c, Data d)
	{
		c.setView(this); 	//sets the controller's view to this view instance in order for the two to be able to communicate
        data = d;
		bottomText = new JTextField(50);
		//initiating and sizing panels
		titlePanel = new JPanel(); titlePanel.setMaximumSize(new Dimension(960, 50)); titlePanel.setBackground(Color.black);
		mainPanel = new JPanel(); mainPanel.setMaximumSize(new Dimension(960, 580)); mainPanel.setBackground(Color.black);
		navPanel = new JPanel(); navPanel.setMaximumSize(new Dimension(960, 60)); navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS)); navPanel.setBackground(Color.black);
		bottomPanel = new JPanel(); bottomPanel.setMaximumSize(new Dimension(960, 30));

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

	void startGUI()
	{
		this.add(titlePanel);
        this.add(mainPanel);
        this.add(navPanel);
        this.add(bottomPanel);

		createNavigationBar();
		createBottomText();
	}

	void createNavigationBar() {
		// create navigation bar / instructions
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		// create array of JTextArea
		JTextArea navBar[] = new JTextArea[12];
		for(int i = 0; i < navBar.length; i++)
		{
			navBar[i] = new JTextArea();
			navBar[i].setEditable(false);
			navBar[i].setForeground(Color.GREEN);
			navBar[i].setBackground(Color.BLACK);

			// space them out evenly horizontally; same height on the screen
			navBar[i].setMaximumSize(new Dimension(78, 60));

			// only add border to the JTextAreas for the controls. No borders for empty areas.
			if(i != 3 && i != 5 && i != 8 && i != 10) {
				navBar[i].setBorder(BorderFactory.createCompoundBorder(border,
					BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			}
            
			// add to view (so they show up)
			this.navPanel.add(navBar[i]);
		}
		// set unique text for the necessary text areas.
		// JTextArea apparently doesn't have a good way to center text,
		// so strings are formatted to be centered in the textbox
		navBar[0].setText("         F1 \n        Edit \n      Game");
		navBar[1].setText("         F2 \n      Game \n  Parameters");
		navBar[2].setText("         F3 \n       Start \n      Game");
		navBar[4].setText("         F5 \n  PreEntered \n     Games");
		navBar[6].setText("         F7");
		navBar[7].setText("         F8 \n       View \n      Game");
		navBar[9].setText("        F10 \n       Flick \n       Sync");
		navBar[11].setText("        F12 \n       Clear \n      Game");
	}

	void createBottomText() {
		// create bottom textbox
		// editmode specific text?
		JTextField bottomText = new JTextField(100);
		bottomText.setEditable(false);
		bottomText.setHorizontalAlignment(JTextField.CENTER);
		bottomText.setText("<Del> to Delete Player, <Ins> to Manually Insert, or edit codename");
		bottomText.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		this.bottomPanel.add(bottomText);
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
