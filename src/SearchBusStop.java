import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.*;

public class SearchBusStop {

    private String file;
    private TernarySearchTree tst;
    private HashMap<String, String[]> busInfo = new HashMap<>();
    private final String[] keys = { "FLAGSTOP", "WB", "NB", "SB", "EB" };
    private final ArrayList<String> keywords = new ArrayList<>(Arrays.asList(keys));

    SearchBusStop(String file) {
        this.file = file;
        this.tst = new TernarySearchTree();
    }

    public void makeTree() throws IOException {
        BufferedReader stops = new BufferedReader(new FileReader(this.file));

        // skip first line
        stops.readLine();

        String line;
        while ((line = stops.readLine()) != null) {

            String[] data = line.split(",");
            String busStopName = data[2];
            String meaningfulBusStopName = getMeaningfulBusStopName(busStopName);

            this.busInfo.put(meaningfulBusStopName, data); // put bus info associated with bus stop into the hashmap
            this.tst.insert(meaningfulBusStopName); // insert bus stop name into tst
        }
        stops.close();
    }

    public String getMeaningfulBusStopName(String busStopName) {
        String firstBusStopName = busStopName.trim().split("\\s+")[0]; // get first name of bus stop

        // if bus stop starts with one of the keys the order of the name is changed.
        if (this.keywords.contains(firstBusStopName)) {
            busStopName = busStopName.substring(firstBusStopName.length() + 1) + " " + firstBusStopName;
        }

        return busStopName;
    }

    public boolean searchBusStop(String prefix) throws IOException {
        return this.tst.foundPrefix(prefix);
    }

    // output all bus info whose name starts with the given prefix.
    public void outputBusStopInfo() {
        ArrayList<String> busStops = this.tst.getBusStops();
        System.out.println("\nHere is a list of bus stop that match with your search:)\n");
        for (String stop : busStops) {
            String[] data = this.busInfo.get(stop); // retrieve the data from the map
            System.out.println(
                    "Bus stop: " + data[2] + "\n"
                            + "Stop ID: " + ((data[0].equals(" ")) ? "unknown" : data[0])
                            + ", Stop Code: " + ((data[1].equals(" ")) ? "unknown" : data[1])
                            + ", Stop Description: " + ((data[3].equals(" ")) ? "unknown" : data[3])
                            + ", Stop Latitude: " + ((data[4].equals(" ")) ? "unknown" : data[4])
                            + ", Stop Longitude: " + ((data[5].equals(" ")) ? "unknown" : data[5])
                            + ", Zone ID: " + ((data[6].equals(" ")) ? "unknown" : data[6])
                            + ", Stop URL: " + ((data[7].equals(" ")) ? "unknown" : data[7])
                            + ", Locaton Type: " + ((data[8].equals(" ")) ? "unknown" : data[8]) + "\n");
        }
    }

}