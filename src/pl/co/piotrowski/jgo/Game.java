package pl.co.piotrowski.jgo;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Karol Piotrowski
 *
 */
public class Game {
	GameMoves moves;
	PlayerInfo playerInfo;
	Game() { 
	}
	List<Integer> getGameState() {
		return moves.listOfMoves;
	}
	
	boolean blackTurn;
	
	boolean isBlackTurn() {
		return blackTurn;
	}
	
	void move(int stoneNo) {
		moves.addMove(stoneNo);
		blackTurn = !blackTurn;
	}
	
	void pass() {
		blackTurn = !blackTurn;
	}
	
	int getLastMove() {
		return moves.listOfMoves.get(moves.listOfMoves.size() - 1);
	}
}	
	



class PlayerInfo {
	int blackPlayerId;
	int whitePlayerId;
	public int getBlackPlayerId() {
		return blackPlayerId;
	}

	public void setBlackPlayerId(int blackPlayerId) {
		this.blackPlayerId = blackPlayerId;
	}

	public int getWhitePlayerId() {
		return whitePlayerId;
	}
	public void setWhitePlayerId(int whitePlayerId) {
		this.whitePlayerId = whitePlayerId;
	}

}

class GameMoves {
	List<Integer> listOfMoves; 
	
	GameMoves() {
		listOfMoves = new ArrayList<Integer>();
	}
	
	void addMove(int stoneNo) {
		listOfMoves.add(stoneNo);
	}
	
}
 
