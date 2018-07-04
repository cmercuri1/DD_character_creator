package setup;

import java.io.FileReader;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class PlayerClass {
	private String name;

	private ArrayList<Resistance> resistances = new ArrayList<Resistance>();

	private ClassArmors classArmors;
	private ClassWeapons classWeapons;

	private int trapDisarm;

	private int fMove;
	private int bMove;
	private String critBuff;
	private String isReligious;
	private String provisions;

	public PlayerClass(JSONObject o) {
		this.getStats(o);

		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("EquipmentData.json"));

			JSONObject armorData = (JSONObject) jsonObject.get("Armor Data");
			JSONObject weaponData = (JSONObject) jsonObject.get("Weapon Data");

			this.getArmors(armorData);
			this.getWeapons(weaponData);
		} catch (Exception ex) {

		}

	}

	private void getStats(JSONObject o) {
		this.name = (String) o.get("name");

		this.resistances.add(new Resistance("Stun", ((Long) o.get("StunResist")).intValue()));
		this.resistances.add(new Resistance("Move", ((Long) o.get("MoveResist")).intValue()));
		this.resistances.add(new Resistance("Blight", ((Long) o.get("BlightResist")).intValue()));
		this.resistances.add(new Resistance("Bleed", ((Long) o.get("BleedResist")).intValue()));
		this.resistances.add(new Resistance("Disease", ((Long) o.get("DiseaseResist")).intValue()));
		this.resistances.add(new Resistance("Debuff", ((Long) o.get("DebuffResist")).intValue()));
		this.resistances.add(new Resistance("Death Blow", ((Long) o.get("DeathBlowResist")).intValue()));
		this.resistances.add(new Resistance("Trap", ((Long) o.get("TrapResist")).intValue()));

		this.trapDisarm = ((Long) o.get("TrapDisarm")).intValue();

		this.fMove = ((Long) o.get("ForwardMove")).intValue();
		this.bMove = ((Long) o.get("BackwardMove")).intValue();
		this.critBuff = (String) o.get("CritBuff");
		this.isReligious = (String) o.get("Religious");
		this.provisions = (String) o.get("Provisions");
	}

	private void getArmors(JSONObject armorData) {
		this.classArmors = new ClassArmors();

		try {
			JSONArray classArmors = (JSONArray) armorData.get(this.name);

			for (Object o : classArmors) {
				JSONObject ca = (JSONObject) o;
				try {
					this.classArmors.addArmor((String) ca.get("ArmorName"), ((Long) ca.get("Level")).intValue(),
							((Long) ca.get("HP")).floatValue(), ((Double) ca.get("DODGE")).floatValue());
				} catch (ClassCastException e) {
					this.classArmors.addArmor((String) ca.get("ArmorName"), ((Long) ca.get("Level")).intValue(),
							((Long) ca.get("HP")).floatValue(), ((Long) ca.get("DODGE")).floatValue());
				}
			}
		} catch (Exception ex) {
		}
	}

	private void getWeapons(JSONObject weaponData) {
		this.classWeapons = new ClassWeapons();

		try {
			JSONArray classWeapons = (JSONArray) weaponData.get(this.name);

			for (Object o : classWeapons) {
				JSONObject ca = (JSONObject) o;

				this.classWeapons.addWeapon((String) ca.get("Name"), ((Long) ca.get("Level")).intValue(),
						((Long) ca.get("MinDamage")).floatValue(), ((Long) ca.get("MaxDamage")).floatValue(),
						((Long) ca.get("Crit")).floatValue(), ((Long) ca.get("Speed")).floatValue());

			}
		} catch (Exception ex) {
		}
	}

	public void display() {
		System.out.println("Movement: " + this.fMove + " forward, " + this.bMove + " backward.");
		System.out.println("Buff Recieved on Crit: " + this.critBuff);
		System.out.println("Religious: " + this.isReligious);
		System.out.println("Provision: " + this.provisions);
	}

	public String getName() {
		return name;
	}

	public ArrayList<Resistance> getResistances() {
		return resistances;
	}

	public int getTrapDisarm() {
		return trapDisarm;
	}

	public int getfMove() {
		return fMove;
	}

	public int getbMove() {
		return bMove;
	}

	public String getCritBuff() {
		return critBuff;
	}

	public String getIsReligious() {
		return isReligious;
	}

	public String getProvisions() {
		return provisions;
	}

	public ClassArmors getClassArmors() {
		return classArmors;
	}

	public ClassWeapons getClassWeapons() {
		return classWeapons;
	}

}
