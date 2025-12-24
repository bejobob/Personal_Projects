/*
Minesweeper Game

by Benjamin Kealey

v 2.3.2 (may 25, 2025): Ok, so now the not-a-mine-on-your-first-click rule actually works. I also fixed
                        the date next to the version number. And then I removed a chunk of code that
                        didn't do anything.
 */
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;
import java.util.ArrayList;

/*TODO list:
    -improve presentation
        -fix weird colour bleeding
        -only the mine that was pressed is red, other are dark grey
        -center grid? (this might make a bunch of coordinate stuff stop working, will have to see)
    -ingame restart button -DONE
    -freeze grid when you press on a mine but still allow player to use restart button
    -chords?
    -get rid of middle-click when testing is over -DONE
    -increase refresh speed -DONE (only conserned cells are repainted)
    -can't press on a mine after using reset button -DONE
    - figure out how to set up github and such
*/
public class MineSweeper extends Frame implements MouseListener {
    
    Scanner scanner = new Scanner(System.in);
    ArrayList<ArrayList<Integer>> zeroCoords = new ArrayList<>();
    int squares_wide;
    int squares_tall;
    int mines;
    int mines_rn = 0;
    boolean valid_input = false;
    String[][] grid_static;
    String[][] grid_dynamic;
    boolean click = false;
    Random random = new Random();
    boolean clicked = false; 
    int iteration = 0;
    int flags = 0;// I innitialize the variables I need

    public static String adjacent(String[][] board, int row, int column){ // detects the number of mines a cell is touching
        int touching_mines = 0; // start at 0
        for (int i = -1; i <= 1; i++){ 
            for (int j = -1; j <= 1; j++){ // these two loops identify the indeces for all the cells around the target cell
                try { // but if the cell is out of bounds, I catch it
                    if (board[row-i][column-j] == "M"){
                        touching_mines++; // if there is a mine, increase the value
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }
        return String.valueOf(touching_mines); // and we return the number of cells
    }
    public void zeros(int row, int column){
        iteration++;
        if (iteration == 1){
            zeroCoords.clear();
        }
        for (int i = -1; i <= 1; i++){ 
            for (int j = -1; j <= 1; j++){ // these two loops identify the indeces for all the cells around the target cell
                try { // but if the cell is out of bounds, I catch it
                    if (grid_dynamic[row + i][column + j] != null){
                        continue;
                    }
                    grid_dynamic[row+i][column+j] = grid_static[row+i][column+j];
                    repaint((row+i)*20+20,(column+j)*20+40,20,20);
                    if ("".equals(grid_dynamic[row+i][column+j])){
                        zeros(row+i, column+j);
                    }
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }
        iteration = 0;;
    }
    public void showmines(int row, int col){
        if (grid_dynamic[row][col] == "M"){
            for (int i = 0; i < squares_wide; i++){ 
                for (int j = 0; j < squares_tall; j++){
                    if (grid_static[i][j] == "M") {
                        grid_dynamic[i][j] = "M";
                    }
                }
            }
            repaint();
        }
    }
    public void gengrid(int rangee, int colonne){
        while (mines_rn != mines) { // I randomly select {mines} cells and identify them as cells
            int ran_row = random.nextInt(squares_wide);
            int ran_col = random.nextInt(squares_tall);
            if (ran_row == rangee && ran_col == colonne) {
                continue;
            }
            else if (grid_static[ran_row][ran_col] != "M") {
                grid_static[ran_row][ran_col] = "M";
                mines_rn ++;
            }
        } for (int row = 0; row < grid_static.length; row++) {
            for (int column = 0; column < grid_static[0].length; column++) {
                if (grid_static[row][column] != "M"){
                    grid_static[row][column] = adjacent(grid_static, row, column); // I count the number of mines each cell is touching
                    if (grid_static[row][column].equals("0")) {
                        grid_static[row][column] = "";
                    }
                } // all this is done to get the static_grid ready for the game. It will not be changed after this point
            }
        }
    }
    public MineSweeper() 
    { 
        while (!valid_input) { // I ask the user to define the size of the grid and the number of mines
            try {
                System.out.println("Enter the width of the grid (number of squares)");
                squares_wide = scanner.nextInt();
                System.out.println("Enter the height of the grid (number of squares)");
                squares_tall = scanner.nextInt();
                System.out.println("Enter the number of mines in the grid:");
                mines = scanner.nextInt();
                valid_input = true;
                grid_static = new String[squares_wide][squares_tall];
                grid_dynamic = new String[squares_wide][squares_tall]; // and innitialize the two grid-lists
            } catch (InputMismatchException e) {
                System.out.println("Please enter an integer"); // error message
                scanner.nextLine();
            }
        }
        setVisible(true); 
        setSize(squares_wide*20, squares_tall*20); // I create the canvas
        addWindowListener(new WindowAdapter() { 
            @Override
            public void windowClosing(WindowEvent e) 
            { 
                System.exit(0); // kill the program if the window is closed
            } 
        }); 
        addMouseListener(this); // I think this just waits to see when a mouse button is pressed
    } 
    public void paint(Graphics g){ // paints the canvas
        g.setFont(new Font("Arial", Font.BOLD, 12));
        for(int rw=20; rw<=squares_wide*20; rw = rw+20 ){ // just defining the coordinate stuff for drawing the squares (cells)
            for (int col=40; col<=squares_tall*20+20; col=col+20){ // there's a weird upper margin. y=2xh seems to work though
                
                if (grid_dynamic[(rw-20)/20][(col-40)/20] == null || grid_dynamic[(rw-20)/20][(col-40)/20] == "f") {
                    g.setColor(new Color(76,84,92));
                    
                } else if (grid_dynamic[(rw-20)/20][(col-40)/20] == "M"){
                    g.setColor(new Color(64, 0,0));
                } else {
                    g.setColor(new Color(56,64,72));
                }
                g.drawRect(rw, col, 20, 20);
                g.fillRect(rw, col, rw, col);
                g.setColor(Color.BLACK);
                g.drawRect(rw, col, 20, 20); // draw the rectangles and stuff
                
                try {
                    String cell = grid_dynamic[(rw - 20) / 20][(col - 40) / 20];
                    switch (cell) {
                        case "1":
                            g.setColor(Color.BLUE);
                            break;
                        case "2":
                            g.setColor(Color.GREEN);
                            break;
                        case "3":
                            g.setColor(Color.RED);
                            break;
                        case "4":
                            g.setColor(new Color(102,0,153));
                            break;
                        case "5":
                            g.setColor(new Color(153,102,0));
                            break;
                        case "6":
                            g.setColor(new Color(0,128,128));
                            break;
                        case "7":
                            g.setColor(Color.BLACK);
                            break;
                        case "8":
                            g.setColor(Color.GRAY);
                            break;
                        case "M", "f":
                            g.setColor(Color.RED);
                            break;
                        default:
                            break;
                    }
                    g.drawString(grid_dynamic[(rw-20)/20][(col-40)/20], rw+7, col+15); // I draw the value of each cell of the dynamic grid on the canvas. They start as blank because the dynamic grid is empty at first, and you can't draw a null value
                } catch (NullPointerException e) { // so I catch it here
                }   
            }
        }

        // Draw Mines: X and smiley only once, after the grid
        g.setColor(Color.BLACK);
        g.drawString("Mines: " + (mines - flags), squares_wide * 20 + 50, squares_tall * 20 - 50);
        g.setColor(Color.YELLOW);
        g.fillRect(squares_wide * 20 + 50, squares_tall * 20 - 40, 30, 30);
        g.setColor(Color.BLACK);
        g.drawString(":)", squares_wide * 20 + 62, squares_tall * 20 - 22);
    }
    @Override
    public void mouseClicked(MouseEvent e) { // do this stuff when the mouse is pressed
        
        int mouseX = e.getX();
        int mouseY = e.getY();

        int clRw = (mouseX - 20)/20;
        int clCol = (mouseY - 40)/20; // these four lines get the coordinates of the mouseclick and identify the row and column that those coordinates are associated with

        if (clCol >= 0 && clCol < squares_tall && clRw >= 0 && clRw < squares_wide){ // if the click was inside the grid (the grid is smaller than the canvas)
            if (e.getButton() == MouseEvent.BUTTON1){ // left click
                gengrid(clRw, clCol);
                clicked = true;
                if (grid_dynamic[clRw][clCol] == null){
                    grid_dynamic[clRw][clCol] = grid_static[clRw][clCol]; // I update the value of the cell in the dynamic grid to match the value of the coresponding cell of the static grid
                    showmines(clRw, clCol);
        
                    if ("".equals(grid_dynamic[clRw][clCol])){ // if the value of the clicked cell is 0, reveal the cells around it 
                        zeros(clRw, clCol);
                    }
                } else {
                    int falgs = 0;
                    for (int k = 0; k < 2; k++){
                        for (int i = -1; i <= 1; i++){ 
                            for (int j = -1; j <= 1; j++){
                                try {
                                    if (k == 0){
                                        if (grid_dynamic[clRw+i][clCol+j] == "f"){
                                            falgs++;
                                        }
                                    } else if ((String.valueOf(falgs)).equals(adjacent(grid_static, clRw, clCol))){
                                        if (grid_dynamic[clRw+i][clCol+j] == "f"){
                                            continue;
                                        } else {
                                            grid_dynamic[clRw+i][clCol+j] = grid_static[clRw+i][clCol+j];
                                            repaint((clRw+i)*20+20,(clCol+j)*20+40,20,20);
                                            if (grid_dynamic[clRw+i][clCol+j] == ""){
                                                zeros(clRw+i, clCol+j);
                                            } else if (grid_dynamic[clRw+i][clCol+j] == "M"){
                                                showmines(clRw+i, clCol+j);
                                            }
                                        }
                                    }
                                } catch (IndexOutOfBoundsException er) {     
                                }
                            }
                        }
                    }
                }
            } else if (e.getButton() == MouseEvent.BUTTON3){ // right click
                
                switch (grid_dynamic[clRw][clCol]){
                    case "f":
                        grid_dynamic[clRw][clCol] = null;
                        flags--;
                        break;
                    case null:
                        grid_dynamic[clRw][clCol] = "f";
                        flags++;
                        break;
                    default:
                        break;
                }
                repaint(squares_wide*20+50, squares_tall*20-62, 60, 20);
            }
        } else if ((squares_wide*20+82 >= mouseX && mouseX >= squares_wide*20+50) && (squares_tall*20-10 >= mouseY && mouseY >= squares_tall*20-40)) {
            clicked = false;
            for (int i = 0; i<squares_wide; i++){
                for (int j = 0; j<squares_tall; j++){
                    grid_dynamic[i][j] = null;
                    grid_static[i][j] = null;
                }
            }
            mines_rn = 0;
            flags = 0;
            repaint();
        }
        
            repaint(clRw*20+20,clCol*20+40,20,20);
        
    } 
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    public static void main(String[] args) 
    { 
        new MineSweeper(); 
    }
}