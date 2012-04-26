package proj4;

import java.util.Random;

public class TicTacToeBoard {

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
	
	//Win-Lose-Tie Record
	private int win;
	private int lose;
	private int tie;
	
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
		
		//Win-Lose-Tie record
		win  = 0;
		lose = 0;
		tie = 0;
		
	}
	
	
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
	
	public int RandomEmptySpacePosition() throws SpaceOutOfBounds{
		
		Random randNum = new Random();
		int pos;
		
		do {
			
			pos = randNum.nextInt(9);
			
		}while( !isEmpty( pos ) );
		
		return pos;
	}
	
	private boolean isEmpty( int pos ) throws SpaceOutOfBounds{
		
		int val = getSpaceVal( pos );
		
		if( val == 0 )
			return true;
		else
			return false;		
		
	}
	
	private int getSpaceVal( int pos ) throws SpaceOutOfBounds {
		
		if( pos >= TOTAL_SPACES || pos < 0 )
			throw new SpaceOutOfBounds();
		
		
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
	
	public void randomXmove() throws SpaceOutOfBounds{
		
		int pos = RandomEmptySpacePosition();
		
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
	
	
	
	
}
