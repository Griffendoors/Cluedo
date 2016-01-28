package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import pieces.Actor;
import board.Board;
import cards.Card;


public class gui {

	private JFrame frameMain;
	private JFrame startUpFrame;

	private JPanel mainFrameContentPanel;

	private JComponent boardDisplay;
	private JComponent infoDisplay;
	private JComponent diceDisplay;
	private JComponent portraitDisplay;

	private JButton buttonAccuse;
	private JButton buttonSuggest;
	private JButton buttonRoll;
	private JButton buttonEndTurn;

	private JScrollPane scrollCharacter;
	private JScrollPane scrollWeapon;
	private JScrollPane scrollRoom;
	private JScrollPane scrollEvents;

	private JTextArea charactersList;
	private JTextArea weaponsList;
	private JTextArea roomsList;
	private JTextArea eventsList;

	private List<JTextArea> playerNameEntries;

	private GridBagConstraints gbc;

	private Board playingBoard;
	private static gui GUI;

	private int mainFrameWidth;
	private int mainFrameHeight;
	private int altWindowWidth;
	private int altWindowHeight;

	private int currentRoll;

	private String events;

	public gui(){
		mainFrameWidth = 800;
		mainFrameHeight = 950;

		altWindowWidth = 400;
		altWindowHeight = 400;

		currentRoll = 0;

		welcomePlayers();
	}
	/**
	 * This method is the initial method that is called when a new gui object is created, ie. When a new game is started.
	 * This method sets up an initial welcome screen with several componenets, including start up instructions, text areas for users to input their names,
	 * and a button for the players to press once they have finished inputing names, and wish to start the game.
	 */
	public void welcomePlayers() {

		startUpFrame = new JFrame("Welcome to Cluedo");
		startUpFrame.setLayout(new GridLayout(8,4));

		JPanel startUpPanel = new JPanel(new GridLayout(1,1));

		//Instructions for game initialisiaton are displayed on the start screen.
		//These are coordinates for the graphics object to draw them on the associated JComponent
		final double tx1 = 140;
		final double ty1 = 20;
		final double tx2 = 12.5;
		final double ty2 = 35;

		JComponent startUpComponent = new JComponent(){
			protected void paintComponent(Graphics g){
				g.setColor(Color.BLACK);
				g.drawString("Welcome to Cluedo!",(int)tx1,(int)ty1);
				g.drawString("Please Enter player names, leaving unused characters unchanged or blank",(int) tx2,(int) ty2);
				;}};

				//JText areas for the start up screen, that will accept text input from the users as their player names
				JTextArea nameA = new JTextArea("Miss Scarlett");
				JTextArea nameB = new JTextArea("Colonel Mustard");
				JTextArea nameC = new JTextArea("Mrs White");
				JTextArea nameD = new JTextArea("Reverend Green");
				JTextArea nameE = new JTextArea("Mrs Peacock");
				JTextArea nameF = new JTextArea("Professor Plum");

				//A simple border to surrond each of the name entry JTextAreas
				Border border = new Border() {

					public void paintBorder(Component c, Graphics g, int x, int y, int width,
							int height) {
						g.drawRect(x, y, width, height);

					}

					public boolean isBorderOpaque() {
						return true;
					}

					public Insets getBorderInsets(Component c) {
						return new Insets(0,0,0,0);
					}
				};
				nameA.setBorder(border);
				nameB.setBorder(border);
				nameC.setBorder(border);
				nameD.setBorder(border);
				nameE.setBorder(border);
				nameF.setBorder(border);


				//A JButton with a startGameButListen Listener is added to the start up window
				JButton startUpStartGame = new JButton("Lets go!");
				startUpStartGame.addActionListener(new startGameButListen(this));


				//Add the elements to the content Panel , and then to the start up window
				startUpFrame.add(startUpPanel);
				startUpPanel.add(startUpComponent);
				startUpFrame.add(nameA);
				startUpFrame.add(nameB);
				startUpFrame.add(nameC);
				startUpFrame.add(nameD);
				startUpFrame.add(nameE);
				startUpFrame.add(nameF);
				startUpFrame.add(startUpStartGame);

				//Set the start up windows initial display variables
				JFrame.setDefaultLookAndFeelDecorated(true);
				startUpFrame.setSize(altWindowWidth,altWindowHeight);
				startUpFrame.setVisible(true);
				startUpFrame.setResizable(true);


				//When startUpFrame is created and appears on screen, it is centred on the screen
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) ((dimension.getWidth() - startUpFrame.getWidth()) / 2);
				int y = (int) ((dimension.getHeight() - startUpFrame.getHeight()) / 2);
				startUpFrame.setLocation(x, y);

				//JText Areas that will be retrieved by startGameButListen
				//The startGameButListen will extract their text (user names) and use the String values to construct a new Board Object.
				playerNameEntries = new ArrayList<JTextArea>();
				playerNameEntries.add(nameA);
				playerNameEntries.add(nameB);
				playerNameEntries.add(nameC);
				playerNameEntries.add(nameD);
				playerNameEntries.add(nameE);
				playerNameEntries.add(nameF);
	}

	/**
	 * This method can be regarded as a auxillary constructor for the gui class, however it is only called once a user presses the 'Lets go!' button in the start up window.
	 * Pressing that button will dispose of the start up window and call this method.
	 * This method calls other methods to setup the main game window interface, set up the game board object and create the main window interface's asociated components.
	 * Additionally, this method calls the methods, to lay out all of the main game windows elements and add listeners the main game windows buttons.
	 * Finally this Method begins the game, by calling the startTurn() method.
	 * This method accepts a List strings, called 'names' as a parameter. These are provided to the method by the start up window, once the 'Lets go!' button is pressed.
	 * @param names
	 */
	public void gameStarted(List<String>names){
		setupWindow();
		setupBoard(names);
		createMainWindowElements();
		setFrameLayout();
		addListeners();
		startTurn();
	}
	/**
	 * This method is responsible for construction the main frame window, that will contain all of the games visual, and interactive elements
	 */
	public void setupWindow(){
		frameMain = new JFrame("Cluedo");
		frameMain.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		frameMain.setSize(mainFrameWidth,mainFrameHeight);

		//When the main window Frame is displayed, its position is set to the middle of the screen.
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frameMain.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frameMain.getHeight()) / 2);
		frameMain.setLocation(x, y);
		frameMain.setResizable(true);
		frameMain.setVisible(true);
	}

	/**
	 * Initialise the playing board for the game, with the provided list of users names as Strings.
	 * @param names
	 */
	public void setupBoard(List<String> names) {
		playingBoard =  new Board(names);
	}

	/**
	 * Before Elements are added to the game window, there layout particulars are specified using the 'Grid Bag Constraints' Object.
	 */
	private void setFrameLayout() {

		// Adding the boardDisplay
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 4;
		gbc.weighty = 0.75;
		gbc.ipadx = 600;
		gbc.ipady = 505;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		mainFrameContentPanel.add(boardDisplay, gbc);
		gbc.ipadx = 0;
		gbc.ipady = 0;
		gbc.gridheight = 1;

		// Adding the infoDisplay
		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 5;
		gbc.ipadx = 400;
		gbc.ipady = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		mainFrameContentPanel.add(infoDisplay, gbc);
		gbc.gridheight = 1;

		// Adding the EventsList
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.gridwidth = 1;
		gbc.gridheight = 5;
		gbc.ipadx = 150;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.EAST;
		mainFrameContentPanel.add(scrollEvents, gbc);
		gbc.gridheight = 1;
		gbc.gridwidth = 1;

		//Adding portrait
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipady = 100;
		gbc.ipadx = 72;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(portraitDisplay, gbc);
		gbc.ipady = 0;

		// Adding the CharactersList
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(scrollCharacter, gbc);

		// Adding the WeaponsList
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(scrollWeapon, gbc);

		// Adding the RoomsList
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(scrollRoom, gbc);

		//dce display
		gbc.ipady = 12;
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(diceDisplay, gbc);
		gbc.ipady = 0;

		// Adding the buttonRoll
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(buttonRoll, gbc);

		// Adding the buttonAccuse
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(buttonAccuse, gbc);

		// Adding the buttonSuggest
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(buttonSuggest, gbc);

		// Adding the buttonEndTurn
		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTH;
		mainFrameContentPanel.add(buttonEndTurn, gbc);
	}


	/**
	 * This Method constructs all of the elements that will be present in the main frame window, but does not add them (Except the menu Bar)
	 */
	@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
	private void createMainWindowElements() {
		mainFrameContentPanel = new JPanel(new GridBagLayout());
		mainFrameContentPanel.setBackground(Color.WHITE);
		gbc = new GridBagConstraints();
		frameMain.setContentPane(mainFrameContentPanel);

		boardDisplay = new JComponent(){
			protected void paintComponent(Graphics g){drawDisplay(g);}
		};
		infoDisplay = new JComponent(){
			protected void paintComponent(Graphics g){drawInfo(g);}
		};
		diceDisplay = new JComponent(){
			protected void paintComponent(Graphics g){drawDice(g);}
		};
		portraitDisplay = new JComponent(){
			protected void paintComponent(Graphics g){drawPortrait(g);}
		};

		buttonAccuse = new JButton("Accuse");
		buttonSuggest = new JButton("Suggest");
		buttonRoll = new JButton("Roll Dice");
		buttonEndTurn = new JButton("End Turn");

		charactersList = new JTextArea();
		weaponsList = new JTextArea();
		roomsList = new JTextArea();
		eventsList = new JTextArea();
		eventsList.setEditable(false);

		scrollCharacter = new JScrollPane(charactersList);
		scrollWeapon = new JScrollPane(weaponsList);
		scrollRoom = new JScrollPane(roomsList);
		scrollEvents = new JScrollPane(eventsList);

		scrollCharacter.setBorder(BorderFactory.createTitledBorder("Det. Notes - Characters"));
		scrollWeapon.setBorder(BorderFactory.createTitledBorder("Det. Notes - Weapons"));
		scrollRoom.setBorder(BorderFactory.createTitledBorder("Det. Notes - Rooms"));
		scrollEvents.setBorder(BorderFactory.createTitledBorder("Previous events:"));



		//add the menu bar to the main frame window
		JMenuBar menu = new JMenuBar();
		JMenu m = new JMenu("File");
		JMenuItem resetItem = new JMenuItem("Reset");
		menu.add(m);
		m.add(resetItem);
		frameMain.setJMenuBar(menu);

		//The reset method is called, if a user presses the reset button in the JMenu
		resetItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});




	}
	/**
	 * This method adds the custom Listener classes to each of the games non-trivial, or non-nested buttons.
	 * This method also adds the custom Listeners for the board display, and the card selection during a suggestion.
	 */
	private void addListeners(){
		boardDisplay.addMouseListener(new playBoardMouseListen(this));
		buttonEndTurn.addActionListener(new endTurnButListen(this));
		buttonSuggest.addActionListener(new startSugButListen(this));
		buttonAccuse.addActionListener(new startAcuButListen(this));
		buttonRoll.addActionListener(new diceRollButListen(this));

		setUpCloseOperation();
	}
	/**
	 *This method is adding an operation to the main game window. The operation is triggered when the user presses the exit button.
	 *The Operation sets up a new minimal, cenetered frame that contains 2 radio buttons and a JButton.
	 *The user is prompted to confirm their choice for exiting the game, by selecting a radio button (Yes or No) and confirming by pressing the JButton.
	 */
	private void setUpCloseOperation(){
		frameMain.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){

				//A minimal confirmation window is created
				final JFrame closeFrame = new JFrame("Exit?");
				JPanel closeContent = new JPanel(new FlowLayout());
				closeFrame.setContentPane(closeContent);

				//J Radio Buttons 'Yes' and 'No' are created
				final JRadioButton yes = new JRadioButton("Yes");
				final JRadioButton no = new JRadioButton("No");
				ButtonGroup group = new ButtonGroup();
				group.add(yes);
				group.add(no);


				closeContent.add(yes);
				closeContent.add(no);

				yes.setSelected(false);
				no.setSelected(false);

				//Action listeners added to the JButtons
				yes.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						no.setSelected(false);
						yes.setSelected(true);

					}
				});

				no.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						yes.setSelected(false);
						no.setSelected(true);

					}
				});

				JButton close = new JButton("Confirm");
				close.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if(yes.isSelected()){
							System.exit(0);
						}
						else{
							closeFrame.dispose();
						}

					}
				});

				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				int x = (int) ((dimension.getWidth() - closeFrame.getWidth()) / 2);
				int y = (int) ((dimension.getHeight() - closeFrame.getHeight()) / 2);
				closeFrame.setLocation(x, y);

				closeContent.add(close);
				closeFrame.setSize(500,500);
				closeFrame.setResizable(false);
				closeFrame.setVisible(true);
				closeFrame.pack();
				closeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});

	}



	/**
	 *This method is called initially when the game is started, to initiate game play.
	 *Additionally, it is called every time a turn has ended (after the endTurn() method has finished).
	 *This method updates variables associated with the current players character, including - a reference to the current square they are positioned on,
	 *Retrieves their detective notes and displays them on screen, calls the newTurn() method, and updates all of the displays.
	 */
	public void startTurn(){

		playingBoard.getCurrentActor().setOldSquare(playingBoard.getCurrentActor().getSquare());

		List<String> currentDetNotes =  playingBoard.getCurrentActor().getDetectiveNotes();
		charactersList.setText(currentDetNotes.get(0));
		weaponsList.setText(currentDetNotes.get(1));
		roomsList.setText(currentDetNotes.get(2));

		newTurn();

		repaintAll();
	}

	/**
	 * This method is called either when the player presses the main frame window's 'End Turn' Button, as well as when game logic dictates the current players turn is over.
	 * This method saves the players detective notes to be retrieved again on their next turn, as well as calling endTurn() from the associated playing board.
	 * Finally this method starts the next players turn by calling startTurn().
	 */
	public void endTurn() {
		List<String> currentDetNotes = new ArrayList<String>();
		currentDetNotes.add(charactersList.getText());
		currentDetNotes.add(weaponsList.getText());
		currentDetNotes.add(roomsList.getText());
		playingBoard.getCurrentActor().setDetectiveNotes(currentDetNotes);

		playingBoard.endTurn();

		//updating the dice roll to 0 is neccesary to allow the next player to roll the dice, it tells the board the dice has not yet been rolled
		currentRoll = 0;
		repaintAll();
		startTurn();
	}

	/**
	 * This methods primary function is to create privacy between players as their turns alternate, it is called when a players turn is started / the last players turn ends.
	 * The game interface is hidden from view and another window is displayed, prompting the next player in the turn cycle that it is their turn.
	 * When the new player confirms they are ready to take their turn, the main game window interface is redisplayed, under the new players control, showing all of the respective
	 * details of the new player ie. their cards and character portrait.
	 *
	 */
	public void newTurn(){
		frameMain.setVisible(false);

		String nextPlayer = playingBoard.getCurrentActor().getUser();

		final JFrame newTurnFrame = new JFrame("New Turn");
		newTurnFrame.setVisible(true);
		newTurnFrame.setSize(mainFrameHeight/2,mainFrameWidth/2);

		JPanel panel = new JPanel(new FlowLayout());

		JButton ready = new JButton(nextPlayer+", Click to take Turn");
		ready.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
				newTurnFrame.dispose();
				frameMain.setVisible(true);
			}});

		panel.add(ready);
		newTurnFrame.add(panel);
		newTurnFrame.pack();

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - newTurnFrame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - newTurnFrame.getHeight()) / 2);
		newTurnFrame.setLocation(x, y);
	}

	/**
	 * draw method for the JComponent boardDisplay
	 * This method passes a graphics object to the playing board to draw itself.
	 * @param g
	 */
	private void drawDisplay(Graphics g){
		g.setColor(Color.BLACK);
		playingBoard.draw(g);

	}

	/**
	 * draw method for the JComponent infoDisplay
	 * This method draws all of the current players cards in the infoDisplay, at the bottom of the game window ie. the cards the player has in their hand.
	 * @param g
	 */
	private void drawInfo(Graphics g){
		g.setColor(Color.BLACK);

		int cardPlaceX = 105; //starting point for card
		int cardPlaceY = 20; //always 0
		int cardGap = 48;	// gap between cards on display

		Actor a = playingBoard.getCurrentActor();
		List<Card>cards = a.getCards();
		for(int i = 0; i < cards.size(); i++){
			if(i<3){
				cards.get(i).draw(cardGap+(cardPlaceX*i),cardPlaceY,g);
			}
			else{ //More than 3 cards in players hand - adjust x & y values to draw 2 rows
				cards.get(i).draw(cardGap+(cardPlaceX*(i-3)),cardPlaceY*7,g);
			}
		}
	}

	/**
	 * Draw the dice roll in the diceDisplay.
	 */
	private void drawDice(Graphics g){


		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0,0, (int)portraitDisplay.getSize().getWidth(), (int)portraitDisplay.getSize().getHeight());
		if(currentRoll==0) return;
		g.setColor(Color.BLACK);
		g.drawString("You rolled "+currentRoll+"",((int)diceDisplay.getSize().getWidth())/4,((int)diceDisplay.getSize().getHeight())/2);
	}

	/**
	 * draw the current Players character portraint in the portraitDisplay in the game Window.
	 */
	private void drawPortrait(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(0,0, (int)portraitDisplay.getSize().getWidth(), (int)portraitDisplay.getSize().getHeight(),10,10);
		playingBoard.getCurrentActor().drawPortrait(g, (int)portraitDisplay.getSize().getWidth(), (int)portraitDisplay.getSize().getHeight());
	}

	/**
	 * This method is of public visibility, that can be called from anywhere.
	 * It calls repaint for each of the JComponents in the game window, updating their display.
	 */
	public void repaintAll() {
		boardDisplay.repaint();
		infoDisplay.repaint();
		diceDisplay.repaint();
		portraitDisplay.repaint();
	}


	/**
	 * This method receives an Integer and displays it as the 'dice roll' on screen.
	 * This integer is then passed to the associated Board to calculate valid player moves.
	 * @param roll
	 */
	public void setCurrentRoll(int roll){
		currentRoll = roll;
		diceDisplay.repaint();
		getPlayingBoard().checkValidMoves(currentRoll);
		boardDisplay.repaint();
	}

	/**
	 * This method creates a new gui object, and resets the game back to its start position.
	 */
	public void reset(){
		frameMain.setVisible(false);
		GUI = new gui();
	}
	public int getCurrentRoll(){
		return currentRoll;
	}

	/**
	 * This method adds a suggestion that has been made's information to the eventsList in the game window.
	 * @param s
	 */
	public void addToEvents(String s){
		events = s + events;
		eventsList.setText(events);
	}

	public JFrame getStartUpFrame() {
		return startUpFrame;
	}

	public JComponent getBoardDisplay() {
		return boardDisplay;
	}

	public List<JTextArea> getPlayerNameEntries(){
		return playerNameEntries;
	}

	public int getAltWindowWidth() {
		return altWindowWidth;
	}

	public int getAltWindowHeight() {
		return altWindowHeight;
	}

	public Board getPlayingBoard() {
		return playingBoard;
	}

	public static void main(String[] args) {
		GUI = new gui();
	}

	public JFrame getFrameMain() {
		return frameMain;
	}



}