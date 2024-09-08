import java.util.Scanner;

public class mainBattagliaNavale {

    public static void main(String[] args) {
        int mode;
        Scanner in = new Scanner(System.in);

        // Ask the player if they want to play 1v1 or against the CPU
        System.out.print("Do you want to play 1vs1 (1) or against the CPU (2)? ");
        mode = in.nextInt();

        // If the player chooses 1v1 mode
        if (mode == 1) {
            Giocatore player1 = new Giocatore();
            Giocatore player2 = new Giocatore();

            // Get player 1's name
            System.out.print("Enter player 1's name: ");
            String name1 = in.nextLine();
            player1.setName(name1);
            in.nextLine(); // Clear the input buffer

            // Get player 2's name
            System.out.print("Enter player 2's name: ");
            String name2 = in.nextLine();
            player2.setName(name2);

            // Both players position their ships
            player1.positionShipsMain();
            player2.positionShipsMain();

            boolean victory = false;

            // Main game loop for 1v1 mode
            do {
                // Player 1 attacks Player 2
                player1.attack(player2.getBattleCamp(), player2.getShips());
                victory = player1.victory();  // Check if Player 1 won
                player1.outPutFilter();       // Output Player 1's filtered grid (with hidden ships)

                if (!victory) { // If Player 1 hasn't won, it's Player 2's turn
                    player2.attack(player1.getBattleCamp(), player1.getShips());
                    victory = player2.victory();  // Check if Player 2 won
                    player2.outPutFilter();       // Output Player 2's filtered grid
                }
            } while (!victory);  // Continue until a player wins

        } else {
            // If the player chooses to play against the CPU
            in.nextLine(); // Clear the input buffer
            Intelligence CPU = new Intelligence();

            // CPU positions its ships
            CPU.positionShips();
            CPU.outPut(); // Output the CPU's grid (for debugging)

            Giocatore player1 = new Giocatore();

            // Get the player's name
            System.out.print("\n" + "Enter player name: ");
            String name1 = in.nextLine();
            player1.setName(name1);

            // Player positions their ships
            player1.positionShipsMain();

            boolean victory = false;

            // Main game loop for CPU vs Player mode
            do {
                // Player attacks the CPU
                player1.attack(CPU.getBattleCamp(), CPU.getShips());
                victory = player1.victory();  // Check if Player won
                player1.outPutFilter();       // Output Player's filtered grid

                if (!victory) {  // If Player hasn't won, the CPU takes its turn
                    CPU.mainAttack(player1.getBattleCamp(), player1.getShips());
                    victory = CPU.victory();  // Check if the CPU won
                    CPU.outPutFilter();       // Output the CPU's filtered grid
                }
            } while (!victory);  // Continue until a player wins
        }
    }
}
