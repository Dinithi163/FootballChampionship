package league;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.swing.JFrame;

public class PremierLeagueManager implements LeagueManage{
	//Create a empty list of clubs
	List<FootballClub> listClubs = new ArrayList<>();
	//Create a empty list of matches.
	List<MatchData> listMatches = new ArrayList<>();
	
	//Implementation of required method of LeagueManager
	public int getNoOfClubs() {
		return listClubs.size();
	}
	
	//Menu Console
	String menu() {
		System.out.println(
				"\n Chosse option from below :\n"
				+ "C :\t Create a new club and add to the league.\n"
				+ "R :\t Remove an existing football club from the premier league.\n"
				+ "D :\t Display the various statistics for a selected club.\n"
				+ "T :\t Display the Premier League Table.\n"
				+ "A :\t Add a match score.\n"
				+ "S :\t Save data in to a file.\n"
				+ "G :\t Launch GUI\n"
				+ "Q :\t Quit program\n");
		Scanner scanner = new Scanner(System.in);
		String menuChoice = scanner.nextLine().toLowerCase();
		return menuChoice;
	}
	
	//Create a new football club and add it in the premier league.
	void clubCreate() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter name of thr club :");
		String name = scanner.nextLine();
		while(nameValidationFailed(name)) {
			name = scanner.nextLine();
		}
		System.out.println("Enter the location of the club :");
		String location = scanner.nextLine();
		while(nameValidationFailed(location)) {
			location = scanner.nextLine();
		}
		
		//Add club to the premier league list
		listClubs.add(new FootballClub(name,location));
		System.out.println(name + " club has been added.");
		displayClubsNoInfo();
	}
	
	//Delete (relegate) an existing football club from the premier league.
	void clubRemove() {
		Scanner scanner = new Scanner(System.in);
		displayClubNames(listClubs);
		System.out.println("Enter the name of the club to remove :");
		String name =scanner.nextLine();
		while(clubNotInLeague(name)) {
			System.out.println("Can not find the club name. Try again!");
			name = scanner.nextLine();
		}
		//Remove Club
		Iterator<FootballClub> itr = listClubs.iterator();
		while(itr.hasNext()){
			FootballClub club = itr.next();
			if(club.getName().equals(name)) {
				itr.remove();
				System.out.println(name + " club deleted");
			}
		}
		displayClubsNoInfo();
	}
	
	//Display the various statistics for a selected club.
	void clubStatsDisplay() {
		Scanner scanner = new Scanner(System.in);
		displayClubNames(listClubs);
		
		System.out.println("Name of the club :");
		String name = scanner.nextLine();
		while(clubNotInLeague(name)) {
			System.out.println("Can not find the club name. Try Again!");
			name = scanner.nextLine();
		}
		//Display club information
		Iterator<FootballClub> itr = listClubs.iterator();
		while(itr.hasNext()) {
			FootballClub club = itr.next();
			if(club.getName().equals(name)) {
				System.out.println(
						name + " Club from "+ club.getLocation() + ":\n"
						+ "------------------------------------------\n"
						+ "Matches played :\t" + club.getPlayedMatches() + "\n"
						+ "Matches won :\t" + club.getWins() + "\n"
						+ "Matches draw :\t"+ club.getDraws() + "\n"
						+ "Matches defeat :\t"+ club.getDefeats() + "\n"
						+ "Number of goals scored :\t"+ club.getgScored() + "\n"
						+ "Number of goals received :\t"+ club.getgReceived() + "\n"
						+ "Total points :\t"+ club.getPoints() + "\n"
						);
			}
		}
	}
	
	//Display the Premier League Table
	void tableDisplay() {
		System.out.format("                CLUB  |  MP   W   D   L  GS  GR  Pts\n");
		List<FootballClub> clubsCopy = listClubs.stream().collect(Collectors.toList());
		//sort to display clubs in right order
		for(int i=0; i<clubsCopy.size() -1; i++){
			FootballClub unsortFirst = clubsCopy.get(i);
			FootballClub bestClub = unsortFirst;
			int index = i;
			
			for (int j=i+1; j<clubsCopy.size(); j++) {
				FootballClub next = clubsCopy.get(j);
				
				if (bestClub.getPoints() == next.getPoints()) {
					//Resolve same points with goal difference
					if (bestClub.getgScored() - bestClub.getgReceived() < next.getgScored() - next.getgReceived()) {
						bestClub = next;
						index = j;
					}
				}
				if (bestClub.getPoints() < next.getPoints()) {
					bestClub = next;
					index =j;
				}
			}
			FootballClub sorted = bestClub;
			clubsCopy.set(index, unsortFirst);
			clubsCopy.set(i, sorted);
			//print clubs with highest points directly
			displayRow(sorted);
			//After the last iteration display the last element
			if (i==clubsCopy.size()-2) {
				displayRow(clubsCopy.get(i+1));
			}
		}
	}
	private void displayRow(FootballClub club) {
		System.out.format("%20s" + "%7d" + "%4d" + "%4d" + "%4d" + "%4d" + "%4d" + "%4d\n",
		club.getName(),
		club.getPlayedMatches(),
		club.getWins(),
		club.getDraws(),
		club.getDefeats(),
		club.getgScored(),
		club.getgReceived(),
		club.getPoints());
	}
	
	//Add a played match with its score and its date
	void matchAdd() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter date of the match(dd-mm) :");
		String date =scanner.nextLine();
		while (wrongDateFormat(date)) {
			System.out.print("Wrong date format. Try Again!");
			date = scanner.nextLine();
		}
		//Display current clubs
		displayClubNames(listClubs);
		//Input club names
		System.out.print("Enter the first club name : ");
		String nameClub_1 =scanner.nextLine();
		while (clubNotInLeague(nameClub_1)) {
			System.out.print("Club is not in League registerd. Try Again!");
			nameClub_1 = scanner.nextLine();
		}
		System.out.print("Enter the second club name : ");
		String nameClub_2 =scanner.nextLine();
		while (clubNotInLeague(nameClub_2)) {
			System.out.print("Club is not in League registerd. Try Again!");
			nameClub_2 = scanner.nextLine();
		}
		//First club score
		System.out.print("Enter the number of goals scored by "+ nameClub_1 +" :");
		String scoreClub_1String = scanner.nextLine();
		while (isNotNumberInRange(scoreClub_1String)) {
			System.out.print("Inavalid Number. Try Again!");
			scoreClub_1String = scanner.nextLine();
		}
		int scoreClub_1 = Integer.parseInt(scoreClub_1String);
		//Second club score
		System.out.print("Enter the number of goals scored by "+ nameClub_2 +" :");
		String scoreClub_2String = scanner.nextLine();
		while (isNotNumberInRange(scoreClub_2String)) {
			System.out.print("Inavalid Number. Try Again!");
			scoreClub_2String = scanner.nextLine();
		}
		int scoreClub_2 = Integer.parseInt(scoreClub_2String);
		
		//Statistics of clubs
		if (scoreClub_1 == scoreClub_2) {
			updateDraw(nameClub_1, nameClub_2, scoreClub_1);
		} else if (scoreClub_1 > scoreClub_2) {
			updateWinOrDefeat(nameClub_1, nameClub_2, scoreClub_1, scoreClub_2);
		} else {
			updateWinOrDefeat(nameClub_2, nameClub_1, scoreClub_2, scoreClub_1);
		}
		
		//Add match to the match ist
		listMatches.add(new MatchData(date, nameClub_1, nameClub_2, scoreClub_1, scoreClub_2));
		System.out.println("Adding......\n" + nameClub_1 + "\t" + nameClub_2 + "\t" + "["+date+"]" + "\t" + scoreClub_1 + "\t" + scoreClub_2+ "\nThe match has been added!");
	}
	//Draw
	private void updateDraw(String club1, String club2, int score) {
		Iterator<FootballClub> itr = listClubs.iterator();
		while (itr.hasNext()) {
			FootballClub club = itr.next();
			if (club.getName().equals(club1)) {
				club.setPlayedMatches(club.getPlayedMatches()+1);
				club.setDraws(club.getDraws()+1);
				club.setPoints(club.getPoints()+1);
				club.setgScored(club.getgScored()+score);
				club.setgReceived(club.getgReceived()+score);
			}
		}
		Iterator<FootballClub> itr2 = listClubs.iterator();
		while (itr2.hasNext()) {
			FootballClub club = itr2.next();
			if (club.getName().equals(club2)) {
				club.setPlayedMatches(club.getPlayedMatches()+1);
				club.setDraws(club.getDraws()+1);
				club.setPoints(club.getPoints()+1);
				club.setgScored(club.getgScored()+score);
				club.setgReceived(club.getgReceived()+score);
			}
		}
	}
	//Wins or Defeat
	private void updateWinOrDefeat(String winner, String defeator, int winnerScore, int defeatorScore) {
		Iterator<FootballClub> itr = listClubs.iterator();
		while (itr.hasNext()) {
			FootballClub club = itr.next();
			if (club.getName().equals(winner)) {
				club.setPlayedMatches(club.getPlayedMatches()+1);
				club.setWins(club.getWins()+1);
				club.setgScored(club.getgScored()+ winnerScore);
				club.setgReceived(club.getgReceived() + defeatorScore);
				club.setPoints(club.getPoints() + 3);
			}
		}
		Iterator<FootballClub> itr2 = listClubs.iterator();
		while (itr2.hasNext()) {
			FootballClub club = itr2.next();
			if (club.getName().equals(defeator)) {
				club.setPlayedMatches(club.getPlayedMatches()+1);
				club.setDefeats(club.getDefeats()+1);
				club.setgScored(club.getgScored()+ defeatorScore);
				club.setgReceived(club.getgReceived() + winnerScore);
			}
		}
	}
	//////////////////////////////////////////////////
	
	//Saving in a file of all the information entered by the user.
	void saveToFile() throws  Exception{
		try {
			File file = new File("." + File.separator + "clubs_list.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			
			Iterator<FootballClub> itr = listClubs.iterator();
			while(itr.hasNext()) {
				FootballClub club = itr.next();
				writer.write(
						club.getName() + "\n"
						+ club.getLocation() + "\n"
						+ club.getPlayedMatches() + "\n"
						+ club.getWins() + "\n"
						+ club.getDraws() + "\n"
						+ club.getDefeats() + "\n"
						+ club.getgScored() + "\n"
						+ club.getgReceived() + "\n"
						+ club.getPoints()+ "\n");
			}
			writer.close();
			System.out.println("Club information has been saved !");
		}
		catch (Exception error){
			System.out.println("Exception error : "+ error);
		}
		
		//Save list of played matches
		try {
			File file2 = new File("." + File.separator + "played_matches.txt");
			file2.createNewFile();
			FileWriter writer2 = new FileWriter(file2);
			
			Iterator<MatchData> itr = listMatches.iterator();
			while (itr.hasNext()) {
				MatchData match = itr.next();
				writer2.write(
						match.getDate() + "\n"
						+ match.getNameClub_1() + "\n"
						+ match.getNameClub_2() + "\n"
						+ match.getScoreClub_1() + "\n"
						+ match.getScoreClub_2() + "\n");
			}
			writer2.close();
			System.out.println("Match data has been saved!");
		}
		catch (Exception error){
			System.out.println("Exception error : " +error);
		}
	}
	//////////////////////////////////////////////////////////////
	
	//resume the previous state of the program
	void loadDataFromFile() throws Exception{
		try {
			//Load club list
			String path = System.getProperty("user.dir");
			Scanner readFile = new Scanner(new BufferedReader(new FileReader(path + File.separator + "clubs_list.txt")));
			while (readFile.hasNext()) {
				String name = readFile.nextLine();
				String location = readFile.nextLine();
				FootballClub club = new FootballClub(name, location);
				club.setPlayedMatches(Integer.parseInt(readFile.nextLine()));
				club.setWins(Integer.parseInt(readFile.nextLine()));
				club.setDraws(Integer.parseInt(readFile.nextLine()));
				club.setDefeats(Integer.parseInt(readFile.nextLine()));
				club.setgScored(Integer.parseInt(readFile.nextLine()));
				club.setgReceived(Integer.parseInt(readFile.nextLine()));
				club.setPoints(Integer.parseInt(readFile.nextLine()));
				listClubs.add(club);
			}
			readFile.close();
			System.out.println(".......Clubs has been loaded!");
		}
		catch (FileNotFoundException error) {
			System.out.println("Exception error:\nNo data to load!\nAdd and save data first, then reopen the simulator.\n");
		}
		
		//load match list
		try {
			String path = System.getProperty("user.dir");
			Scanner readFile = new Scanner(new BufferedReader(new FileReader(path + File.separator + "played_matches.txt")));
			while (readFile.hasNext()) {
				String date = readFile.nextLine();
				String nameClub_1 = readFile.nextLine();
				String nameClub_2 = readFile.nextLine();
				int scoreClub_1 = Integer.parseInt(readFile.nextLine());
				int scoreClub_2 = Integer.parseInt(readFile.nextLine());
				MatchData match = new MatchData(date, nameClub_1 ,nameClub_2,scoreClub_1,scoreClub_2 );
				match.setDate(date);
				match.setNameClub_1(nameClub_1);
				match.setNameClub_2(nameClub_2);
				match.setScoreClub_1(scoreClub_1);
				match.setScoreClub_2(scoreClub_2);
				listMatches.add(match);
			}
			readFile.close();
			System.out.println("... Matches history has been loaded!");
		}
		catch (FileNotFoundException error) {
            System.out.println("Exception error:\nNo data to load!\nAdd and save data first, then reopen the simulator.\n");
        }
	}
	
	///////////////////////////////////////////////////////////////////
	private void displayClubsNoInfo() {
	        System.out.println("There are now " + getNoOfClubs() + " clubs in the Premier League.");
	}
	
	private void displayClubNames(List<FootballClub> clubsList) {
        System.out.println("Current clubs list:");
        clubsList.forEach(club -> {
            if (clubsList.indexOf(club) < clubsList.size()-1) {
                System.out.print(club.getName() + ", ");
            } else {
                System.out.println(club.getName());
            }
        });
    }
	
	
	// Validation methods
    private boolean nameValidationFailed(String name) {
        if (name.equals("")) {
            System.out.print("Name cannot be empty!\nTry again: ");
            return true;
        }
        if (isInteger(name)) {
            System.out.print("Name cannot be a number!\nTry again: ");
            return true;
        }
        return false;
    }
    
    private boolean clubNotInLeague(String name) {
        Iterator<FootballClub> itr = listClubs.iterator();
        while (itr.hasNext()) {
            FootballClub club = itr.next();
            if (club.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    private boolean isNotNumberInRange(String numberAsString) {
        try {
            int number = Integer.parseInt(numberAsString);
            if (number >= 0 && number < 100) {
                return false;
            }
            return true;
        } catch (NumberFormatException error) {
            return true;
        }
    }

    private boolean isInteger(String name) {
        try {
            int integer = Integer.parseInt(name);
            return true;
        }
        catch(NumberFormatException error) {
            return false;
        }
    }

    private boolean wrongDateFormat(String date) {
        if (Pattern.matches("[0-3][0-9]-[0-1][0-9]", date)) {
            return false;
        }
        return true;
    }
    
    //Star GUI
    void startGUI() {
    	System.out.println("Progarm Starting in GUI...........");
    	JFrame gui = new GUI(listClubs, listMatches, "Football Championship Simulator");
    	gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	gui.setSize(900,500);
    	gui.setVisible(true);
    }
    
}
