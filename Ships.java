
public class Ships {
	private String x;
	private int y;
	private int shipLength;
	private String horizontalVertical;
	private int hit = 0;
	String name;
	// construct
	Ships(){
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public int getX() {
		char ch = x.charAt(0);
		int num = ch;
		num = num - 64;
		return num;
	}
	
	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getName() {
		return name;
	}
	
	public void setX(String x) {
		this.x = x;
	}
	
	public void setX(int x) {
		x = x + 64;
		String str = Integer.toString( x );
		this.x = str;
	}
	
	public void setShipLength(int shipLength) {
		this.shipLength = shipLength;
	}
	
	public int getShipLength() {
		return shipLength;
	}

	public void setHorizontalVertical(String horizontalVertical) {
		this.horizontalVertical = horizontalVertical;
	}
	
	public void setHorizontalVertical(int horizontalVertical) {
		if (horizontalVertical == 0) {
			this.horizontalVertical = "O";
		}
		else {
			this.horizontalVertical = "V";
		}
		
	}
	
	public String getHorizontalVertical() {
		return horizontalVertical;
	}
	
	private int[] occupiedLength() {
		int[] occupied = new int[shipLength];
		for (int i = 0; i < shipLength; i++) {
			if (horizontalVertical.equals("O")) {
				occupied[i] = getY() + i ;
			}
			else{
				occupied[i] = getX() + i ;
			}
		}
		return occupied;
	}
	// hit to the ships 
	public void hitToTheShips(String X , int Y) {
		char ch = X.charAt(0);
		int num = ch;
		num = num - 64;
		
		if (horizontalVertical.equals("O")) {
			int[] occupied = occupiedLength();
			for (int i = 0; i < shipLength; i++) {
				if (num == getX() && Y == occupied[i]) {
					System.out.println("\n" + "nave nemica colpita");
					hit++;
				}
			}
		}
		else {
			int[] occupied = occupiedLength();
			for (int i = 0; i < shipLength; i++) {
				if (num == occupied[i] && Y == y) {
					System.out.println("\n" + "nave nemica colpita");
					hit++;
				}
			}
		}
		
	}
	
	// control drowned ship
	public boolean drownedShip () {
		if (hit == shipLength ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// for name of the ships
	public void nameShips(double id) {
		switch (Double.toString(id)) {
		
		case "5.0": {
			name = "Portaerei";
			break;
		}
		case "4.0": {
			name = "Corazzata";
			break;
		}
		case "2.0": {
			name = "Navi d�assalto";
			break;
		}
		case "3.1": {
			name = "Crociere";
			break;
		}
		case "3.2": {
			name = "Sottomarini";
			break;
		}
		default:
			System.out.print("valore non valido");
		}
	}
	
}
