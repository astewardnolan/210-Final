package finalproj;
import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.management.RuntimeErrorException;

import org.checkerframework.checker.units.qual.degrees;

import com.google.common.graph.*;

class Main{

   static ArrayList<Station> allStations = new ArrayList<Station>();
   static MutableValueGraph<Station, String> routes = ValueGraphBuilder.directed().build();
   static MutableValueGraph<Station, String> correctRoutes = ValueGraphBuilder.directed().build();
   static boolean validRoute = false;

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
        String from= userInput.nextLine().trim();
        userInput.nextLine();
        String destination= userInput.nextLine().trim();
        userInput.close();
        System.out.println("from station is "+from);
        System.out.println("destination station is "+destination);


        Station startStation = findStation(from, allStations);
        System.out.println("station station is "+startStation);

        //hardcoded rn!!!!
        depthFirstTraversal(startStation, destination);



        GraphDisplay slay = new GraphDisplay(correctRoutes);
        // Station ugh = allStations.get(0);
        // depthFirstTraversal(ugh,"PON");
        // //System.out.println("hi");
     
    }

    public static Station findStation(String stationName, ArrayList<Station> ar){
      for(int i=0; i<ar.size();i++){
        if(ar.get(i).getCurrentStation().equals(stationName)){
          return ar.get(i);
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
        ArrayList<Station> seen = new ArrayList<Station>();
        //routes.setColor(start,Color.PINK);
        seen.add(start);
        System.out.println(seen.toString());
        depthFirstTraversal(destination,start,seen, routes);
        //Station des = findStation(destination,seen);
        if (!validRoute){
          System.out.println("No route was found between the two stations");
        }
        else{
          for(int i=0;i<seen.size()-1;i++){
            correctRoutes.putEdgeValue(seen.get(i),seen.get(i+1), pyth(seen.get(i).getx(),seen.get(i).gety(),seen.get(i+1).getx(),seen.get(i+1).gety()));
          }
        }

      }

      private static void depthFirstTraversal(String destination, Station node, ArrayList<Station>seen, ValueGraph<Station, String> r){
        //visit(node);
        System.out.println("in second DFT method and destination is "+destination);
        for(Station nb:r.successors(node)){
          System.out.println("current neighbor is "+nb.getCurrentStation());
          if(nb.getCurrentStation().equals(destination)){
            seen.add(nb);
            System.out.println(nb.getCurrentStation());
            System.out.print("end");
            validRoute=true;
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