/**
 * This gives the TicTacToe game a GUI. Except, your the "dumb" player, and
 * the computer is the smart player.
 * 
 * @version 5/9/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 4
 * Section 02
 */

package proj4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class TicTacToeSolverGUI {
	
	private JFrame frame;
	private JButton button[];
	
	private JLabel record;
	private JLabel win;
	private JLabel lose;
	private JLabel tie;
	
	private TicTacToeBoard tb;
	private boolean save;
	
	private int w;
	private int l;
	private int t;
	
	private ArrayList<TicTacToeBoard> gamePath;
	private QuadraticProbingHashTable<TicTacToeBoard> hashTable;
	
	//CONFIG INFO
	private static final String file = "./config.txt";
	private ObjectOutputStream saveFile;
	
	/**
	 * Constructor that sets up the whole GUI.
	 * @param s - whether to save or not
	 */
	public TicTacToeSolverGUI( boolean s ){

		frame = new JFrame("Tic-Tac-Toe Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,400);
        frame.setResizable(false);
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();  
        Rectangle bounds = gc.getBounds(); 
          
        Dimension size = frame.getPreferredSize();  
        frame.setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)),  (int) ((bounds.height / 2) - (size.getHeight() / 2)));
        frame.setVisible(true);
       
        JPanel panel =  new JPanel();
        panel.setLayout(new GridLayout(4,3));
        
        button = new JButton[9];
        
        for(int i=0; i < 9; i++){
        	button[i] = new JButton();
        	panel.add(button[i]);
        	button[i].setBorder(new LineBorder(Color.black));
        	button[i].addActionListener(new Action());        	
        }
        clearBoard();
        
        JPanel results = new JPanel();
        results.setLayout(new GridLayout(4,1));
        record = new JLabel("Record: ");
        win = new JLabel("Win - " + 0);
        lose = new JLabel("Lose - " + 0);
        tie = new JLabel("Tie - " + 0);
        results.add(record);
        results.add(win);
        results.add(lose);
        results.add(tie);
        
        panel.add(results);
        frame.add(panel);
     
        tb = new TicTacToeBoard();
        gamePath = new ArrayList<TicTacToeBoard>();
        
        save = s;
        
        if(save){
        	obtainSaveFile();
        }else{
        	hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
        }
        
        Xmove();
        
        w = t = l = 0;
	}
	
	/**
	 * Moves the X player (SMART COMPUTER PLAYER)
	 */
	public void Xmove(){
		
		int pos = findBestPosition( tb );
        
        tb.Xmove(pos);
        
        button[pos].setText("X");
        button[pos].setBackground(Color.pink);
        button[pos].setEnabled(false);
	}
	
	/**
	 * Finds the best position for the smart player
	 * @param t - board before move needed to make
	 * @return position for best move
	 */
	private int findBestPosition( TicTacToeBoard t ) {
		
		TicTacToeBoard temp, prev;
		int pos = -1;
		boolean doRandom = true;
		
		for(int i=0; i < 9; i++){
			
			if( t.isEmpty( i ) ){
				temp = new TicTacToeBoard( t );
				temp.Omove( i );
				temp = hashTable.find( temp );
				
				if( pos != -1 ){
					prev = new TicTacToeBoard( t );
					prev.Omove( pos );
					prev = hashTable.find( prev );
					if( temp.compareTo(prev) == 1){
						pos = i;
						doRandom = false;
					}
				}else
					pos = i;
				
			}
			
		}
		
		//If all the possible positions have the same win ratio,
		//then pick a random one
		if(doRandom)
			return t.RandomEmptySpacePosition();
		
		return pos;
	}
	
	/**
	 * If it's done, it will output all the correct information
	 * @throws InterruptedException - for the sleep
	 */
	private void isDone() throws InterruptedException{
		
		int whoWon;
		JFrame resultFrame = new JFrame();
		JPanel resultPanel = new JPanel();
		JLabel resultLabel = new JLabel();
		
		resultFrame.setSize(50,100);
		resultFrame.setResizable(false);
		GraphicsConfiguration gc = resultFrame.getGraphicsConfiguration();  
        Rectangle bounds = gc.getBounds(); 
         
        Dimension size = frame.getPreferredSize();  
        resultFrame.setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)),  (int) ((bounds.height / 2) - (size.getHeight() / 2)));
		
        resultPanel.add(resultLabel);
        resultFrame.add(resultPanel);
		
		if(tb.isDone()){
			
			whoWon = tb.whoWon();
			
			if(whoWon == 1){
				l++;
				lose.setText("Lose - " + l);
				resultLabel.setText("You Lose!!");
			}
			if(whoWon == 2){
				w++;
				win.setText("Win - " + w);
				resultLabel.setText("You Win!!");
			}
			if(whoWon == 0){
				t++;
				tie.setText("Tie - " + t);
				resultLabel.setText("Tie!!");
			}
			
			resultFrame.setVisible(true);
			Thread.sleep(1000);
			tb = new TicTacToeBoard();
			clearBoard();				
		}
		
	}
	
	/**
	 * Completely clears the GUI board
	 */
	private void clearBoard(){
		
		
		for(int i=0; i < 9; i++){
			button[i].setEnabled(true);
			button[i].setText("-");
			button[i].setBackground(Color.GRAY);
		}
		
	}
	
	/**
	 * Gets the save file from previous games
	 */
	@SuppressWarnings("unchecked")
	private void obtainSaveFile(){
		
		try{
			//Create the file if it doesn't exist
			if( !( (new File(file)).exists() ) ){
				
				//Create new empty file
				(new File(file)).createNewFile();
				
				//Creates a fresh new hashTable
				hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
			}
			else{
				//Obtain the hashTables saved config.txt file
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis);
			
				//Obtain the previously saved hashTable
				hashTable = (QuadraticProbingHashTable<TicTacToeBoard>)ois.readObject();
				ois.close();
				
				(new File(file)).delete();
			}
			
			//Create save file configs.txt
			FileOutputStream fos = new FileOutputStream(file);
			saveFile = new ObjectOutputStream(fos);
			
		}catch(Exception e){
			e.printStackTrace();
			//Creates a fresh new hashTable
			hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
		}
	}
	
	/**
	 * Takes in the button pressing and listening
	 * @author Rob
	 *
	 */
	private class Action implements ActionListener{

		/**
		 * Listener for when the user presses a button
		 */
		@Override
		public void actionPerformed(ActionEvent a){
			
			String letter = "O";
			try{
				if(a.getSource() == button[0]){
					
					button[0].setText(letter);
					button[0].setBackground(Color.cyan);
					button[0].setEnabled(false);
					tb.Omove(0);
					
					gamePath.add(tb);
					hashTable.insert( tb );
					
					tb = new TicTacToeBoard( tb );
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[1]){
					
					button[1].setText(letter);
					button[1].setBackground(Color.cyan);
					button[1].setEnabled(false);
					tb.Omove(1);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[2]){
					
					button[2].setText(letter);
					button[2].setBackground(Color.cyan);
					button[2].setEnabled(false);
					tb.Omove(2);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[3]){
					
					button[3].setText(letter);
					button[3].setBackground(Color.cyan);
					button[3].setEnabled(false);
					tb.Omove(3);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[4]){
					
					button[4].setText(letter);
					button[4].setBackground(Color.cyan);
					button[4].setEnabled(false);
					tb.Omove(4);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[5]){
					
					button[5].setText(letter);
					button[5].setBackground(Color.cyan);
					button[5].setEnabled(false);
					tb.Omove(5);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[6]){
					
					button[6].setText(letter);
					button[6].setBackground(Color.cyan);
					button[6].setEnabled(false);
					tb.Omove(6);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[7]){
					
					button[7].setText(letter);
					button[7].setBackground(Color.cyan);
					button[7].setEnabled(false);
					tb.Omove(7);
					isDone();
					Xmove();
					isDone();
					
				} else if(a.getSource() == button[8]){
					
					button[8].setText(letter);
					button[8].setBackground(Color.cyan);
					button[8].setEnabled(false);
					tb.Omove(8);
					isDone();
					Xmove();
					isDone();
					
				}
				
				//Clear all memory from file and replace it
				(new File(file)).delete();
				(new File(file)).createNewFile();
				saveFile.writeObject(hashTable);
				
			}catch(InterruptedException e){	} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}