package DFS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import AStar.PegBoard;



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
//			System.out.println("Enter the initial configuration");
			String input = reader.readLine();
			String initialBoardConfig = "";
			initialBoardConfig+=input.trim();

			for(int i=0;i<6;i++) {
				input = reader.readLine();
				initialBoardConfig+=input.trim();
			}

			//input = input.replace(" ", "");
			//input = input.replace(",","");
			//System.out.println("Initial board Configuration is :"+initialBoardConfig);
			PB = new PegBoard(initialBoardConfig, "", 0);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<PegBoard> PBSet = new HashSet<PegBoard>();
		Stack<PegBoard> PBStack = new Stack<PegBoard>();
		PBStack.push(PB);
		int flagPathFound = 0;
		int popCounter = 0;
		while(!PBStack.empty() && flagPathFound==0) {
			PegBoard temp = PBStack.pop();
			popCounter++;
			if (PBSet.contains(temp)) {
				continue;
			} else {
				PBSet.add(temp);
			}
			if (temp.isEndState()) {
				System.out.println(temp.getMoves());
				flagPathFound = 1;
				System.out.println(popCounter);
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
			System.out.println("Total Nodes Expanded : "+popCounter);
			
			/*
			 * Print memory usage and other stats
			 */
			 	Runtime runtime = Runtime.getRuntime();
			    NumberFormat format = NumberFormat.getInstance();

			    StringBuilder sb = new StringBuilder();
			    long maxMemory = runtime.maxMemory();
			    long allocatedMemory = runtime.totalMemory();
			    long freeMemory = runtime.freeMemory();

			    sb.append("used memory: " + format.format((allocatedMemory-freeMemory) / 1024) + "KB\n");
			    sb.append("free memory: " + format.format(freeMemory / 1024) + "KB\n");
			    sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "KB\n");
			    sb.append("max memory: " + format.format(maxMemory / 1024) + "KB\n");
			    sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "KB\n");
			    System.out.println("------ Stats on memory usage (including JVM) ------ KB\n"+sb);
	}
}