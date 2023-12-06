public class Station {

    double xCord;
    double yCord;
    String code;
    
    public Station (double x, double y, String c){
        xCord=x;
        yCord=y;
        code=c;
    }

    public String toString(){
        return "X cord is: "+xCord+", y cord is: "+yCord+", station code is: "+code;
    }

}
