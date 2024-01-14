import java.util.*;
import java.text.*;

class Main {
    public static void main(String[] args) {
        boolean finish = false;
        SearchShortestPath ssp = new SearchShortestPath();
        SearchBusStop sbs = new SearchBusStop("stops.txt");
        boolean wasSearchShortestPathCalled = false;
        boolean wasSearchBusStopCalled = false;

        while (!finish) {
            System.out.println("\nEnter a number:) (0, 1, 2 or 3):\n"
                    + "0: Exit\n"
                    + "1: Get a shortest path between 2 stops\n"
                    + "2: Search for a bus stop\n"
                    + "3: Search for all trips with an arrival time\n");
            Scanner sc = new Scanner(System.in);
            if (sc.hasNextInt()) {
                int userInput = sc.nextInt();
                switch (userInput) {
                    case 0:
                        System.out.println("See you!:)");
                        finish = true;
                        break;
                    case 1:
                        searchShortestPath(ssp, wasSearchShortestPathCalled);
                        wasSearchShortestPathCalled = true;
                        break;
                    case 2:
                        searchBusStop(sbs, wasSearchBusStopCalled);
                        wasSearchBusStopCalled = true;
                        break;
                    case 3:
                        searchArrivalTime();
                        break;
                    default:
                        System.out.println("Please enter 0, 1, 2 or 3");
                        break;
                }
            } else {
                System.out.println("Please enter an integer:(");
            }
        }
    }

    public static void searchShortestPath(SearchShortestPath ssp, boolean wasSearchShortestPathCalled) {
        if (!wasSearchShortestPathCalled) {
            ssp.makeADigraph();
        }

        boolean finished = false;

        while (!finished) {
            int source = 0;
            int dest = 0;
            boolean validInput = false;
            while (!validInput && !finished) {
                System.out.println("\nPlease enter an ID of the source bus stop or type back to go back\n");
                Scanner sc = new Scanner(System.in);
                String userInput = sc.next();
                if (userInput.equals("back")) {
                    finished = true;
                } else {
                    try {
                        source = Integer.parseInt(userInput);
                        if (ssp.isCorrectInput(source)) {
                            validInput = true;
                        } else {
                            System.out.println("Please enter a bus stop ID that exists\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input\n");
                    }
                }
            }

            validInput = false;

            while (!validInput && !finished) {
                System.out.println("\nPlease enter an ID of the destination bus stop or type back to go back\n");
                Scanner sc = new Scanner(System.in);
                String userInput = sc.next();
                if (userInput.equals("back")) {
                    finished = true;
                } else {
                    try {
                        dest = Integer.parseInt(userInput);
                        if (ssp.isCorrectInput(dest)) {
                            validInput = true;
                        } else {
                            System.out.println("Please enter a bus stop ID that exists\n");
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input\n");
                    }
                }
            }
            if(!finished)ssp.outoutShortestPath(source, dest);
        }

    }

    public static void searchBusStop(SearchBusStop sbs, boolean onceCalled) {

        if (!onceCalled) {
            try {
                sbs.makeTree();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        boolean finished = false;
        do {
            System.out.println("Please enter a name of a bus stop you are looking for: \n"
                    + "or type back to go back\n");
            Scanner sc = new Scanner(System.in);
            String prefix = sc.next();
            if (prefix.equals("back")) {
                finished = true;
            } else {
                try {
                    prefix = prefix.toUpperCase();
                    if (sbs.searchBusStop(prefix)) {
                        sbs.outputBusStopInfo();
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    break;
                }
            }

        } while (!finished);
    }

    public static void searchArrivalTime() {

        boolean finished = false;
        while (!finished) {
            System.out.println(
                    "Please enter an arrival time with (hh:mm:ss) format. Plese note that we can accept only in a range 00:00:00 ~ 23:59:59\n"
                            + "or type back to go back");
            Scanner sc = new Scanner(System.in);
            String userInput = sc.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            timeFormat.setLenient(false);
            if (userInput.equals("back")) {
                finished = true;
            } else {
                try {
                    Date userDate = timeFormat.parse(userInput);
                    SearchArrivalTime.output(userInput, dateFormat);
                } catch (Exception e) {
                    {
                        System.out.println("Invalid input.\n");
                    }
                }
            }

        }
    }
}