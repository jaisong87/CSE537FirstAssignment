package AStar;
import java.util.ArrayList;
import java.util.HashMap;


public class PegBoard {

	private String CurrentConfig;
	private String Moves="";
	private String DesiredState = "--000----000--0000000000X0000000000--000----000--";
	public int HScore;
	public int GScore;
	public int FScore;
	public int whichHueristic = 1;//1 or 2. 1 is more informed 2 is less informed
	
	
	
	public PegBoard(String Config, int gscore) {
		GScore = gscore;
		CurrentConfig = Config;
		if (whichHueristic == 1) 
			HScore = GetHueristicScore1();
		else 
			HScore = GetHueristicScore2();
		FScore = GScore + HScore;
	}
	
	public PegBoard(String Config, String moves, int gscore) {
		GScore = gscore;
		CurrentConfig = Config;
		Moves = moves;
		if (whichHueristic == 1) 
			HScore = GetHueristicScore1();
		else 
			HScore = GetHueristicScore2();
		FScore = GScore + HScore;
	}

	//This hueristic is more informed
	public int GetHueristicScore1() { //first Hueristic Count Number of pebbles in board
		int score = 0;
		for (int i=0; i<CurrentConfig.length();i++) {
			if (CurrentConfig.charAt(i) == 'X') {
				score++;
			}
		}
		return score-1; //-1 because the end state should always have score 0, in a admissible heuristic
		//This hueristic will never over-estimate. Hence its an admissible Heuristic
	}

	//This hueristic is less informed
	public int GetHueristicScore2() { //This Hueristic estimates the number of possible moves
		return getNextConfigSize();
		//This heuristic is also an admissible one
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
		for (int i=0; i<CurrentConfig.length();i++) {
			if (CurrentConfig.charAt(i) == 'X') {
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
		for (int i=0; i<CurrentConfig.length();i++) {
			if (CurrentConfig.charAt(i) == 'X') {
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
		if (CurrentConfig.charAt(index-2) == '0' && CurrentConfig.charAt(index-1) == 'X')
			return true;
		else
			return false;
	}
	
	boolean rightMovePossible(int index) {
		if ((index+1)%7 == 0 || (index+2)%7 == 0) //rightmost cell - no right moves possible
			return false;
		if (CurrentConfig.charAt(index+2) == '0' && CurrentConfig.charAt(index+1) == 'X')
			return true;
		else
			return false;
	}
	
	boolean upMovePossible(int index) {
		if (index - 14 < 0) 
			return false;
		if (CurrentConfig.charAt(index-14) == '0' && CurrentConfig.charAt(index-7) == 'X')
			return true;
		else
			return false;
	}
	
	boolean downMovePossible(int index) {
		if (index + 14 > 46) 
			return false;
		if (CurrentConfig.charAt(index+14) == '0' && CurrentConfig.charAt(index+7) == 'X')
			return true;
		else
			return false;
	}
	
	PegBoard leftMove(int index) {
		if (index%7 == 0 || (index-1)%7 == 0) //leftmost cell - no left moves possible
			return null;
		if (CurrentConfig.charAt(index-2) == '0' && CurrentConfig.charAt(index-1) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(CurrentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index-2, 'X');
			newConfig.setCharAt(index-1, '0');
			String newMove = Moves+"("+new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index-2)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, GScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	PegBoard rightMove(int index) {
		if ((index+1)%7 == 0 || (index+2)%7 == 0) //rightmost cell - no right moves possible
			return null;
		if (CurrentConfig.charAt(index+2) == '0' && CurrentConfig.charAt(index+1) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(CurrentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index+2, 'X');
			newConfig.setCharAt(index+1, '0');
			String newMove = Moves+"("+new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index+2)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, GScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	PegBoard upMove(int index) {
		if (index - 14 < 0) 
			return null;
		if (CurrentConfig.charAt(index-14) == '0' && CurrentConfig.charAt(index-7) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(CurrentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index-14, 'X');
			newConfig.setCharAt(index-7, '0');
			String newMove = Moves+"(" + new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index-14)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, GScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	PegBoard downMove(int index) {
		if (index + 14 > 46) 
			return null;
		if (CurrentConfig.charAt(index+14) == '0' && CurrentConfig.charAt(index+7) == 'X')
		{
			StringBuilder newConfig = new StringBuilder(CurrentConfig);
			newConfig.setCharAt(index, '0');
			newConfig.setCharAt(index+14, 'X');
			newConfig.setCharAt(index+7, '0');
			String newMove = Moves+"(" + new Integer(returnMapping(index)).toString()+","+new Integer(returnMapping(index+14)).toString()+"),";
			PegBoard ret = new PegBoard(newConfig.toString(), newMove, GScore + 1);
			return ret;
		}
		else
			return null;
	}
	
	public boolean isEndState() {
		if (CurrentConfig.equals(DesiredState)) 
			return true;
		return false;
	}
	
	public String getMoves() {
		return Moves;
	}
	
	public String getConfig() {
		return CurrentConfig;
	}
	
}
