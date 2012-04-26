package proj4;

public class Main {

	/**
	 * @param args
	 * 
	 * 		-h        <-- history flag
	 * 		-s 		  <-- save flag
	 * 		-d 		  <-- display flag
	 * 		-"number" <-- number of games
	 */
	public static void main(String[] args) {
		
		boolean history = false;
		boolean save = false;
		boolean display = false;
		boolean numOfgames = false;
		
		for(int i=0; i < args.length; i++){
			
			if( args[i].equals("-h") )
				history = true;
			else if( args[i].equals("-s") )
				save = true;
			else if( args[i].equals("-d") )
				display = true;
			else if( args[i].equals("-20") )
				numOfgames = true;
			else{
				System.err.print("Invalid arguent: " + args[i]);
				System.exit(-1);
			}
			
		}
		
		System.out.println("History: " + history);
		System.out.println("Save: " + save);
		System.out.println("Display: " + display);
		

	}

}
