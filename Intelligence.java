
import java.util.*;

public class Intelligence {
	private String[][] battleCamp = new String[11][11];
	private String status;
	Random rand = new Random();
	Ships[] ships = new Ships[10];
	private String name;
	// The last position in here struck
	private int lastY;
	private int lastX;
	private String directionAttack;
	
	// construct  
	Intelligence(){
		name = "CPU";
		status = "research";
		for (int i = 0 ; i<11 ; i++) {
			for (int j = 0 ; j<11 ; j++) {
				battleCamp[i][j] = "0";
				// to insert the number of columns
				String z = Integer.toString(j) ;
				battleCamp[0][j] = z;
			}
			// to insert the number of Rows
			battleCamp[1][0] = ("A") ;battleCamp[2][0] = ("B") ;battleCamp[3][0] = ("C") ;battleCamp[4][0] = ("D") ;battleCamp[5][0] = ("E") ;
			battleCamp[6][0] = ("F") ;battleCamp[7][0] = ("G") ;battleCamp[8][0] = ("H") ;battleCamp[9][0] = ("I") ;battleCamp[10][0] = ("J") ;
		}
		battleCamp[0][0] = " ";	
	}
	
	// get ships
	public Ships[] getShips() {
		return ships;
	}
		
	// get battle camp
	public String[][] getBattleCamp(){
		return battleCamp;
	}
	
	// main attack
	public void mainAttack(String[][] enemyBattleCamp, Ships[] enemyships) {
		switch (status) {
		case "research" : {
			randomAttack(enemyBattleCamp, enemyships);
			break;
		}
		case "Hit" :{
			 positionStudy(enemyBattleCamp , enemyships);
		}
		case "hitAfterHit":{
			hitAfterHit(enemyBattleCamp,enemyships ,lastX - 1 , lastY, false);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + status);
		}
	}
	
	public void positionShips() {
		double [] listShips = new double[10];
		listShips[0] = 5; listShips[1] = 4; listShips[2] = 3.1; listShips[3] = 3.1;
		listShips[4] = 3.2; listShips[5] = 3.2; listShips[6] = 3.2; listShips[7] = 2; listShips[8] = 2;listShips[9] = 2;
		int x,y,HV;
		boolean control;
		boolean overlapping = false;

		for(int i = 0 ; i < 10 ; i ++) {
			
			ships[i] = new Ships();
			ships[i].nameShips(listShips[i]);
			ships[i].setShipLength((int)Math.round (listShips[i]));
			do {
				System.out.print("\t" + ships[i].getName() + (int)Math.round (listShips[i]));
				
				HV = randomHV();
				x = randomHorizontalAndVertica();
				y = randomHorizontalAndVertica();
				control = controlOut(x, y, ships[i].getShipLength(), HV);
				if (control == true) {
					overlapping  = overlappingControl(x, y ,HV,ships[i].getShipLength());
				}
			}while(overlapping == false || control == false);
			
			updateMap(x, y ,ships[i].getShipLength(),HV);
			outPut();
			ships[i].setHorizontalVertical(HV);
			ships[i].setX(x);
			ships[i].setShipLength((int)Math.round (listShips[i]));
			ships[i].setY(y);
		}
		
		/*
		int count = 0;
		for(int i = 0 ; i < 10 ; i ++) {
			for(int j = 0 ; j < 10 ; j ++) {
				if (battleCamp[i][j].equals("S")) {
					 count++;
				}
			}
		}
		System.out.print("\n" + count);
		*/
	}
	
	// random horizontal vertical Choice
	private int randomHV() {
		return rand.nextInt(2);
	}
	
	// random horizontal and Vertical Coordinates
	private int randomHorizontalAndVertica() {
		double r = Math.random();
		int randomNum = (int)(r * (10 - 1)) + 1;
		return randomNum;
	}
	
	// control out of range in the map 
	private boolean controlOut(int x, int y , int leghtShip , int HV ) {
		if (HV == 0) {
			if (y + leghtShip - 1 <= 10 ) {
				return true;
			}
			else {
				System.out.print("La nave esce dai bordi");
				return false;
			}
		}
		else {
			if (x + leghtShip - 1 <= 10 ) {
				return true;
			}
			else {
				System.out.print("La nave esce dai bordi");
				return false;
			}
		}
	}
	
	// control overlapping of the boats
	private boolean overlappingControl(int x, int y , int HV , int shipLength ) {
		if (HV == 0) {
			for (int i = y ; i < y + shipLength  ; i++) {
				if (battleCamp[i][x].equals("S")) {
					return false;
				}
			}
			return true;
		}
		else {
			for (int i = x ; i < x + shipLength ; i++) {
				if (battleCamp[y][i].equals("S")) {
					return false;
				}
			}
			return true;
		}
	}
	
	// output of battle camp  
	public void outPut() {
			for (int i = 0 ; i<11 ; i++) {
				System.out.print("\n");
				for (int j = 0 ; j<11 ; j++) {
					System.out.print(battleCamp[i][j] + " ");
				} 
			}
		}
	
	// update the map
	private void updateMap(int x , int y, int shipLength, int HV) {
		if (HV == 0) {
			for (int i = y ; i < y + shipLength ; i++) {
					battleCamp[i][x] = "S";
				}
			}
		else {
			for (int i = x ; i < x + shipLength ; i++) {
				battleCamp[y][i] = "S";
			}
		}
	}		

	// random attack
	public void randomAttack(String[][] enemyBattleCamp , Ships[] enemiShips) {
		boolean controllo = false;
		do {
			
			int x = randomHorizontalAndVertica();
			int y = randomHorizontalAndVertica();
			System.out.println("\n" + name + " sta attacando il nemico nel punto x:" + x + " e y:" + y);
					switch (enemyBattleCamp[x][y]) {
						case("0"):
							enemyBattleCamp[x][y] = "M"; // M missed the target
							System.out.print("Hai colpito acqua");
							controllo = true;
							break;
						case("S"):
							enemyBattleCamp[x][y] = "X"; // X hit the target
							hitShips(enemiShips,x,y);
							lastX = x;
							lastY = y;
							status = "Hit";
							controllo = true;
							break;
						case("M"):
							System.out.print("\n" + "Punto gia colpito");
							break;
						case("X"):
							System.out.print("\n" + "Punto gia colpito");
							break;	
					}
		}while(controllo == false);
	}
	
	//specific attack 
	private void specificAttack(String[][] enemyBattleCamp , Ships[] enemiShips,int x , int y , boolean flag) {
		//the flag is used to understand if it is from positionStudy or mainAttack
		System.out.println("\n" + name + " sta attacando il nemico nel punto x:" + x + " e y:" + y);
		switch (enemyBattleCamp[x][y]) {
			case("0"):{
				enemyBattleCamp[x][y] = "M"; // M missed the target
				System.out.print("Hai colpito acqua");
				if (flag == false) {
					status = "research"; 
				}
				break;
			}
			case("S"):{
				enemyBattleCamp[x][y] = "X"; // X hit the target
				hitShips(enemiShips,x,y);
				lastX = x;
				lastY = y;
				status = "hitAfterHit";
				break;
			}
		}
	}
	
	private void hitShips(Ships[] enemiShips, int x , int y ) {
		x = x + 64;
		String  str = Integer.toString( x );
		for (int i = 0;i<10;i++) {
			enemiShips[i].hitToTheShips(str,y);
		}
	}
	
	// position Study of the last in here struck
	private void positionStudy(String[][] enemyBattleCamp , Ships[] enemiShips) {
		// up
		if (lastX - 1 >= 1) {
			if(enemyBattleCamp[lastX - 1][lastY] != "H" && enemyBattleCamp[lastX - 1][lastY] != "M") {
				directionAttack = "up";
				specificAttack(enemyBattleCamp, enemiShips ,lastX - 1 , lastY, true);
			}
		}
		// down
		if (lastX + 1 <= 10) {
			if(enemyBattleCamp[lastX + 1][lastY] != "H" && enemyBattleCamp[lastX + 1][lastY] != "M") {
				directionAttack = "down";
				specificAttack(enemyBattleCamp, enemiShips ,lastX + 1 , lastY, true);
			}
		}
		// left 
		if (lastY - 1 >= 1) {
			if(enemyBattleCamp[lastX][lastY - 1] != "H" && enemyBattleCamp[lastX][lastY - 1] != "M") {
				directionAttack = "left";
				specificAttack(enemyBattleCamp, enemiShips ,lastX, lastY - 1, true);
			}
		}
		// right
		if (lastY + 1 <= 10) {
			if(enemyBattleCamp[lastX][lastY + 1] != "H" && enemyBattleCamp[lastX][lastY + 1] != "M") {
				directionAttack = "right";
				specificAttack(enemyBattleCamp, enemiShips ,lastX, lastY + 1, true);
			}
		}
	}
	
	// hit After Hit
	private void hitAfterHit(String[][] enemyBattleCamp , Ships[] enemiShips,int x , int y , boolean flag) {
		switch (directionAttack){
			case "up":{
				if (lastX - 1 >= 1) {
					specificAttack(enemyBattleCamp, enemiShips ,lastX - 1 , lastY, flag);
				}
				else {
					status = "research";
					randomAttack(enemyBattleCamp, enemiShips);
				}
			}
			case "down":{
				if (lastX + 1 >= 10) {
					specificAttack(enemyBattleCamp, enemiShips ,lastX + 1 , lastY, flag);
				}
				else {
					status = "research";
					randomAttack(enemyBattleCamp, enemiShips);
				}
			}
			case "left":{
				if (lastY - 1 >= 1) {
					specificAttack(enemyBattleCamp, enemiShips ,lastX, lastY - 1, flag);
				}
				else {
					status = "research";
					randomAttack(enemyBattleCamp, enemiShips);
				}
			}
			case "right":{
				if (lastY + 1 >= 10) {
					specificAttack(enemyBattleCamp, enemiShips ,lastX, lastY + 1, flag);
				}
				else {
					status = "research";
					randomAttack(enemyBattleCamp, enemiShips);
				}
			}
		}
	}
	
	// victory control 
	public boolean victory() { 
		for(int i = 0 ; i < 11 ; i ++) {
			if(ships[i].drownedShip() == false) {
				return false;
			}
		}
		return true;
	}
	
	// output of battle camp with a filter
	public void outPutFilter() {
			for (int i = 0 ; i<11 ; i++) {
				System.out.print("\n");
				for (int j = 0 ; j<11 ; j++) {
					if(battleCamp[i][j].equals("S")) {
						System.out.print("0" + " ");
					}
					else {
						System.out.print(battleCamp[i][j] + " ");
					}
				} 
			}
		}
}

