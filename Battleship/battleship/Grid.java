package battleship;

public class Grid {

    private final int HEIGHT = 10;
    private final int LENGTH = 10;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private char[][] grid;

    public Grid() {
        grid = new char[HEIGHT][LENGTH];
        makeGrid();
    }

    private void makeGrid() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                grid[i][j] = '~';
            }
        }
    }

    void printGrid() {
        char c = 'A';

        System.out.print(" ");
        for (int i = 1; i <= LENGTH; i++) {
            System.out.print(" " + i);
        }
        System.out.print("\n");
        for (int i = 0; i < HEIGHT; i++) {
            System.out.print(c++);
            for (int j = 0; j < LENGTH; j++) {
                System.out.print(" " + grid[i][j]);
            }
            System.out.print("\n");
        }
    }

    private int isSank(int x, int y, char dir) {
        if (dir == 'u' || dir == 'd') {
            for (int i = y - 1; i >= 0; i--) {
                if (grid[i][x] == 'O') {
                    return 1;
                }
                if (grid[i][x] == 'M' || grid[i][x] == '~') {
                    break;
                }
            }
            for (int i = y + 1; i < HEIGHT; i++) {
                if (grid[i][x] == 'O') {
                    return 1;
                }
                if (grid[i][x] == 'M' || grid[i][x] == '~') {
                    break;
                }
            }
        } else if (dir == 'l' || dir == 'r') {
            for (int i = x - 1; i >= 0; i--) {
                if (grid[y][i] == 'O') {
                    return 1;
                }
                if (grid[y][i] == 'M' || grid[i][x] == '~') {
                    break;
                }
            }
            for (int i = x + 1; i < LENGTH; i++) {
                if (grid[y][i] == 'O') {
                    return 1;
                }
                if (grid[y][i] == 'M' || grid[i][x] == '~') {
                    break;
                }
            }
        }
        return 0;
    }

    private int checkSank(int x, int y) {
        int yMinus = y - 1 < 0 ? y : y - 1;
        int yPlus = y + 1 < grid.length ? y + 1 : y;
        int xMinus = x - 1 < 0 ? x : x - 1;
        int xPlus = x + 1 < grid[y].length ? x + 1 : x;
        char dir;

        if (yMinus != y && grid[yMinus][x] == 'X') {
            dir = 'u';
        } else if (yPlus != y && grid[yPlus][x] == 'X') {
            dir = 'd';
        } else if (xMinus != x && grid[y][xMinus] == 'X') {
            dir = 'l';
        } else if (xPlus != x && grid[y][xPlus] == 'X') {
            dir = 'r';
        } else {
            return 1;
        }
        return isSank(x, y, dir);
    }

    private boolean isAllSank() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < LENGTH; j++) {
                if (grid[i][j] == 'O') {
                    return false;
                }
            }
        }
        return true;
    }

    int registerHit(String cooridinates, Grid foggedGrid) throws IllegalArgumentException {
        int targetY;
        int targetX;
        StringBuilder target = new StringBuilder(cooridinates);

        targetY = target.charAt(0) - 'A';
        target.deleteCharAt(0);
        try {
            targetX = Integer.parseInt(target.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
        if ((targetX - 1 < 0 || targetX > LENGTH) || (targetY < 0 || targetY > HEIGHT)) {
            return -1;
        } else if (grid[targetY][targetX - 1] == 'O' || grid[targetY][targetX - 1] == 'X') {
            grid[targetY][targetX - 1] = 'X';
            foggedGrid.grid[targetY][targetX - 1] = 'X';
            if (checkSank(targetX - 1, targetY) == 0) {
                if (isAllSank()) {
                    return 0;
                }
                return 3;
            }
            return 2;
        } else {
            grid[targetY][targetX - 1] = 'M';
            foggedGrid.grid[targetY][targetX - 1] = 'M';
            return 1;
        }
    }

    int insertShip(String c1, String c2, int lengthOfTheShip) throws IllegalArgumentException {
        if ((c1.isEmpty() || c2.isEmpty())) {
            throw new IllegalArgumentException();
        }
        if (checkCoordinates(new StringBuilder(c1), new StringBuilder(c2)) == -1) {
            throw new IllegalArgumentException();
        }
        if (y1 != y2 && x1 != x2) {
            return -2;
        }
        if (checkShipLength(lengthOfTheShip) == -1) {
            return -1;
        }
        if (checkSurroundings() == -1) {
            return -3;
        }
        if (y1 == y2) {
            for (int i = Math.min(x1, x2) - 1; i < Math.max(x1, x2); i++) {
                grid[y1][i] = 'O';
            }
        } else {
            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
                grid[i][x1 - 1] = 'O';
            }
        }
        return 0;
    }

    private int checkCoordinates(StringBuilder c1, StringBuilder c2) {
        y1 = c1.charAt(0) - 'A';
        if (y1 > 9 || y1 < 0) {
            return -1;
        }
        c1.deleteCharAt(0);
        try{
            x1 = Integer.parseInt(c1.toString());
        } catch(NumberFormatException e) {
            return -1;
        }
        y2 = c2.charAt(0) - 'A';
        if (y2 > 9 || y2 < 0) {
            return -1;
        }
        c2.deleteCharAt(0);
        try {
            x2 = Integer.parseInt(c2.toString());
        } catch (NumberFormatException e) {
            return -1;
        }
        return 0;
    }

    private int checkShipLength(int lengthOfTheShip) {
        int cells;

        if (y1 == y2) {
            cells = Math.max(x1, x2) - Math.min(x1, x2) + 1;
        } else {
            cells = Math.max(y1, y2) - Math.min(y1, y2) + 1;
        }
        return cells > lengthOfTheShip ? -1 : cells < lengthOfTheShip ? -1 : 0;
    }

    private int checkCube(int x, int y) {
        int yMin = y - 1 < 0 ? y : y - 1;
        int yPlus = y + 1 < grid.length ? y + 1 : y;
        int xMin = x - 1 < 0 ? x : x - 1;
        int xPlus = x + 1 < grid[y].length ? x + 1 : x;

        for (int i = yMin; i <= yPlus; i++) {
            for (int j = xMin; j <= xPlus; j++) {
                if (grid[i][j] == 'O') {
                    return -1;
                }
            }
        }
        return 0;
    }

    private int checkSurroundings() {
        if (y1 == y2) {
            for (int i = Math.min(x1, x2) - 1; i < Math.max(x1, x2); i++) {
                if (checkCube(i, y1) == -1) {
                    return -1;
                }
            }
        } else {
            for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
                if (checkCube(x1, i) == -1) {
                    return -1;
                }
            }
        }
        return 0;
    }
}
