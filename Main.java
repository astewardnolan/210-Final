import java.io.*;
import java.util.*;

class Main{

    static HashSet<Station> allStations = new HashSet<Station>();

    public static void main (String args[]){

        Scanner file = null;
        System.out.println("amtrack.txt");
        try {
            file = new Scanner(new File("amtrack.txt"));
            
        }catch(FileNotFoundException e){
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }

        while(file.hasNextLine()){
            String[] data = file.nextLine().split(",");
            allStations.add(new Station(Integer.parseInt(data[0]),Integer.parseInt(data[1]),data[3],data[4]));
        }


        

    }



}