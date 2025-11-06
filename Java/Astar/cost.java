package Astar;

public class cost {
    public static double cost_spent(double current_x, double current_y, double start_x, double start_y) {
        return (current_x - start_x) + (current_y - start_y);
    }
}
