package finalproj;
import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.util.*;
import javax.management.RuntimeErrorException;
import org.checkerframework.checker.units.qual.degrees;
import com.google.common.graph.*;
import java.math.RoundingMode;  
import java.text.DecimalFormat;

class Main{
  static ArrayList<Station> allStations = new ArrayList<Station>();
  static ArrayList<Station> goodPath = new ArrayList<Station>();
  static ArrayList<Integer> degrees = new ArrayList<Integer>();
  static MutableValueGraph<Station, String> routes = ValueGraphBuilder.directed().build();
  static MutableValueGraph<Station, String> correctRoutes = ValueGraphBuilder.directed().build();
  static boolean validRoute = false;
  private static final DecimalFormat round = new DecimalFormat("0.00"); 

  public static void main (String args[]){  
    //Reads in data file of all amtrack routes      
    Scanner file = null;
    try {
      file = new Scanner(new File("data/routes.txt"));    
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

    //turns all the stations and data into nodes and edges of graph
    for(int i=0;i<allStations.size()-1;i++){
      if(allStations.get(i).getCurrentStation().equals(allStations.get(i+1).getCurrentStation())){
        //skips station
      }
      else if(allStations.get(i).getNextStation().equals("END")){
        //skips station
      }
      else{
        routes.putEdgeValue(findStation(allStations.get(i).getCurrentStation(),allStations),findStation(allStations.get(i+1).getCurrentStation(),allStations), pyth(allStations.get(i).getx(),allStations.get(i).gety(),allStations.get(i+1).getx(),allStations.get(i+1).gety()));
      }  
    }

    //reads in data from user input file
    Scanner userInput = null;
    try {
      userInput = new Scanner(new File("data/userInput.txt"));  
    }catch(FileNotFoundException e){  
      System.err.println("Cannot locate file.");
      System.exit(-1);
    }
    //turns user input data into Start station and Destination location
    userInput.nextLine();
    String from= userInput.nextLine().trim();
    userInput.nextLine();
    String destination= userInput.nextLine().trim();
    userInput.close();
    Station startStation = findStation(from, allStations);

    //checks to make sure Start station is valid
    if(startStation!=(null)){
      //creates correct route from user input
      goodPath=depthFirstTraversal(startStation, destination);
      if(goodPath==null){
        System.out.println("no route found");
      }
      else{
        for(int i=0;i<goodPath.size()-1;i++){
          correctRoutes.putEdgeValue(goodPath.get(i),goodPath.get(i+1), pyth(goodPath.get(i).getx(),goodPath.get(i).gety(),goodPath.get(i+1).getx(),goodPath.get(i+1).gety()));
        }
      }
      //dispays graph of correct route from Start station to destination
      GraphDisplay slay = new GraphDisplay(correctRoutes);
      //LOCATION STUFF
      // for(int i=0;i<goodPath.size()-1;i++){
      //   System.out.println("hi");
      //   slay.setLoc(goodPath.get(i), new Point((int)(goodPath.get(i).getx())/10, (int)(goodPath.get(i).gety())/10));
      // }
      

      //displays graph of all the possible amtrack routes and nodes
      //GraphDisplay slay2 = new GraphDisplay(routes);

      //Calculates Statistics: this calculates stats for CORRECT ROUTE, statistics do not apply for all routes
      //number of nodes:
      System.out.println("number of nodes is: "+ goodPath.size());

      //average degree:
      //okay did this kinda manually, assumming it will always be correct bc of yadayadayada
      //System.out.println("Average Degree is "+avgDegree(degrees));
      double average=(((double)(goodPath.size()*2-2)/goodPath.size()));
      System.out.println("average degree is: "+ round.format(average));
      
      //number of edges: using degree sum formual
      System.out.println("number of edges: "+ ((goodPath.size()*2-2)/2));

      //maximum degree:
      Collections.sort(degrees);
      int maxDeg= degrees.get(degrees.size()-1)/2;
      System.out.println("maximum degree: "+ maxDeg );
      

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
          //System.out.println("slay");
          validRoute=true;
          //System.out.println("path in DFT metho"+path);
          return path;
        }
        //System.out.println("in second DFT method and destination is "+destination);
        for(Station nb:r.successors(node)){
         // System.out.println("current neighbor is "+nb.getCurrentStation());
  
          if(!seen.contains(nb)){
            degrees.add(r.degree(nb));
            System.out.println(r.degree(nb));
            //System.out.println("not yet seen");
            path.add(nb);
            //System.out.println(path);
            seen.add(nb);
            return depthFirstTraversal(path,destination, nb,seen,r);
          }
        }
        //this is whgat its returning everytime no matter what

        return null;
      }

      public static int numNodes (ValueGraph<Station,String> g){
        Set<Station> s = g.nodes();
        return s.size();
      }

      public static double avgDegree(ArrayList<Integer> d){
        int sum=0;
        for(int i=0;i<d.size();i++){
          System.out.println(d.get(i));
          sum+=d.get(i);

        }
        System.out.println(d.size());
        System.out.println(sum);
        return sum/d.size();
      }

      


}