/*
 * Game.java
 *
 * Created on May 20, 2006, 2:11 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package lineoffive;

import javax.swing.*;          
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author aw
 */
public class Game implements /*MouseListener*/ ActionListener 
{
    private static int BOARD_SIZE = 19;
    private static int CELL_DIM = 19;
    //private static String labelPrefix = "Number of button clicks: ";
    //private int numClicks = 0;
    //final JLabel label = new JLabel(labelPrefix + "0    ");

    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    final static String LOOKANDFEEL = "System";

    JFrame frame = null;
    
    CellButton[][] buttons = new CellButton[BOARD_SIZE][BOARD_SIZE];
    Icon leftTopBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.LEFT, CellIcon.TOP);
    Icon leftBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.LEFT, CellIcon.MIDDLE);
    Icon leftBottomBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.LEFT, CellIcon.BOTTOM);
    Icon rightTopBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.RIGHT, CellIcon.TOP);
    Icon rightBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.RIGHT, CellIcon.MIDDLE);
    Icon rightBottomBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.RIGHT, CellIcon.BOTTOM);
    Icon topBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.MIDDLE, CellIcon.TOP);
    Icon bottomBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.MIDDLE, CellIcon.BOTTOM);
        
    Icon blankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK);
    Icon blankStarCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.MIDDLE, CellIcon.MIDDLE, true);
       
    Icon whiteCellIcon = new CellIcon(CELL_DIM, CellIcon.WHITE);
    Icon blackCellIcon = new CellIcon(CELL_DIM, CellIcon.BLACK);

    JLabel blackLabel = new JLabel();
    JTextField blackName = new JTextField("Black");
    JLabel whiteLabel = new JLabel();
    JTextField whiteName = new JTextField("White");
    JButton startButton = new JButton("Start Game");
    JButton undoButton = new JButton("Undo");
    JButton passButton = new JButton("Pass");
    JButton pauseResumeButton = new JButton("Pause Game");
    JButton loadButton = new JButton("Load Game");
    JButton saveButton = new JButton("Save Game");
    JButton beginButton = new JButton("|<");
    JButton forwardButton = new JButton(">");
    JButton backwardButton = new JButton("<");
    JButton endButton = new JButton(">|");
    JTextArea infoArea = new JTextArea("Welcome to Game of Line-of-Five!");
    
    JFileChooser fileChooser = new JFileChooser();
    
    Vector moves = new Vector();
    Vector movesBackup = null;
    int moveCursor = 0;
    
    int side = CellIcon.BLACK;
    //boolean isWon = false;

    public Game(JFrame frame)
    {
        this.frame = frame;
    }
    
    public Component createComponents() 
    {
        //JButton button = new JButton("+");
        //button.setMnemonic(KeyEvent.VK_I);
        //button.addActionListener(this);
        //label.setLabelFor(button);

        Icon leftTopBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.LEFT, CellIcon.TOP);
        Icon leftBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.LEFT, CellIcon.MIDDLE);
        Icon leftBottomBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.LEFT, CellIcon.BOTTOM);
        Icon rightTopBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.RIGHT, CellIcon.TOP);
        Icon rightBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.RIGHT, CellIcon.MIDDLE);
        Icon rightBottomBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.RIGHT, CellIcon.BOTTOM);
        Icon topBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.MIDDLE, CellIcon.TOP);
        Icon bottomBlankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.MIDDLE, CellIcon.BOTTOM);
        
        Icon blankCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK);
        Icon blankStarCellIcon = new CellIcon(CELL_DIM, CellIcon.BLANK, CellIcon.MIDDLE, CellIcon.MIDDLE, true);
        
        Icon whiteCellIcon = new CellIcon(CELL_DIM, CellIcon.WHITE);
        Icon blackCellIcon = new CellIcon(CELL_DIM, CellIcon.BLACK);
        
        /*
         * An easy way to put space between a top-level container
         * and its contents is to put the contents in a JPanel
         * that has an "empty" border.
         */
        JPanel pane = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        //CellButton[][] buttons = new CellButton[BOARD_SIZE][BOARD_SIZE];
        for(int i = 1; i < BOARD_SIZE-1; i++)
        {
            for(int j = 1; j < BOARD_SIZE-1; j++)
            {
                //JButton button = new JButton("", blankCellIcon);
                CellButton button = new CellButton(blankCellIcon);
                buttons[i][j] = button;
            }
        }
        
        //stars
        buttons[3][3] = new CellButton(blankStarCellIcon);
        buttons[3][BOARD_SIZE/2] = new CellButton(blankStarCellIcon);
        buttons[3][BOARD_SIZE-4] = new CellButton(blankStarCellIcon);
        buttons[BOARD_SIZE-4][3] = new CellButton(blankStarCellIcon);
        buttons[BOARD_SIZE-4][BOARD_SIZE/2] = new CellButton(blankStarCellIcon);
        buttons[BOARD_SIZE-4][BOARD_SIZE-4] = new CellButton(blankStarCellIcon);
        buttons[BOARD_SIZE/2][3] = new CellButton(blankStarCellIcon);
        buttons[BOARD_SIZE/2][BOARD_SIZE/2] = new CellButton(blankStarCellIcon);
        buttons[BOARD_SIZE/2][BOARD_SIZE-4] = new CellButton(blankStarCellIcon);
        
        // corner and boundary
        buttons[0][0] = new CellButton(leftTopBlankCellIcon);
        buttons[BOARD_SIZE-1][0] = new CellButton(leftBottomBlankCellIcon);
        buttons[0][BOARD_SIZE-1] = new CellButton(rightTopBlankCellIcon);
        buttons[BOARD_SIZE-1][BOARD_SIZE-1] = new CellButton(rightBottomBlankCellIcon);
        for(int i = 1; i < BOARD_SIZE-1; i++ )
        {
            buttons[i][0] = new CellButton(leftBlankCellIcon);
            buttons[i][BOARD_SIZE-1] = new CellButton(rightBlankCellIcon);
            buttons[0][i] = new CellButton(topBlankCellIcon);
            buttons[BOARD_SIZE-1][i] = new CellButton(bottomBlankCellIcon);
        }
       
        
        for(int i = 0; i < BOARD_SIZE; i++)
        {
            for(int j = 0; j < BOARD_SIZE; j++)
            {
                CellButton button = buttons[i][j];
                button.setActionCommand(""+i+" "+j);
                button.setEnabled(false);
                button.setBorderPainted(false);
                //button.setRolloverEnabled(true);
                button.setContentAreaFilled(false);
                Dimension dimension = new Dimension(CELL_DIM, CELL_DIM);
                button.setSize(dimension);
                button.setMinimumSize(dimension);
                button.setMaximumSize(dimension);
                button.setPreferredSize(dimension);
                button.addActionListener(this);
                
                //JLabel label = new JLabel(blankCellIcon);
                //System.out.println("getMouseListeners()=" + label.getMouseListeners());
                //label.addMouseListener(this);
                //label.addMouseMotionListener(this);
                
                pane.add(button);
                //pane.add(label);
            }
        }

        /*pane.setBorder(BorderFactory.createEmptyBorder(
                                        30, //top
                                        30, //left
                                        10, //bottom
                                        30) //right
                                        );*/
        
        // The info panel
        JPanel infoPane = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        blackLabel.setIcon(blackCellIcon);
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        infoPane.add(blackLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        infoPane.add(blackName, c);
        whiteLabel.setIcon(whiteCellIcon);
        c.gridx = 0;
        c.gridy = 1;
        infoPane.add(whiteLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        infoPane.add(whiteName, c);
        
        c.gridx = 0;
        c.gridy = 2;
        infoPane.add(startButton, c);
        startButton.setActionCommand("Start Game");
        startButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 3;
        infoPane.add(undoButton, c);
        undoButton.setEnabled(false);
        undoButton.setActionCommand("Undo");
        undoButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 4;
        passButton.setEnabled(false);
        passButton.setActionCommand("Pass");
        passButton.addActionListener(this);
        infoPane.add(passButton, c);
        c.gridx = 0;
        c.gridy = 7;
        ReplayActionListener replayListener = new ReplayActionListener();
        pauseResumeButton.setEnabled(false);
        pauseResumeButton.setActionCommand("Pause Game");
        pauseResumeButton.addActionListener(replayListener);
        infoPane.add(pauseResumeButton, c);
        c.gridx = 0;
        c.gridy = 5;
        loadButton.setEnabled(true);
        loadButton.setActionCommand("Load Game");
        loadButton.addActionListener(replayListener);
        infoPane.add(loadButton, c);
        c.gridx = 0;
        c.gridy = 6;
        saveButton.setEnabled(false);
        saveButton.setActionCommand("Save Game");
        saveButton.addActionListener(this);
        infoPane.add(saveButton, c);
        c.gridx = 0;
        c.gridy = 8;
        beginButton.setEnabled(false);
        beginButton.setActionCommand("Begin");
        beginButton.addActionListener(replayListener);
        infoPane.add(beginButton, c);
        c.gridx = 1;
        c.gridy = 8;
        forwardButton.setEnabled(false);
        forwardButton.setActionCommand("Forward");
        forwardButton.addActionListener(replayListener);
        infoPane.add(forwardButton, c);
        c.gridx = 2;
        c.gridy = 8;
        backwardButton.setEnabled(false);
        backwardButton.setActionCommand("Backward");
        backwardButton.addActionListener(replayListener);
        infoPane.add(backwardButton, c);
        c.gridx = 3;
        c.gridy = 8;
        endButton.setEnabled(false);
        endButton.setActionCommand("End");
        endButton.addActionListener(replayListener);
        infoPane.add(endButton, c);
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 0, 0, 0);
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 4;
        infoArea.setEditable(false);
        infoPane.add(infoArea, c);
        
        JPanel mainPane = new JPanel(new GridLayout(1, 2));
        mainPane.add(pane);
        mainPane.add(infoPane);
        mainPane.setBorder(BorderFactory.createEmptyBorder(
                                        30, //top
                                        30, //left
                                        10, //bottom
                                        30) //right
                                        );
        
        return mainPane;
    }

    public void actionPerformed(ActionEvent e) 
    {
        String command = e.getActionCommand();
        if(command.equals("Start Game"))
        {
            startButton.setEnabled(true);
            startButton.setActionCommand("Quit Game");
            startButton.setText("Quit Game");
            loadButton.setEnabled(false);
            saveButton.setEnabled(false);
            passButton.setEnabled(true);
            undoButton.setEnabled(false);
            pauseResumeButton.setText("Pause Game");
            pauseResumeButton.setActionCommand("Pause Game");
            pauseResumeButton.setEnabled(true);
            resetBoard();
            blackName.setEditable(false);
            whiteName.setEditable(false);
            this.side = CellIcon.BLACK;
            blackLabel.setText("<<<");
            whiteLabel.setText("   ");
            infoArea.setText("Game " + blackName.getText() + " vs " + whiteName.getText() + " in progress...");
            this.moves.removeAllElements();
            //this.isWon = false;
            return;
        }
        else if(command.equals("Quit Game"))
        {
            int answer = JOptionPane.showConfirmDialog(
                    frame, "Do you really want to quit the game?",
                    "Please confirm:",
                    JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION)
            {
                startButton.setEnabled(true);
                startButton.setActionCommand("Start Game");
                startButton.setText("Start Game");
                loadButton.setEnabled(true);
                saveButton.setEnabled(true);
                passButton.setEnabled(false);
                undoButton.setEnabled(false);
                blackName.setEditable(true);
                whiteName.setEditable(true);
                pauseResumeButton.setText("Pause Game");
                pauseResumeButton.setActionCommand("Pause Game");
                pauseResumeButton.setEnabled(false);
                infoArea.setText("Game " + blackName.getText() + " vs " + whiteName.getText() + " stopped.");
            }
            return;
        }
        else if(command.equals("Undo"))
        {
            Move move = (Move)moves.lastElement();
            moves.removeElementAt(moves.size()-1);
            
            undo(move);
            
            switchTurn();
            
            if(moves.size() == 0)
            {
                undoButton.setEnabled(false);
            }
            return;
        }
        else if(command.equals("Pass"))
        {
            switchTurn();
            moves.add(Move.PASS);
            return;
        }
        else if(command.equals("Save Game"))
        {
            int answer = fileChooser.showOpenDialog(frame);
            if(answer == JFileChooser.APPROVE_OPTION) 
            {
                File file = fileChooser.getSelectedFile();
                saveGame(file);
            }
            return;
        }
        /*else if(this.isWon == true)
        {
            return;
        }*/
        
        String indices[] = command.split(" ");
        int row = Integer.parseInt(indices[0]);
        int col = Integer.parseInt(indices[1]);
        moves.add(new Move(row, col));
        undoButton.setEnabled(true);
        CellButton button = (CellButton)buttons[row][col];
        button.setEnabled(false);
        if(this.side == CellIcon.BLACK)
        {
            button.setIcon(blackCellIcon);
            this.side = CellIcon.WHITE;
            whiteLabel.setText("<<<");
            blackLabel.setText("   ");
        }
        else
        {
            button.setIcon(whiteCellIcon);
            this.side = CellIcon.BLACK;
            blackLabel.setText("<<<");
            whiteLabel.setText("   ");
        }
        
        if(win(row, col))
        {
            String player = (this.side == CellIcon.BLACK) ? whiteName.getText() : blackName.getText();
            infoArea.setText("Player " + player + " won the game!");
            startButton.setEnabled(true);
            startButton.setActionCommand("Start Game");
            startButton.setText("Start Game");
            pauseResumeButton.setEnabled(true);
            loadButton.setEnabled(true);
            saveButton.setEnabled(true);
            //undoButton.setEnabled(false);
            //passButton.setEnabled(false);
            blackName.setEditable(true);
            whiteName.setEditable(true);
            //this.isWon = true;
            /*for(int i = 0; i < BOARD_SIZE; i++)
            {
                for(int j = 0; j < BOARD_SIZE; j++)
                {
                    buttons[i][j].setEnabled(false);
                }
            }*/
        }
        //button.setState(CellIcon.BLACK);
        //numClicks++;
        //label.setText(labelPrefix + numClicks);
    }

    /*public void mousePressed(MouseEvent e) 
    {
       //saySomething("Mouse pressed; # of clicks: "
       //             + e.getClickCount(), e);
    }

    public void mouseReleased(MouseEvent e) 
    {
       //saySomething("Mouse released; # of clicks: "
       //             + e.getClickCount(), e);
    }

    public void mouseEntered(MouseEvent e) 
    {
       //saySomething("Mouse entered", e);
    }

    public void mouseExited(MouseEvent e) 
    {
       //saySomething("Mouse exited", e);
    }

    public void mouseClicked(MouseEvent e) 
    {
       //saySomething("Mouse clicked (# of clicks: "
       //             + e.getClickCount() + ")", e);
    }*/

    private void resetBoard()
    {
        for(int i = 1; i < BOARD_SIZE-1; i++)
        {
            for(int j = 1; j < BOARD_SIZE-1; j++)
            {
                buttons[i][j].setIcon(blankCellIcon);
                buttons[i][j].setEnabled(true);
            }
        }
        
        //stars
        buttons[3][3].setIcon(blankStarCellIcon);
        buttons[3][3].setEnabled(true);
        buttons[3][BOARD_SIZE/2].setIcon(blankStarCellIcon);
        buttons[3][BOARD_SIZE/2].setEnabled(true);
        buttons[3][BOARD_SIZE-4].setIcon(blankStarCellIcon);
        buttons[3][BOARD_SIZE-4].setEnabled(true);
        buttons[BOARD_SIZE-4][3].setIcon(blankStarCellIcon);
        buttons[BOARD_SIZE-4][3].setEnabled(true);
        buttons[BOARD_SIZE-4][BOARD_SIZE/2].setIcon(blankStarCellIcon);
        buttons[BOARD_SIZE-4][BOARD_SIZE/2].setEnabled(true);
        buttons[BOARD_SIZE-4][BOARD_SIZE-4].setIcon(blankStarCellIcon);
        buttons[BOARD_SIZE-4][BOARD_SIZE-4].setEnabled(true);
        buttons[BOARD_SIZE/2][3].setIcon(blankStarCellIcon);
        buttons[BOARD_SIZE/2][3].setEnabled(true);
        buttons[BOARD_SIZE/2][BOARD_SIZE/2].setIcon(blankStarCellIcon);
        buttons[BOARD_SIZE/2][BOARD_SIZE/2].setEnabled(true);
        buttons[BOARD_SIZE/2][BOARD_SIZE-4].setIcon(blankStarCellIcon);
        buttons[BOARD_SIZE/2][BOARD_SIZE-4].setEnabled(true);
        
        buttons[0][0].setIcon(leftTopBlankCellIcon);
        buttons[0][0].setEnabled(true);
        buttons[BOARD_SIZE-1][0].setIcon(leftBottomBlankCellIcon);
        buttons[BOARD_SIZE-1][0].setEnabled(true);
        buttons[0][BOARD_SIZE-1].setIcon(rightTopBlankCellIcon);
        buttons[0][BOARD_SIZE-1].setEnabled(true);
        buttons[BOARD_SIZE-1][BOARD_SIZE-1].setIcon(rightBottomBlankCellIcon);
        buttons[BOARD_SIZE-1][BOARD_SIZE-1].setEnabled(true);
        for(int i = 1; i < BOARD_SIZE-1; i++ )
        {
            buttons[i][0].setIcon(leftBlankCellIcon);
            buttons[i][0].setEnabled(true);
            buttons[i][BOARD_SIZE-1].setIcon(rightBlankCellIcon);
            buttons[i][BOARD_SIZE-1].setEnabled(true);
            buttons[0][i].setIcon(topBlankCellIcon);
            buttons[0][i].setEnabled(true);
            buttons[BOARD_SIZE-1][i].setIcon(bottomBlankCellIcon);
            buttons[BOARD_SIZE-1][i].setEnabled(true);
        }
    }

    private void switchTurn()
    {
        if(this.side == CellIcon.BLACK)
        {
            this.side = CellIcon.WHITE;
            whiteLabel.setText("<<<");
            blackLabel.setText("   ");
        }
        else
        {
            this.side = CellIcon.BLACK;
            blackLabel.setText("<<<");
            whiteLabel.setText("   ");
        }
    }
    
    private void undo(Move move)
    {
        // A pass
        if(move.equals(Move.PASS))
        {
            return;
        }
        
        int row = move.row;
        int col = move.column;
        
        // stars
        if((row == 3 && col == 3) ||
           (row == 3 && col == BOARD_SIZE/2) ||
           (row == 3 && col == BOARD_SIZE-4) ||
           (row == BOARD_SIZE-4 && col == 3) ||
           (row == BOARD_SIZE-4 && col == BOARD_SIZE/2) ||
           (row == BOARD_SIZE-4 && col == BOARD_SIZE-4) ||
           (row == BOARD_SIZE/2 && col == 3) ||
           (row == BOARD_SIZE/2 && col == BOARD_SIZE/2) ||
           (row == BOARD_SIZE/2 && col == BOARD_SIZE-4))     
        {
            buttons[row][col].setIcon(blankStarCellIcon);
        }
        // corners
        else if(row == 0 && col == 0)
        {
            buttons[0][0].setIcon(leftTopBlankCellIcon);
        }
        else if(row == BOARD_SIZE-1 && col == 0)
        {
            buttons[BOARD_SIZE-1][0].setIcon(leftBottomBlankCellIcon);
        }
        else if(row == 0 && col == BOARD_SIZE-1)
        {
            buttons[0][BOARD_SIZE-1].setIcon(rightTopBlankCellIcon);
        }
        else if(row == BOARD_SIZE-1 && col == BOARD_SIZE-1)
        {
            buttons[BOARD_SIZE-1][BOARD_SIZE-1].setIcon(rightBottomBlankCellIcon);
        }
        // borders
        else if(col == 0)   //left
        {
            buttons[row][col].setIcon(leftBlankCellIcon);
        }
        else if(col == BOARD_SIZE-1)    //right 
        {
            buttons[row][col].setIcon(rightBlankCellIcon);
        }
        else if(row == 0)   //top
        {
            buttons[row][col].setIcon(topBlankCellIcon);
        }
        else if(row == BOARD_SIZE-1)    //bottom
        {
            buttons[row][col].setIcon(bottomBlankCellIcon);
        }
        // rest
        else   
        {
            buttons[row][col].setIcon(blankCellIcon);
        }
        
        buttons[row][col].setEnabled(true);
    }

    /**
    * pre-condition: (x y) is a legal move
    */
    private boolean win(int x, int y)
    {
        int ch=((CellIcon)buttons[x][y].getIcon()).getState();
        int i, j, count;

        /* vertical */
        i=x; j=y;
        count=0;
        while(i>=0 && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            i--; 
        }
        i=x+1;
        while(i<BOARD_SIZE && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            i++;
        }
        if(count>=5) return true;

        /* horizontal */
        i=x; j=y;
        count=0;
        while(j>=0 && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            j--;
        }
        j=y+1;
        while(j<BOARD_SIZE && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            j++;
        }
        if(count>=5) return true;

        /* up-diagonal */
        i=x; j=y;
        count=0;
        while(i>=0 && j>=0 && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            i--; j--;
        }
        i=x+1; j=y+1;
        while(i<BOARD_SIZE && j<BOARD_SIZE && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            i++; j++;
        }
        if(count>=5) return true;

        /* down-diagonal */
        i=x; j=y;
        count=0;
        while(i<BOARD_SIZE && j>=0 && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            i++; j--;
        }
        i=x-1; j=y+1;
        while(i>=0 && j<BOARD_SIZE && ((CellIcon)buttons[i][j].getIcon()).getState()==ch)
        {
            count++;
            i--; j++;
        }
        if(count>=5) return true;

        return false;
    }
    
    private void saveGame(File file)
    {
        try
        {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            out.writeChars(blackName.getText());
            out.writeChar('\n');
            out.writeChars(whiteName.getText());
            out.writeChar('\n');
            Date date = new Date();
            out.writeChars(DateFormat.getDateInstance().format(date));
            out.writeChar('\n');
            
            for(int i = 0; i < moves.size(); i++)
            {
                Move move = (Move)moves.elementAt(i);
                out.writeInt(move.row);
                out.writeChar(' ');
                out.writeInt(move.column);
                out.writeChar('\n');
            }
            out.close();
        }
        catch(IOException e)
        {
            infoArea.setText("File I/O Exception " + e);
        }

    }
    
    private void loadGame(File file)
    {
        String lineSepString = System.getProperty("line.separator");
        char lineSep = lineSepString.charAt(lineSepString.length()-1);

        try
        {
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            StringBuffer buffer = new StringBuffer(20);
            char chr;
            while((chr = in.readChar()) != lineSep)
                buffer.append(chr);
            //System.out.println("blackName: " + buffer);
            String blackNameStr = buffer.toString(); 
            buffer.setLength(0);
            while((chr = in.readChar()) != lineSep)
                buffer.append(chr);
            //System.out.println("whiteName: " + buffer);
            String whiteNameStr = buffer.toString();
            buffer.setLength(0);
            while((chr = in.readChar()) != lineSep)
                buffer.append(chr);
            //System.out.println("Date: " + buffer);
            Date date = DateFormat.getDateInstance().parse(buffer.toString());
            
            Vector movesTemp = new Vector();
            try
            {
                while(true)
                {
                    int row = in.readInt();
                    in.readChar();
                    int col = in.readInt();
                    in.readChar();
                    Move move = Move.create(row, col);
                    movesTemp.add(move);
                }
            }
            catch (EOFException e) 
            {
            }
            finally
            {
                in.close();
            }
            
            // Load successful, do GUI changes
            blackName.setText(blackNameStr);
            whiteName.setText(whiteNameStr);
            blackName.setEditable(false);
            whiteName.setEditable(false);
            
            resetBoard();
            moves.removeAllElements();
            this.side = CellIcon.BLACK;
            //this.isWon = false;
            passButton.setEnabled(true);
            saveButton.setEnabled(true);
            pauseResumeButton.setEnabled(true);
            pauseResumeButton.setActionCommand("Pause Game");
            pauseResumeButton.setText("Pause Game");
            beginButton.setEnabled(false);
            forwardButton.setEnabled(false);
            backwardButton.setEnabled(false);
            endButton.setEnabled(false);
            
            for(int i = 0; i < movesTemp.size(); i++)
            {
                Move move = (Move)movesTemp.elementAt(i);
                if(move.equals(Move.PASS))
                {
                    passButton.doClick();
                }
                else
                {
                    buttons[move.row][move.column].doClick();
                }
            }
        }
        catch(IOException e)
        {
            infoArea.setText("File I/O Exception: " + e);
        }
        catch(ParseException e)
        {
            infoArea.setText("File ParseException: " + e);
        }
    }

    private static void initLookAndFeel() {
        String lookAndFeel = null;

        if (LOOKANDFEEL != null) {
            if (LOOKANDFEEL.equals("Metal")) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("System")) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            } else if (LOOKANDFEEL.equals("Motif")) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            } else if (LOOKANDFEEL.equals("GTK+")) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            } else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                                   + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            } catch (ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                                   + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
            } catch (UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                                   + lookAndFeel
                                   + ") on this platform.");
                System.err.println("Using the default look and feel.");
            } catch (Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                                   + lookAndFeel
                                   + "), for some reason.");
                System.err.println("Using the default look and feel.");
                e.printStackTrace();
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        initLookAndFeel();

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("Line of Five");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game app = new Game(frame);
        Component contents = app.createComponents();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static class Move
    {
        static final Move PASS = new Move(-1, -1);
        
        int row;
        int column;
        
        Move(int row, int col)
        {
            this.row = row;
            this.column = col;
        }
        
        static Move create(int row, int col)
        {
            if(row == PASS.row && col == PASS.column)
            {
                return PASS;
            }
            else
            {
                return new Move(row, col);
            }   
        }
    }
    
    private class ReplayActionListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            String command = e.getActionCommand();
            
            if(command.equals("Pause Game"))
            {
                pauseResumeButton.setText("Resume Game");
                pauseResumeButton.setActionCommand("Resume Game");
                beginButton.setEnabled(moves.size()>0);
                forwardButton.setEnabled(false);
                backwardButton.setEnabled(moves.size()>0);
                endButton.setEnabled(false);
                //undoButton.setEnabled(true);
                movesBackup = new Vector(moves);
                moveCursor = movesBackup.size()-1;
                infoArea.setText("Game " + blackName.getText() + " vs " + whiteName.getText() + " Paused.");
                return;
            }
            else if(command.equals("Resume Game"))
            {
                pauseResumeButton.setText("Pause Game");
                pauseResumeButton.setActionCommand("Pause Game");
                pauseResumeButton.setEnabled(true);
                beginButton.setEnabled(false);
                forwardButton.setEnabled(false);
                backwardButton.setEnabled(false);
                endButton.setEnabled(false);
                while(moves.size()-1 > moveCursor)
                {
                    undoButton.doClick();
                }
                while(moveCursor < movesBackup.size()-1)
                {
                    moveCursor++;
                    Move move = (Move)movesBackup.elementAt(moveCursor);
                    if(move.equals(Move.PASS))
                    {
                        passButton.doClick();
                    }
                    else
                    {
                        buttons[move.row][move.column].doClick();
                    }
                }
                infoArea.setText("Game " + blackName.getText() + " vs " + whiteName.getText() + " Resumed.");
            }
            else if(command.equals("Begin"))
            {
                forwardButton.setEnabled(movesBackup.size()>0);
                backwardButton.setEnabled(false);
                beginButton.setEnabled(false);
                endButton.setEnabled(movesBackup.size()>0);
                while(moves.size()>0)
                {
                    undoButton.doClick();
                }
                moveCursor = -1;
            }
            else if(command.equals("Forward"))
            {
                while(moves.size()-1 > moveCursor)
                {
                    undoButton.doClick();
                }
                moveCursor++;
                Move move = (Move)movesBackup.elementAt(moveCursor);
                
                beginButton.setEnabled(true);
                forwardButton.setEnabled(moveCursor < movesBackup.size()-1);
                backwardButton.setEnabled(true);
                endButton.setEnabled(moveCursor < movesBackup.size()-1);
                
                if(move.equals(Move.PASS))
                {
                    passButton.doClick();
                }
                else
                {
                    buttons[move.row][move.column].doClick();
                }
            }
            else if(command.equals("Backward"))
            {
                while(moves.size()-1 > moveCursor)
                {
                    undoButton.doClick();
                }

                moveCursor--;
                beginButton.setEnabled(moveCursor >= 0);
                forwardButton.setEnabled(true);
                backwardButton.setEnabled(moveCursor >= 0);
                endButton.setEnabled(true);

                undoButton.doClick();
            }
            else if(command.equals("End"))
            {
                forwardButton.setEnabled(false);
                backwardButton.setEnabled(movesBackup.size()>0);
                beginButton.setEnabled(movesBackup.size()>0);
                endButton.setEnabled(false);
                while(moves.size()-1 > moveCursor)
                {
                    undoButton.doClick();
                }
                while(moveCursor < movesBackup.size()-1)
                {
                    moveCursor++;
                    Move move = (Move)movesBackup.elementAt(moveCursor);
                    if(move.equals(Move.PASS))
                    {
                        passButton.doClick();
                    }
                    else
                    {
                        buttons[move.row][move.column].doClick();
                    }
                }
            }
            else if(command.equals("Load Game"))
            {
                int answer = fileChooser.showOpenDialog(frame);
                if(answer == JFileChooser.APPROVE_OPTION)
                {
                    File file = fileChooser.getSelectedFile();
                    loadGame(file);
                }
            }
        }
    }
            
}
