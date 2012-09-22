import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
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
		
		Stack<PegBoard> PBStack = new Stack<PegBoard>();
		PBStack.push(PB);
		while(!PBStack.empty()) {
			PegBoard temp = PBStack.pop();
			if (temp.isEndState()) {
				System.out.println(temp.getMoves());
				System.exit(0);
			}
			else {
				ArrayList<PegBoard> config = temp.getNextConfig(); 
				Collections.reverse(config);
				for (PegBoard i: config) {
					PBStack.push(i);
				}
			}
		}
		System.out.println("No end state could be found");
	}
}