package finalproj;
import java.io.*;
import java.util.*;
import com.google.common.graph.*;

class Main{

    static HashSet<Station> allStations = new HashSet<Station>();
    //MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();

    public static void main (String args[]){

        MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();
        
        Scanner file = null;
        System.out.println("amtrack.txt");
        try {
            System.out.println("file found");
            file = new Scanner(new File("data/amtrack.txt"));
            
        }catch(FileNotFoundException e){
            
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
        file.nextLine();

        while(file.hasNextLine()){
              
            String[] data = file.nextLine().split(",");
            
            if(data[0].length()>0 )  {
                allStations.add(new Station(Double.parseDouble(data[0]),Double.parseDouble(data[1]),data[3],data[4],data[5]));
            }  
    }
        System.out.println(allStations);

        weightedGraph.addNode(3);
        weightedGraph.addNode(2);
        
        System.out.println(weightedGraph);

        //graph building stuff here!
        for(Station station: allStations){
            //weightedGraph.addNode(destinationStation);
            
        }


    }



}