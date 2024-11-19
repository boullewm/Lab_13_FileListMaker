import java.util.Scanner;
    public class SafeInput {
        public static String getNonZeroLenString(Scanner pipe, String prompt) {
            String retString = ""; // Set this to zero length. Loop runs until it isn't
            do {
                System.out.print("\n" + prompt + ": "); // show prompt add space
                retString = pipe.nextLine();
            } while (retString.length() == 0);

            return retString;

        }

        public static int getInt(Scanner pipe, String prompt) {
            int num = 0;
            boolean done = false;
            do {
                System.out.print("\n" + prompt + ": ");
                if (pipe.hasNextInt()) {
                    num = pipe.nextInt();
                    pipe.nextLine();
                    done = true;
                } else {
                    System.out.println("You must enter an integer");
                    pipe.nextLine();
                }
            } while (!done);
            return num;
        }

        public static double getDouble(Scanner pipe, String prompt) {
            double dou = 0.0;
            boolean done = false;
            do {
                System.out.print("\n" + prompt + ": ");
                if (pipe.hasNextDouble()) {
                    dou = pipe.nextDouble();
                    pipe.nextLine();
                    done = true;
                } else {
                    System.out.println("You must enter a number");
                    pipe.nextLine();
                }
            } while (!done);
            return dou;
        }

        public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
            int rangedInt = 0;
            boolean done = false;
            do {
                System.out.print("\n" + prompt + ": ");
                if (pipe.hasNextInt()) {
                    rangedInt = pipe.nextInt();
                    if (rangedInt <= high && rangedInt >= low) {
                        pipe.nextLine();
                        done = true;
                    } else {
                        System.out.println("You must enter a number within the range");
                        pipe.nextLine();
                    }
                }
            } while (!done);
            return rangedInt;
        }

        public static double getRangedDouble(Scanner pipe, String prompt, double low, double high) {
            double rangedDouble = 0.0;
            boolean done = false;
            do {
                System.out.print("\n" + prompt + ": ");
                if (pipe.hasNextDouble()) {
                    rangedDouble = pipe.nextDouble();
                    if (rangedDouble <= high && rangedDouble >= low) {
                        pipe.nextLine();
                        done = true;
                    } else {
                        System.out.println("You must enter a number within the range");
                        pipe.nextLine();
                    }
                }
            } while (!done);
            return rangedDouble;
        }

        public static boolean getYNConfirm(Scanner pipe, String prompt) {
            String input;
            boolean done;
            while (true) {
                System.out.print("\n" + prompt + ": ");
                input = pipe.nextLine();
                if (input.equalsIgnoreCase("Y")) {
                    done = true;
                    return true;
                } else if (input.equalsIgnoreCase("N")) {
                    done = false;
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }
        }

        public static String getRegExString(Scanner pipe, String prompt, String regEx) {
            String input;
            while (true) {
                System.out.print("\n" + prompt + ": ");
                input = pipe.nextLine();
                if (input.matches(regEx)) {
                    return input;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }
