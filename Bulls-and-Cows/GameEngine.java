package bullscows;

import java.util.*;

public class GameEngine {
    int bulls;
    int cows;
    int range;
    String genNumber;
    Scanner scanner = new Scanner(System.in);
    String word = "";

    private void startGame() {
        System.out.println("Please, enter the secret code's length:");
        String _range = scanner.nextLine();
        try {
            this.range = Integer.parseInt(_range);
        } catch (Exception e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", _range);
            System.exit(1);
        }

        if (this.range > 36 || this.range <= 0) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", this.range);
        } else {
            System.out.println("Input the number of possible symbols in the code:");
            int pos = scanner.nextInt();
            if (pos < this.range) {
                System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", this.range, pos);
                System.exit(1);
            } else if (pos > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                System.exit(1);
            }
            String message = this.position_calculator(pos);
            this.generateRandom();
            String star = "*".repeat(this.range);
            System.out.printf("The secret is prepared: %s %s\n", star, message);
            menu();
        }
    }

    private String position_calculator(int pos) {
        char[] possibilities = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
        , 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        StringBuilder word = new StringBuilder();
        char last_index = 'a';
        for (int i = 0; i < pos; i++) {
            if (possibilities[i] >= 'a') {
                word.append(possibilities[i]);
            }
            last_index = possibilities[i];
        }
        StringBuilder message = new StringBuilder();

        if (last_index > 'a') {
            message.append("(0-9, a-").append(last_index).append(").");
        } else {
            message.append("(0-").append(last_index).append(").");
        }

        this.word = String.valueOf(word);
        return String.valueOf(message);

    }

    private void menu() {
        System.out.println("Okay, let's start a game!");
        int c = 1;
        while (true) {
            System.out.printf("Turn %d:\n", c);
            String num = scanner.next();
            this.count_bulls_cows(num);
            this.print_results();
            if (num.equals(this.genNumber)) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            c++;
        }
    }

    private void generateRandom() {
        StringBuilder number = new StringBuilder();
        int c = 0;
        int i = 0;
        String psRndNumber = System.nanoTime() * 621 + this.word;
        List<String> characters = Arrays.asList(psRndNumber.split(""));
        Collections.shuffle(characters);
        StringBuilder afterShuffle = new StringBuilder();
        for (String chr : characters) {
            afterShuffle.append(chr);
        }
        String shuffle = String.valueOf(afterShuffle);
        do {
            char val = shuffle.charAt(c);
            if (!String.valueOf(number).contains(String.valueOf(val))) {
                number.append(val);
                i++;
            }

            c++;
        } while (this.range > i);
        this.genNumber = String.valueOf(number);
    }

    private void count_bulls_cows(String num) {
        this.bulls = 0;
        this.cows = 0;
        for (int i = 0; i < num.length(); i++) {

            char letter = num.charAt(i);
            char secret_letter = this.genNumber.charAt(i);

            if (letter == secret_letter) {
                this.bulls++;
            } else if (this.genNumber.contains(String.valueOf(letter))) {
                this.cows++;
            }
        }
    }

    private  void print_results() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.bulls == 0 && this.cows == 0) {
            stringBuilder = new StringBuilder("Grade: None");
        } else if (this.bulls > 0 && this.cows > 0) {
            stringBuilder.append(this.bulls).append(this.bulls == 1 ? " bull" : " bulls").append(" and ").
                    append(this.cows).append(this.cows == 1 ? " cow" : " cows");
        } else {
            stringBuilder.append(this.bulls > 0 ? this.bulls == 1 ? this.bulls + " bull" : this.bulls + " bulls" : "").
                    append(this.cows > 0 ? this.cows == 1 ? this.cows + " cow" : this.cows + " cows" : "");
        }
        System.out.println(stringBuilder);
    }

    public void game() {
        startGame();
    }
}
