package numbers;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

enum Properties {
    ODD, EVEN, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD;

    static final String[] listOfProperties = Arrays
            .stream(values())
            .map(Objects::toString)
            .toArray(String[]::new);

    public static boolean noSuchProperty (String keyword) {
        //comparing keyword to each of properties
        for (String prop : listOfProperties) {
            if (keyword.equals(prop)) return false;
        }
        return true;
    }

    public static String[] getListOfProperties () {
        return listOfProperties;
    }
}

public class Main {
    final static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        System.out.println("Welcome to Amazing Numbers!\n");
        requests();
        while (true) {
            System.out.print("\nEnter a request: ");
            String line = scan.nextLine().toLowerCase();
            if (line.equals("")) {
                requests();
                continue;
            }
            long number = 0;
            long count = 0;
            String[] arguments = line.split(" ");
            if (arguments.length >= 1) {
                try {
                    number = Long.parseLong(arguments[0]);
                } catch (Exception ignored) {
                    print_errors("notNatural");
                    continue;
                }

                if (number == 0) {
                    System.out.println("Goodbye!");
                    break;
                }
                if (number < 0) {
                    print_errors("smallerThanZero");
                    continue;
                }
            }
            if (arguments.length >= 2) {
                try {
                    count = Long.parseLong(arguments[1]);
                } catch (Exception ignored) {
                    print_errors("notNatural");
                    continue;
                }
                if (count < 0) {
                    print_errors("secondSmallerThanZero");
                    continue;
                }
            }
            if (arguments.length == 1) {
                print_properties(number);
                continue;
            }
            if (arguments.length == 2) {
                System.out.println();
                for (long i = number; i < number + count; i++) {
                    StringBuilder prop = new StringBuilder(append_spec(i));
                    print(i, prop);
                }
                continue;
            }
            if (arguments.length > 2) {
                int flag = 0;
                String[] prop = new String[arguments.length - 2];
                System.arraycopy(arguments, 2, prop, 0, prop.length);
                String[] minusProp = {};
                for (String item : prop) {
                    if (item.startsWith("-")) {
                        minusProp = Arrays.copyOf(minusProp, minusProp.length + 1);
                        minusProp[minusProp.length - 1] = item.toLowerCase();
                    }
                }
                for (int i = 0; i < prop.length; i++) {
                    prop[i] = prop[i].toLowerCase();
                }
                if (arguments.length == 4) {
                    // Special cases for length 4
                    if (!check_in_array(prop[0]) && check_in_array(prop[1])) {
                        System.out.printf("\nThe property [%s] is wrong.\n", prop[0].toUpperCase());
                        property_print();
                        continue;
                    }
                    if (check_in_array(prop[0]) && !check_in_array(prop[1])) {
                        System.out.printf("\nThe property [%s] is wrong.\n", prop[1].toUpperCase());
                        property_print();
                        continue;
                    }
                    if (!check_in_array(prop[0]) && !check_in_array(prop[1])) {
                        System.out.printf("\nThe properties [%s, %s] are wrong.\n", prop[0].toUpperCase(), prop[1].toUpperCase());
                        property_print();
                        continue;
                    }
                    if (check_mutually(prop[0], prop[1])) {
                        System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n", prop[0].toUpperCase(), prop[1].toUpperCase());
                        print_errors("mutuallyExclusive");
                        continue;
                    }
                    if (prop[0].equals(prop[1].substring(1))) {
                        System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n", prop[0].toUpperCase(), prop[1].toUpperCase());
                        print_errors("mutuallyExclusive");
                        continue;
                    }
                    if (prop[1].equals(prop[0].substring(1))) {
                        System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n", prop[0].toUpperCase(), prop[1].toUpperCase());
                        print_errors("mutuallyExclusive");
                        continue;
                    }
                }
                for (String item : prop) {
                    if (!check_in_array(item)) {
                        flag = 1;
                        System.out.printf("\nThe property [%s] is wrong.\n", item.toUpperCase());
                        property_print();
                        break;
                    }
                }
                if (flag == 1) {
                    continue;
                }

                for (int i = 0; i < prop.length; i++) {
                    for (int j = 0; j < prop.length; j++) {
                        if (i != j && check_mutually(prop[i], prop[j])) {
                            System.out.printf("\nThe request contains mutually exclusive properties: [%s, %s]\n", prop[i].toUpperCase(), prop[j].toUpperCase());
                            print_errors("mutuallyExclusive");
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 1) {
                        break;
                    }
                }
                if (flag == 1) {
                    continue;
                }
                // All test cases passed.
                multipleInput(number, (int) count, prop);

            }
        }
    }

    static void multipleInput(long number, int count, String[] args) {
        if (checkIsWrong(args)) {
            return;
        }
        StringBuilder isExists = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].toUpperCase();
            if (!Properties.noSuchProperty(args[i].replace("-", ""))) {
                continue;
            }
            isExists.append(args[i]).append(" ");
        }
        if (0 != isExists.length()) {
            ifPropertyWrong(isExists.toString().split(" "));
            return;
        }
        int counter = 0;
        do {
            boolean checkNumber = true;
            for (String key : args) {
                checkNumber &= hasProperty(number, key.replace("-", "")) != (key.charAt(0) == '-');
            }
            if (checkNumber) {
                printOneNumberInfo(number);
                counter++;
            }
            number++;
        } while (counter != count);
    }

    static boolean hasProperty (long number, String keyword) {
        switch (keyword) {
            case "ODD": return !odd_even(number);
            case "EVEN": return odd_even(number);
            case "BUZZ": return buzz(number);
            case "DUCK": return duck(String.valueOf(number));
            case "PALINDROMIC": return palindrome(String.valueOf(number));
            case "GAPFUL": return gapful(String.valueOf(number));
            case "SPY": return spy(String.valueOf(number));
            case "SQUARE": return square_number(number);
            case "SUNNY": return sunny(number);
            case "JUMPING": return jumping(String.valueOf(number));
            case "HAPPY": return happy(String.valueOf(number));
            case "SAD": return !happy(String.valueOf(number));
        }
        return false;
    }

    static void ifPropertyWrong(String[] propertyList) {
        if (propertyList.length != 1) {
            StringBuilder info = new StringBuilder();
            for (String prop : propertyList) {
                info.append(prop).append(", ");
            }
            info.delete(info.lastIndexOf(", "), info.length());
            System.out.println("The properties [" + info + "] are wrong.");
        } else System.out.println("The property [" + propertyList[0] + "] is wrong.");
        System.out.println("Available properties "+ Arrays.toString(Properties.getListOfProperties()));
    }

    static void printOneNumberInfo(long number) {
        if (number >= 0) {
            StringBuilder info;
            info = new StringBuilder(number + " is ");
            info.append(buzz(number) ? "buzz, " : "");
            info.append(duck(String.valueOf(number)) ? "duck, " : "");
            info.append(palindrome(String.valueOf(number)) ? "palindromic, " : "");
            info.append(gapful(String.valueOf(number)) ? "gapful, " : "");
            info.append(spy(String.valueOf(number)) ? "spy, " : "");
            info.append(square_number(number) ? "square, " : "");
            info.append(sunny(number) ? "sunny, " : "");
            info.append(jumping(String.valueOf(number)) ? "jumping, " : "");
            info.append(happy(String.valueOf(number)) ? "happy, " : "");
            info.append(!happy(String.valueOf(number)) ? "sad, " : "");
            info.append(!odd_even(number) ? "odd" : "even");
            System.out.println(info);
        } else {
            System.out.println("\nThe first parameter should be a natural number or zero.\n");
        }
    }


    static boolean checkIsWrong(String[] keywords) {
        String[] keywordArr = Arrays
                .stream(keywords)
                .map(String::new)
                .toArray(String[]::new);
        for (int i = 0; i < keywords.length; i++) {
            keywordArr[i] = keywordArr[i].toUpperCase();
            if (keywordArr[i].charAt(0) != '-') {
                keywordArr[i] = "+" + keywordArr[i];
            }
        }
        String keys = Arrays.toString(keywordArr);
        String wrongCombination = null;
        if (keys.contains("+EVEN") && keys.contains("+ODD")) {
            wrongCombination = "EVEN, ODD";
        }
        if (keys.contains("+SPY") && keys.contains("+DUCK")) {
            wrongCombination = "SPY, DUCK";
        }
        if (keys.contains("+SQUARE") && keys.contains("+SUNNY")) {
            wrongCombination = "SQUARE, SUNNY";
        }
        if (keys.contains("+HAPPY") && keys.contains("+SAD")) {
            wrongCombination = "HAPPY, SAD";
        }
        if (keys.contains("-EVEN") && keys.contains("-ODD")) {
            wrongCombination = "-EVEN, -ODD";
        }
        if (keys.contains("-HAPPY") && keys.contains("-SAD")) {
            wrongCombination = "-HAPPY, -SAD";
        }
        for (String prop : Properties.getListOfProperties()) {
            if (keys.contains("-" + prop) && keys.contains("+" + prop)) {
                wrongCombination = "-" + prop + ", +" + prop;
            }
        }
        if (wrongCombination == null) {
            return false;
        } else {
            System.out.println("The request contains mutually exclusive properties: [" + wrongCombination + "]\n" + "There are no numbers with these properties.");
            return true;
        }
    }



    public static void print_errors(String operation) {
        switch (operation) {
            case "notNatural":
                System.out.println("\nThe first parameter should be a natural number or zero.");
                break;
            case "smallerThanZero":
                System.out.println("The first parameter should be a natural number or zero.");
                break;
            case "secondSmallerThanZero":
                System.out.println("The second parameter should be a natural number");
                break;
            case "mutuallyExclusive":
                System.out.println("There are no numbers with these properties.");
                break;
        }

    }

    public static void property_print() {
        System.out.println("Available properties:");
        System.out.println("[EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
    }

    public static boolean check_mutually(String phrase1, String phrase2) {
        if(phrase1.substring(1).equals(phrase2)) {
            return true;
        }
        if (phrase2.substring(1).equals(phrase1)) {
            return true;
        }

        String[][] mutual = {{"EVEN", "ODD"}, {"ODD", "EVEN"}, {"DUCK", "SPY"},
                {"SPY", "DUCK"}, {"SUNNY", "SQUARE"}, {"SQUARE", "SUNNY"}};

        for (String[] strings : mutual) {
            for (int j = 0; j < strings.length - 1; j++) {
                if ((strings[j] + strings[j + 1]).equals((phrase1 + phrase2).toUpperCase())) {
                    return true;
                }
                if ((strings[j] + strings[j + 1]).equals((phrase1.substring(1) + phrase2.substring(1)).toUpperCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean jumping(String number) {
        char[] chars = number.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (Math.abs(Integer.parseInt(String.valueOf(chars[i])) -  Integer.parseInt(String.valueOf(chars[i + 1]))) != 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean square_number(long number) {
        return (Math.sqrt(number) * 10) % 10 == 0;
    }

    public static boolean sunny(long number) {
        return square_number(number + 1);
    }

    public static StringBuilder append_spec(long start_number) {
        StringBuilder prop = new StringBuilder();
        prop.append(odd_even(start_number) ? "even, " : "").append(!odd_even(start_number) ? "odd, " : "").
                append(buzz(start_number) ? "buzz, " : "").append(duck(String.valueOf(start_number)) ? "duck, " : "").
                append(palindrome(String.valueOf(start_number)) ? "palindrome, " : "").append(gapful(String.valueOf(start_number)) ? "gapful, " : "").
                append(spy(String.valueOf(start_number)) ? "spy, " : "").append(square_number(start_number) ? "square, " : "").
                append(sunny(start_number) ? "sunny, " : "").append(jumping(String.valueOf(start_number)) ? "jumping, " : "").
                append(happy(String.valueOf(start_number)) ? "happy, " : "").append(!happy(String.valueOf(start_number)) ? "sad, " : "");
        return prop;
    }

    public static void print (long number, StringBuilder str) {
        String st = str.toString();
        String print_string = st.replaceAll("", "");
        StringBuilder stringBuilder = new StringBuilder(print_string);
        stringBuilder.deleteCharAt(stringBuilder.length() - 1).deleteCharAt(stringBuilder.length() - 1);
        System.out.println("             " + number + " is " + stringBuilder);
    }

    public static boolean check_in_array(String value) {
        if (value.startsWith("-")) {
            value = value.substring(1);
        }
        String[] prop = {"buzz", "duck", "palindromic", "gapful", "spy", "square", "sunny", "jumping", "even", "odd", "happy", "sad"};
        for (String str : prop) {
            if (value.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static void requests() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties");
        System.out.println("- enter two natural numbers to obtain the properties of the list:");
        System.out.println(" * the first parameter represents a starting number;");
        System.out.println(" * the second parameter shows how many consecutive numbers are to be printed;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit");
    }

    public static void print_properties(long number) {
        System.out.println("\nProperties of " +number);
        System.out.println("        even: " +odd_even(number));
        System.out.println("         odd: " +!odd_even(number));
        System.out.println("        buzz: " +buzz(number));
        System.out.println("        duck: " +duck(String.valueOf(number)));
        System.out.println(" palindromic: " +palindrome(String.valueOf(number)));
        System.out.println("      gapful: " +gapful(String.valueOf(number)));
        System.out.println("         spy: " +spy(String.valueOf(number)));
        System.out.println("      square: " +square_number(number));
        System.out.println("       sunny: " +sunny(number));
        System.out.println("     jumping: " +jumping(String.valueOf(number)));
        System.out.println("       happy: " +happy(String.valueOf(number)));
        System.out.println("         sad: " +!happy(String.valueOf(number)));
    }

    public static boolean happy(String number) {
        String originalNumber = number;
        int c = 0;
        while (c != 50) {
            long total = 0;
            for (int i = 0; i < number.length(); i++) {
                long num = Long.parseLong(String.valueOf(number.charAt(i)));
                total += num * num;
            }
            if (total == 1) {
                return true;
            }
            if (total == Long.parseLong(originalNumber)) {
                return false;
            }
            c++;
            number = String.valueOf(total);
        }
        return false;
    }

    public static boolean spy(String number) {
        long total = 0;
        long product = 1;
        char[] numbers = number.toCharArray();
        for (char ch : numbers) {
            total += Long.parseLong(String.valueOf(ch));
            product *= Long.parseLong(String.valueOf(ch));
        }
        return total == product;
    }

    public static boolean gapful(String number) {
        String first_digit = String.valueOf(number.charAt(0));
        String last_digit = String.valueOf(number.charAt(number.length() - 1));
        String num = first_digit + last_digit;
        long return_number = Long.parseLong(num);
        long real_number = Long.parseLong(number);
        return real_number % return_number == 0 && number.length() >= 3;
    }

    public static boolean palindrome(String number) {
        char[] ch = number.toCharArray();
        char[] reverse = new char[number.length()];
        int k = 0;
        for (int i = number.length() - 1; i >= 0; i--) {
            reverse[k] = ch[i];
            k++;
        }
        return String.valueOf(ch).equals(String.valueOf(reverse));
    }

    public static boolean duck(String number) {

        char[] ch = new char[number.length()];

        for (int i = 0; i < number.length(); i++) {
            ch[i] = number.charAt(i);
        }

        boolean digit_0_check = ch[0] == '0';
        if (digit_0_check) {
            for (int i = 1; i < number.length(); i++) {
                if (ch[i] == '0') {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = 1; i < number.length(); i++) {
                if (ch[i] == '0') {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean buzz(long number) {
        boolean is_divisible = divisible_7(number);
        boolean last_digits = last_digit_7(number);
        return is_divisible || last_digits;
    }

    public static boolean odd_even(long number) {
        return number % 2 == 0;
    }

    public static boolean divisible_7 (long number) {
        long divide_10 = number / 10;
        long remainder = number % 10;
        long total = divide_10 - (remainder * 2);
        return total % 7 == 0;
    }

    public static boolean last_digit_7 (long number) {
        return number % 10 == 7;
    }
}
