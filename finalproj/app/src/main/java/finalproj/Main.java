package finalproj;
import java.awt.Color;
import java.io.*;
import java.util.*;

import javax.management.RuntimeErrorException;

import org.checkerframework.checker.units.qual.degrees;

import com.google.common.graph.*;

class Main{

   static ArrayList<Station> allStations = new ArrayList<Station>();
   static ArrayList<Station> goodPath = new ArrayList<Station>();
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
        for(int i=0;i<allStations.size()-1;i++){
             //fix for consectuive
             if(allStations.get(i).getCurrentStation().equals(allStations.get(i+1).getCurrentStation())){
              System.out.println("i is "+allStations.get(i)+" and i+1 is "+allStations.get(i+1));

             }
             else{
              routes.putEdgeValue(findStation(allStations.get(i).getCurrentStation(),allStations),findStation(allStations.get(i+1).getCurrentStation(),allStations), pyth(allStations.get(i).getx(),allStations.get(i).gety(),allStations.get(i+1).getx(),allStations.get(i+1).gety()));
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
        if(startStation!=(null)){
          System.out.println("station station is "+startStation);

          //ArrayList<Station> path= depthFirstTraversal(startStation, destination);
          goodPath=depthFirstTraversal(startStation, destination);
          System.out.println(goodPath);
          if(goodPath==null){
            System.out.println("no route found");
          }
          else{
            for(int i=0;i<goodPath.size()-1;i++){
              correctRoutes.putEdgeValue(goodPath.get(i),goodPath.get(i+1), pyth(goodPath.get(i).getx(),goodPath.get(i).gety(),goodPath.get(i+1).getx(),goodPath.get(i+1).gety()));
            }
          }
          GraphDisplay slay = new GraphDisplay(correctRoutes);
          //GraphDisplay slay2 = new GraphDisplay(routes);
          //okay we have a real LAX issue!!!
          //okay more in general, we have issue with the very last or first stops, prob bc of END and START neighbors!!

        }
        else if (startStation==(null)){
         System.out.println("You start station does not exist :( Sad bad no good)");
        
        }
  
    }

    public static Station findStation(String stationName, ArrayList<Station> ar){
      for(int i=0; i<ar.size();i++){
        if(ar.get(i).getCurrentStation().equals(stationName)){
          return ar.get(i);
        }
      }
      System.out.println("The station you entered is not valid");
      return null;
      //throw new RuntimeErrorException(null, "The station you entered is not valid");
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

      
//nick said we should return arraylist 
      public static ArrayList<Station> depthFirstTraversal(Station start, String destination){
        ArrayList<Station> seen = new ArrayList<Station>();
        ArrayList<Station> path= new ArrayList<Station>();
        seen.add(start);
        path.add(start);
        // ArrayList<Station> pathypathpath = new ArrayList<Station>();
        // //System.out.println("path in first DFT"+depthFirstTraversal(path,destination,start,seen,routes));
        // pathypathpath = depthFirstTraversal(path,destination,start,seen,routes);
        // System.out.println("pathypathapth: "+pathypathpath);
        return depthFirstTraversal(path,destination,start,seen,routes);


      }

      private static ArrayList<Station> depthFirstTraversal(ArrayList<Station> path,String destination, Station node, ArrayList<Station>seen, ValueGraph<Station, String> r){
        //where can we make a new arraylist????? im so confused??? we want it to be a new one each time
        if(destination.equals(node.getCurrentStation().trim())){
          System.out.println("slay");
          validRoute=true;
          System.out.println("path in DFT metho"+path);
          return path;
        }
        System.out.println("in second DFT method and destination is "+destination);
        for(Station nb:r.successors(node)){
          System.out.println("current neighbor is "+nb.getCurrentStation());
  
          if(!seen.contains(nb)){
            System.out.println("not yet seen");
            path.add(nb);
            System.out.println(path);
            seen.add(nb);
            return depthFirstTraversal(path,destination, nb,seen,r);
          }
        }
        //this is whgat its returning everytime no matter what

        return null;
      }

      



}