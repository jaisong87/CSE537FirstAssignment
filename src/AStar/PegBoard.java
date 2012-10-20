package AStar;
import java.util.ArrayList;
import java.util.HashMap;

/* Class to represent a state of the game
 * Contains functions to search for differentpossible states from current state
 */
public class PegBoard {
	private String currentConfig;
	private String movesTillNow="";
	private String desiredState = "--000----000--0000000000X0000000000--000----000--";
	public float hScore;
	public float gScore;
	public float fScore;
	public int heuristicToUse = 2;//1 or 2. 1 is more informed 2 is less informed
			
/*
 * Construct a state with a given board config and gScore
 */
	public PegBoard(String Config, float gscore) {
		gScore = gscore;
		currentConfig = Config;
		if (heuristicToUse == 1) 
			hScore = GetHueristicScore1();
		else if (heuristicToUse == 2) 
			hScore = GetHueristicScore2();
		else if (heuristicToUse == 4) 
			hScore = GetHueristicScore4();
		else 
			hScore = GetHueristicScore3();
		fScore = gScore + hScore; 
	}

	/* Construct a state with information on board state and moves till now*/
	public PegBoard(String Config, String moves, float gscore) {
		gScore = gscore;
		currentConfig = Config;
		movesTillNow = moves;
		if (heuristicToUse == 1) 
			hScore = GetHueristicScore1();
		else if (heuristicToUse == 2) 
			hScore = GetHueristicScore2();
		else if (heuristicToUse == 4) 
			hScore = GetHueristicScore4();
		else 
			hScore = GetHueristicScore3();
		fScore = gScore + hScore;
	}

	/* The Heuristic Count Number of pebbles in board
	 * If there exists a goal state from a board of N pegs
	 * We can reach the goal in (N-1) moves without any
	 * over estimation. 
	 */
	public float GetHueristicScore1() { 
		int score = 0;
		for (int i=0; i<currentConfig.length();i++) {
			if (currentConfig.charAt(i) == 'X') {
				score++;
			}
	}
		/* score -1 because the end state should always have score 0, in a admissible heuristic
		/*This hueristic will never over-estimate. Hence its an admissible Heuristic
		 */	
		return score-1; 
	}

	//This hueristic is less informed
	public float GetHueristicScore2() { //This Hueristic estimates the number of possible moves
		int badNodes = 0;
		int totalNodes = 0;
		
		for (int px =0;px<7;px++)
			for(int py=0;py<7;py++)
			{
				int curPos = px*7+py;
				if(currentConfig.charAt(curPos)=='X')
					totalNodes++;
				if(px>=2 && py>=2 && currentConfig.charAt(curPos)=='X')
				{
					if( (currentConfig.charAt(curPos-1) == '0') && (currentConfig.charAt(curPos-2) == 'X'))
						badNodes++;
					if( (currentConfig.charAt(curPos-7) == '0') && (currentConfig.charAt(curPos-14) == 'X'))
						badNodes++;
				}
			}
	    float finalScore = (badNodes-1);
		return  finalScore;
	}
	
	public float GetHueristicScore3() {
		int badNodes = 0;
		int totalNodes = 0;
		
		for (int px =0;px<7;px++)
			for(int py=0;py<7;py++)
			{
				int curPos = px*7+py;
				if(currentConfig.charAt(curPos)=='X')
					totalNodes++;
				if(px>=3 && py>=3 && currentConfig.charAt(curPos)=='X')
				{
					if( (currentConfig.charAt(curPos-1) == '0') && (currentConfig.charAt(curPos-2) == '0') && (currentConfig.charAt(curPos-3) == 'X'))
						badNodes++;
					if( (currentConfig.charAt(curPos-7) == '0') && (currentConfig.charAt(curPos-14) == '0') && (currentConfig.charAt(curPos-21) == 'X'))
						badNodes++;
				}
			}
	    float finalScore = (totalNodes-1);
	    finalScore/=2;;
	    finalScore +=(badNodes);
		return  finalScore;
	}

	public float GetHueristicScore4() {
		int triangles = 0;
		int totalHoles = 0;
		int totalOuterNodes = 0;

		for(int i=0;i<7;i++)
		{
			if(currentConfig.charAt(i)=='X')
				totalOuterNodes++;
			if(currentConfig.charAt(7+i)=='X')
				totalOuterNodes++;
			if(currentConfig.charAt(48-i)=='X')
				totalOuterNodes++;
			if(currentConfig.charAt(48-7-i)=='X')
				totalOuterNodes++;
		}
			
		return totalOuterNodes-1;
		}

	public int returnMapping(int index) {
		if (index<7) 
			return index-2;
		if (index<14) 
			return index-6;
		if (index<35)
			return index-8;
		if (index<42)
			return index-10;
		return index-14;
	}
	
	public ArrayList<PegBoard> getNextConfig() {
		ArrayList<PegBoard> PegList= new ArrayList<PegBoard>();
		for (int i=0; i<currentConfig.length();i++) {
			if (currentConfig.charAt(i) == 'X') {
				PegBoard left = leftMove(i);
				if (left != null)
					PegList.add(left);
				PegBoard right = rightMove(i);
				if (right != null)
					PegList.add(right);
				PegBoard up = upMove(i);
				if (up != null)
					PegList.add(up);
				PegBoard down = downMove(i);
				if (down != null)
					PegList.add(down);
			}
			
		}
		return PegList;
	}
	
	public int getNextConfigSize() {
		int moves = 0;
		for (int i=0; i<currentConfig.length();i++) {
			if (currentConfig.charAt(i) == 'X') {
				if (leftMovePossible(i))
					moves++;
				if (rightMovePossible(i))
					moves++;
				if (upMovePossible(i))
					moves++;
				if (downMovePossible(i))
					moves++;
			}
		}
		return moves;
	}
	
	boolean leftMovePossible(int index) {
		if (index%7 == 0 || (index-1)%7 == 0) //leftmost cell - no left moves possible
			return false;
		if (currentConfig.charAt(index-2) == '0' && currentConfig.charAt(index-1) == 'X')
			return true;
		else
			return false;
	}
	
	boolean rightMovePossible(int index) {
		if ((index+1)%7 == 0 || (index+2)%7 == 0) //rightmost cell - no right moves possible
			return false;
		if (currentConfig.charAt(index+2) == '0' && currentConfig.charAt(index+1) == 'X')
			return true;
		else
			return false;
	}
	
	boolean upMovePossible(int index) {
		if (index - 14 < 0) 
			return false;
		if (currentConfig.charAt(index-14) == '0' && currentConfig.charAt(index-7) == 'X')
			return true;
		else
			return false;
	}
	
	boolean downMovePossible(int index) {
		if (index + 14 > 46) 
			return false;
		if (currentConfig.charAt(index+14) == '0' && currentConfig.charAt(index+7) == 'X')
			return true;
		else
			return false;
	}
	
	PegBoard leftMove(int index) {
		if (index%7 == 0 || (index-1)%7 == 0) //leftmost cell - no left moves possible
			return null;
		if (currentConfig.charAt(index-2) == '0' && currentConfig.charAt(index-1) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(currentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index-2, 'X');
			newConfig.setCharAt(index-1, '0');
			String newMove = movesTillNow+"("+new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index-2)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, gScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	PegBoard rightMove(int index) {
		if ((index+1)%7 == 0 || (index+2)%7 == 0) //rightmost cell - no right moves possible
			return null;
		if (currentConfig.charAt(index+2) == '0' && currentConfig.charAt(index+1) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(currentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index+2, 'X');
			newConfig.setCharAt(index+1, '0');
			String newMove = movesTillNow+"("+new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index+2)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, gScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	PegBoard upMove(int index) {
		if (index - 14 < 0) 
			return null;
		if (currentConfig.charAt(index-14) == '0' && currentConfig.charAt(index-7) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(currentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index-14, 'X');
			newConfig.setCharAt(index-7, '0');
			String newMove = movesTillNow+"(" + new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index-14)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, gScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	PegBoard downMove(int index) {
		if (index + 14 > 46) 
			return null;
		if (currentConfig.charAt(index+14) == '0' && currentConfig.charAt(index+7) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(currentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index+14, 'X');
			newConfig.setCharAt(index+7, '0');
			String newMove = movesTillNow+"(" + new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index+14)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, gScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	public boolean isEndState() {
		if (currentConfig.equals(desiredState)) 
			return true;
		return false;
	}
	
	public String getMoves() {
		return movesTillNow;
	}
	
	public String getConfig() {
		return currentConfig;
	}
	
}
