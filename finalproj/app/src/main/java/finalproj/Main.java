package finalproj;
import java.io.*;
import java.util.*;

import javax.management.RuntimeErrorException;

import com.google.common.graph.*;

class Main{

   static ArrayList<Station> allStations = new ArrayList<Station>();
   static MutableValueGraph<Station, String> routes = ValueGraphBuilder.directed().build();

    //MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();

    public static void main (String args[]){

        //is there a reason this is Integer, Double?
       // MutableValueGraph<Station, String> routes = ValueGraphBuilder.directed().build();
        
        Scanner file = null;
        //System.out.println("amtrack.txt");
        String[] amtrakFiles = new String[]{"CHItoLAX.txt","CHItoNOL.txt","LAXtoCHI","LAXtoSEA.txt","NOLtoCHI.txt","SABtoWAS","SEAtoLAX.txt","WAStoSAB"};
        try {
            System.out.println("file found");
            file = new Scanner(new File("data/CHItoLAX.txt"));
            
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
    file.close();
        System.out.println(allStations);


        //graph building stuff here!
        for(int i=0;i<allStations.size();i++){
            if(!allStations.get(i).getNextStation().equals("END")){
              //can you add edges without nodes 
              routes.putEdgeValue(allStations.get(i),allStations.get(i+1), pyth(allStations.get(i).getx(),allStations.get(i).gety(),allStations.get(i+1).getx(),allStations.get(i+1).gety()));
            }
          }
        
        Scanner userInput = null;
        //System.out.println("amtrack.txt");
        try {
            System.out.println("file found");
            userInput = new Scanner(new File("data/userInput.txt"));
            
        }catch(FileNotFoundException e){
            
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }

        //SECOND FILE SCANNER FOR USER INPUT
        userInput.nextLine();
        String from= userInput.nextLine();
        userInput.nextLine();
        String destination= userInput.nextLine();
        userInput.close();


        Station startStation = findStation(from);
        System.out.println(startStation);

        depthFirstTraversal(startStation, destination);



        // //GraphDisplay slay = new GraphDisplay(routes);
        // Station ugh = allStations.get(0);
        // depthFirstTraversal(ugh,"PON");
        // //System.out.println("hi");
     
    }

    public static Station findStation(String stationName){
      for(int i=0; i<allStations.size();i++){
        if(allStations.get(i).getCurrentStation().equals(stationName)){
          return allStations.get(i);
        }
      }
      //test this....
      throw new RuntimeErrorException(null, "The station you entered is not valid");
    }
    /**
     * calcualtes distance between two end points using pythagorean theorem
     */
    public static String pyth (double x1, double y1, double x2, double y2){
        double xLen = Math.abs(x2-x1);
        double yLen = Math.abs(y2-y1);
        double distance = Math.sqrt(Math.pow(xLen,2)+Math.pow(yLen,2));
        int d= (int) Math.floor(54.6*distance);
        return d+" miles";
      }

      public static void depthFirstTraversal(Station start, String destination){
        System.out.println("HI");
        HashSet<Station> seen = new HashSet<Station>();
        seen.add(start);
        System.out.println(seen.toString());
        depthFirstTraversal(destination,start,seen, routes);
      }

      private static void depthFirstTraversal(String destination, Station node, HashSet<Station>seen, ValueGraph<Station, String> r){
        //visit(node);
        for(Station nb:r.successors(node)){
          if(nb.getCurrentStation().equals(destination)){
            System.out.println(nb.getCurrentStation());
            System.out.print("end");
            break;
          }

          if(!seen.contains(nb)){
            System.out.println("not yet seen");
            seen.add(nb);
            depthFirstTraversal(destination, nb,seen,r);
          }
        }
      }

      



}