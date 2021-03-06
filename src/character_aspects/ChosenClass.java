package character_aspects;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import setup.Armor;
import setup.PlayerClass;
import setup.Weapon;

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
	private ArrayList<Condition> conditions;

	public ChosenClass() {
		this.resistances = new ArrayList<Ability>();
		this.statistics = new ArrayList<Ability>();
		this.conditions = new ArrayList<Condition>();
		this.xp = 0;
		this.level = 0;
		this.setUpArrays();
	}

	public void assignClass(PlayerClass pclass) {
		this.chosen = pclass;
		this.stress = new Stress(this);

		this.alterAbility("res", "STUN", this.chosen.getResistances().get(0).getValue());
		this.alterAbility("res", "MOVE", this.chosen.getResistances().get(1).getValue());
		this.alterAbility("res", "BLEED", this.chosen.getResistances().get(2).getValue());
		this.alterAbility("res", "BLIGHT", this.chosen.getResistances().get(3).getValue());
		this.alterAbility("res", "DISEASE", this.chosen.getResistances().get(4).getValue());
		this.alterAbility("res", "DEBUFF", this.chosen.getResistances().get(5).getValue());
		this.alterAbility("res", "DEATH BLOW", this.chosen.getResistances().get(6).getValue());
		this.alterAbility("res", "TRAP", this.chosen.getResistances().get(7).getValue());

		this.alterAbility("stat", "TRAP DISARM", this.chosen.getTrapDisarm());
	}

	public void setXP(int xp) {
		this.xp = xp;
		this.checkXP("Radiant");
	}

	private void checkXP(String difficulty) {
		if (!difficulty.equals("Radiant") && !difficulty.equals("Darkest")) {
			System.out.println("Incorrect difficulty type");
			System.exit(0);
		}
		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("XPData.json"));

			JSONObject difficultyData = (JSONObject) jsonObject.get("Difficulty");
			JSONArray currDiff = (JSONArray) difficultyData.get(difficulty);

			int lvl = 0;
			for (Object o : currDiff) {
				int xpReq = ((Long) ((JSONObject) o).get("xp required")).intValue();
				if ((this.xp - xpReq) >= 0) {
					lvl = ((Long) ((JSONObject) o).get("Level")).intValue();
				} else {
					break;
				}
			}
			this.changeLevel(lvl);
		} catch (Exception ex) {
			ex.printStackTrace();
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
		float improvement = this.level * 10;

		this.alterAbility("res", "STUN", this.chosen.getResistances().get(0).getValue() + improvement);
		this.alterAbility("res", "MOVE", this.chosen.getResistances().get(1).getValue() + improvement);
		this.alterAbility("res", "BLIGHT", this.chosen.getResistances().get(2).getValue() + improvement);
		this.alterAbility("res", "BLEED", this.chosen.getResistances().get(3).getValue() + improvement);
		this.alterAbility("res", "DEBUFF", this.chosen.getResistances().get(5).getValue() + improvement);
		this.alterAbility("res", "TRAP", this.chosen.getResistances().get(7).getValue() + improvement);

		this.alterAbility("stat", "TRAP DISARM", this.chosen.getTrapDisarm() + improvement);
	}

	private void getTitle() {
		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("TitleData.json"));
			JSONArray titles = (JSONArray) jsonObject.get("Titles");

			for (Object o : titles) {
				int lvl = ((Long) ((JSONObject) o).get("Level")).intValue();
				if (this.level == lvl) {
					this.title = (String) ((JSONObject) o).get("Title");
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
		this.alterAbility("stat", "MAX HP", this.chosenArmor.getHp());
		this.alterAbility("stat", "CURR HP", this.chosenArmor.getHp());
	}

	public void chooseWeapon(int index) {
		for (int i = 0; i < this.chosen.getClassWeapons().getWeapons().size(); i++) {
			if (index == this.chosen.getClassWeapons().getWeapons().get(i).getLevel()) {
				this.chosenWeapon = this.chosen.getClassWeapons().getWeapons().get(i);
				break;
			}
		}
		this.alterAbility("stat", "MIN DAM", this.chosenWeapon.getMinDam());
		this.alterAbility("stat", "MAX DAM", this.chosenWeapon.getMaxDam());
		this.alterAbility("stat", "CRIT", this.chosenWeapon.getCrit());
		this.alterAbility("stat", "SPEED", this.chosenWeapon.getSpeed());
	}

	private void setUpArrays() {
		this.resistances.add(new Ability("STUN", 0, 0, 200));
		this.resistances.add(new Ability("MOVE", 0, 0, 200));
		this.resistances.add(new Ability("BLIGHT", 0, 0, 200));
		this.resistances.add(new Ability("BLEED", 0, 0, 200));
		this.resistances.add(new Ability("DISEASE", 0, 0, 200));
		this.resistances.add(new Ability("DEBUFF", 0, 0, 200));
		this.resistances.add(new Ability("DEATH BLOW", 0, 0, 87));
		this.resistances.add(new Ability("TRAP", 0, 0, 200));

		this.statistics.add(new Ability("DODGE", 0, 0, 150));
		this.statistics.add(new AbilityMulti("MAX HP", 0, 0, 1000));
		this.statistics.add(new AbilityMulti("CURR HP", 0, 0, 1000));
		this.statistics.add(new AbilityMulti("MIN DAM", 0, 0, 200));
		this.statistics.add(new AbilityMulti("MAX DAM", 0, 0, 200));
		this.statistics.add(new Ability("CRIT", 0, 0, 100));
		this.statistics.add(new Ability("SPEED", 0, 0, 50));
		this.statistics.add(new Ability("TRAP DISARM", 0, 0, 135));
		this.statistics.add(new Ability("STRESS RESIST", 100, 0, 200));
		this.statistics.add(new Ability("VIRTUE CHANCE", 25, 1, 95));
		this.statistics.add(new Ability("ACCURACY", 0, 0, 200));
		this.statistics.add(new Ability("PROTECTION", 0, 0, 80));
	}

	private void alterAbility(String array, String cat, float val) {
		if (array.equals("res")) {
			for (Ability res : this.resistances) {
				if (res.getName().equals(cat)) {
					res.setBaseValue(val);
				}
			}
		} else if (array.equals("stat")) {
			for (Ability res : this.statistics) {
				if (res.getName().equals(cat)) {
					res.setBaseValue(val);
				}
			}
		}
	}

	private void alterStatistics(float dam, float crit, float speed, float dodge, float hp, float acc, float prot,
			float stress) {
		this.alterAbilityMod("stat", "MIN DAM", dam);
		this.alterAbilityMod("stat", "MAX DAM", dam);
		this.alterAbilityMod("stat", "CRIT", crit);
		this.alterAbilityMod("stat", "SPEED", speed);
		this.alterAbilityMod("stat", "DODGE", dodge);
		this.alterAbilityMod("stat", "MAX HP", hp);
		this.alterAbilityMod("stat", "CURR HP", hp);
		this.alterAbilityMod("stat", "ACCURACY", acc);
		this.alterAbilityMod("stat", "PROTECTION", prot);
		this.alterAbilityMod("stat", "STRESS RESIST", stress);
	}

	private void alterResistances(float stun, float move, float blight, float bleed, float disease, float debuff,
			float death, float trap) {
		this.alterAbilityMod("res", "STUN", stun);
		this.alterAbilityMod("res", "MOVE", move);
		this.alterAbilityMod("res", "BLIGHT", blight);
		this.alterAbilityMod("res", "BLEED", bleed);
		this.alterAbilityMod("res", "DISEASE", disease);
		this.alterAbilityMod("res", "DEBUFF", debuff);
		this.alterAbilityMod("res", "DEATH BlOW", death);
		this.alterAbilityMod("res", "TRAP", trap);

		this.alterAbilityMod("stat", "TRAP DISARM", trap);
	}

	private void alterAbilityMod(String array, String cat, float val) {
		if (array.equals("res")) {
			for (Ability res : this.resistances) {
				if (res.getName().equals(cat)) {
					res.setModifier(val);
				}
			}
		} else if (array.equals("stat")) {
			for (Ability res : this.statistics) {
				if (res.getName().equals(cat)) {
					res.setModifier(val);
				}
			}
		}
	}

	public float getAbility(String array, String cat) {
		if (array.equals("res")) {
			for (Ability res : this.resistances) {
				if (res.getName().equals(cat)) {
					return res.getFinal();
				}
			}
		} else if (array.equals("stat")) {
			for (Ability res : this.statistics) {
				if (res.getName().equals(cat)) {
					return res.getFinal();
				}
			}
		}
		return -1;
	}

	public void addCondition(Condition con) {
		this.conditions.add(con);
		this.updateModifiers();
	}

	public void removeCondition(Condition con) {
		this.conditions.remove(con);
		this.updateModifiers();
	}

	private void updateModifiers() {
		float stunmod = 0;
		float moveMod = 0;
		float blightMod = 0;
		float bleedMod = 0;
		float diseaseMod = 0;
		float debuffMod = 0;
		float deathMod = 0;
		float trapMod = 0;

		float hpMod = 0;
		float dodgeMod = 0;
		float damMod = 0;
		float speedMod = 0;
		float accMod = 0;
		float critMod = 0;
		float protMod = 0;
		float stressMod = 0;

		for (int i = 0; i < this.conditions.size(); i++) {
			stunmod += this.conditions.get(i).getStunMod();
			moveMod += this.conditions.get(i).getMoveMod();
			blightMod += this.conditions.get(i).getBlightMod();
			bleedMod += this.conditions.get(i).getBleedMod();
			diseaseMod += this.conditions.get(i).getDiseaseMod();
			debuffMod += this.conditions.get(i).getDebuffMod();
			deathMod += this.conditions.get(i).getDeathMod();
			trapMod += this.conditions.get(i).getTrapMod();

			hpMod += this.conditions.get(i).getHpMod();
			dodgeMod += this.conditions.get(i).getDodgeMod();
			damMod += this.conditions.get(i).getDamMod();
			speedMod += this.conditions.get(i).getSpeedMod();
			accMod += this.conditions.get(i).getAccMod();
			critMod += this.conditions.get(i).getCritMod();
			protMod += this.conditions.get(i).getProtMod();
			stressMod += this.conditions.get(i).getStressMod();
		}
		this.alterResistances(stunmod, moveMod, blightMod, bleedMod, diseaseMod, debuffMod, deathMod, trapMod);
		this.alterStatistics(damMod, critMod, speedMod, dodgeMod, hpMod, accMod, protMod, stressMod);
	}

	private void triggerConditions() {
		Condition cur;
		ArrayList<Integer> remove = new ArrayList<Integer>();
		for (int i = 0; i < this.conditions.size(); i++) {
			cur = this.conditions.get(i);
			cur.trigger();
			if (cur.getDuration() == 0) {
				remove.add(i);
			}
		}
		if (remove.size() > 0) {
			this.removeConditions(remove);
		}
	}

	private void removeConditions(ArrayList<Integer> remove) {
		for (int i = (remove.size() - 1); i == 0; i--) {
			this.conditions.remove(remove.get(i));
		}

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
		System.out.println("STATS:");
		for (int i = 0; i < this.statistics.size() / 2; i++) {
			statistics.get(i).display();
			System.out.print("   ");
		}
		System.out.println();
		for (int i = this.statistics.size() / 2; i < this.statistics.size(); i++) {
			statistics.get(i).display();
			System.out.print("   ");
		}
		System.out.println();
		System.out.println("RESTISTANCES:");
		for (int i = 0; i < this.resistances.size() / 2; i++) {
			resistances.get(i).display();
			System.out.print("   ");
		}
		System.out.println();
		for (int i = this.resistances.size() / 2; i < this.resistances.size(); i++) {
			resistances.get(i).display();
			System.out.print("   ");
		}
		System.out.println();

		this.chosen.display();
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