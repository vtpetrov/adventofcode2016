package day1;

import core.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Day 1
 * http://adventofcode.com/2016/day/1
 */
public class FindBunnyHeadquarter {

    private static final String INPUT_FILE_NAME = "day1_input.txt";
    static Path inputFile = FileSystems.getDefault().getPath("src", "main", "resources", INPUT_FILE_NAME);
    static Scanner in = new Scanner("null");
    private static final int ARRAY_SIZE = 436;
    private static final int ARRAY_CENTER = ARRAY_SIZE / 2;
    static int map[][] = new int[ARRAY_SIZE][ARRAY_SIZE];


    public static void main(String[] args) throws Throwable {

        System.out.println("inputFile.toString() = " + inputFile.toString());
        Scanner in = new Scanner("null");

        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);


        Scanner mainIn = new Scanner("prazno");

        try {
            mainIn = new Scanner(inputFile).useDelimiter(", ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Current input: " + mainIn.nextLine() + '\n');

        System.out.println("P1 Easter Bunny HQ is [ " + partOne() + " ] blocks away");
        System.out.println("\n\nP2 Easter Bunny HQ is [ " + partTwo() + " ] blocks away");


    }

    /**
     * Calculates the distance to the Bunny Headquarter and returns it.
     *
     * @return distance to bunny headquarter
     * @throws Throwable
     */
    private static Long partOne() throws Throwable {
        long distance = 0;
        Direction currentDirection = Direction.NORTH;
        String currentTurn = "N";
        int currentMoveLength = 0;

        long east = 0;
        long west = 0;
        long north = 0;
        long south = 0;

        long elem = 0;

        in = new Scanner(inputFile).useDelimiter(", ");

        while (in.hasNext()) {
            String currentElem = in.next();
            elem++;
//            System.out.println(" --------===  " + elem + "  ====--------");
            currentTurn = String.valueOf(currentElem.charAt(0));
            currentDirection = calculateNewDirection(currentDirection, currentTurn);
            currentMoveLength = Integer.valueOf(currentElem.substring(1, currentElem.length()));

            switch (currentDirection) {
                case EAST:
                    east += currentMoveLength;
                    break;
                case WEST:
                    west += currentMoveLength;
                    break;
                case NORTH:
                    north += currentMoveLength;
                    break;
                case SOUTH:
                    south += currentMoveLength;
                    break;
            }

        }

        distance = Math.abs(east - west) + Math.abs(south - north);

        in.close();

        return distance;
    }


    /**
     * --- Part Two ---
     * <p>
     * Then, you notice the instructions continue on the back of the Recruiting Document. Easter Bunny HQ is actually at the first location you visit twice.
     * <p>
     * For example, if your instructions are R8, R4, R4, R8, the first location you visit twice is 4 blocks away, due East.
     * <p>
     * How many blocks away is the first location you visit twice?
     *
     * @throws Throwable
     */
    private static long partTwo() throws Throwable {

        long distance = 0;

        //land in the middle of the map:
        map[ARRAY_CENTER][ARRAY_CENTER] = -1;

//        System.out.println("Initial map:");
//        printBunnyMap();

        in = new Scanner(inputFile).useDelimiter(", ");

        Direction currentDirection = Direction.NORTH;
        String currentTurn = "N";
        int currentMoveLength = 0;

        int currPosition[] = new int[2];
        currPosition[0] = ARRAY_CENTER;
        currPosition[1] = ARRAY_CENTER;

        int crossing_x = 0;
        int crossing_y = 0;

        int move = 0;

        while (in.hasNext()) {
            move++;
            String currentElem = in.next();
            System.out.println(move + " , currentElem = " + currentElem);

            currentTurn = String.valueOf(currentElem.charAt(0));
            currentDirection = calculateNewDirection(currentDirection, currentTurn);
            currentMoveLength = Integer.valueOf(currentElem.substring(1, currentElem.length()));

            boolean crossed = false;

            switch (currentDirection) {
                case EAST: // move horizontally +
                    for (int d = 1; d <= currentMoveLength; d++) {
                        int curr_x = currPosition[0];
                        int curr_y = currPosition[1] + d;

                        if (map[curr_x][curr_y] != 0) { //reached crossing point.
                            crossing_x = curr_x;
                            crossing_y = curr_y;
                            System.out.println("curr_x = " + curr_x);
                            System.out.println("curr_y = " + curr_y);
                            map[curr_x][curr_y] = -8;
                            crossed = true;
                            break;
                        }

                        map[curr_x][curr_y] = move;

                    }
                    currPosition[1] += currentMoveLength;
                    break;

                case WEST: // move horizontally -
                    for (int d = 1; d <= currentMoveLength; d++) {
                        int curr_x = currPosition[0];
                        int curr_y = currPosition[1] - d;

                        if (map[curr_x][curr_y] != 0) { //reached crossing point.
                            crossing_x = curr_x;
                            crossing_y = curr_y;
                            System.out.println("curr_x = " + curr_x);
                            System.out.println("curr_y = " + curr_y);
                            map[curr_x][curr_y] = -8;
                            crossed = true;
                            break;
                        }

                        map[curr_x][curr_y] = move;
                    }
                    currPosition[1] -= currentMoveLength;
                    break;

                case NORTH: // move vertically -
                    for (int d = 1; d <= currentMoveLength; d++) {
                        int curr_x = currPosition[0] - d;
                        int curr_y = currPosition[1];

                        if (map[curr_x][curr_y] != 0) { //reached crossing point.
                            crossing_x = curr_x;
                            crossing_y = curr_y;
                            System.out.println("curr_x = " + curr_x);
                            System.out.println("curr_y = " + curr_y);
                            map[curr_x][curr_y] = -8;
                            crossed = true;
                            break;
                        }

                        map[curr_x][curr_y] = move;
                    }
                    currPosition[0] -= currentMoveLength;
                    break;

                case SOUTH: // move vertically +
                    for (int d = 1; d <= currentMoveLength; d++) {
                        int curr_x = currPosition[0] + d;
                        int curr_y = currPosition[1];

                        if (map[curr_x][curr_y] != 0) { //reached crossing point.
                            crossing_x = curr_x;
                            crossing_y = curr_y;
                            System.out.println("curr_x = " + curr_x);
                            System.out.println("curr_y = " + curr_y);
                            map[curr_x][curr_y] = -8;
                            crossed = true;
                            break;
                        }

                        map[curr_x][curr_y] = move;
                    }
                    currPosition[0] += currentMoveLength;
                    break;
            }


            if (crossed) {

                System.out.println();
                System.out.println("FINAL map:");
                printBunnyMap();

                in.close();
                return calculateDistancePartTwo(crossing_x, crossing_y);
            }

        }

        System.out.println();
        System.out.println("FINAL map:");
        printBunnyMap();

        in.close();

        return calculateDistancePartTwo(crossing_x, crossing_y);
    }

    private static int calculateDistancePartTwo(int crossing_x, int crossing_y) {
        return Math.abs(crossing_x - ARRAY_CENTER) + Math.abs(crossing_y - ARRAY_CENTER);
    }

    private static void printBunnyMap() {

        System.out.print("   ");
        for (int a = 0; a < ARRAY_SIZE; a++) {// horizontal row numbers:
            System.out.print("|" + a + returnSpaces(3 - String.valueOf(a).length()));
        }
        System.out.println();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            System.out.print(+i + returnSpaces(3 - String.valueOf(i).length()) + "|"); // vertical row number
            for (int j = 0; j < ARRAY_SIZE; j++) {
                System.out.print(map[i][j] + returnSpaces(4 - String.valueOf(map[i][j]).length()));
            }
            System.out.print(" | [" + i + "]");
            System.out.println();
        }
        System.out.println();
    }

    private static String returnSpaces(int i) {
        StringBuilder sb = new StringBuilder();
        for (int b = 0; b < i; b++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    private static Direction calculateNewDirection(Direction currentDirection, String currentTurn) {

        switch (currentDirection) {
            case EAST:
                if ("R".equals(currentTurn)) {
                    return Direction.SOUTH;
                } else if ("L".equals(currentTurn)) {
                    return Direction.NORTH;
                } else throw new Error(String.format("invalid turn: [%s]", currentTurn));
            case WEST:
                if ("R".equals(currentTurn)) {
                    return Direction.NORTH;
                } else if ("L".equals(currentTurn)) {
                    return Direction.SOUTH;
                } else throw new Error(String.format("invalid turn: [%s]", currentTurn));
            case NORTH:
                if ("R".equals(currentTurn)) {
                    return Direction.EAST;
                } else if ("L".equals(currentTurn)) {
                    return Direction.WEST;
                } else throw new Error(String.format("invalid turn: [%s]", currentTurn));
            case SOUTH:
                if ("R".equals(currentTurn)) {
                    return Direction.WEST;
                } else if ("L".equals(currentTurn)) {
                    return Direction.EAST;
                } else throw new Error(String.format("invalid turn: [%s]", currentTurn));
            default:
                return null;
        }
    }

}
