package setup;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
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
		this.getArmors();
		this.getWeapons();
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

	private void getArmors() {
		Charset charset = Charset.forName("US-ASCII");
		Path file = FileSystems.getDefault().getPath("", "ArmorData.txt");

		this.classArmors = new ClassArmors();

		String nm;
		int lvl;
		float hp;
		float ddg;

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			while ((line = reader.readLine()) != null) {

				linebreak = line.split(",");
				if (linebreak[0].equals(name)) {
					nm = linebreak[1];
					lvl = Integer.parseInt(linebreak[2]);
					hp = Float.parseFloat(linebreak[3]);
					ddg = Float.parseFloat(linebreak[4]);

					this.classArmors.addArmor(nm, lvl, hp, ddg);
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
	private void stuff() {
		JSONParser parser = new JSONParser();
		
		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("ClassData.json"));
			
			JSONArray classData = (JSONArray) jsonObject.get("Class Data");
			
			for(Object o:classData){
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void getWeapons() {
		Charset charset = Charset.forName("US-ASCII");
		Path file = FileSystems.getDefault().getPath("", "WeaponData.txt");

		this.classWeapons = new ClassWeapons();

		String nm;
		int lvl;
		float min;
		float max;
		float crt;
		float spd;

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			while ((line = reader.readLine()) != null) {

				linebreak = line.split(",");
				if (linebreak[0].equals(name)) {
					nm = linebreak[1];
					lvl = Integer.parseInt(linebreak[2]);
					min = Float.parseFloat(linebreak[3]);
					max = Float.parseFloat(linebreak[4]);
					crt = Float.parseFloat(linebreak[5]);
					spd = Float.parseFloat(linebreak[6]);

					this.classWeapons.addWeapon(nm, lvl, min, max, crt, spd);
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
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
