package league;

public class FootballClub extends SportsClub{
	private int playedMatches;
	private int wins;
	private int draws;
	private int defeats;
	private int gReceived; //number of received goals
	private int gScored; //number of scored goals
	private int points;
	
	
	//Constuctor
	public FootballClub(String name, String location) {
		super(name, location);
	}

	//Getters & Setters
	public int getPlayedMatches() {
		return playedMatches;
	}

	public void setPlayedMatches(int playedMatches) {
		this.playedMatches = playedMatches;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getDraws() {
		return draws;
	}

	public void setDraws(int draws) {
		this.draws = draws;
	}

	public int getDefeats() {
		return defeats;
	}

	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	public int getgReceived() {
		return gReceived;
	}

	public void setgReceived(int gReceived) {
		this.gReceived = gReceived;
	}

	public int getgScored() {
		return gScored;
	}

	public void setgScored(int gScored) {
		this.gScored = gScored;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
	
}
