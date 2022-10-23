/**
 * File name: OthelloNetworkModalViewController.java
 * Author: Hoang Tung Nguyen, 040977322
 * Course: CST8221 - JAP, Lab Section: 302
 * Assignment: 2 Part 1
 * Date: November 22, 2020
 * Professor: Daniel Cormier
 * Purpose: This class implement the modal for othello game
 * Class list: OthelloNetworkModalViewController, Controller
 */

package othello;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;


public class OthelloNetworkModalViewController extends JDialog
{
    //global variables:
    JButton connect;
    JButton cancel;
    String message = "";
    String statusText = "";
    
    JLabel statusLabel;

    //-----------------------------------------------------
    /** Whether the user pressed the Connect button. */
    private Boolean hasConnected=false;
    
    /** A reference to the private inner Controller class for use by the two buttons. */
    private Controller handler = new Controller();
    
    /** The CombobBox you will need to create.*/
    //NOTE:  You're free to rename it, but as there are some references to it in this stub,
    //you'll need to be consistent when renaming them all.
    private JComboBox portInput;
    
    /** The text field where the user will enter the hostname to connect to.*/
    //As above, you're free to rename this.  But be careful.
    private JTextField addressInput;
    
    private JTextField nameInput;

    //These are suggested implementations.  You're free to tackle it differently of course.

/**
 * Prepare the UI for modal window
 * @param mainView - Parent frame
 */
public OthelloNetworkModalViewController (JFrame mainView)
    {
        //In Swing, it's ideal if we provide reference frame this will sit atop.
        //The title isn't relevant since we want this to be an undecorated dialog.
        super(mainView,"Enter Network Address",true);
        
        //Important note!  Uncomment this line ONLY when you're nearly ready.
        //It'll be a lot harder to get rid of the modal when it's undecorated.
        //So save uncommenting this for nearly last, when you've debugged everything
        //and you're doing your final testing.
        
        setUndecorated(true); 
        
        //This will hold your UI.  You may rename it if you want to.
        Container networkPanel = getContentPane();
        
        //create components
        //first layer
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        
        //second layer
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        //third layer
        JPanel upper = new JPanel(new BorderLayout());
        upper.setPreferredSize(new Dimension(WIDTH, 130));
        JPanel lower = new JPanel(new BorderLayout());
        
        //forth layer
        JPanel labelsPanel = new JPanel(new GridLayout(3, 1));
        JPanel boxPanel = new JPanel(new GridLayout(3, 1));
        JPanel buttonsPanel = new JPanel(new BorderLayout(10, 10));
        JPanel statusPanel = new JPanel(new GridLayout());
        
        //fifth layer
        JPanel addressLabelPanel = new JPanel(new BorderLayout());
        JLabel addressLabel = new JLabel("Address: ");
        JPanel portLabelPanel = new JPanel(new BorderLayout());
        JLabel portLabel = new JLabel("Port: ");
        JPanel nameLabelPanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Name: ");
        JPanel statusLabelPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("");
        
        JPanel addressInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 9));
        addressInput = new JTextField(20); //Set text field width to 20 columns
        addressInput.setPreferredSize(new Dimension(0, 26));
        
        JPanel portInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 9));
        Object[] portValue = {"", "31000", "41000", "51000"};
        portInput = new JComboBox(portValue);
        portInput.setEditable(true);
        portInput.setSelectedIndex(0);
        portInput.setPreferredSize(new Dimension(130, 26));
        
        JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 9));
        nameInput = new JTextField(20); //Set text field width to 20 columns
        nameInput.setPreferredSize(new Dimension(0, 26));
        
        JPanel buttonJPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 3, 5));
        connect = new JButton("Connect");
        cancel = new JButton("Cancel");
        cancel.addActionListener(handler);
        connect.addActionListener(handler);
        cancel.setActionCommand("X");
        connect.setActionCommand("C");
        connect.setPreferredSize(new Dimension(90, 30));
        cancel.setPreferredSize(new Dimension(85, 30));
        
        //set Mnemonic for address and port
        addressLabel.setDisplayedMnemonic(KeyEvent.VK_A);
        addressLabel.setLabelFor(addressInput);
        portLabel.setDisplayedMnemonic(KeyEvent.VK_P);
        portLabel.setLabelFor(portInput);
        
        //adding UI
        networkPanel.add(root);
        root.add(main, BorderLayout.CENTER);
        main.add(upper, BorderLayout.NORTH);
        main.add(lower, BorderLayout.CENTER);
        upper.add(labelsPanel, BorderLayout.WEST);
        upper.add(boxPanel, BorderLayout.CENTER);
        upper.add(statusPanel, BorderLayout.SOUTH);
        lower.add(buttonsPanel, BorderLayout.EAST);
        buttonsPanel.add(buttonJPanel2, BorderLayout.SOUTH);
        buttonJPanel2.add(connect);
        buttonJPanel2.add(cancel);
        boxPanel.add(addressInputPanel);
        boxPanel.add(portInputPanel);
        boxPanel.add(nameInputPanel);
        addressInputPanel.add(addressInput);
        portInputPanel.add(portInput);
        nameInputPanel.add(nameInput);
        labelsPanel.add(addressLabelPanel);
        labelsPanel.add(portLabelPanel);
        labelsPanel.add(nameLabelPanel);
        addressLabelPanel.add(addressLabel, BorderLayout.CENTER);
        portLabelPanel.add(portLabel, BorderLayout.CENTER);
        nameLabelPanel.add(nameLabel, BorderLayout.CENTER);
        statusPanel.add(statusLabelPanel);
        statusLabelPanel.add(statusLabel, BorderLayout.CENTER);
        
        //Now you're on your own!  Put your own UI in here.
        //Stick to GridLayout, BorderLayout and FlowLayout this
        //time around.

        //This statement should be the last one.
        pack();
    }

    /** This method returns the value the user has entered.
        @return The actual value, unless there was an error or the user pressed the cancel button.
    */

    public String getAddress()
    {
        if (hasConnected)
        {
            return (addressInput.getText());
        }
        else
        {
            //You can return whatever error message you like.  This isn't cast in stone.
            return ("Error:  Invalid Address or attempt cancelled.");
        }
    }
    
    /** This method returns the value the user has entered.
        @return The actual value, unless there was an error or the user pressed the cancel button.
    */

    public String getName()
    {
        if (hasConnected)
        {
            return (nameInput.getText());
        }
        else
        {
            //You can return whatever error message you like.  This isn't cast in stone.
            return ("Error:  Invalid Name or attempt cancelled.");
        }
    }

    /** This method returns the port the user has selected OR ENTERED in the Combo Box.
    @return The port selected.  Returns -1 on invalid port or cancel pressed.
    */
    
    public int getPort()
    {
        int portnum;
        if (hasConnected)
        {
            //Ensure the user has entered digits.
            //You should probably also ensure the port numbers are in the correct range.
            //I did not.  That's from 0 to 65535, by the way.  Treat it like invalid input.
            try
            {
                portnum = Integer.parseInt((String)portInput.getSelectedItem());
            }
                catch(NumberFormatException nfe)
            {
                //I've been using a negative portnum as an error state in my main code.
                portnum = -1;
            }

            return portnum;
        }
        return -1;
    }
    
    /** Responsible for final cleanup and hiding the modal. Does not do much at the moment.*/
    public void hideModal()
    {
        //Add any code that you may want to do after the user input has been processed
        //and you're figuratively closing up the shop.
        setVisible(false);
        
    }
    
    /** Returns whether or not the user had pressed connect.
    @return True if the user pressed Connect, false if the user backed out with cancel.
    */
    public boolean pressedConnect()
    {
        return hasConnected;
    }
    
    /**
     * Return the message prepared in this controller
     * @return message for the user
     */
    public String getMessage() {
        return message;
    }
    
    /** The Controller for managing user input in the network dialogue.
    @author Daniel Cormier
    @version 1.3
    @since 1.8.0_261
    @see OthelloViewController
    */
    
    private class Controller implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent evt)
        {
            String s = evt.getActionCommand();
            message = ""; //message string output to pinkbox
            statusText = ""; //status message
            
            //I set the action command on my connect button to "C".
            hasConnected=true;
            if ("C".equals(s))
            {
                //In Assignment 2-2, we will be making revisions here.
                //This would be a great place to update the "Status" portion of the UI.
                if (getAddress().isEmpty()) {
                    statusText += "Address must not be blank\n";
                } else {
                    if (getPort() == -1) {
                        statusText += "Valid port ranges are from 0 to 65535\n";
                    } else {
                        if (getName().length() < 3) {
                            statusText += "Name must be at least three character long.\n";
                        }
                    }
                }
                statusLabel.setText(statusText);

                //Hide the modal. For part 2, we may not want to hide the modal right away.
                if (statusText.isEmpty()) {
                    hideModal();
                }
                else {
                    message = "";
                    hasConnected = false;
                }
            }
            else //My "Cancel" button has an action command of "X" and gets called here.
            {
                message = "";
                message += "Cancel Pressed\n";
                statusLabel.setText("");
                hasConnected = false;
                hideModal();
            }
        }
    }
}
        

        

