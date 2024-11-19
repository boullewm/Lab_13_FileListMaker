import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;
public class FileListMaker {
    private static final ArrayList<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = null;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String menuChoice;
        do {
            displayMenu();
            menuChoice = SafeInput.getRegExString(in, "Enter your choice (A/D/I/M/V/O/S/C/Q)", "[AaDdIiMmVvOoSsCcQq]").toUpperCase();
            try {
                switch (menuChoice) {
                    case "A":
                        addItem(in);
                        break;
                    case "D":
                        deleteItem(in);
                        break;
                    case "I":
                        insertItem(in);
                        break;
                    case "M":
                        moveItem(in);
                        break;
                    case "V":
                        viewList();
                        break;
                    case "O":
                        openList(in);
                        break;
                    case "S":
                        saveList(in);
                        break;
                    case "C":
                        clearList(in);
                        break;
                    case "Q":
                        if (handleUnsavedChanges(in)) {
                            System.out.println("Exiting program...");
                            return;
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("File operation error: " + e.getMessage());
            }
        } while (true);
    }
    private static void displayMenu() {
        System.out.println("\nMenu Options:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("M - Move an item");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
    }
    private static void addItem(Scanner in) {
        String newItem = SafeInput.getNonZeroLenString(in, "Enter the item to add");
        list.add(newItem);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }
    private static void deleteItem(Scanner in) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to delete.");
            return;
        }
        int index = SafeInput.getRangedInt(in, "Enter the item number to delete", 1, list.size()) - 1;
        System.out.printf("Deleted: %s%n", list.remove(index));
        needsToBeSaved = true;
    }
    private static void insertItem(Scanner in) {
        int index = SafeInput.getRangedInt(in, "Enter the position to insert the item", 1, list.size() + 1) - 1;
        String newItem = SafeInput.getNonZeroLenString(in, "Enter the item to insert");
        list.add(index, newItem);
        needsToBeSaved = true;
        System.out.println("Item inserted.");
    }
    private static void moveItem(Scanner in) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to move.");
            return;
        }
        int sourceIndex = SafeInput.getRangedInt(in, "Enter the item number to move", 1, list.size()) - 1;
        int destIndex = SafeInput.getRangedInt(in, "Enter the new position", 1, list.size()) - 1;
        String item = list.remove(sourceIndex);
        list.add(destIndex, item);
        needsToBeSaved = true;
        System.out.println("Item moved.");
    }
    private static void viewList() {
        System.out.println("\nCurrent List:");
        if (list.isEmpty()) {
            System.out.println("[No items in the list]");
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d: %s%n", i + 1, list.get(i));
            }
        }
    }
    private static void openList(Scanner in) throws IOException {
        if (handleUnsavedChanges(in)) {
            String fileName = SafeInput.getNonZeroLenString(in, "Enter the file name to open (without extension)") + ".txt";
            Path filePath = Paths.get(fileName);
            if (!Files.exists(filePath)) {
                System.out.println("File not found.");
                return;
            }
            list.clear();
            list.addAll(Files.readAllLines(filePath));
            currentFileName = fileName;
            needsToBeSaved = false;
            System.out.println("List loaded from " + fileName);
        }
    }
    private static void saveList(Scanner in) throws IOException {
        if (currentFileName == null) {
            currentFileName = SafeInput.getNonZeroLenString(in, "Enter a file name to save as (without extension)") + ".txt";
        }
        Files.write(Paths.get(currentFileName), list);
        needsToBeSaved = false;
        System.out.println("List saved to " + currentFileName);
    }
    private static void clearList(Scanner in) {
        if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list? (Y/N)")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }
    private static boolean handleUnsavedChanges(Scanner in) throws IOException {
        if (needsToBeSaved) {
            boolean save = SafeInput.getYNConfirm(in, "You have unsaved changes. Save now? (Y/N)");
            if (save) {
                saveList(in);
            }
            return save;
        }
        return true;
    }
}
