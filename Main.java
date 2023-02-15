// Will run the program and initialize classes

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.Insets;


public class Main extends JFrame
{
	Controller controller;
	View view;

	public Main()
	{
		//creates the model, controller and view and allows them to communicate with each other
		controller = new Controller();
		view = new View(controller);
		
		//JFrame window customization
		this.setTitle("Photon Laser Tag Simulation (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
		this.setSize(960, 720);
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//passes listeners through the view and game in order to have controller do the controlling
		this.addKeyListener(controller);

		//Text Field Inputs
		view.setLayout(null);
		Insets insets = view.getInsets();

		Player teamRed[] = new Player[20];
		for(int i = 0; i < teamRed.length; i++)
		{
			teamRed[i] = new Player();
			view.add(teamRed[i].idField);
			view.add(teamRed[i].nameField);
			teamRed[i].idField.setBounds(100 + insets.left, 100 + insets.top + 25*i, teamRed[i].idSize.width, teamRed[i].idSize.height);
			teamRed[i].nameField.setBounds(175 + insets.left, 100 + insets.top + 25*i, teamRed[i].nameSize.width, teamRed[i].nameSize.height);
		}

		Player teamGreen[] = new Player[20];
		for(int i = 0; i < teamGreen.length; i++)
		{
			teamGreen[i] = new Player();
			view.add(teamGreen[i].idField);
			view.add(teamGreen[i].nameField);
			teamGreen[i].idField.setBounds(500 + insets.left, 100 + insets.top + 25*i, teamGreen[i].idSize.width, teamGreen[i].idSize.height);
			teamGreen[i].nameField.setBounds(575 + insets.left, 100 + insets.top + 25*i, teamGreen[i].nameSize.width, teamGreen[i].nameSize.height);
		}
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