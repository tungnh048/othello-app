/**
 * File name: OthelloViewController.java
 * Author: Hoang Tung Nguyen, 040977322 | Manh Nghia Duong, 040971252
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 1 Part 2
 * Date: November 15, 2020
 * Professor: Daniel Cormier
 * Purpose: This class implement the MVC model to create a GUI of othello game
 * Class list: OthelloViewController, Controller
 */
package othello;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class create the main Frame of the whole game
 *
 * @author Hoang Tung Nguyen
 * @version 1.0
 * @see othello
 */
public class OthelloViewController extends JFrame {

    private final int BLACK = OthelloModel.BLACK;
    private final int WHITE = OthelloModel.WHITE;
    private final int EMPTY = OthelloModel.EMPTY;

    private JLabel[][] boardPieces;
    private JLabel p1Pic;
    private JLabel p2Pic;
    private JPanel p1PiecesPanel;
    private JPanel p2PiecesPanel;
    private JTextArea pinkBoxText;
    private OthelloModel model = new OthelloModel();
    private OthelloNetworkModalViewController networkDialog = new OthelloNetworkModalViewController(this);
    private JCheckBox validMoves;
    private JPanel root;
    private JButton submit;
    private ButtonGroup debugGroup;
    private JMenuItem menuFileExit;
    private JMenuItem menuFileNewGame;
    private JMenuItem menuFileLoad;
    private JMenuItem menuFileSave;
    private ButtonGroup skinGroup;
    private JMenuItem menuHelpAbout;
    private ImageIcon blackIcon;
    private ImageIcon whiteIcon;
    private Image image, newimg;
    private JButton temp;
    private JRadioButtonMenuItem subDebugNorG, subDebugCorT, subDebugSide, subDebug1x, subDebug2x, subDebugEmpty, subDebugInnerSqr;
    private JRadioButtonMenuItem subReskinNor, subReskinCD, subReskinPB;
    private JMenuItem menuNetworkNewConnection, menuNetworkDisconnect;
    private String[][] boardLabels = { //labels
        {"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "1"},
        {"A2", "B2", "C2", "D2", "E2", "F2", "G2", "H2", "2"},
        {"A3", "B3", "C3", "D3", "E3", "F3", "G3", "H3", "3"},
        {"A4", "B4", "C4", "D4", "E4", "F4", "G4", "H4", "4"},
        {"A5", "B5", "C5", "D5", "E5", "F5", "G5", "H5", "5"},
        {"A6", "B6", "C6", "D6", "E6", "F6", "G6", "H6", "6"},
        {"A7", "B7", "C7", "D7", "E7", "F7", "G7", "H7", "7"},
        {"A8", "B8", "C8", "D8", "E8", "F8", "G8", "H8", "8"},
        {"A", "B", "C", "D", "E", "F", "G", "H", "move"}
    };

    private int currentPlayer = BLACK;
    private int x = -1, y = -1;
    private int buttonPressCounter = 0;
    private static final int DIMENSION = 8; //board 8x8

    /**
     * This class have basic codes that read user's inputs or commands
     */
    private class Controller implements ActionListener {

        /**
         * Method print out to console action commands that user take
         *
         * @param e - action variable return void
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            EventQueue.invokeLater(() -> {
                //Show current player valid moves
                //if showValidMove check box is ticked, show it on the board
                if (e.getSource().equals(validMoves)) {
                    showValidMoves();
                    root.updateUI();
                }
                //player choose new game
                if (e.getSource().equals(menuFileNewGame)) {
                    switchScenario(0);
                }
                //player choose normal scenario
                if (e.getSource().equals(subDebugNorG)) {
                    switchScenario(0);
                }
                //player choose corner test
                if (e.getSource().equals(subDebugCorT)) {
                    switchScenario(1);
                }
                //player choose side test
                if (e.getSource().equals(subDebugSide)) {
                    switchScenario(2);
                }
                //player choose 1x capture test
                if (e.getSource().equals(subDebug1x)) {
                    switchScenario(3);
                }
                //player choose 2x capture test
                if (e.getSource().equals(subDebug2x)) {
                    switchScenario(4);
                }
                //player choose empty test
                if (e.getSource().equals(subDebugEmpty)) {
                    switchScenario(5);
                }
                //player choose inner square test
                if (e.getSource().equals(subDebugInnerSqr)) {
                    switchScenario(6);
                }
                //player choose to exit
                if (e.getSource().equals(menuFileExit)) {
                    System.exit(0);
                }
                //player choose about button menu
                if (e.getSource().equals(menuHelpAbout)) {
                    JOptionPane.showMessageDialog(root, "Othello Game\n" + "November 2020");
                }
                //player choose to change skin to default
                if (e.getSource().equals(subReskinNor)) {
                    switchSkins(1);
                }
                //player choose to change skin to pumpkins and bats
                if (e.getSource().equals(subReskinPB)) {
                    switchSkins(2);
                }
                //player choose to change skin to cat and dogs
                if (e.getSource().equals(subReskinCD)) {
                    switchSkins(3);
                }
                //player choose disconnect
                if (e.getSource().equals(menuNetworkDisconnect)) {
                    pinkBoxText.append("Disconnecting from network connection.\n");
                }
                //player choose new connection
                if (e.getSource().equals(menuNetworkNewConnection)) {
                    networkDialog.setSize(310, 200);
                    Point thisLocation = getLocation();
                    Dimension parentSize = root.getSize();
                    Dimension dialogSize = networkDialog.getSize();
                    
                    int offsetX = (parentSize.width-dialogSize.width)/2+thisLocation.x;
                    int offsetY = (parentSize.height-dialogSize.height)/2+thisLocation.y;
                    networkDialog.setLocation(offsetX, offsetY);
                    networkDialog.setVisible(true);
                    try {
                        String address = networkDialog.getAddress();
                        int portNumber = networkDialog.getPort();
                        String name = networkDialog.getName();
                        pinkBoxText.append("Negotiating Connection to localhost on port " + portNumber + "\n");
                        OthelloNetworkController ONC = new OthelloNetworkController(address, portNumber, name);
                        pinkBoxText.append("Connection Successful\n"
                                + "Welcome to Nguyen's and Duong's Othello Server\n"
                                + "Use '/help' for commands\n");
                        menuNetworkDisconnect.setEnabled(true);
                        menuNetworkNewConnection.setEnabled(false);
                        submit.setEnabled(true);
                    } catch (Exception ex) {
                        pinkBoxText.append("Error: Connection refused. Server is not available. Check port or restart server\n");
                    }
                    pinkBoxText.append(networkDialog.getMessage());
                }
                //player pressed disconnect button
                if (e.getSource().equals(menuNetworkDisconnect)) {
                    pinkBoxText.append("Disconnected from server\n"); 
                    
                }
                //player pressed skip button
                if (e.getActionCommand().equalsIgnoreCase("skip")) {
                    toggleCurrentPlayer();
                    toggleMoveSkipButton(0);
                    clearTable();
                    setTable(model.getBoard());
                    showValidMoves();
                }
                //player make a move
                playerMove(e);
                updateChips();

                //update the GUI after done with logic
                root.updateUI();
            });
        }
    }

    /**
     * Default constructor
     */
    public OthelloViewController() {
    }

    /**
     * Prepare the whole GUI for user to interact
     */
    public void createGUI() {
        //set up the main frame

        this.setTitle("Othello Game");
        this.setSize(1010, 620);
        this.setLocationRelativeTo(null);
        this.setVisible(true); //set for JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        //draw the game board
        //board components:
        boardPieces = new JLabel[9][9]; //buttons on board
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(9, 9));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardPieces[i][j] = new JLabel();
                boardPieces[i][j].setLayout(new GridBagLayout());
                boardPieces[i][j].setOpaque(true);
                if (i == 8 || j == 8) {
                    if (i == 8 && j == 8) {
                        temp = createButton(boardLabels[i][j], boardLabels[i][j], Color.BLACK, Color.WHITE, new Controller());
                        temp.setFont(new Font("Arial", Font.PLAIN, 10));
                    } else {
                        temp = createButton(boardLabels[i][j], boardLabels[i][j], Color.BLACK, Color.LIGHT_GRAY, new Controller());
                    }
                    temp.setPreferredSize(new Dimension(60, 60));
                    boardPieces[i][j].add(temp);
                } else {
                    if ((i + j) % 2 == 0) {
                        boardPieces[i][j].setBackground(Color.BLACK);
                        boardPieces[i][j].setBorder(BorderFactory.createEmptyBorder());
                    } else {
                        boardPieces[i][j].setBackground(Color.WHITE);
                        boardPieces[i][j].setBorder(BorderFactory.createEmptyBorder());
                    }
                }
                boardPieces[i][j].setPreferredSize(new Dimension(60, 60));
                board.add(boardPieces[i][j]);
            }
        }

        //set layout for the whole frame
        root = new JPanel(new BorderLayout(5, 5)); //First layer
        root.setBackground(Color.GRAY);
        root.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));

        //create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenu menuGame = new JMenu("Game");
        JMenu menuNetwork = new JMenu("Network");
        JMenu menuHelp = new JMenu("Help");
        
        //create menuFile components
        menuFileNewGame = new JMenuItem("New Game");
        menuFileNewGame.addActionListener(new Controller());
        menuFileLoad = new JMenuItem("Load");
        menuFileLoad.addActionListener(new Controller());
        menuFileLoad.setEnabled(false);
        menuFileSave = new JMenuItem("Save");
        menuFileSave.addActionListener(new Controller());
        menuFileSave.setEnabled(false);
        menuFileExit = new JMenuItem("Exit");
        menuFileExit.addActionListener(new Controller());
        
        //create submenus for menuGame
        JMenu subMenuGameReskin = new JMenu("Reskin Pieces");
        JMenu subMenuGameDebugScenario = new JMenu("Debug Scenarios");
        
        //create menuNetwork items
        menuNetworkNewConnection = new JMenuItem("New Connection");
        menuNetworkNewConnection.addActionListener(new Controller());
        menuNetworkDisconnect = new JMenuItem("Disconnect");
        menuNetworkDisconnect.setEnabled(false);
        menuNetworkDisconnect.addActionListener(new Controller());
        
        //create menuHelp components
        menuHelpAbout = new JMenuItem("About");
        menuHelpAbout.addActionListener(new Controller());

        //creating radio buttons for ReSkin Pieces
        skinGroup = new ButtonGroup();
        subReskinNor = new JRadioButtonMenuItem("Normal Pieces");
        skinGroup.add(subReskinNor);
        subReskinNor.addActionListener(new Controller());
        subReskinNor.setSelected(true);
        subReskinCD = new JRadioButtonMenuItem("Cats vs. Dogs");
        subReskinCD.addActionListener(new Controller());
        skinGroup.add(subReskinCD);
        subReskinPB = new JRadioButtonMenuItem("Pumpkins vs. Bats");
        subReskinPB.addActionListener(new Controller());
        skinGroup.add(subReskinPB);

        //creating radio buttons for Debug Scenarios
        debugGroup = new ButtonGroup();
        subDebugNorG = new JRadioButtonMenuItem("Normal Game");
        subDebugNorG.addActionListener(new Controller());
        subDebugNorG.setSelected(true);
        debugGroup.add(subDebugNorG);
        subDebugCorT = new JRadioButtonMenuItem("Corner Test");
        subDebugCorT.addActionListener(new Controller());
        debugGroup.add(subDebugCorT);
        subDebugSide = new JRadioButtonMenuItem("Side Test");
        subDebugSide.addActionListener(new Controller());
        debugGroup.add(subDebugSide);
        subDebug1x = new JRadioButtonMenuItem("1x Capture Test");
        subDebug1x.addActionListener(new Controller());
        debugGroup.add(subDebug1x);
        subDebug2x = new JRadioButtonMenuItem("2x Capture Test");
        subDebug2x.addActionListener(new Controller());
        debugGroup.add(subDebug2x);
        subDebugEmpty = new JRadioButtonMenuItem("Empty Board");
        subDebugEmpty.addActionListener(new Controller());
        debugGroup.add(subDebugEmpty);
        subDebugInnerSqr = new JRadioButtonMenuItem("Inner Square Test");
        subDebugInnerSqr.addActionListener(new Controller());
        debugGroup.add(subDebugInnerSqr);

        //Adding menu bar components
        menuBar.add(menuFile);
        menuBar.add(menuGame);
        menuBar.add(menuNetwork);
        menuBar.add(menuHelp);
        //Adding menu File components
        menuFile.add(menuFileNewGame);
        menuFile.add(menuFileLoad);
        menuFile.add(menuFileSave);
        menuFile.add(menuFileExit);
        //Adding menu Game components
        menuGame.add(subMenuGameReskin);
        menuGame.add(subMenuGameDebugScenario);
        //Adding submenu Reskin components
        subMenuGameReskin.add(subReskinNor);
        subMenuGameReskin.add(subReskinCD);
        subMenuGameReskin.add(subReskinPB);
        //Adding submenu Debug components
        subMenuGameDebugScenario.add(subDebugNorG);
        subMenuGameDebugScenario.add(subDebugCorT);
        subMenuGameDebugScenario.add(subDebugSide);
        subMenuGameDebugScenario.add(subDebug1x);
        subMenuGameDebugScenario.add(subDebug2x);
        subMenuGameDebugScenario.add(subDebugEmpty);
        subMenuGameDebugScenario.add(subDebugInnerSqr);
        //Adding items to menu Network
        menuNetwork.add(menuNetworkNewConnection);
        menuNetwork.add(menuNetworkDisconnect);
        //Adding menu Help components
        menuHelp.add(menuHelpAbout);

        JPanel main = new JPanel(new BorderLayout(5, 5)); //Second layer contains board, RIGHT JPanel named rightSide
        main.setBackground(Color.GRAY);
        JPanel rightSide = new JPanel(new BorderLayout(5, 5)); //third layer contains checkbox, pinkBox, playerPieces
        rightSide.setBackground(Color.GRAY);

        JPanel checkBox = new JPanel(new BorderLayout(0, 0)); //forth layer

        JPanel pinkBox = new JPanel(new FlowLayout(0, 5, 1)); //forth layer
        pinkBox.setBackground(Color.PINK);
        pinkBoxText = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(pinkBoxText);
        scrollPane.setPreferredSize(new Dimension(450, 415));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        pinkBoxText.setBackground(Color.PINK);
        pinkBoxText.setText("Player 1 initialized with 2 pieces.\nPlayer 2 initialized with 2 pieces.\n");

        //set up the lower right corner of the frame
        JPanel playerPiece = new JPanel(new BorderLayout(0, 0)); //forth layer
        JPanel playerLabels = new JPanel(new GridLayout(2, 1));
        p1PiecesPanel = new JPanel(new GridBagLayout());
        p2PiecesPanel = new JPanel(new GridBagLayout());
        JLabel p1Pieces = new JLabel("Player 1 Pieces:");
        JLabel p2Pieces = new JLabel("Player 2 Pieces:");

        JPanel playerPics = new JPanel(new BorderLayout(0, 0));
        blackIcon = new ImageIcon(getClass().getResource("black_s.png")); // all black's pieces
        whiteIcon = new ImageIcon(getClass().getResource("white_s.png")); // all white's pieces
        p1Pic = new JLabel(blackIcon); //add icon to player's piece
        p2Pic = new JLabel(whiteIcon); //add icon to player's piece
        p1Pic.setText("2 ");
        p1Pic.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p2Pic.setText("2 ");
        p2Pic.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //create check box
        validMoves = new JCheckBox("Show Valid Moves");
        validMoves.addActionListener(new Controller());

        //create panel for the bottom part
        JPanel bottomField = new JPanel(new BorderLayout());

        //create text field
        JTextField textField = new JTextField();
        textField.setBackground(Color.red);

        submit = createButton("Submit", "Submit pressed", Color.RED, Color.BLACK, new Controller());
        submit.setEnabled(false);

        this.setJMenuBar(menuBar);
        root.add(main, BorderLayout.CENTER);    //put a JPanel over the whole frame for easy layout and flow control
        root.add(bottomField, BorderLayout.SOUTH);    //the text field below
        bottomField.add(textField, BorderLayout.LINE_START);
        bottomField.add(submit, BorderLayout.LINE_END);
        main.add(rightSide, BorderLayout.CENTER);     //add the third layer into second layer to the right
        main.add(board, BorderLayout.WEST);     //board currently at left of 2nd layer
        rightSide.add(checkBox, BorderLayout.NORTH); //add checkbox to the top of rightSide panel
        rightSide.add(pinkBox, BorderLayout.CENTER); //add pinkbox to the center
        rightSide.add(playerPiece, BorderLayout.SOUTH); //add playerPiece field to the bottom of rightSide
        scrollPane.setVisible(true);
        pinkBox.add(scrollPane);
        checkBox.add(validMoves);

        playerPiece.add(playerPics, BorderLayout.EAST);
        playerPics.add(p1Pic, BorderLayout.PAGE_START);
        playerPics.add(p2Pic, BorderLayout.AFTER_LINE_ENDS);
        playerPiece.add(playerLabels, BorderLayout.WEST);
        playerLabels.add(p1PiecesPanel);
        playerLabels.add(p2PiecesPanel);
        p1PiecesPanel.add(p1Pieces);
        p2PiecesPanel.add(p2Pieces);

        //set icons on game board (temporary for this assignment only)
        getContentPane().add(root);     //  get contents for the frame to display
        model.initialize(0);

        EventQueue.invokeLater(() -> {
            //set the table to the default state
            setTable(model.getBoard());

            //show the current player on the UI (black first)
            p1PiecesPanel.setBackground(Color.YELLOW);
        });
    }
    
/**
 * this method helps user to switch scenario (game modes) 
 * @param scenario the number that matches with the game mode
 *       0 - DEFAULT
 *       1 - CORNER TEST
 *       2 - SIDE TEST
 *       3 - 1x CAPTURE TEST
 *       4 - 2x CAPTURE TEST
 *       5 - EMPTY TEST
 *       6 - INNER TEST
 * return void
 */
    private void switchScenario(int scenario) {
        model.initialize(scenario);
        clearTable();
        setTable(model.getBoard());
        if (currentPlayer == WHITE) {
            toggleCurrentPlayer();
        }
        x = -1;
        y = -1;
        buttonPressCounter = 0;
        validMoves.setSelected(false);
        subDebugNorG.setSelected(true);
        updateChips();
        resetAllButtonColor();
        pinkBoxText.setText(getInitialChips());
        temp = (JButton) boardPieces[DIMENSION][DIMENSION].getComponent(0);
        temp.setEnabled(true);
        if (!temp.getActionCommand().equalsIgnoreCase("move")) {
            temp = (JButton) boardPieces[DIMENSION][DIMENSION].getComponent(0);
            temp.setText(boardLabels[DIMENSION][DIMENSION]);
            temp.setActionCommand(boardLabels[DIMENSION][DIMENSION]);
        }
        buttonPressCounter = 0;
        x = -1; //reset the x and y axis for positioning
        y = -1;
    }

/**
 * this method gets the initial chips of both players based on the initialised game mode
 * @return a string that notifies both player's initialised pieces in the pink box text
 */
    private String getInitialChips() {
        int blackCounter = 0, whiteCounter = 0;
        int[][] board = model.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == BLACK) {
                    blackCounter++;
                }
                if (board[i][j] == WHITE) {
                    whiteCounter++;
                }
            }
        }
        return "Player 1 initialized with " + blackCounter + " piece(s)\n"
                + "Player 2 initialized with " + whiteCounter + " piece(s)\n";
    }

/**
 * this method helps switching both pieces' skins based on user's choice
 * @param skinCode 
 * return void
 */
    private void switchSkins(int skinCode) {
        switch (skinCode) {
            case 1: //default skin
                blackIcon = new ImageIcon(getClass().getResource("black_s.png")); // all black's pieces
                whiteIcon = new ImageIcon(getClass().getResource("white_s.png")); // all white's pieces
                break;
            case 2: // bat and pumpkin skin
                blackIcon = new ImageIcon(getClass().getResource("bat.png")); // all black's pieces
                whiteIcon = new ImageIcon(getClass().getResource("pumpkin.png")); // all white's pieces
                break;
            case 3: // cat and dog skin
                blackIcon = new ImageIcon(getClass().getResource("dog.png")); // all black's pieces
                whiteIcon = new ImageIcon(getClass().getResource("cat.png")); // all white's pieces
                break;
        }
        resizeImages();
        clearTable();
        setTable(model.getBoard());
        p1Pic.setIcon(blackIcon);
        p2Pic.setIcon(whiteIcon);
        showValidMoves();
    }

/**
 * this method resizes the images of both player's pieces
 * return void
 */
    private void resizeImages() {
        image = blackIcon.getImage(); // transform it 
        newimg = image.getScaledInstance(34, 34, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        blackIcon = new ImageIcon(newimg);  // transform it back
        image = whiteIcon.getImage(); // transform it 
        newimg = image.getScaledInstance(34, 34, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        whiteIcon = new ImageIcon(newimg);  // transform it back
    }

/**
 * this method helps set both player's pieces the board based on the game mode 
 * @param board - 2D array that keeps track of both player's pieces 
 * return void
 */
    private void setTable(int board[][]) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    boardPieces[i][j].add(new JLabel(blackIcon));
                } else if (board[i][j] == 2) {
                    boardPieces[i][j].add(new JLabel(whiteIcon));
                }
            }
        }
    }

/**
 * this method helps clear the board/table
 * return void
 */
    private void clearTable() {
        for (int i = 0; i < boardPieces.length - 1; i++) {
            for (int j = 0; j < boardPieces[i].length - 1; j++) {
                boardPieces[i][j].removeAll();
            }
        }
    }

/**
 * this method helps add check mark(s) if there are valid move(s) for the current player to make
 * @param board - 2D array that keeps track of both player's pieces 
 * @param player the current player in the game
 * @return true - if there are valid move(s) and check mark(s) are set on board
 *         false - if there are no valid move(s) and check mark(s) are set off board
 */
    private boolean addCheck(int board[][], int player) {
        boolean flag = false;
        ImageIcon checkmark = new ImageIcon(getClass().getResource("checkmark.png"));
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                //add a check if it is valid + the tile is empty of chip + it hasn't already have a check mark
                if (model.isValid(i, j, player, true) && board[i][j] == EMPTY && boardPieces[i][j].getComponentCount() == 0) {
                    boardPieces[i][j].add(new JLabel(checkmark));
                    flag = true;
                }
            }
        }
        return flag;
    }

/**
 * this method helps remove all the check mark(s) on the board
 * @param board - 2D array that keeps track of both player's pieces 
 * @param player the current player
 */
    private void removeCheck(int board[][], int player) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (model.isValid(i, j, player, true) && board[i][j] == EMPTY) {
                    boardPieces[i][j].removeAll();
                }
            }
        }
    }

    /**
     * this method helps toggle the move/skip button in different situation
     * @param mode 0 -toggle the move to skip and vice-versa
     *             1 - disable the button and vice-versa
     */
    private void toggleMoveSkipButton(int mode) {
        temp = (JButton) boardPieces[DIMENSION][DIMENSION].getComponent(0);
        switch (mode) {
            case 0:
                if (temp.getActionCommand().equalsIgnoreCase("move")) {
                    temp = (JButton) boardPieces[DIMENSION][DIMENSION].getComponent(0);
                    temp.setText("skip");
                    temp.setActionCommand("skip");
                } else {
                    temp = (JButton) boardPieces[DIMENSION][DIMENSION].getComponent(0);
                    temp.setText(boardLabels[DIMENSION][DIMENSION]);
                    temp.setActionCommand(boardLabels[DIMENSION][DIMENSION]);
                }
                break;
            case 1:
                if (temp.isEnabled()) {
                    temp.setEnabled(false);
                } else {
                    temp.setEnabled(true);
                }
        }

    }
    
/**
 * this method checks the current player has any moves left. If both of them don't have moves left to play. 
 * It notifies that the game is over and shouts out the winner
 * return void
 */
    private void checkEndGame() {
        if (currentPlayer == BLACK) {
            if (!model.canMove(BLACK)) {
                toggleMoveSkipButton(0);
                pinkBoxText.append("Player " + currentPlayer + " has no moves left. Press skip!\n");
            }
        } else {
            if (!model.canMove(WHITE)) {
                toggleMoveSkipButton(0);
                pinkBoxText.append("Player " + currentPlayer + " has no moves left. Press skip!\n");
            }
        }
        if (!model.canMove(BLACK) && !model.canMove(WHITE)) { //if both have no moves to play (end game condition)
            toggleMoveSkipButton(1);
            int blackChips = model.getChips(BLACK), whiteChips = model.getChips(WHITE);
            int winner = 0;
            if (blackChips > whiteChips) {
                winner = BLACK;
            } else {
                winner = WHITE;
            }
            pinkBoxText.append("END OF GAME\n"
                    + "Player " + BLACK + " finished with " + blackChips + " chip(s).\n"
                    + "Player " + WHITE + " finished with " + whiteChips + " chip(s).\n"
                    + "Player " + winner + " wins!\n\n"
                    + "Play again? (Select 'New Game' from the File menu.");
        }
    }
    
/**
 * this method will add check mark(s) if the "show valid moves" checkbox is selected
 *                                   otherwise check mark(s) are turned off
 * return void
 */
    private void showValidMoves() {
        if (validMoves.isSelected()) {
            addCheck(model.getBoard(), currentPlayer);
        } else {
            removeCheck(model.getBoard(), currentPlayer);
        }
    }

/**
 * this method highlights the background of the current player's name in the scoreboard on their turn 
 * return void
 */
    private void toggleCurrentPlayer() {
        if (currentPlayer == BLACK) {
            currentPlayer = WHITE;
            p1PiecesPanel.setBackground(null);
            p2PiecesPanel.setBackground(Color.YELLOW);
            pinkBoxText.append("Currently playing: player " + currentPlayer + "\n");
        } else {
            currentPlayer = BLACK;
            p1PiecesPanel.setBackground(Color.YELLOW);
            p2PiecesPanel.setBackground(null);
            pinkBoxText.append("Currently playing: player " + currentPlayer + "\n");
        }
    }

/**
 * This method resets the colour of all buttons except move button
 * return void
 */
    private void resetAllButtonColor() {
        for (int i = 0; i < boardPieces.length; i++) {
            for (int j = 0; j < boardPieces[DIMENSION].length; j++) {
                if ((i == DIMENSION || j == DIMENSION) && i != j) {
                    boardPieces[i][j].getComponent(0).setBackground(Color.LIGHT_GRAY);
                }
            }
        }
    }

/**
 * this method updates the amount of chips of both players in the scoreboard
 * return void
 */
    private void updateChips() {
        p1Pic.setText(model.getChips(BLACK) + "");
        p2Pic.setText(model.getChips(WHITE) + "");
    }
    
/**
 * this method makes a valid move for the player on the board
 * @param e - acknowledges the action from the buttons pressed by user and make valid move 
 * return void
 */
    private void playerMove(ActionEvent e) {
        if (buttonPressCounter == 3) {
            buttonPressCounter = 0;
            x = -1; //reset the x and y axis for positioning
            y = -1;
        }

        for (int i = 0; i < boardPieces[DIMENSION].length - 1; i++) { //loop through the horizontal buttons
            if (e.getSource().equals(boardPieces[DIMENSION][i].getComponent(0))) { //get the pressed button coordinate
                y = i;
                //if (already press a button and next button press is on the same column or row) OR
                // (already press both buttons, each from horizonal and vertical)
                if ((buttonPressCounter > 0 && x == -1) || (x != -1 && y != -1 && buttonPressCounter == 2)) {
                    resetAllButtonColor();
                    x = -1;
                    buttonPressCounter = 0;
                }
                boardPieces[DIMENSION][i].getComponent(0).setBackground(Color.WHITE);
                buttonPressCounter++;
            }
        }
        for (int i = 0; i < boardPieces.length - 1; i++) {  //loop through the vertical buttons
            if (e.getSource().equals(boardPieces[i][DIMENSION].getComponent(0))) { // get the pressed button coordinate 
                x = i;
                //if (already press a button and next button press is on the same column or row) OR
                // (already press both buttons, each from horizonal and vertical)
                if ((buttonPressCounter > 0 && y == -1) || (x != -1 && y != -1 && buttonPressCounter == 2)) {
                    resetAllButtonColor();
                    y = -1;
                    buttonPressCounter = 0;
                }
                boardPieces[i][DIMENSION].getComponent(0).setBackground(Color.WHITE);
                buttonPressCounter++;
            }
        }
        //if move/skip button is pressed
        if (e.getSource().equals(boardPieces[DIMENSION][DIMENSION].getComponent(0))) {
            resetAllButtonColor();
            buttonPressCounter++;
        }
        //if move/skip button is pressed AND one or both horizontal and vertical haven't been pressed
        if (e.getSource().equals(boardPieces[DIMENSION][DIMENSION].getComponent(0)) && (x == -1 || y == -1)) {
            resetAllButtonColor();
            buttonPressCounter = 0;
            x = -1; //reset the x and y axis for positioning
            y = -1;
        }
        //if both horizonal and vertical is pressed correctly and the move button is pressed
        if (x != -1 && y != -1 && e.getSource().equals(boardPieces[DIMENSION][DIMENSION].getComponent(0))) {
            int flippedChips = model.move(x, y, currentPlayer);
            if (flippedChips != 0) {
                pinkBoxText.append("Player " + currentPlayer + " has captured " + flippedChips + " piece(s)\n");
                toggleCurrentPlayer();
                clearTable();
                setTable(model.getBoard());
                checkEndGame();
                showValidMoves();
            }
        }
    }
    
    public void appendPinkBoxText(String s) {
        pinkBoxText.append(s);
    }

    /**
     * This function create a JButton with given parameters
     *
     * @param text Title of the button that the user will see
     * @param ac The action command (or user data) for that button
     * @param fgc The foreground text colour for the button
     * @param bgc The background colour for the button
     * @param handler The event manager for that button
     * @return JButton object
     */
    private JButton createButton(String text, String ac, Color fgc, Color bgc, ActionListener handler) {
        JButton jb = new JButton();
        jb.setText(text);
        jb.setActionCommand(ac);
        jb.setForeground(fgc);
        jb.setBackground(bgc);
        jb.addActionListener(handler);
        jb.setFocusPainted(false);
        return jb;
    }
}
