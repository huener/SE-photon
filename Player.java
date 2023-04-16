import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

// Container class for player, an array of these Player objects will be utilized for gameplay (array in Main.java??)
class Player
{
	String codename;
	int playerID;
	String tableIndex;
	JTextField idField, nameField;
	Dimension idSize, nameSize;
	JTextArea playerNumText;
	int score;
	Boolean idIn, nameIn;
	public Player(String codename, int playerID, String tableIndex)
	{
		this.codename = codename;
		this.playerID = playerID;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
		idIn = false;
		nameIn = false;
	}

	public Player(String codename, String tableIndex)
	{
		this.codename = codename;
		this.playerID = -1;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
		idIn = false;
		nameIn = false;
	}

	public Player(int playerID, String tableIndex)
	{
		this.codename = "";
		this.playerID = playerID;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
		idIn = false;
		nameIn = false;
	}

	public Player(String tableIndex)
	{
		this.codename = "";
		this.playerID = -1;
		this.tableIndex = tableIndex;
		setTextFields();
		score = 0;
		idIn = false;
		nameIn = false;
	}

	void setTextFields()
	{
		//set up text boxes associated with each player slot
		idField = new JTextField(6);
		nameField = new JTextField(15);
		idField.setName('I' + tableIndex);
		nameField.setName('N' + tableIndex);

		playerNumText = new JTextArea();
		playerNumText.setForeground(Color.WHITE);
		playerNumText.setBackground(Color.BLACK);
		playerNumText.setEditable(false);
		playerNumText.setFont(new java.awt.Font("Arial", Font.PLAIN, 15));
		idSize = idField.getPreferredSize();
		nameSize = nameField.getPreferredSize();
	}
	//sets idIn to true
	void setIdIn()
	{
		idIn = true;
	}
	//sets nameIn to true
	void setNameIn()
	{
		nameIn = true;
	}
	//sets text field editability
	//nameField can only be editable when id has been input, idField can only be editable when it hasn't been input already
	void textFieldsEditable(Boolean editVal)
	{
		idField.setEditable((editVal && !idIn) || (idIn && nameIn));
		idField.setRequestFocusEnabled((editVal && !idIn) || (idIn && nameIn));
		nameField.setEditable(editVal && idIn && !nameIn);
		nameField.setFocusable(editVal && (!nameIn || !idIn));
		nameField.setRequestFocusEnabled(editVal && (!nameIn || !idIn));
	}
	void resetTextFields()
	{
		idIn = false;
		nameIn = false;
		idField.setText("");
		nameField.setText("");
	}
	//checks if an id exists in the database, sets name field as such
	void queryForName()
	{
		if(Data.checkForID(Integer.parseInt(idField.getText())))
		{
			nameField.setText(Data.getCodeName(Integer.parseInt(idField.getText())));
			nameIn = true;
		}

	}
}
