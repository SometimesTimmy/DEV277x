/*
This project will help you get more familiar with arrays.
You will be recreating the game of battleships.
A player will place 5 of their ships on a 10 by 10 grid.
The computer player will deploy five ships on the same grid.
Once the game starts the player and computer take turns,
trying to sink each other's ships by guessing the coordinates to "attack".
The game ends when either the player or computer has no ships left.
 */

import java.util.*;

public class BattleShips {
    static int player_ships = 0;
    static int comp_ships = 0;
    static String[][] ocean = new String[10][10];

    public static void main(String[] args) {
        intro();
        createOcean();
        while (player_ships < 5) {
            deployPShips();
        }
        System.out.println("Computer is deploying ships.");
        while (comp_ships < 5) {
            deployCShips();
        }
        System.out.println("------------------------------------------");
        while (!(comp_ships == 0 || player_ships == 0)) {
            System.out.println("");
            battleP();
            battleC();
            System.out.println("\n Your ships: "+player_ships+" | Computer ships: "+comp_ships);
            System.out.println("------------------------------------------");
        }
        if (comp_ships == 0) {
            System.out.println("Hooray! You win the battle :)");
        } else {
            System.out.println("Oh no! You lost the battle :(");
        }
    }
    public static void intro() {
        System.out.println("**** Welcome to Battle Ships game ****\n");
        System.out.println("Right now, the sea is empty.\n");
    }
    public static void createOcean() {
        System.out.print("  ");
        System.out.println("0123456789");
        for (int row = 0; row < ocean.length; row++) {
            System.out.print(row+"|");
            for (int col = 0; col < ocean[row].length; col++) {
                if (ocean[row][col] == null // empty space
                        || ocean[row][col] == "2" // computer's ships = hidden to plyaer
                        || ocean[row][col] == "m") { // computer's missed shots = hidden to player
                    System.out.print(" ");
                } else if (ocean[row][col].equals("@")) {
                    System.out.print("@");
                } else if (ocean[row][col].equals("x")) {
                    System.out.print("x");
                } else if (ocean[row][col].equals("!")) {
                    System.out.print("!");
                } else if (ocean[row][col].equals("-")) {
                    System.out.print("-");
                }
            }
            System.out.println("|"+row);
        }
        System.out.print("  ");
        System.out.println("0123456789\n");
    }
    public static void deployPShips() {
        Scanner input = new Scanner(System.in);
        System.out.println("Where would you like to place your ship #"+(player_ships+1)+"?");
        int x = -1;
        int y = -1;
        while (!(x >= 0 && x <=10)) {
            System.out.print("Enter X coordinate for your ship: ");
            x = input.nextInt();
            if (x < 0 || x > 10) {
                System.out.println("Invalid X coordinate.");
            }
        }
        while (!(y >= 0 && y <= 10)) {
            System.out.print("Enter Y coordinate for your ship: ");
            y = input.nextInt();
            if (y < 0 || y > 10) {
                System.out.println("Invalid Y coordinate.");
            }
        }
        if (ocean[x][y] == null) {
            ocean[x][y] = "@";
            player_ships++;
            System.out.println("");
            createOcean();
            System.out.println(player_ships+" ship(s) have been placed.");
        } else {
            System.out.println("You have another ship in the same location already.");
        }
    }
    public static void deployCShips() {
        // standard pattern: Min + (int)(Math.random() * ((Max - Min) + 1))
        int x = (int)(Math.random()*(9+1)); // min is 0, and max is 10.
        int y = (int)(Math.random()*(9+1));
        if (ocean[x][y] == null) {
            ocean[x][y] = "2";
            System.out.println((comp_ships+1)+". ship DEPLOYED");
            comp_ships++;
        } else {
            ;
        }
    }
    public static void battleP() {
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR TURN");
        createOcean();
        int x = -1;
        int y = -1;
        while (!(x >= 0 && x <=10)) {
            System.out.print("Enter X coordinate to fire: ");
            x = input.nextInt();
            if (x < 0 || x > 10) {
                System.out.println("Invalid X coordinate.");
            }
        }
        while (!(y >= 0 && y <= 10)) {
            System.out.print("Enter Y coordinate to fire: ");
            y = input.nextInt();
            if (y < 0 || y > 10) {
                System.out.println("Invalid Y coordinate.");
            }
        }
        if (ocean[x][y].equals("@")) {
            System.out.println("Oh no, you sunk your own ship :(\n");
            ocean[x][y] = "x";
            player_ships--;
        } else if (ocean[x][y].equals("2")) {
            System.out.println("Boom! You sunk an enemy ship!\n");
            ocean[x][y] = "!";
            comp_ships--;
        } else { // // effectively means (ocean[x][y] == null)
            System.out.println("You missed.\n");
            ocean[x][y] = "-";
        }
        createOcean(); // go back and add "x", "!", and "-" into createOcean().
    }
    public static void battleC() {
        System.out.println("COMPUTER'S TURN");
        int x = (int)(Math.random()*(9+1)); // min is 0, and max is 10.
        int y = (int)(Math.random()*(9+1));
        if (ocean[x][y] == null || ocean[x][y] == "@") {
            System.out.println("Computer fired at coordinates["+x+","+y+"].");
            if (ocean[x][y] == null) {
                System.out.println("Computer missed.\n");
                ocean[x][y] = "m"; // computer will not fire at "m" again. hidden to player
            } else {
                System.out.println("The Computer sunk one of your ships!\n");
                player_ships--;
            }
        // re-run battleC() if random coordinates select
        } else if (ocean[x][y].equals("2") // computer's own ships
                || ocean[x][y].equals("!") // computer's sunken ships
                || ocean[x][y].equals("x") // player's sunken ships
                || ocean[x][y].equals("-") // player's miss
                || ocean[x][y].equals("m")) { // computer's miss
            battleC();
        }
        createOcean();
    }
}
