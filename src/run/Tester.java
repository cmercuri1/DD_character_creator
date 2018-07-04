package run;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import character_aspects.ChosenClass;
import setup.PlayerClass;

public class Tester {
	private ArrayList<PlayerClass> classes;
	private ChosenClass choice;

	public Tester() {
	}

	public void run() {
		this.open();

		this.choices();
	}

	private void open() {
		this.classes = new ArrayList<PlayerClass>();
		JSONParser parser = new JSONParser();
		
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("ClassData.json"));
			
			JSONArray classData = (JSONArray) jsonObject.get("Class Data");
			
			for(Object o:classData){
				this.classes.add(new PlayerClass((JSONObject) o));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void choices() {
		System.out.println("Welcome to Darkest Dungeon Character Creator/Runner");

		this.choice = new ChosenClass();
		Scanner in = new Scanner(System.in);

		while (true) {
			this.chooseClass(in);
			this.chooseName(in);
			this.chooseXP(in);
			this.chooseWeaponsArmor(in);

			System.out.println("Are you happy with this character?");
			System.out.println("Enter 'yes' to confirm, 'no' to start again");
			String s;
			while (true) {
				s = in.next();
				if (!s.equals("yes") && !s.equals("no")) {
					System.out.println("Please enter 'yes' or 'no'");
				} else {
					break;
				}
			}
			if (s.equals("yes")) {
				break;
			}
		}
		this.choice.display();

		this.stressTest(in);

		in.close();
	}

	private void chooseClass(Scanner in) {
		boolean canConfirm = false;

		System.out.println("Enter an Class number from 1-17, enter 'q' at any time to quit.");
		String s;
		while (true) {
			s = in.next();
			try {
				int val = Integer.parseInt(s);
				if (val > 17 || val < 1) {
					System.out.println("Please enter a value within the parameters");
					canConfirm = false;
				} else {
					val--;
					this.choice.assignClass(this.classes.get(val));
					canConfirm = true;
					System.out.println("You have chosen: " + this.classes.get(val).getName());
					System.out.println("Enter 'yes' to confirm, or another number to change");
				}
			} catch (NumberFormatException ex) {
				if (s.equals("q")) {
					exitProgram();
				} else if (s.equals("yes") && canConfirm == true) {
					break;
				} else {
					System.out.print("Please enter a valid number");
					if (canConfirm == true) {
						System.out.println(", or 'yes' to confirm");
					} else {
						System.out.println();
					}
				}
			}
		}
	}

	private void chooseName(Scanner in) {
		System.out.println("Enter a name for your character (q will not quit here)");
		String s;
		s = in.next();
		
		choice.setCharName(s);
		
	}

	private void chooseXP(Scanner in) {
		String s;
		System.out.println("Enter a starting XP greater than 0");
		while (true) {
			s = in.next();
			try {
				int val = Integer.parseInt(s);
				if (val < 0) {
					System.out.println("Please enter a value within the parameters");
				} else {
					this.choice.setXP(val);
					break;
				}
			} catch (NumberFormatException ex) {
				if (s.equals("q")) {
					exitProgram();
				} else {
					System.out.println("Please enter a valid number");
				}
			}
		}
	}

	private void chooseWeaponsArmor(Scanner in) {
		boolean canConfirm = false;

		if (this.choice.getItemLevel() > 1) {
			System.out.println("Enter an Item level number from 1-" + this.choice.getItemLevel());
		} else {
			System.out.println("Enter an Item level number equal to 1");
		}

		String s;
		while (true) {
			s = in.next();
			try {
				int val = Integer.parseInt(s);
				if (val > 5 || val < 1) {
					System.out.println("Please enter a value within the parameters");
				} else {
					if (this.choice.chooseItems(val) == 1) {
						System.out.println("Enter 'yes' to confirm, or another number to change");
						canConfirm = true;
					}

				}
			} catch (NumberFormatException ex) {
				if (s.equals("q")) {
					exitProgram();
				} else if (s.equals("yes") && canConfirm == true) {
					break;
				} else {
					System.out.print("Please enter a valid number");
					if (canConfirm == true) {
						System.out.println(", or 'yes' to confirm");
					} else {
						System.out.println();
					}
				}
			}
		}
	}

	private void stressTest(Scanner in) {
		String s;
		while (true) {
			System.out.println("Enter a stress increase value.");
			s = in.next();

			try {
				int val = Integer.parseInt(s);
				if (val >= 0) {
					this.choice.getStress().addStress(val);
				} else {
					this.choice.getStress().stressHeal(-val);
				}

				this.choice.display();
			} catch (NumberFormatException ex) {
				if (s.equals("q")) {
					exitProgram();
				}
			}

			System.out.println("Are you done testing?");
			System.out.println("Please enter 'yes' or 'no'");
			while (true) {
				s = in.next();
				
				if (s.equals("q")) {
					exitProgram();
				} else if (!s.equals("yes") && !s.equals("no")) {
					System.out.println("Please enter 'yes' or 'no'");
				} else {
					break;
				}
			}
			if (s.equals("yes")) {
				break;
			}

		}
	}

	private void exitProgram() {
		System.out.println("You have Quit Program");
		System.exit(0);
	}
}
