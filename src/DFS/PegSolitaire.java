package DFS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;



public class PegSolitaire {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PegSolitaire obj = new PegSolitaire();
		obj.printMoves();
	}

	private void printMoves() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		PegBoard PB = null;
		try {
			System.out.println("Enter the initial configuration");
			String input = reader.readLine();
			input = input.replace(" ", "");
			input = input.replace(",","");
			PB = new PegBoard(input, "");			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<PegBoard> PBSet = new HashSet<PegBoard>();
		Stack<PegBoard> PBStack = new Stack<PegBoard>();
		PBStack.push(PB);
		int flagPathFound = 0;
		while(!PBStack.empty()) {
			PegBoard temp = PBStack.pop();
			if (PBSet.contains(temp)) {
				continue;
			} else {
				PBSet.add(temp);
			}
			if (temp.isEndState()) {
				System.out.println(temp.getMoves());
				flagPathFound = 1;
				//System.exit(0);//If you remove this line then all the possible paths will be found 
			}
			else {
				ArrayList<PegBoard> config = temp.getNextConfig(); 
				Collections.reverse(config);
				for (PegBoard i: config) {
					PBStack.push(i);
				}
			}
		}
		if (flagPathFound == 0) {
			System.out.println("No end state could be found");
		}
	}
}