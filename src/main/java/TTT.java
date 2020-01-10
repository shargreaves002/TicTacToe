import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class TTT extends JFrame implements WindowListener, ActionListener{

    private static int player = 1;
    private static int i;
    private JButton[] buttons;
    private static String winner = "";

    public static void main(String[] args) {
        //Make a frame and make it visible
        TTT frame = new TTT("Tic Tac Toe");
        frame.setVisible(true);
    }

    private TTT (String title) {
        //properties of the frame
        super(title);
        addWindowListener(this);
        setSize(450, 489);
        setResizable(false);
        setLayout(new GridLayout(3, 3));
        setLocationRelativeTo(null);
        buttons = new JButton[9];

        //create our buttons
        for (i=0; i<buttons.length; i++){
            //just setting properties of our buttons
            buttons[i] = new JButton (String.valueOf(i));
            buttons[i].setSize(150, 150);
            buttons[i].setName(String.valueOf(i));
            add(buttons[i]);

            //Here's where we handle what happens when you click one.
            buttons[i].addActionListener(this);
        }
    }

    //Determines the active player
    private int activePlayer() {
        player += 1;
        return player % 2;
    }

    //Handles wins
    private void checkForWin() {
        //Creates an array with the text of the buttons
        String[] board = new String[9];
        for (i=0; i<buttons.length; i++){
            board[i] = buttons[i].getText();
        }

        //Check for vertical wins
        for (i=0; i < 3; i++) {
            if (board[i].equals(board[i+3]) && board[i].equals(board[i+6])) {
                winner =  board[i];
            }
        }

        //Check for horizontal wins
        for (i=0; i < 7; i+=3) {
            if (board[i].equals(board[i+1]) && board[i].equals(board[i+2])) {
                winner = board[i];
            }
        }

        //Checks for diagonal wins
        if (board[0].equals(board[4]) && board[0].equals(board[8]) ||
                board[2].equals(board[4]) && board[2].equals(board[6])) {
            winner = board[4];
        }

        // initializes choice to a value that doesn't do anything
        // prevents a null pointer exception later
        int choice = 3;



        //If there's no winner, prompt to play again
        for(int i = 0; i < 10; i++){
            //Check all the buttons to see if they're all X or O
            if (i < 9 && !board[i].equals("X") && !board[i].equals("O")) {
                break;
            }

            //If i = 9, it means all 9 buttons have been pressed and we need to prompt to restart.
            if (i == 9) {
                winner = "Nobody";
                break;
            }
        }

        //If the game is over, display message and prompt to restart
        if (winner.equals("X") || winner.equals("O") || winner.equals("Nobody")) {
            choice = JOptionPane.showOptionDialog(null,
                    winner + " won! would you like to play again?",
                    "Play again?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, null, null);
        }

        //Quits the program or resets the board, depending on their choice
        if (choice == JOptionPane.YES_OPTION) {
            restartGame();
        } else if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
    }

    /* Handles game restarts. Intentionally doesn't set active player,
    so the loser from last game goes first */
    private void restartGame(){
        for (i = 0; i < buttons.length; i++){
            buttons[i].setName(String.valueOf(i));
            buttons[i].setText(String.valueOf(i));
            buttons[i].setEnabled(true);
        }
        winner = "";
    }

    //If user closes the window, exit the program
    public void windowClosing(WindowEvent e) {
        dispose();
        System.exit(0);
    }
    //filler to make the class concrete
    public void windowOpened(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}

    public void actionPerformed(ActionEvent e) {
        //finds active player and the button that they pressed
        int currentPlayer = activePlayer();
        JButton button = (JButton) e.getSource();

        //sets the text on button according to the current active player
        int name = Integer.parseInt(button.getName());
        if (currentPlayer == 0) {
            buttons[name].setText("X");
        } else if (currentPlayer == 1) {
            buttons[name].setText("O");
        }

        //make it so you can't click the same button twice
        buttons[name].setEnabled(false);

        //Now see if the play caused a win condition
        checkForWin();
    }
}
