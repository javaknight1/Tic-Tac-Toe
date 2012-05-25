/**
 * This is the TicTacToeBoard. This contains all the information
 * in one board includes all 9 spaces and win/lose/tie record
 * with previously playing this board. 
 * 
 * @version 5/9/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 4
 * Section 02
 */

package proj4;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

@SuppressWarnings("serial")
public class TicTacToeBoard implements Comparable<TicTacToeBoard>, Serializable{

	//Total space
	final int TOTAL_SPACES = 9;
	
	//Top Row
	private int tLeft;
	private int tMiddle;
	private int tRight;
	private final static int tLeft_KEY = 0;
	private final static int tMiddle_KEY = 1;
	private final static int tRight_KEY = 2;
	
	//Middle Row
	private int mLeft;
	private int mMiddle;
	private int mRight;
	private final static int mLeft_KEY = 3;
	private final static int mMiddle_KEY = 4;
	private final static int mRight_KEY = 5;
	
	//Bottom Row
	private int bLeft;
	private int bMiddle;
	private int bRight;
	private final static int bLeft_KEY = 6;
	private final static int bMiddle_KEY = 7;
	private final static int bRight_KEY = 8;
	
	//Array of the positions of empty tiles
	private LinkedList<Integer> emptyTiles;
	
	//Win-Lose-Tie Record
	public int win;
	public int lose;
	public int tie;
	
	/**
	 * Constructor that makes all the tiles empty
	 */
	public TicTacToeBoard (){

		//Top row
		tLeft = 0;
		tMiddle = 0;
		tRight = 0;
		
		//Middle row
		mLeft = 0;
		mMiddle = 0;
		mRight = 0;
		
		//Bottom row
		bLeft = 0;
		bMiddle = 0;
		bRight = 0;
		
		//Empty Tiles
		emptyTiles = new LinkedList<Integer>();
		for(int i=0; i < 9; i++)
			emptyTiles.add( i );
		
		//Win-Lose-Tie record
		win  = 0;
		lose = 0;
		tie = 0;
		
	}
	
	/**
	 * Duplicate the tic tac toe board
	 * @param t - the board being duplicated
	 */
	@SuppressWarnings("unchecked")
	public TicTacToeBoard( TicTacToeBoard tb ){
		
		
		//Top row
		tLeft = tb.tLeft;
		tMiddle = tb.tMiddle;
		tRight = tb.tRight;
		
		//Middle row
		mLeft = tb.mLeft;
		mMiddle = tb.mMiddle;
		mRight = tb.mRight;
		
		//Bottom row
		bLeft = tb.bLeft;
		bMiddle = tb.bMiddle;
		bRight = tb.bRight;
		
		this.emptyTiles = (LinkedList<Integer>) tb.emptyTiles.clone();
		
		//Win/Lose/Tie
		win = tb.win;
		lose = tb.lose;
		tie = tb.tie;
		
	}
	
	/**
	 * the hashCode function
	 */
	@Override
	public int hashCode(){
		
		int hash = 0;
		
		hash += (bRight  * Math.pow(10, 0) );
		hash += (bMiddle * Math.pow(10, 1) );
		hash += (bLeft   * Math.pow(10, 2) );
		
		hash += (mRight  * Math.pow(10, 3) );
		hash += (mMiddle * Math.pow(10, 4) );
		hash += (mLeft   * Math.pow(10, 5) );
		
		hash += (tRight  * Math.pow(10, 6) );
		hash += (tMiddle * Math.pow(10, 7) );
		hash += (tLeft   * Math.pow(10, 8) );		
		
		return hash;
	}
	
	/**
	 * the equals function
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TicTacToeBoard other = (TicTacToeBoard) obj;
		if (bLeft != other.bLeft) {
			return false;
		}
		if (bMiddle != other.bMiddle) {
			return false;
		}
		if (bRight != other.bRight) {
			return false;
		}
		if (mLeft != other.mLeft) {
			return false;
		}
		if (mMiddle != other.mMiddle) {
			return false;
		}
		if (mRight != other.mRight) {
			return false;
		}
		if (tLeft != other.tLeft) {
			return false;
		}
		if (tMiddle != other.tMiddle) {
			return false;
		}
		if (tRight != other.tRight) {
			return false;
		}
		return true;
	}

	/**
	 * Creates the hashCode into a String
	 * @return hashCode String
	 */
	public String hashString(){
		
		String hash = "";
		
		hash = hash.concat( String.valueOf(tLeft) );
		hash = hash.concat( String.valueOf(tMiddle) );
		hash = hash.concat( String.valueOf(tRight) );
		
		hash = hash.concat( String.valueOf(mLeft) );
		hash = hash.concat( String.valueOf(mMiddle) );
		hash = hash.concat( String.valueOf(mRight) );
		
		hash = hash.concat( String.valueOf(bLeft) );
		hash = hash.concat( String.valueOf(bMiddle) );
		hash = hash.concat( String.valueOf(bRight) );
		
		return hash;		
	}
	
	/**
	 * toString method. Only the spaces
	 */
	public String toString(){

		String hash = this.hashString();
		String board = "";
		int rowCount = 0;
		
		for(int i=0; i < 9; i++){
			
			rowCount++;
			
			if(rowCount > 3){
				board = board.concat("\n");
				rowCount = 1;
			}
			
			if(hash.charAt(i) == '0')
				board = board.concat(".");
			if(hash.charAt(i) == '1')
				board = board.concat("X");
			if(hash.charAt(i) == '2')
				board = board.concat("O");		
			
		}

		return board;
	}
	
	/**
	 * Gives a random empty space position 
	 * @return int of position
	 */
	public int RandomEmptySpacePosition() {
		
		Random randNum = new Random();
		int pos;
		
		if( emptyTiles.size() > 1){
			pos = randNum.nextInt( emptyTiles.size() - 1);
			pos = emptyTiles.get( pos );
		}
		else
			pos = emptyTiles.getFirst();
		
		return pos;
	}
	
	/**
	 * Checks to see if a specific position is empty
	 * @param pos - position
	 * @return true - if empty, false - if not
	 */
	public boolean isEmpty( int pos ) {
		
		int val = getSpaceVal( pos );
		
		if( val == 0 )
			return true;
		else
			return false;		
		
	}
	
	/**
	 * Gets the value of a certain position
	 * @param pos - position
	 * @return int position value
	 */
	private int getSpaceVal( int pos ) {
		
		if( pos == tLeft_KEY )
			return tLeft;
		
		if( pos == tMiddle_KEY )
			return tMiddle;
		
		if( pos == tRight_KEY )
			return tRight;
		
		if( pos == mLeft_KEY )
			return mLeft;
		
		if( pos == mMiddle_KEY )
			return mMiddle;
		
		if( pos == mRight_KEY )
			return mRight;
		
		if( pos == bLeft_KEY )
			return bLeft;
		
		if( pos == bMiddle_KEY )
			return bMiddle;
		
		if( pos == bRight_KEY )
			return bRight;
			
		return -1;
	}
	
	/**
	 * Puts a 1 for X, in a certain position
	 * @param pos - position
	 */
	public void Xmove( int pos ) {
		
		emptyTiles.removeFirstOccurrence(pos);
		
		if( pos == tLeft_KEY )
			tLeft = 1;
		
		if( pos == tMiddle_KEY )
			tMiddle = 1;
		
		if( pos == tRight_KEY )
			tRight = 1;
		
		if( pos == mLeft_KEY )
			mLeft = 1;
		
		if( pos == mMiddle_KEY )
			mMiddle = 1;
		
		if( pos == mRight_KEY )
			mRight = 1;
		
		if( pos == bLeft_KEY )
			bLeft = 1;
		
		if( pos == bMiddle_KEY )
			bMiddle = 1;
		
		if( pos == bRight_KEY )
			bRight = 1;
		
	}
	
	/**
	 * Puts a 2 for O, in a certain position
	 * @param pos - position
	 */
	public void Omove( int pos ) {

		emptyTiles.removeFirstOccurrence(pos);
		
		if( pos == tLeft_KEY )
			tLeft = 2;
		
		if( pos == tMiddle_KEY )
			tMiddle = 2;
		
		if( pos == tRight_KEY )
			tRight = 2;
		
		if( pos == mLeft_KEY )
			mLeft = 2;
		
		if( pos == mMiddle_KEY )
			mMiddle = 2;
		
		if( pos == mRight_KEY )
			mRight = 2;
		
		if( pos == bLeft_KEY )
			bLeft = 2;
		
		if( pos == bMiddle_KEY )
			bMiddle = 2;
		
		if( pos == bRight_KEY )
			bRight = 2;		
		
	}
	
	/**
	 * Checks to see if the board has ended
	 * @return true - if ended, false - still playable
	 */
	public boolean isDone() {
		
		//Top row win/lose
		if(tLeft == tMiddle && tMiddle == tRight && !isEmpty(tRight_KEY))
			return true;
		
		//Middle row win/lose
		if(mLeft == mMiddle && mMiddle == mRight && !isEmpty(mRight_KEY))
			return true;
		
		//Bottom row win/lose
		if(bLeft == bMiddle && bMiddle == bRight && !isEmpty(bRight_KEY))
			return true;
		
		//Left column win/lose
		if(tLeft == mLeft && mLeft == bLeft && !isEmpty(bLeft_KEY))
			return true;
		
		//Middle column win/lose
		if(tMiddle == mMiddle && mMiddle == bMiddle && !isEmpty(bMiddle_KEY))
			return true;
		
		//Right column win/lose
		if(tRight == mRight && mRight == bRight && !isEmpty(bRight_KEY))
			return true;
		
		//Diagonal "\ direction" win/lose
		if(tLeft == mMiddle && mMiddle == bRight && !isEmpty(bRight_KEY))
			return true;
		
		//Diagonal "/ direction" win/lose
		if(tRight == mMiddle && mMiddle == bLeft && !isEmpty(bLeft_KEY))
			return true;
		
		//The board is full and it's a tie
		if( isBoardFull() )
			return true;
		
		//No wins/loses/ties
		return false;
	}

	/**
	 * Checks to see if the board is completely full
	 * @return true - full, false, not
	 */
	private boolean isBoardFull(){
		
		String board = this.hashString();
		char tile;
		
		for(int i=0; i < 9; i++){
			tile = board.charAt(i);
			
			if(tile == '0')
				return false;			
		}
		
		return true;
	}
	
	/**
	 * Returns the a value to represent who won
	 * 1 - X
	 * 2 - O
	 * 0 - Tie 
	 * @return int of who won
	 */
	public int whoWon(){
		
		//Top row win/lose
		if(tLeft == tMiddle && tMiddle == tRight){
			if(tLeft == 1)
				return 1;
			if(tLeft == 2)
				return 2;
		}
		
		//Middle row win/lose
		if(mLeft == mMiddle && mMiddle == mRight){
			if(mLeft == 1)
				return 1;
			if(mLeft == 2)
				return 2;
		}
		
		//Bottom row win/lose
		if(bLeft == bMiddle && bMiddle == bRight){
			if(bLeft == 1)
				return 1;
			if(bLeft == 2)
				return 2;
		}
		
		//Left column win/lose
		if(tLeft == mLeft && mLeft == bLeft){
			if(tLeft == 1)
				return 1;
			if(tLeft == 2)
				return 2;
		}
		
		//Middle column win/lose
		if(tMiddle == mMiddle && mMiddle == bMiddle){
			if(tMiddle == 1)
				return 1;
			if(tMiddle == 2)
				return 2;
		}
		
		//Right column win/lose
		if(tRight == mRight && mRight == bRight){
			if(tRight == 1)
				return 1;
			if(tRight == 2)
				return 2;
		}
		
		//Diagonal "\ direction" win/lose
		if(tLeft == mMiddle && mMiddle == bRight){
			if(tLeft == 1)
				return 1;
			if(tLeft == 2)
				return 2;
		}
		
		//Diagonal "/ direction" win/lose
		if(tRight == mMiddle && mMiddle == bLeft){
			if(tRight == 1)
				return 1;
			if(tRight == 2)
				return 2;
		}
		
		//Tie
		return 0;
	}
	
	/**
	 * Increments the value according to who won
	 * @param wtl - the person who won
	 */
	public void incrementWTL( int wtl ){
		
		if( wtl == 0 )
			tie++;
		
		if( wtl == 1 )
			lose++;
		
		if( wtl == 2 )
			win++;
		
	}
	
	/**
	 * compareTo function. Uses the win Ratio
	 */
	public int compareTo(TicTacToeBoard that) {
		if( this.winRatio() > that.winRatio() )
			return 1;
		if( this.winRatio() < that.winRatio() )
			return -1;
		return 0;		
	}
	
	/**
	 * Win ratio of this board
	 * @return the percentage of winning
	 */
	public double winRatio(){
		
		int totalGames = win + lose;
		
		if(totalGames == 0.0)
			return 0.0;
		
		double ratio = (double)win/(double)totalGames;
		ratio *= 100;
		
		return ratio;
	}
	
}
