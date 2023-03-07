// handles keyboard navigation commands

//import java.awt.event.MouseListener;
//import java.awt.event.MouseEvent;
// hellpo
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;


class Controller implements ActionListener, KeyListener, FocusListener
{
	View view;
	Data data;

	// Mode selectors
	boolean editMode;
	boolean entryMode = true; //switches to the player action screen
	Player focus, prevFocus;


	public Controller(Data data)
	{
		this.data = data;
		editMode = false;
		focus = data.teamRed[0];
		prevFocus = focus;
	}

	void setView(View v)
	{
		view = v;
	}

	//KeyListener methods
	public void keyPressed(KeyEvent e)	{	}
	public void keyTyped(KeyEvent e)	{	}
	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_DOWN: 	break;
			case KeyEvent.VK_UP:	break;
			case KeyEvent.VK_LEFT:  break;
			case KeyEvent.VK_RIGHT:	break;

			case KeyEvent.VK_F1:	editMode = !editMode; break;
			case KeyEvent.VK_F3:	break; // TODO: start game
			case KeyEvent.VK_F5:	
				entryMode = !entryMode; // TODO: PreEntered Games
				if(entryMode) 
				{
					view.mainPanel.remove(view.actionPanel);
					view.mainPanel.add(view.entryPanel); 
				}
				else
				{
					view.mainPanel.remove(view.entryPanel);
					view.mainPanel.add(view.actionPanel); 
				}
			case KeyEvent.VK_F8:	break; // TODO: View Game
			case KeyEvent.VK_F10:	break; // TODO: Flick Sync
			case KeyEvent.VK_F12:	break; // TODO: clear game text fields

			case KeyEvent.VK_TAB:		   // TODO: data.teamRed[0].idField is the only JTextField component that does not listen to tab or shift+tab commands for some reason. why?
			case KeyEvent.VK_ENTER:	break; // TODO: tab and enter both need to cause the player object to update variables & querey database

		}
	}


	public void actionPerformed(ActionEvent e)	{	}

	void editTextFields(Boolean editVal)
	{
		for(int i = 0; i < data.teamRed.length; i++)
		{
			data.teamRed[i].idField.setEditable(editVal);
			data.teamRed[i].nameField.setEditable(editVal);
			data.teamRed[i].idField.setRequestFocusEnabled(editVal);
			data.teamRed[i].nameField.setRequestFocusEnabled(editVal);

			data.teamGreen[i].idField.setEditable(editVal);
			data.teamGreen[i].nameField.setEditable(editVal);
			data.teamGreen[i].idField.setRequestFocusEnabled(editVal);
			data.teamGreen[i].nameField.setRequestFocusEnabled(editVal);
		}
	}

	// These are the events for the FocusListener - they allow us to determine what happens to a given player entry JTextField
	// when the user clicks/tabs to or away from it. The logic currently present allows us to to determine exactly which player
	// is currently being updated, with the option to even determine exactly which field is focused as well. 
	public void focusGained(FocusEvent e)
	{
		char[] name = ((e.getComponent()).getName()).toCharArray();
		char team = name[1];
		// char field = name[0];

		String indexStr = "";
		int index = 0;
		for(int i = 2; i < name.length; i++)
		{
			indexStr = indexStr + name[i];
			index = Integer.parseInt(indexStr);
		}

		if (team == 'R')
			focus = data.teamRed[index]; 
		else
			focus = data.teamGreen[index];
	}

	public void focusLost(FocusEvent e)
	{
		char[] name = ((e.getComponent()).getName()).toCharArray();
		char team = name[1];
		char field = name[0];

		String indexStr = "";
		int index = 0;
		for(int i = 2; i < name.length; i++)
		{
			indexStr = indexStr + name[i];
			index = Integer.parseInt(indexStr);
		}

		if (team == 'R')
			prevFocus = data.teamRed[index]; 
		else
			prevFocus = data.teamGreen[index];

		if(field == 'I')
		{
			try
			{
				if(prevFocus.playerID != Integer.parseInt(prevFocus.idField.getText()))
				prevFocus.playerID = Integer.parseInt(prevFocus.idField.getText());
			}
			catch(Exception x)
			{
				System.out.println("Cannot parse int " + prevFocus.idField.getText());
			}

		}
			
		else
		{
			if (prevFocus.codename != (prevFocus.nameField.getText()))
				prevFocus.codename = prevFocus.nameField.getText();
		}
			
	}

	//
	// This code is called every time the controller is updated, which is specified by the framelimiter in main.java
	// When something changes between the focus, keyboard, or mode selectors, this is where it goes.
	void update()
	{
		if (editMode && (view.splash == true))
		{
			// enable editing the text fields and database querying upon player focus loss
			editTextFields(true);

			try
			{ 
				if ((focus != prevFocus) && (prevFocus.playerID > 0) && (prevFocus.codename != ""))
				{


					System.out.println("Sending player info to database: " + prevFocus.playerID + " | " + prevFocus.codename);
					Data.insertPlayer(prevFocus.playerID, prevFocus.codename);

					prevFocus = focus;
				}
			}
			catch (Exception e)
			{
				System.out.println("Non-player field focused...");
				prevFocus = focus;
			}
		}
		else
		{
			// disable text field editing
			//editTextFields(false);
		}
	}

}

