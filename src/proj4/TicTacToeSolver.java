/**
 * This is a Helper class that goes along with the TicTacToeBaord class.
 * This generates the dumb and smart player's moves and outputs them. 
 * 
 * @version 5/9/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 4
 * Section 02
 */

package proj4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class TicTacToeSolver {

	//The hashTable that includes all the possible moves
	private QuadraticProbingHashTable<TicTacToeBoard> hashTable;
	
	//Total number of games
	private int numOfgames;
	
	//Total win/lose/tie record
	private int win;
	private int lose;
	private int tie;
	
	//Flags called on the command line
	private boolean history;
	private boolean save;
	
	//CONFIG INFO
	private static final String file = "./config.txt";
	private ObjectOutputStream saveFile;
	
	//Favorite board
	private TicTacToeBoard favoriteBoard;
	
	/**
	 * Plain constructor
	 */
	public TicTacToeSolver(){
		
		//Default of 1 game
		numOfgames = 1;
		
		//Creates a fresh hashTable
		hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
		
		//Create new favorite board
		favoriteBoard = new TicTacToeBoard();
		
		//Starts the total saved score at 0
		win = tie = lose = 0;
		
		//None of the flags were marked
		history = save = false;
		
	}
	
	/**
	 * Constructor that takes in a specified number of games
	 * @param games - games to play
	 */
	public TicTacToeSolver( int games ){
		
		//Sets the number of games
		numOfgames = games;
		
		//Creates a fresh hashTable
		hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
		
		//Create new favorite board
		favoriteBoard = new TicTacToeBoard();
		
		//Starts the total saved score at 0
		win = tie = lose = 0;
		
		//None of the flags were marked
		history = save = false;
		
	}
	
	/**
	 * Contructor that takes in the number of games and some flags
	 * @param games - number of games to play
	 * @param h - history flag
	 * @param s - save flag
	 */
	public TicTacToeSolver( int games, boolean h, boolean s ){
		
		//Sets the number of games
		numOfgames = games;
		
		//Sets the flags, accordingly
		history = h;
		save = s;
		
		//Creates a fresh new hashTable
		hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
		
		//Create new favorite board
		favoriteBoard = new TicTacToeBoard();
		
		//Starts the total saved score at 0
		win = lose = tie = 0;		
		
	}
	
	/**
	 * toString method. Gives all Final Results
	 */
	public String toString(){
		
		int totalGames = win + lose;
		double Oaverage = ((double)win/(double)totalGames) * 100;
		double Xaverage = ((double)lose/(double)totalGames) * 100;
		String title;
		
		if(save)
			title = "FINAL REPORT - contains saved game\n-----------\n";
		else
			title = "FINAL REPORT\n-----------\n";
		String line  = "\n----------------------------------";
		
		//Final Game info
		String games    = ("\nNumber of games played:                " + numOfgames);
		
		String Xwin     = ("\nTotal X PLAYER (DUMB) wins:            " + lose);
		String Xpercent = ("\nTotal X PLAYER (DUMB) win percentage:  " + Xaverage + "%\n");
		
		String Olose    = ("\nTotal O PLAYER (SMART) wins:           " + win);
		String Opercent = ("\nTotal O PLAYER (SMART) win percentage: " + Oaverage + "%\n");
		
		String tTie     = ("\nTotal TIES:                            " + tie + "\n");
		
		//Final Hash Table info
		String bucket    = ("\nHashTable buckets:          ") + String.valueOf( hashTable.bucket() );
		String entries   = ("\nHashTable entries:          ") + String.valueOf( hashTable.entries() );
		String collision = ("\nCollision:                  ") + String.valueOf( hashTable.collision() );
		String fullhash  = ("\nPercent of HashTable full:  ") + String.valueOf( hashTable.fullPercent() );
		
		favoriteBoard = hashTable.find(favoriteBoard);
		//Favorite move
		String move = "\nMy favorite first move is:\n" + favoriteBoard + "\nWon " + favoriteBoard.win + " out of " + (favoriteBoard.win + favoriteBoard.lose) + " which is " + favoriteBoard.winRatio() + "%";
		
		return "\n\n" + title + games + line + Xwin + Xpercent + Olose + Opercent + tTie + line + bucket + entries + collision + fullhash + line + move + "\n";
	}
	
	/**
	 * Simply solves the games
	 */
	public void solve(){
		
		
		TicTacToeBoard tb;
		LinkedList<TicTacToeBoard> gamePath;
		int wtl;
		int game = 1;
		int move;
		boolean firstMove;
		
		obtainSaveFile();
		
		//Make hashTable empty if save is false
		if(!save){
			hashTable = new QuadraticProbingHashTable<TicTacToeBoard>();
		}
		
		for(int i=0; i < numOfgames; i++){
			
			gamePath = new LinkedList<TicTacToeBoard>();
			tb = new TicTacToeBoard();
			move = 1;
			firstMove = true;
			
			if(history){
				System.out.println("GAME " + game);
				System.out.println("-------");
			}
			
			//Go through one game until it's done
			while( !tb.isDone() ){
				
				double boardRecord = 0.0;
				
				//Create another board and add it to the gamePath
				tb = new TicTacToeBoard( tb );
				
				
				//Let the "dumb" player move
				//Insert board into gamePath
				tb.Xmove( tb.RandomEmptySpacePosition() );
				
				if(history){
					System.out.println("MOVE " + move);
					System.out.println("PLAYER X");
					System.out.println("-------");
					System.out.println(tb + "\n");
					move++;
				}
				
				
				//If the "dumb" player makes a winning move
				if( tb.isDone() )
					break;
				
				//Find best position, and
				//move there		
				tb.Omove( findBestPosition( tb ) );
				
				//Insert result into hashTable
				if( !hashTable.contains(tb) )
					hashTable.insert( tb );
				else
					tb = hashTable.find( tb );

				gamePath.add( tb );
				
				boardRecord = tb.winRatio();
				
				if(history){
					System.out.println("MOVE " + move);
					System.out.println("PLAYER O");
					System.out.println("-------");
					System.out.println(tb);
					if(boardRecord >= 0.0 || boardRecord <= 0.0)
						System.out.println("In the past, this move has led\nus to win " + boardRecord + "% of the time.");
					else
						System.out.println("This is a new move.\nThis was chosen randomly.");
						
					move++;
				}
				
				//Compare to see if this is better than the saved best move
				if(firstMove){
				
					favoriteBoard = hashTable.find(favoriteBoard);
					double favRatio = favoriteBoard.winRatio();
					double otherRatio = tb.winRatio();
					
					if(otherRatio > favRatio)
						favoriteBoard = new TicTacToeBoard(tb);
					
				}
				firstMove = false;
			}
			
			//Find out who win/lose or if its a tie
			wtl = tb.whoWon();
			
			if(wtl == 0){
				tie++;
				if(history){
					System.out.println("----------------------------------");
					System.out.println("TIE!!");
					System.out.println("X PLAYER (DUMB) WINS: " + win);
					System.out.println("O PLAYER (SMART) WINS:  " + lose);
					System.out.println("TIES:                  " + tie);
					System.out.println("----------------------------------");
				}
			}
			if(wtl == 1){
				lose++;
				if(history){
					System.out.println("----------------------------------");
					System.out.println("PLAYER X WINS!!");
					System.out.println("X PLAYER (DUMB) WINS: " + win);
					System.out.println("O PLAYER (SMART) WINS:  " + lose);
					System.out.println("TIES:                  " + tie);
					System.out.println("----------------------------------");
				}
			}
			if(wtl == 2){
				win++;
				if(history){
					System.out.println("----------------------------------");
					System.out.println("PLAYER O WINS!!");
					System.out.println("X PLAYER (DUMB) WINS: " + win);
					System.out.println("O PLAYER (SMART) WINS:  " + lose);
					System.out.println("TIES:                  " + tie);
					System.out.println("----------------------------------");
				}
			}
			
			//System.out.println("X PLAYER (SMART) WINS: " + win);
			//System.out.println("O PLAYER (DUMB) WINS:  " + lose);
			//System.out.println("TIES:                  " + tie);
			//System.out.println("----------------------------------");
			//System.out.println(tb);
			
			//Increment the wins/loses/ties to the boards in gamePath
			for(int j=0; j < gamePath.size(); j++)
				hashTable.find( gamePath.get( j ) ).incrementWTL( wtl );
			
			game++;	
		}
		
		try {
			
			saveFile.writeObject(hashTable);
			saveFile.close();
			
		} catch (IOException e) {}
		
		
	}
	
	/**
	 * Finds the best position for the smart player
	 * @param t - board before move needed to make
	 * @return position for best move
	 */
	private int findBestPosition( TicTacToeBoard t ) {
		
		TicTacToeBoard temp, prev;
		int pos = -1;
		
		for(int i=0; i < 9; i++){
			
			if( t.isEmpty( i ) ){
				temp = new TicTacToeBoard( t );
				temp.Omove( i );
				temp = hashTable.find( temp );
				
				if( pos != -1 ){
					prev = new TicTacToeBoard( t );
					prev.Omove( pos );
					prev = hashTable.find( prev );
					if( temp.compareTo(prev) > 0){
						pos = i;
					}
				}else
					pos = t.RandomEmptySpacePosition();
				
			}
			
		}
		
		return pos;
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
				
				//(new File(file)).delete();
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
}
