package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static int totalIncome = 0;
    public static int currentIncome = 0;
    public static int n_tickets = 0;

    public static void main(String[] args) {
        int[] ans = menu();
        int row = ans[0];
        int col = ans[1];
        String[][] cinema = create_cinema(row, col);
        getTotalIncome(cinema);
        menu_2(cinema, row, col);

    }

    public static float getPercentage(String[][] cinema) {
        return (float) (Cinema.n_tickets * 100) / (cinema.length * cinema[0].length);
    }

    public static void getTotalIncome(String[][] cinema) {
        int row = cinema.length;
        int col = cinema[0].length;
        int first_row;
        int second_row;
        int cost1 = 10;
        int cost2;
        if (row * col > 60) {
            first_row = row / 2;
            cost2 = 8;
            second_row = row - first_row;
            Cinema.totalIncome = (first_row * col * cost1) + (second_row * col * cost2);
        } else {
            Cinema.totalIncome = (row * col * cost1);
        }
    }

    public static void buy_ticket(String[][] cinema, int row, int col) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter a row number:");
            int row_number = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            int seat_number = scanner.nextInt();
            if (row_number - 1 < 0 || row_number - 1 > cinema.length - 1 || seat_number - 1 < 0 || seat_number - 1 > cinema.length - 1) {
                System.out.println("Wrong input!\n");
                continue;
            }
            else if (!cinema[row_number - 1][seat_number - 1].equals("S")) {
                System.out.println("That ticket has already been purchased!\n");
                continue;
            }
            cinema[row_number - 1][seat_number - 1] = "B";
            int first_row;
            int second_row;
            int cost1 = 10;
            int cost2 = 10;
            if (row * col > 60) {
                first_row = row / 2;
                cost2 = 8;
                second_row = row - first_row;

            } else {
                first_row = row / 2;

            }
            if (row * col > 60 && row_number > first_row) {
                System.out.println("Ticket price: $" + cost2 + "\n");
                Cinema.currentIncome += cost2;
            } else {
                System.out.println("Ticket price: $" + cost1 + "\n");
                Cinema.currentIncome += cost1;
            }
            Cinema.n_tickets += 1;
            break;
        }


    }

    public static void menu_2(String[][] cinema, int row, int col) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("3. Statistics");
            System.out.println("0. Exit");
            int choice = scan.nextInt();
            if (choice == 1) {
                print_cinema(cinema);
            }
            else if (choice == 2) {
                buy_ticket(cinema, row, col);
            }
            else if (choice == 3) {
                statistics(cinema);
            }
            else if (choice == 0) {
                break;
            }
        }

    }

    public static void statistics(String[][] cinema) {
        System.out.println("\nNumber of purchased tickets: " + Cinema.n_tickets);
        System.out.printf("Percentage: %.2f%s\n", getPercentage(cinema), "%");
        System.out.println("Current income: $" + Cinema.currentIncome);
        System.out.println("Total income: $" + Cinema.totalIncome + "\n");
    }

    public static String[][] create_cinema(int row, int col) {
        String[][] cinema = new String[row][col];
        for (String[] strings : cinema) {
            Arrays.fill(strings, "S");
        }
        return cinema;
    }

    public static void print_cinema(String[][] arr){
        System.out.println("\nCinema:");
        System.out.print("  ");
        for (int i = 0; i < arr[0].length; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        for (int i = 0; i < arr.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < arr[i].length; j++) {

                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[] menu() {
        int[] row_col = new int[2];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int row = scanner.nextInt();
        row_col[0] = row;
        System.out.println("Enter the number of seats in each row:");
        int col = scanner.nextInt();
        row_col[1] = col;
        return row_col;
    }
}
