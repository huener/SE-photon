<img src="https://github.com/huener/SE-photon/blob/main/logo1.jpg" width="790" height="335">

# About

The main software for Photon Laser Tag.

# Developers

+ Theavan Saitang     
+ Gael Brice     
+ Edith Avalos-Perez     
+ Kayla Machamer     
+ Phillip Drake     
+ Avery Batson


<body><pre>
|\   \\\\__     o
| \_/    o \    o 
\> _   (( <_  oo  
| / \__+___/      
|/     |/
</pre></body>
 
- Press F1 to enable edit mode. 
- Click navigation works on the player text fields but not the menu buttons on the bottom of the screen. 
  > Tabbing on the keyboard works as well for the most part, however a known bug is that tab navigation is broken for the top left cell.
- The program will send player information to the database once another player's text field is focused by clicking or tabbing. 
  > Then the program pulls the information from the previously focused player text fields and updates the player information, with an integer check for the player ID       field.
- If the player information is present and does not already exist in the database, then the program finally sends the player info to the database.
