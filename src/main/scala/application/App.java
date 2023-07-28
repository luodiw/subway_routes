package application;
import backend.TransitAPI;
import java.util.Scanner;

public class App {

    private static final String INPUT_FILE_PATH = "./src/main/resources/stations_eng.csv";

    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
        TransitAPI api = new TransitAPI(INPUT_FILE_PATH);
        while (true) {
            System.out.println("Type your start station: ");
            String from = scanner.nextLine();
            if(from.equals("")) {
                break;
            }
            System.out.println("Type your station");
            String to = scanner.nextLine();
            System.out.println("Route:");
            try {
                System.out.println(api.getRoute(from, to));
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("Station doesn't exist!");
            }
        }
    }
}
