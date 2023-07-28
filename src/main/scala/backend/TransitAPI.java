package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class TransitAPI {

    private final List<Station> stations;

    public TransitAPI(String path) {
        stations = getStationsFromCSV(path);
        addConnections(path);
    }

    private List<Station> getStationsFromCSV(String filePath) {
        BufferedReader csvReader;
        ArrayList<Station> l = new ArrayList<>();
        try {
            csvReader = new BufferedReader(new FileReader(filePath));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                l.add(new Station(data[0], data[1]));
            }
            csvReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return l;
    }

    private void addConnections(String path) {
        BufferedReader csvReader;
        try {
            csvReader = new BufferedReader(new FileReader(path));
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Station s = getStation(data[1]);
                Arrays.stream(Arrays.copyOfRange(data, 2, data.length)).map(this::getStation).forEach(e -> {
                    e.connect(s);
                    s.connect(e);
                });
            }
            csvReader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private Station getStation(String station) {
        return stations.stream().filter(s -> (s.getName().strip().toLowerCase().contains(station.strip().toLowerCase()))).findAny().get();
    }

    public String getRoute(String from, String to) {
        StringBuffer sb = new StringBuffer();
        Station f = getStation(from);
        Station t = getStation(to);
        LinkedList<Station> route = new LinkedList<>();
        route.add(t);
        route = f.route(route);
        route.forEach(s -> sb.append(s.toString()).append("\n"));
        sb.append("Number of stations: " + (route.size() - 1) + "\n");
        return sb.toString();
    }
}
