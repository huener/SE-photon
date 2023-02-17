// handles keyboard navigation commands

//import java.awt.event.MouseListener;
//import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;


class Controller implements ActionListener, KeyListener
{
	View view;
	Data data;

	// Arrow keys
	boolean keyUp, keyDown, keyLeft, keyRight;

	// Mode selectors
	boolean editMode;

	public Controller(Data data)
	{
		this.data = data;
		editMode = false;
	}

	void setView(View v)
	{
		view = v;
	}

	//KeyListener methods
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_DOWN: 	keyDown = true; break;
			case KeyEvent.VK_UP: 	keyUp = true; break;
			case KeyEvent.VK_LEFT: 	keyLeft = true; break;
			case KeyEvent.VK_RIGHT:	keyRight = true; break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_DOWN: 	keyDown = false; break;
			case KeyEvent.VK_UP: 	keyUp = false; break;
			case KeyEvent.VK_LEFT: 	keyLeft = false; break;
			case KeyEvent.VK_RIGHT:	keyRight = false; break;

			case KeyEvent.VK_F1:	editMode = !editMode; break;
			case KeyEvent.VK_F3:	break; // TODO: start game
			case KeyEvent.VK_F5:	break; // TODO: PreEntered Games
			case KeyEvent.VK_F8:	break; // TODO: View Game
			case KeyEvent.VK_F10:	break; // TODO: Flick Sync
			case KeyEvent.VK_F12:	break; // TODO: clear game text fields

			case KeyEvent.VK_TAB:
			case KeyEvent.VK_ENTER:	break; // TODO: tab and enter both need to cause the player object to update variables & querey database

		}
	}

	public void keyTyped(KeyEvent e)	{	}
	public void actionPerformed(ActionEvent e)	{	}

	//checks if key is pressed when update is pushed
	void update()
	{
		if (editMode && (view.splash == true))
		{
			// enable editing the text fields
			editTextFields(true);
			// arrow key behavior

		}
		else
		{
			// disable text field editing
			editTextFields(false);
		}
	}

	void editTextFields(Boolean editVal)
	{
		for(int i = 0; i < data.teamRed.length; i++)
		{
			data.teamRed[i].idField.setEditable(editVal);
			data.teamRed[i].nameField.setEditable(editVal);
		}

		for(int i = 0; i < data.teamGreen.length; i++)
		{
			data.teamGreen[i].idField.setEditable(editVal);
			data.teamGreen[i].nameField.setEditable(editVal);
		}
	}

}

