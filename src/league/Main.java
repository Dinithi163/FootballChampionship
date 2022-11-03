package league;

public class Main {
	public static void main(String[] args) throws Exception {

        System.out.println("Welcome to the Football Championship Simulator!\n");
        // Create Premier League instance
        PremierLeagueManager premierLeague = new PremierLeagueManager();
        // Load data from file if exists
        premierLeague.loadDataFromFile();
        // Current no of clubs in the league
        System.out.println("########  The season 2020-21 is ongoing!  ########");
        System.out.println("There currently are " + premierLeague.getNoOfClubs() + " clubs in the Premier League.");

        // Loading main menu:
        String menuChoice = premierLeague.menu();
        while (!menuChoice.equals("q")) {
            switch (menuChoice) {
                case "c":
                    System.out.println("\nCreate new club:\n---------------------");
                    premierLeague.clubCreate();
                    menuChoice = premierLeague.menu();
                    break;
                case "r":
                    System.out.println("\nRemove club from Premier League:\n---------------------");
                    premierLeague.clubRemove();
                    menuChoice = premierLeague.menu();
                    break;
                case "d":
                    System.out.println("\nDisplay details & stats of a club:\n---------------------");
                    premierLeague.clubStatsDisplay();
                    menuChoice = premierLeague.menu();
                    break;
                case "t":
                    System.out.println("\nDisplay Premier League table:\n--------------------------------------------------");
                    premierLeague.tableDisplay();
                    menuChoice = premierLeague.menu();
                    break;
                case "a":
                    System.out.println("\nAdd a played match:\n---------------------");
                    premierLeague.matchAdd();
                    menuChoice = premierLeague.menu();
                    break;
                case "s":
                    System.out.println("\nSave data to the file:\n---------------------");
                    premierLeague.saveToFile();
                    menuChoice = premierLeague.menu();
                    break;
                case "g":
                    System.out.println("\nLaunch Graphical User Interface:\n---------------------");
                    premierLeague.startGUI();
                    menuChoice = premierLeague.menu();
                    break;
                default:
                    System.out.println("\nInvalid input!");
                    premierLeague.menu();
            }
        }
        // Closing the program:
        System.out.println("\nYour session has ended. Goodbye!");
    }
}
