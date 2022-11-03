package league;

public class MatchData {
	private String date;
	private String nameClub_1;
	private String nameClub_2;
	private int scoreClub_1;
	private int scoreClub_2;
	
	//Constructor
	public MatchData(String date, String nameClub_1, String nameClub_2, int scoreClub_1, int scoreClub_2) {
		super();
		this.date = date;
		this.nameClub_1 = nameClub_1;
		this.nameClub_2 = nameClub_2;
		this.scoreClub_1 = scoreClub_1;
		this.scoreClub_2 = scoreClub_2;
	}
	
	//Getters & Setters
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNameClub_1() {
		return nameClub_1;
	}

	public void setNameClub_1(String nameClub_1) {
		this.nameClub_1 = nameClub_1;
	}

	public String getNameClub_2() {
		return nameClub_2;
	}

	public void setNameClub_2(String nameClub_2) {
		this.nameClub_2 = nameClub_2;
	}

	public int getScoreClub_1() {
		return scoreClub_1;
	}

	public void setScoreClub_1(int scoreClub_1) {
		this.scoreClub_1 = scoreClub_1;
	}

	public int getScoreClub_2() {
		return scoreClub_2;
	}

	public void setScoreClub_2(int scoreClub_2) {
		this.scoreClub_2 = scoreClub_2;
	}
	
	
}
