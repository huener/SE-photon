import javax.swing.JTextField;
import java.awt.Dimension;

// Container class for player, an array of these Player objects will be utilized for gameplay (array in Main.java??)
class Player
{
	String codename;
	int playerID;
	JTextField idField, nameField;
	Dimension idSize, nameSize;

	public Player(String codename, int playerID)
	{
		this.codename = codename;
		this.playerID = playerID;
		setTextFields();
	}

	public Player(String codename)
	{
		this.codename = codename;
		this.playerID = -1;
		setTextFields();
	}

	public Player(int playerID)
	{
		this.codename = "";
		this.playerID = playerID;
		setTextFields();
	}

	public Player()
	{
		this.codename = "";
		this.playerID = -1;
		setTextFields();
	}

	void setTextFields()
	{
		//set up text boxes associated with each player slot
		idField = new JTextField(6);
		nameField = new JTextField(15);
		idSize = idField.getPreferredSize();
		nameSize = nameField.getPreferredSize();
	}
}