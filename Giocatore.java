import java.io.UnsupportedEncodingException;
import java.util.*;

public class Giocatore {
    private String[][] battleCamp = new String[11][11];
    private Scanner in = new Scanner(System.in);
    Ships[] ships = new Ships[10];
    String name;
    
    // Constructor
    Giocatore(){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                battleCamp[i][j] = "0";
                // Insert column numbers
                String z = Integer.toString(j);
                battleCamp[0][j] = z;
            }
            // Insert row letters
            battleCamp[1][0] = "A"; battleCamp[2][0] = "B"; battleCamp[3][0] = "C"; battleCamp[4][0] = "D"; 
            battleCamp[5][0] = "E"; battleCamp[6][0] = "F"; battleCamp[7][0] = "G"; battleCamp[8][0] = "H"; 
            battleCamp[9][0] = "I"; battleCamp[10][0] = "J";
        }
        // Set the top-left corner to space for formatting
        battleCamp[0][0] = " ";
    }
    
    // control of victory 
	public boolean victory() { 
		for(int i = 0 ; i < 11 ; i ++) {
			if(ships[i].drownedShip() == false) {
				return false;
			}
		}
		return true;
	}

    // Set player name
    public void setName(String name) {
        this.name = name;
    }
    
    // Get player's ships
    public Ships[] getShips() {
        return ships;
    }
    
    // Get the battle grid
    public String[][] getBattleCamp(){
        return battleCamp;
    }
    
    // Output the battle grid to the console
    public void outPut() {
        for (int i = 0; i < 11; i++) {
            System.out.print("\n");
            for (int j = 0; j < 11; j++) {
                System.out.print(battleCamp[i][j] + " ");
            }
        }
    }
    
    // Main method for positioning ships on the grid
    public void positionShipsMain() {
        double[] listShips = new double[10];
        listShips[0] = 5; listShips[1] = 4; listShips[2] = 3.1; listShips[3] = 3.1;
        listShips[4] = 3.2; listShips[5] = 3.2; listShips[6] = 3.2; listShips[7] = 2; listShips[8] = 2; listShips[9] = 2;
        
        boolean overlapping = false;
        boolean validPlacement = false;
        int y;
        String x, HV;
        
        for (int i = 0; i < 10; i++) {
            ships[i] = new Ships();
            ships[i].nameShips(listShips[i]);
            ships[i].setShipLength((int) Math.round(listShips[i]));
            
            do {
                // Get ship orientation (horizontal or vertical)
                HV = horizontalVerticalChoice(ships[i].getName());
                outPut();
                
                // Get ship row position
                x = inputShipPositionsRows("\n" + ships[i].getName() + " (" + Math.round(listShips[i]) + " cells) " + "\nEnter row (A - J): ");
                
                // Get ship column position
                y = inputShipPositionsColons("\n" + ships[i].getName() + " (" + Math.round(listShips[i]) + " cells) " + "\nEnter column (1 - 10): ");
                
                // Check if placement is valid and within boundaries
                validPlacement = controlVariable(y, x, (int) Math.round(listShips[i]), HV);
                
                if (validPlacement) {
                    // Check if the ship overlaps with other ships
                    overlapping = overlappingBoats(x, y, ships[i].getShipLength(), HV);
                }
                
            } while (!overlapping || !validPlacement);
            
            ships[i].setHorizontalVertical(HV);
            ships[i].setX(x);
            ships[i].setShipLength((int) Math.round(listShips[i]));
            ships[i].setY(y);
            updateMap(y, ships[i].getX(), (int) Math.round(listShips[i]), HV);
            outPut();    
        }
    }

    // horizontal or vertical choice
	public String horizontalVerticalChoice (String nameShips) {		
		boolean flag = false;
		do {
			System.out.print("\n" + name + " scielga la posizionare per la "+ nameShips +": in orizontale ( O ) oppure in verticale ( V ): ");
			String selection = in.nextLine();
			selection = selection.toUpperCase();
			if (selection.length() == 1) {
				if (selection.equals("O") || selection.equals("V")) {
					return selection;
				}
			}
			else {
				System.out.println("\n" + "stringa troppo lunga");
			}
		}while(flag == false);
		return "";
	}

    // Input for row selection for ship positioning
    public String inputShipPositionsRows(String outputMessage) {
        boolean isValid = false;
        do {
            System.out.print("\n" + outputMessage);
            String posx = in.nextLine();
            
            if (posx.length() <= 1) {
                posx = posx.toUpperCase();
                char ch = posx.charAt(0);
                int num = ch;
                
                if (num <= 74 && num > 64) {
                    return posx;
                } else {
                    System.out.print("\nInvalid letter");
                }
            } else {
                System.out.print("\nString too long");
            }
        } while (!isValid);
        return "";
    }
    
    // Input for column selection for ship positioning
    public int inputShipPositionsColons(String outputMessage) {
        boolean isValid = false;
        while (!isValid) {
            System.out.print(outputMessage);
            int posy = in.nextInt();
            in.nextLine();
            
            if (posy <= 10 && posy >= 1) {
                return posy;
            } else {
                System.out.print("Invalid number");
            }
        }
        return 0;
    }
    
    // Control to check if the ship fits on the grid
    public boolean controlVariable(int posy, String posx, int shipLength, String HV) {
        if (HV.equals("O")) { // Horizontal
            if (posy + shipLength - 1 <= 10) {
                return true;
            } else {
                System.out.print("Ship exceeds grid boundaries");
                return false;
            }
        } else { // Vertical
            posx = posx.toUpperCase();
            char ch = posx.charAt(0);
            int num = ch;
            
            if (num + shipLength - 1 <= 74) {
                return true;
            } else {
                System.out.print("\nShip exceeds grid boundaries");
                return false;
            }
        }
    }
    
    // Check for overlapping ships
    public boolean overlappingBoats(String x, int y, int shipLength, String HV) {
        if (HV.equals("O")) { // Horizontal
            for (int i = 0; i < 11; i++) {
                if (battleCamp[i][0].equals(x)) {
                    for (int j = y; j < shipLength + y; j++) {
                        if (battleCamp[i][j].equals("S")) {
                            System.out.print("\nThe ship overlaps with another");
                            return false;
                        }
                    }
                }
            }
            return true;
        } else { // Vertical
            for (int i = 0; i < 11; i++) {
                if (battleCamp[i][0].equals(x)) {
                    for (int j = i; j < shipLength + i; j++) {
                        if (battleCamp[j][y].equals("S")) {
                            System.out.print("\nThe ship overlaps with another");
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }
    
    // Output the battle grid with visual filter (water, hits, etc.)
    public void outPutFilter() {
        for (int i = 0; i < 11; i++) {
            System.out.print("\n");
            for (int j = 0; j < 11; j++) {
                switch (battleCamp[i][j]) {
                    case "S":
                        System.out.print("💧" + " ");
                        break;
                    case "H":
                        System.out.print("❌" + " ");
                        break;
                    case "0":
                        System.out.print("💧" + " ");
                        break;
                    default:
                        System.out.print(battleCamp[i][j] + " ");
                        break;
                }
            }
        }
    }
    
    // Attack system, checks enemy grid for hits and misses
    public void attack(String[][] enemyGrid, Ships[] enemyShips) {
        boolean isValid = false;
        do {
            System.out.println("\n" + name + " is attacking the enemy");
            String x = inputShipPositionsRows("Enter the row (A - J): ");
            int y = inputShipPositionsColons("\nEnter the column (1 - 10): ");
            
            for (int i = 0; i < 11; i++) {
                if (enemyGrid[i][0].equals(x)) {
                    switch (enemyGrid[i][y]) {
                        case "0":
                            enemyGrid[i][y] = "M"; // M for missed
                            System.out.print("You hit water");
                            isValid = true;
                            break;
                        case "S":
                            enemyGrid[i][y] = "X"; // X for hit
                            hitShips(enemyShips, x, y);
                            isValid = true;
                            break;
                        case "M":
                            System.out.print("\nAlready attacked this point");
                            break;
                        case "X":
                            System.out.print("\nAlready attacked this point");
                            break;
                    }
                }
            }
        } while (!isValid);
    }
    
    // control hit
	private void hitShips(Ships[] enemiShips, String x , int y ) {
		for (int i = 0;i<10;i++) {
			enemiShips[i].hitToTheShips(x,y);
		}
	}   

    // update the map
	public void updateMap(int x , int y, int shipLength,  String HV) {
		if (HV.equals("V")) {
			for (int i = y ; i<y + shipLength ; i++) {
				battleCamp[i][x] = "S";
			}
		}
		else {
			for (int i = x ; i<x + shipLength ; i++) {
				battleCamp[y][i] = "S";
			}
		}
	}

}