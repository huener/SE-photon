// Will run the program and initialize classes

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.Border; 
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Insets;
import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame
{
	//creates the model, controller and view and allows them to communicate with each other
	Controller controller = new Controller();
	View view = new View(controller);

	public Main()
	{
		//JFrame window customization
		this.setTitle("Photon Laser Tag Simulation (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
		this.setSize(960, 720);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//passes listeners through the view and game in order to have controller do the controlling
		this.addKeyListener(controller);

        	splashScreen();	// draw splash screen and sleep before text boxes are created

		//Text Field Inputs
		view.setLayout(null);
		Insets insets = view.getInsets();

		Player teamRed[] = new Player[20];
		for(int i = 0; i < teamRed.length; i++)
		{
			teamRed[i] = new Player();
			view.add(teamRed[i].idField);
			view.add(teamRed[i].nameField);
			teamRed[i].idField.setBounds(100 + insets.left, 75 + insets.top + 25*i, teamRed[i].idSize.width, teamRed[i].idSize.height);
			teamRed[i].nameField.setBounds(175 + insets.left, 75 + insets.top + 25*i, teamRed[i].nameSize.width, teamRed[i].nameSize.height);
		}

		Player teamGreen[] = new Player[20];
		for(int i = 0; i < teamGreen.length; i++)
		{
			teamGreen[i] = new Player();
			view.add(teamGreen[i].idField);
			view.add(teamGreen[i].nameField);
			teamGreen[i].idField.setBounds(500 + insets.left, 75 + insets.top + 25*i, teamGreen[i].idSize.width, teamGreen[i].idSize.height);
			teamGreen[i].nameField.setBounds(575 + insets.left, 75 + insets.top + 25*i, teamGreen[i].nameSize.width, teamGreen[i].nameSize.height);
		}

        	createNavigationBar(insets);
        
        	createBottomText(insets);        
	}

    	void splashScreen() {
        	view.repaint();
        
        	// let splash screen display for 3 seconds
        	if(view.splash == false) {
            		try
            		{
                		TimeUnit.SECONDS.sleep(3);
            		} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			view.splash = true; // toggle splash variable so splash screen stops showing
		}
	}
	
	void createNavigationBar(Insets insets1) {
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
			navBar[i].setBounds(insets1.left + (i*78), insets1.top + 590, 78, 60);

			// only add border to the JTextAreas for the controls. No borders for empty areas.
			if(i != 3 && i != 5 && i != 8 && i != 10) {
				navBar[i].setBorder(BorderFactory.createCompoundBorder(border,
					BorderFactory.createEmptyBorder(1, 1, 1, 1)));
			}
            
			// add to view (so they show up)
			view.add(navBar[i]);
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

	void createBottomText(Insets insets1) {
		// create bottom textbox
		// editmode specific text?
		JTextField bottomText = new JTextField(50);
		bottomText.setBounds(insets1.left, insets1.top + 650, 940, 32);
		bottomText.setEditable(false);
		bottomText.setHorizontalAlignment(JTextField.CENTER);
		bottomText.setText("<Del> to Delete Player, <Ins> to Manually Insert, or edit codename");
		bottomText.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
		view.add(bottomText);
	}

	//MAIN PROGRAM:: the actual code that is ran on start
	public static void main(String[] args)
	{
        	Main instance = new Main();
		instance.run();	//actually runs the game
	}

	public void run()
	{
		while(true)
		{
			controller.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 40 milliseconds
			try
			{
				Thread.sleep(40); //changed to update every 40 ms, or 25 fps
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
}
