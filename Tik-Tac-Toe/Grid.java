package tictactoe;

import java.util.Arrays;
import java.util.Scanner;

class Grid {
    int row;
    int col;
    String[][] grid;

    public Grid(int row, int col) {
        this.row = row;
        this.col = col;
        this.grid = new String[row][col];
    }

    public void makeGrid() {
        for (String[] strings : grid) {
            Arrays.fill(strings, "");
        }
    }

    public void takeCoordinate() {
        String option = "X";
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the coordinates: ");
            String coordinates = scanner.nextLine();
            char[] cor = coordinates.toCharArray();
            int row;
            int col;
            try {
                row = Integer.parseInt(String.valueOf(cor[0])) - 1;
                col = Integer.parseInt(String.valueOf(cor[2])) - 1;
                if (row > 2 || row < 0 || col > 2 || col < 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            boolean isFree = isFree(row, col);
            if (!isFree) {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }
            this.grid[row][col] = option;
            printGrid();

            if (checkStatus().equals("X wins")) {
                System.out.println("X wins");
                break;
            } else if (checkStatus().equals("O wins")) {
                System.out.println("O wins");
                break;
            } else if (isFull()) {
                System.out.println("Draw");
                break;
            }
            if (option.equals("X"))
                option = "O";
            else {
                option = "X";
            }
        }
    }

    private boolean isFree(int row, int col) {
        return this.grid[row][col].equals("");
    }

    private boolean isFull() {
        for (String[] item : this.grid) {
            for (String i : item) {
                if (i.equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

    public String checkStatus() {
        StringBuilder rowCheck = new StringBuilder();
        StringBuilder colCheck = new StringBuilder();
        StringBuilder diagonalCheck = new StringBuilder();
        StringBuilder reverseDiagonalCheck = new StringBuilder();

        for (String[] strings : grid) {
            // Check the row
            for (String string : strings) {
                rowCheck.append(string);
            }
            if (String.valueOf(rowCheck).equals("XXX")) {
                return "X wins";
            }
            if (String.valueOf(rowCheck).equals("OOO")) {
                return "O wins";
            }
            rowCheck.setLength(0);
        }

        for (int i = 0; i < grid.length; i++) {
            // Check the column
            for (int j = 0; j < grid[i].length; j++) {
                colCheck.append(grid[j][i]);
            }
            if (String.valueOf(colCheck).equals("XXX")) {
                return "X wins";
            }
            if (String.valueOf(colCheck).equals("OOO")) {
                return "O wins";
            }
            colCheck.setLength(0);
        }

        for (int i = 0; i < grid.length; i++) {
            // check the diagonal
            for (int j = 0; j < grid[i].length; j++) {
                if (i == j) {
                    diagonalCheck.append(grid[i][j]);
                }
                if (String.valueOf(diagonalCheck).equals("XXX")) {
                    return "X wins";
                } else if (String.valueOf(diagonalCheck).equals("OOO")) {
                    return "O wins";
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            // Check the reverse diagonal
            for (int j = 0; j < grid[i].length; j++) {
                if (i + j == grid.length - 1) {
                    reverseDiagonalCheck.append(grid[i][j]);
                }
                if (String.valueOf(reverseDiagonalCheck).equals("XXX")) {
                    return "X wins";
                } else if (String.valueOf(reverseDiagonalCheck).equals("OOO")) {
                    return "O wins";
                }
            }
        }
        return "None";
    }

    public void printGrid () {
        System.out.println("-".repeat(grid[0].length * 3));
        for (String[] strings : grid) {
            System.out.print("| ");
            for (int j = 0; j < grid[0].length; j++) {
                if (strings[j].equals(""))
                    System.out.print(" " + " ");
                else
                    System.out.print(strings[j] + " ");
            }
            System.out.print("|\n");
        }
        System.out.println("-".repeat(grid[0].length * 3));
    }
}
