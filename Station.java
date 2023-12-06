public class Station {

    double xCord;
    double yCord;
    String prevStation;
    String destinationStation;
    String nextStation;
    
    public Station (double x, double y, String from, String current, String next){
        xCord=x;
        yCord=y;
        prevStation=from;
        destinationStation = current;
        nextStation = next;
    }

    public String toString(){
        return "X cord is: "+xCord+", y cord is: "+yCord+", train is coming from "+fromCode+" and going to "+toCode;
    }

}
