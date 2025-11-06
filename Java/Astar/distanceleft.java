package Astar;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class distanceleft {
    public static double distance(double current_x, double current_y, double target_x, double target_y) {
        return sqrt(pow((target_y - current_y), 2) + pow((target_x - current_x), 2));
    }
}
