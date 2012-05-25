/**
 * This is the Main class. 
 * 
 * @version 5/9/2012
 * @author Rob Avery <pw97976@umbc.edu>
 * CMSC 341 - Spring 2012 - Project 4
 * Section 02
 */

package proj4;

public class Main {

	/**
	 * @param args
	 * 
	 * 		-h        <-- history flag
	 * 		-s 		  <-- save flag
	 * 		-d 		  <-- display flag
	 * 		-"number" <-- number of games
	 * @throws SpaceOutOfBounds 
	 */
	public static void main(String[] args) {
		
		boolean history = false;
		boolean save = false;
		boolean display = false;
		int numOfgames = 1;
		TicTacToeSolver ts;
		
		for(int i=0; i < args.length; i++){
			
			if( args[i].equals("-h") )
				history = true;
			else if( args[i].equals("-s") )
				save = true;
			else if( args[i].equals("-d") )
				display = true;
			else if( isNumberArgument( args[i] ) )
				numOfgames = removeStringFlag( args[i] );
			else{
				System.err.print("Invalid arguent: " + args[i]);
				System.exit(-1);
			}
			
		}
		
		
		ts = new TicTacToeSolver( numOfgames, history, save);
		
		if(display)
			displayOn(save);
		else
			displayOff( ts );

	}

	/**
	 * Outputs through command line.
	 * @param ts -  the solver with the information
	 */
	private static void displayOff( TicTacToeSolver ts ) {
		
		ts.solve();
		
		System.out.println(ts);
		
	}
	
	/**
	 * Creates a gui for the TicTacToe game.
	 * @param s - whether or not to save previous games
	 */
	@SuppressWarnings("unused")
	private static void displayOn( boolean s ) {
		
		TicTacToeSolverGUI gui = new TicTacToeSolverGUI(s);
	
	}
	
	/**
	 * Removes the "-" in the number flag
	 * @param arg - number flag
	 * @return flag without "-"
	 */
	private static int removeStringFlag(String arg) {
		
		String r = "";

		for (int i = 1; i < arg.length(); i ++)
				r += arg.charAt(i);

		
		return Integer.parseInt(r);
	}

	/**
	 * Check to see if it's a valid number flag
	 * @param arg - argument being checked
	 * @return if it's a valid number flag or not
	 */
	private static boolean isNumberArgument(String arg) {

		boolean first = true;
		
		for(int i=0; i < arg.length(); i++){
			if(first){
				first = false;
				if( !(arg.charAt(i) == '-') )
					return false;
			}else if( !(Character.isDigit( arg.charAt(i) ) ) )
				return false;			
		}
		
		return true;
	}

}
