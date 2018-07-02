import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/* ChosenClass Class for managing all the aspects of a character */
public class ChosenClass {
	private String charName;
	private int xp;
	private String title;

	private int level;
	private int itemLevel;

	private PlayerClass chosen;
	private Armor chosenArmor;
	private Weapon chosenWeapon;

	private Stress stress;
	
	private ArrayList<Ability> resistances;
	private ArrayList<Ability> statistics;

	public ChosenClass() {
		this.resistances = new ArrayList<Ability>();
		this.statistics = new ArrayList<Ability>();
		this.xp = 0;
		this.level = 0;
		this.setUpArrays();
	}

	public void assignClass(PlayerClass pclass) {
		this.stress = new Stress(pclass.getName(), this);
		this.chosen = pclass;
		
		this.alterAbility("res", "STUN", this.chosen.getResistances().get(0).getValue());
		this.alterAbility("res", "MOVE", this.chosen.getResistances().get(1).getValue());
		this.alterAbility("res", "BLEED", this.chosen.getResistances().get(2).getValue());
		this.alterAbility("res", "BLIGHT", this.chosen.getResistances().get(3).getValue());
		this.alterAbility("res", "DISEASE", this.chosen.getResistances().get(4).getValue());
		this.alterAbility("res", "DEBUFF", this.chosen.getResistances().get(5).getValue());
		this.alterAbility("res", "DEATHBLOW", this.chosen.getResistances().get(6).getValue());
		this.alterAbility("res", "TRAP", this.chosen.getResistances().get(7).getValue());
		
		this.alterAbility("stat", "TRAP DISARM", this.chosen.getTrapDisarm());
	}

	public void setXP(int xp) {
		this.xp = xp;
		this.checkXP("Radiant");
	}

	private void checkXP(String difficulty) {
		Charset charset = Charset.forName("US-ASCII");
		if (!difficulty.equals("Radiant") && !difficulty.equals("Darkest")) {
			System.out.println("Incorrect difficulty type");
			System.exit(0);
		}
		Path file = FileSystems.getDefault().getPath("", difficulty + "XP.txt");

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			int lvl = 0;

			while ((line = reader.readLine()) != null) {
				linebreak = line.split(",");
				if ((this.xp - Integer.parseInt(linebreak[1])) >= 0) {
					lvl = Integer.parseInt(linebreak[0]);
				} else {
					this.changeLevel(lvl);
					break;
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	private void changeLevel(int lvl) {
		this.setLevel(lvl);
		this.applyLevel();
		this.setMaxItemLevel();
		this.getTitle();
	}

	private void setLevel(int lvl) {
		this.level = lvl;
	}

	private void applyLevel() {
		float improvement = this.level*10;
		
		this.alterAbility("res", "STUN", this.chosen.getResistances().get(0).getValue() + improvement);
		this.alterAbility("res", "MOVE", this.chosen.getResistances().get(1).getValue() + improvement);
		this.alterAbility("res", "BLEED", this.chosen.getResistances().get(2).getValue() + improvement);
		this.alterAbility("res", "BLIGHT", this.chosen.getResistances().get(3).getValue() + improvement);
		this.alterAbility("res", "DEBUFF", this.chosen.getResistances().get(5).getValue() + improvement);
		this.alterAbility("res", "TRAP", this.chosen.getResistances().get(7).getValue() + improvement);
		
		this.alterAbility("stat", "TRAP DISARM", this.chosen.getTrapDisarm() + improvement);
	}

	private void getTitle() {
		Charset charset = Charset.forName("US-ASCII");
		Path file = FileSystems.getDefault().getPath("", "TitleData.txt");

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			while ((line = reader.readLine()) != null) {

				linebreak = line.split(",");
				if (this.level == Integer.parseInt(linebreak[0])) {
					this.title = linebreak[1];
					break;
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	private void setMaxItemLevel() {
		if (level >= 5) {
			this.itemLevel = 5;
		} else if (level >= 3) {
			this.itemLevel = 4;
		} else if (level >= 2) {
			this.itemLevel = 3;
		} else if (level >= 1) {
			this.itemLevel = 2;
		} else {
			this.itemLevel = 1;
		}
	}

	public int chooseItems(int index) {
		if (index > this.itemLevel) {
			System.out.println("The inputted item level is too high");
			return 0;
		}
		this.chooseArmor(index);
		this.chooseWeapon(index);
		return 1;
	}

	public void chooseArmor(int index) {
		for (int i = 0; i < this.chosen.getClassArmors().getArmors().size(); i++) {
			if (index == this.chosen.getClassArmors().getArmors().get(i).getLevel()) {
				this.chosenArmor = this.chosen.getClassArmors().getArmors().get(i);
				break;
			}
		}
		this.alterAbility("stat", "DODGE", this.chosenArmor.getDodge());
		this.alterAbility("stat", "MAXHP", this.chosenArmor.getHp());
		this.alterAbility("stat", "MINHP", this.chosenArmor.getHp());
	}

	public void chooseWeapon(int index) {
		for (int i = 0; i < this.chosen.getClassWeapons().getWeapons().size(); i++) {
			if (index == this.chosen.getClassWeapons().getWeapons().get(i).getLevel()) {
				this.chosenWeapon = this.chosen.getClassWeapons().getWeapons().get(i);
				break;
			}
		}
		this.alterAbility("stat", "MINDAM", this.chosenWeapon.getMinDam());
		this.alterAbility("stat", "MAXDAM", this.chosenWeapon.getMaxDam());
		this.alterAbility("stat", "CRIT", this.chosenWeapon.getCrit());
		this.alterAbility("stat", "SPEED", this.chosenWeapon.getSpeed());
	}
	
	private void setUpArrays() {
		this.resistances.add(new Ability("STUN",0,0,200));
		this.resistances.add(new Ability("MOVE",0,0,200));
		this.resistances.add(new Ability("BLIGHT",0,0,200));
		this.resistances.add(new Ability("BLEED",0,0,200));
		this.resistances.add(new Ability("DISEASE",0,0,200));
		this.resistances.add(new Ability("DEBUFF",0,0,200));
		this.resistances.add(new Ability("DEATHBLOW",0,0,87));
		this.resistances.add(new Ability("TRAP",0,0,200));
		
		this.statistics.add(new Ability("DODGE",0,0,150));
		this.statistics.add(new AbilityMulti("MAXHP",0,0,1000));
		this.statistics.add(new AbilityMulti("CURRHP",0,0,1000));
		this.statistics.add(new AbilityMulti("MINDAM",0,0,200));
		this.statistics.add(new AbilityMulti("MAXDAM",0,0,200));
		this.statistics.add(new Ability("CRIT",0,0,100));
		this.statistics.add(new Ability("SPEED",0,0,50));
		this.statistics.add(new Ability("TRAP DISARM",0,0,135));
		this.statistics.add(new Ability("STRESSRESIST",100,0,100));
		this.statistics.add(new Ability("VIRTUECHANCE",25,1,95));
	}
	
	private void alterAbility(String array, String cat, float val) {
		if (array.equals("res")) {
			for(Ability res: this.resistances) {
				if(res.getName().equals(cat)) {
					res.setBaseValue(val);
				}
			}
		} else if (array.equals("stat")) {
			for(Ability res: this.statistics) {
				if(res.getName().equals(cat)) {
					res.setBaseValue(val);
				}
			}
		}
	}
	
	public void alterResistances(float stun, float move, float blight, float bleed,
			float disease, float debuff, float death, float trap) {
		this.alterAbilityMod("res", "STUN", stun);
		this.alterAbilityMod("res", "MOVE", move);
		this.alterAbilityMod("res", "BLIGHT", blight);
		this.alterAbilityMod("res", "BLEED", bleed);
		this.alterAbilityMod("res", "DISEASE", disease);
		this.alterAbilityMod("res", "DEBUFF", debuff);
		this.alterAbilityMod("res", "DEATHBlOW", death);
		this.alterAbilityMod("res", "TRAP", trap);
		
		this.alterAbilityMod("stat", "TRAP DISARM", trap);
	}
	
	private void alterAbilityMod(String array, String cat, float val) {
		if (array.equals("res")) {
			for(Ability res: this.resistances) {
				if(res.getName().equals(cat)) {
					res.setModifier(val);
				}
			}
		} else if (array.equals("stat")) {
			for(Ability res: this.statistics) {
				if(res.getName().equals(cat)) {
					res.setModifier(val);
				}
			}
		}
	}
	
	public float getAbility(String array, String cat) {
		if (array.equals("res")) {
			for(Ability res: this.resistances) {
				if(res.getName().equals(cat)) {
					return res.getFinal();
				}
			}
		} else if (array.equals("stat")) {
			for(Ability res: this.statistics) {
				if(res.getName().equals(cat)) {
					return res.getFinal();
				}
			}
		}
		return -1;
	}

	public void display() {
		System.out.println("Your character is:");
		System.out.println(charName);

		System.out.println(this.title + " " + this.chosen.getName());

		this.displayStats();

		System.out.println("EQUIPMENT:");

		if (this.chosenArmor != null) {
			this.chosenArmor.display();
		}
		if (this.chosenWeapon != null) {
			this.chosenWeapon.display();
		}

		this.stress.display();
	}

	private void displayStats() {
		
	}

	public PlayerClass getChosen() {
		return this.chosen;
	}

	public void setCharName(String name) {
		this.charName = name;
	}

	public Stress getStress() {
		return stress;
	}

	public int getItemLevel() {
		return itemLevel;
	}

}
