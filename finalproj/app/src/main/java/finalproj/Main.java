package finalproj;
import java.io.*;
import java.util.*;
import com.google.common.graph.*;

class Main{

   static ArrayList<Station> allStations = new ArrayList<Station>();

    //MutableValueGraph<Integer, Double> weightedGraph = ValueGraphBuilder.directed().build();

    public static void main (String args[]){

        //is there a reason this is Integer, Double?
        MutableValueGraph<Station, String> routes = ValueGraphBuilder.directed().build();
        
        Scanner file = null;
        System.out.println("amtrack.txt");
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

        //weightedGraph.addNode(3);
        //weightedGraph.addNode(2);
        
        //System.out.println(weightedGraph);

        //graph building stuff here!
        for(int i=0;i<allStations.size();i++){
            if(!allStations.get(i).getNextStation().equals("END")){
              //can you add edges without nodes 
              routes.putEdgeValue(allStations.get(i),allStations.get(i+1), pyth(allStations.get(i).getx(),allStations.get(i).gety(),allStations.get(i+1).getx(),allStations.get(i+1).gety()));
            }
          }

        //GraphDisplay slay = new GraphDisplay(routes);

          Scanner scan = new Scanner(System.in);
          System.out.println("What Amtrak station are you leaving from? (enter the 3 letter station code in all caps)");
          String from = scan.nextLine();
          System.out.println("What station do you want to get to? (enter the 3 letter station code in all caps)");
          String destination = scan.nextLine();



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



}