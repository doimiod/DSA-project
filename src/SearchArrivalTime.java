import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class SearchArrivalTime {
    public static final String MAX_TIME = "23:59:59";

    public static boolean output(String userInput, SimpleDateFormat dateFormat) {
        PriorityQueue<String[]> sortedData = getSortedDataForOutput(userInput, dateFormat);
        if (sortedData.size() == 0) {
            System.out.println("There is no match\n");
            return false;
        } else {
            System.out.println("\nHere are " + sortedData.size() + " trips that match your search:)\n");
            while (sortedData.size() > 0) {
                String[] data = sortedData.poll();
                System.out.println(Arrays.toString(data));
            }
        }
        return true;
    }

    public static PriorityQueue<String[]> getSortedDataForOutput(String userInput, SimpleDateFormat dateFormat) {
        // this priority queue can store bus stop data sorted by bus stop ID.
        PriorityQueue<String[]> minHeap = new PriorityQueue<>(
                (a, b) -> (Integer.parseInt(a[0]) - Integer.parseInt(b[0])));

        try {
            BufferedReader times = new BufferedReader(new FileReader("stop_times.txt"));
            // skip a first line.
            times.readLine();
            String line;
            while ((line = times.readLine()) != null) {
                String[] data = line.split(",");
                String arrivalTime = data[1];
                // compare user input and the time the file has. if they are same then the data will be added to the minHeap.
                if (dateFormat.parse(userInput).compareTo(dateFormat.parse(arrivalTime)) == 0)
                    minHeap.add(data);
            }
            times.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return minHeap;
    }

}