// Will run the program and initialize classes

import javax.swing.JFrame;
import java.awt.Toolkit;

import java.util.concurrent.TimeUnit;

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
			
			// let splash screen display for 4 seconds (it gets drawn in view.repaint() if view.splash==true)
           		if(view.splash == false) {
                		try
                		{
                    			TimeUnit.SECONDS.sleep(4);
                		} catch(Exception e) {
                    			e.printStackTrace();
                    			System.exit(1);
                		}
                		view.splash = true; // toggle splash variable so it stops showing
            		}

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
