package fr.artutu.mumblelink.utils;

public class GameState { 
	
	private String state = "";
	
	public static GameState WAITING = new GameState("WAITING");
	public static GameState PLAYING = new GameState("PLAYING");
	public static GameState STARTING = new GameState("STARTING");
	public static GameState ENDING = new GameState("ENDING");
	
	public GameState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof GameState) {
			GameState state = (GameState) o;
			return state.toString().equals(toString());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "@GameState#" + this.state.toUpperCase();
	}
	
}
