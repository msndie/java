package battleship;

import java.util.Scanner;

public class Player {

    static Scanner sc = new Scanner(System.in);
    private Grid grid;
    private Grid foggedGrid;
    private String name;
    private SHIPS stage;

    private enum SHIPS {

        AIRCRAFT(5, "Aircraft Carrier"),
        BATTLESHIP(4, "Battleship"),
        SUBMARINE(3, "Submarine"),
        CRUISER(3, "Cruiser"),
        DESTROYER(2, "Destroyer") {
            @Override
            SHIPS next() {
                return null;
            }
        };

        private int cells;
        private String name;

        SHIPS(int cells, String name) {
            this.cells = cells;
            this.name = name;
        }

        int getCells() {
            return cells;
        }

        String getName() {
            return name;
        }

        SHIPS next() {
            return values()[ordinal() + 1];
        }
    }

    public Player(String name) {
        stage = SHIPS.AIRCRAFT;
        this.name = name;
        grid = new Grid();
        foggedGrid = new Grid();
    }

    public String getName() {
        return name;
    }

    public void placeShips() {
        String c1;
        String c2;
        int status;

        System.out.println(name + ", place your ships on the game field");
        grid.printGrid();
        while (stage != null) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n", stage.getName(), stage.getCells());
            while (true) {
                c1 = sc.next();
                c2 = sc.next();
                try {
                    status = grid.insertShip(c1, c2, stage.getCells());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error!");
                    continue;
                }
                if (status == 0) {
                    break;
                } else {
                    System.out.println("Error!");
                }
            }
            stage = stage.next();
            grid.printGrid();
        }
    }

    public void printGrids() {
        foggedGrid.printGrid();
        System.out.println("---------------------");
        grid.printGrid();
    }

    public int shoot(Player player2) {
        int status;
        String str;

        while (true) {
            str = sc.next();
            try {
                status = player2.grid.registerHit(str, foggedGrid);
            } catch (IllegalArgumentException e) {
                System.out.println("Error!");
                continue;
            }
            if (status == 0) {
                System.out.println("You sank the last ship. You won. Congratulations!");
                return 0;
            } else if (status == 1) {
                System.out.println("You missed.");
                return 1;
            } else if (status == 2) {
                System.out.println("You hit a ship!");
                return 1;
            } else if (status == 3) {
                System.out.println("You sank a ship!");
                return 1;
            } else {
                System.out.println("Error!");
            }
//            switch (status) {
//                case -1:
//                    System.out.println("Error!");
//                    break;
//                case 1:
//                    System.out.println("You missed. Try again:");
//                    break;
//                case 2:
//                    System.out.println("You hit a ship! Try again:");
//                    break;
//                case 3:
//                    System.out.println("You sank a ship! Specify a new target:");
//                    break;
//                default:
//                    System.out.println("You sank the last ship. You won. Congratulations!");
//            }
        }
    }
}
