import battleship.*;

public class Main {
    public static void main (String[] args) {
        Battleship game = new Battleship("Player 1", "Player 2");
        game.gameStart();
    }
}