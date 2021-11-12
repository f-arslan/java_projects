package tictactoe;

public class Main {
    public static void main(String[] args) {
        int row = 3;
        int col = 3;
        Grid grid = new Grid(row, col);
        grid.makeGrid();
        grid.printGrid();
        grid.takeCoordinate();
    }
}
