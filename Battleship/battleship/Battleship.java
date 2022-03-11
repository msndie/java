package battleship;

import java.util.Scanner;

public class Battleship {

    static Scanner sc = new Scanner(System.in);
    private Player player1;
    private Player player2;
//    private SHIPS stage;
//
//    private enum SHIPS {
//
//        AIRCRAFT(5, "Aircraft Carrier"),
//        BATTLESHIP(4, "Battleship"),
//        SUBMARINE(3, "Submarine"),
//        CRUISER(3, "Cruiser"),
//        DESTROYER(2, "Destroyer") {
//            @Override
//            SHIPS next() {
//                return null;
//            }
//        };
//
//        private int cells;
//        private String name;
//
//        SHIPS(int cells, String name) {
//            this.cells = cells;
//            this.name = name;
//        }
//
//        int getCells() {
//            return cells;
//        }
//
//        String getName() {
//            return name;
//        }
//
//        SHIPS next() {
//            return values()[ordinal() + 1];
//        }
//    }

    public Battleship(String nameOne, String nameTwo) {
        player1 = new Player(nameOne);
        player2 = new Player(nameTwo);
    }

    public void gameStart() {
//        Grid grid = new Grid();
//        Grid foggedGrid = new Grid();
//        String c1;
//        String c2;
//        int status;

//        grid.printGrid();
//        while (stage != null) {
//            System.out.printf("Enter the coordinates of the %s (%d cells):\n", stage.getName(), stage.getCells());
//            while (true) {
//                c1 = sc.next();
//                c2 = sc.next();
//                try {
//                    status = grid.insertShip(c1, c2, stage.getCells());
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Error!");
//                    continue;
//                }
//                if (status == 0) {
//                    break;
//                } else {
//                    System.out.println("Error!");
//                    continue;
//                }
//            }
//            stage = stage.next();
//            grid.printGrid();
//        }
//        System.out.println("The game starts!");
//        foggedGrid.printGrid();
//        System.out.println("Take a shot!");
//        shootingPhase(grid, foggedGrid);
//        grid.printGrid();
        player1.placeShips();
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        player2.placeShips();
        System.out.println("Press Enter and pass the move to another player");
        sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        while (true) {
            player1.printGrids();
            System.out.println(player1.getName() + ", it's your turn:");
            if (player1.shoot(player2) == 0) {
                return;
            }
            System.out.println("Press Enter and pass the move to another player");
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            player2.printGrids();
            System.out.println(player2.getName() + ", it's your turn:");
            if (player2.shoot(player1) == 0) {
                return;
            }
            System.out.println("Press Enter and pass the move to another player");
            sc.nextLine();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

//    private void shootingPhase(Grid grid, Grid foggedGrid) {
//        int status = -1;
//        String str;
//
//        while (status != 0) {
//            str = sc.next();
//            try {
//                status = grid.registerHit(str, foggedGrid);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Error!");
//                continue;
//            }
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
//        }
//    }

//    private void shoot(char[][] grid, char[][]foggedGrid) {
//        StringBuilder target = new StringBuilder();
//        String str;
//        int targetY;
//        int targetX;
//        while (true) {
//            str = sc.next();
//            target.append(str);
//            targetY = target.charAt(0) - 'A';
//            target.deleteCharAt(0);
//            try {
//                targetX = Integer.parseInt(target.toString());
//            } catch (NumberFormatException e) {
//                System.out.println("Error");
//                return;
//            }
//            if ((targetX - 1 < 0 || targetX > LENGTH) || (targetY < 0 || targetY > HEIGHT)) {
//                System.out.println("Error!");
//                target.delete(0, target.length());
//            } else if (grid[targetY][targetX - 1] == 'O') {
//                grid[targetY][targetX - 1] = 'X';
//                foggedGrid[targetY][targetX - 1] = 'X';
//                printGrid(foggedGrid);
//                System.out.println("You hit a ship!");
//                return;
//            } else {
//                grid[targetY][targetX - 1] = 'M';
//                foggedGrid[targetY][targetX - 1] = 'X';
//                printGrid(foggedGrid);
//                System.out.println("You missed!");
//                return;
//            }
//        }
//    }

//    private int checkCoordinates(StringBuilder c1, StringBuilder c2) {
//        y1 = c1.charAt(0) - 'A';
//        if (y1 > 9 || y1 < 0) {
//            return -1;
//        }
//        c1.deleteCharAt(0);
//        try{
//            x1 = Integer.parseInt(c1.toString());
//        } catch(NumberFormatException e) {
//            System.out.println("Error");
//            return -1;
//        }
//        y2 = c2.charAt(0) - 'A';
//        if (y2 > 9 || y2 < 0) {
//            return -1;
//        }
//        c2.deleteCharAt(0);
//        try {
//            x2 = Integer.parseInt(c2.toString());
//        } catch (NumberFormatException e) {
//            System.out.println("Error");
//            return -1;
//        }
//        return 0;
//    }
//
//    private int checkShipLength() {
//        int cells;
//
//        if (y1 == y2) {
//            cells = Math.max(x1, x2) - Math.min(x1, x2) + 1;
//            if (cells > stage.getCells() || cells < stage.getCells()) {
//                System.out.println("Error");
//                return -1;
//            }
//        } else if (x1 == x2) {
//            cells = Math.max(y1, y2) - Math.min(y1, y2) + 1;
//            if (cells > stage.getCells() || cells < stage.getCells()) {
//                System.out.println("Error");
//                return -1;
//            }
//        } else {
//            System.out.println("Error");
//            return -1;
//        }
//        return 0;
//    }
//
//    private int checkCube(char[][] grid, int x, int y) {
//        int countO = 0;
//        int yMin = y - 1 < 0 ? y : y - 1;
//        int yPlus = y + 1 < grid.length ? y + 1 : y;
//        int xMin = x - 1 < 0 ? x : x - 1;
//        int xPlus = x + 1 < grid[y].length ? x + 1 : x;
//
//        for (int i = yMin; i <= yPlus; i++) {
//            for (int j = xMin; j <= xPlus; j++) {
//                if (grid[i][j] == 'O') {
//                    return -1;
//                }
//            }
//        }
//        return 0;
//    }
//
//    private int checkSurroundings(char[][] grid) {
//        if (y1 == y2) {
//            for (int i = Math.min(x1, x2) - 1; i < Math.max(x1, x2); i++) {
//                if (checkCube(grid, i, y1) == -1) {
//                    System.out.println("Error");
//                    return -1;
//                }
//            }
//        } else {
//            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
//                if (checkCube(grid, x1, i) == -1) {
//                    System.out.println("Error");
//                    return -1;
//                }
//            }
//        }
//        return 0;
//    }
//
//    private int insertShip(char[][] grid, StringBuilder c1, StringBuilder c2) {
//        if (checkCoordinates(c1, c2) == -1 || checkShipLength() == -1 || checkSurroundings(grid) == -1) {
//            return -1;
//        }
//        if (y1 == y2) {
//            for (int i = Math.min(x1, x2) - 1; i < Math.max(x1, x2); i++) {
//                grid[y1][i] = 'O';
//            }
//        } else {
//            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
//                grid[i][x1 - 1] = 'O';
//            }
//        }
//        return 0;
//    }
}
