import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class SearchShortestPath {

    private HashMap<Integer, String[]> busInfo = new HashMap<>();
    private HashMap<Integer, Integer> busIDWithPlace = new HashMap<>();
    private HashMap<Integer, Integer> placeWithBusID = new HashMap<>();
    private int busPlace = 0;
    EdgeWeightedDigraph G;

    public void makeADigraph() {
        this.G = new EdgeWeightedDigraph(getVertexCount()); // initialise the graph.
        addStopTimesEdges();
        addTransfersEdges();
    }

    public int getVertexCount() {
        try {
            BufferedReader stops = new BufferedReader(new FileReader("stops.txt"));
            // skip a first line.
            stops.readLine();
            String line;
            while ((line = stops.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                this.busInfo.put(id, data);
            }
            stops.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return this.busInfo.size();
    }

    public void addStopTimesEdges() {
        try {
            BufferedReader times = new BufferedReader(new FileReader("stop_times.txt"));
            // skip a first line.
            times.readLine();
            String line;
            int i = 0;
            int prevTripId = 0;
            int prevStopId = 0;
            while ((line = times.readLine()) != null) {
                String[] data = line.split(",");
                int currTripId = Integer.parseInt(data[0]);
                int currStopId = Integer.parseInt(data[3]);
                // for the first bus stop there's no other bus stop to make an edge.
                if (i != 0) {
                    if (prevTripId == currTripId) {
                        if (!this.busIDWithPlace.containsKey(prevStopId)) {
                            this.busIDWithPlace.put(prevStopId, this.busPlace);// create a place for bus stop to put it
                                                                               // in the graph.
                            this.placeWithBusID.put(this.busPlace, prevStopId);// this map enables us to get stop id
                                                                               // associated with a place in the graph.
                            this.busPlace++;
                        }
                        if (!this.busIDWithPlace.containsKey(currStopId)) {
                            this.busIDWithPlace.put(currStopId, this.busPlace);
                            this.placeWithBusID.put(this.busPlace, currStopId);
                            this.busPlace++;
                        }

                        DirectedEdge e = new DirectedEdge(this.busIDWithPlace.get(prevStopId),
                                this.busIDWithPlace.get(currStopId), 1); // make an edge from a bus stop to aone
                                                                         // another.
                        this.G.addEdge(e); // add the edge above to the graph
                    }
                }
                prevTripId = currTripId;
                prevStopId = currStopId;
                i++;
            }
            times.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addTransfersEdges() {
        try {
            BufferedReader transfers = new BufferedReader(new FileReader("transfers.txt"));
            transfers.readLine();
            String line;
            while ((line = transfers.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                int nextId = Integer.parseInt(data[1]);
                int transferType = Integer.parseInt(data[2]);
                double cost = 0;
                if (transferType == 0) {
                    cost = 2;
                } else {
                    cost = Double.parseDouble(data[3]) / 100;
                }
                if (!this.busIDWithPlace.containsKey(id)) {
                    this.busIDWithPlace.put(id, this.busPlace);
                    this.placeWithBusID.put(this.busPlace, id);
                    this.busPlace++;
                }
                if (!this.busIDWithPlace.containsKey(nextId)) {
                    this.busIDWithPlace.put(nextId, this.busPlace);
                    this.placeWithBusID.put(this.busPlace, nextId);
                    this.busPlace++;
                }
                DirectedEdge e = new DirectedEdge(this.busIDWithPlace.get(id), this.busIDWithPlace.get(nextId), cost);
                this.G.addEdge(e);
            }
            transfers.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void outoutShortestPath(int s, int dest) {
        if (s == dest) {
            System.out.println("Hmm... They are the same stop lol. Total cost: 0\n");
            return;
        }
        int sPlace = this.busIDWithPlace.get(s); // get the graph place associated with the bus ID.
        int destPlace = this.busIDWithPlace.get(dest);
        DijkstraSP sp = new DijkstraSP(this.G, sPlace); // G = graph, s = source
        // print stops that go through a shortest path
        if (sp.hasPathTo(destPlace)) {
            System.out.println(
                    "Here are stops en route from " + s + " to " + dest + ". Total cost: " + sp.distTo(destPlace));// sp.distTo
                                                                                                                   // =
                                                                                                                   // total
                                                                                                                   // Cost
            // get a shortest path from the source vertex {@code s} to vertex {@code dest}
            // as an iterable of edges, and {@code null} if no such path
            for (DirectedEdge e : sp.pathTo(destPlace)) {
                String[] data = this.busInfo.get(this.placeWithBusID.get(e.to()));
                // System.out.println("Bus stop id: " + this.placeWithBusID.get(e.to()) +
                // "\tName: " + data[2] // e.to() is a head vertex represented by place.
                // + "\t Cost from " + this.placeWithBusID.get(e.from()) + ": " + e.weight());
                System.out.printf("Bus stop id: %-8d Name: %-40s Cost from %-5d: %.2f%n",
                this.placeWithBusID.get(e.to()), // e.to() is a head vertex represented by place.
                data[2], this.placeWithBusID.get(e.from()), e.weight());
            }
        } else {
            System.out.printf("There is no path from %d to %d\n", s, dest);
        }
    }

    public boolean isCorrectInput(int id) {
        return this.busInfo.containsKey(id);
    }

}