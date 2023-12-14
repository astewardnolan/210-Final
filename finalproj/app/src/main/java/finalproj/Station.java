package finalproj;
/**
 * class to create station object
 */
public class Station {
    double xCord;
    double yCord;
    String prevStation;
    String destinationStation;
    String nextStation;

/**
 * constructor to create indivdual station object
 * @param x x cordinate, or latiude location of station
 * @param y y cordinate, or longitude location of station
 * @param from Stations previous neighbor
 * @param current current Station
 * @param next Stations next neighbor
 */
    public Station (double x, double y, String from, String current, String next){
        xCord=x;
        yCord=y;
        prevStation=from;
        destinationStation = current;
        nextStation = next;
    }

    /**
     * acessor for x cordinate
     * @return xcord
     */
    public double getx(){
        return xCord;
    }
    /**
     * accessor for y cordinate
     * @return ycord
     */
    public double gety(){
        return yCord;
    }

    /**
     * accessor for Stations previous station
     * @return previous station
     */
    public String getPrevStation(){
        return prevStation;
    }

    /**
     *accessor for current Stations 
     * @return current station
     */
    public String getCurrentStation(){
        return destinationStation;
    }

    /**
     *accessor for Stations next station
     * @return next station
     */
    public String getNextStation(){
        return nextStation;
    }

    /** 
     * converts Station to string of its name (3 digit code)
     */
    public String toString(){
        return destinationStation;
    }
}
