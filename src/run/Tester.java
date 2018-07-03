package run;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

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
		Charset charset = Charset.forName("US-ASCII");
		Path file = FileSystems.getDefault().getPath("", "ClassData.txt");

		this.classes = new ArrayList<PlayerClass>();

		String cn;
		int sn;
		int mv;
		int bt;
		int bd;
		int ds;
		int df;
		int db;
		int tp;
		int fm;
		int bm;
		String cb;
		String ir;
		String pr;

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			while ((line = reader.readLine()) != null) {

				linebreak = line.split(",");
				cn = linebreak[0];
				sn = Integer.parseInt(linebreak[1]);
				mv = Integer.parseInt(linebreak[2]);
				bt = Integer.parseInt(linebreak[3]);
				bd = Integer.parseInt(linebreak[4]);
				ds = Integer.parseInt(linebreak[5]);
				df = Integer.parseInt(linebreak[6]);
				db = Integer.parseInt(linebreak[7]);
				tp = Integer.parseInt(linebreak[8]);
				fm = Integer.parseInt(linebreak[9]);
				bm = Integer.parseInt(linebreak[10]);
				cb = linebreak[11];
				ir = linebreak[12];
				pr = linebreak[13];

				classes.add(new PlayerClass(cn, sn, mv, bt, bd, ds, df, db, tp, fm, bm, cb, ir, pr));
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
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

		System.out.println("Enter an Class number from 1-17, 'q' to quit.");
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
					System.out.print("Please enter a valid number, or 'q' to quit");
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
		System.out.println("Enter a name for your character, 'q' to quit");
		String s;
		s = in.next();
		if (s.equals("q")) {
			exitProgram();
		} else {
			choice.setCharName(s);
		}
	}

	private void chooseXP(Scanner in) {
		String s;
		System.out.println("Enter a starting XP greater than 0, 'q' to quit.");
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
					System.out.println("Please enter a valid number, or 'q' to quit");
				}
			}
		}
	}

	private void chooseWeaponsArmor(Scanner in) {
		boolean canConfirm = false;

		if (this.choice.getItemLevel() > 1) {
			System.out.println("Enter an Item level number from 1-" + this.choice.getItemLevel() + ", 'q' to quit.");
		} else {
			System.out.println("Enter an Item level number equal to 1, 'q' to quit.");
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
					System.out.print("Please enter a valid number, or 'q' to quit");
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
				if(val >= 0) {
					this.choice.getStress().addStress(val);
				} else {
					this.choice.getStress().stressHeal(-val);
				}
				
				this.choice.display();
			} catch (NumberFormatException ex) {

			}

			System.out.println("Are you done testing?");
			System.out.println("Please enter 'yes' or 'no'");
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
	}

	private void exitProgram() {
		System.out.println("You have Quit Program");
		System.exit(0);
	}
}
