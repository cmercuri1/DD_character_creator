package character_aspects;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/* Stress Class for handling character's stress as well as choosing and implementing any afflictions/virtues */
public class Stress {
	private int totalStress;

	private static int stressThreshold1 = 100;
	private static int stressThreshold2 = 200;

	private AffVirt current;

	private ArrayList<AffVirt> afflictions;
	private ArrayList<AffVirt> virtues;

	private ChosenClass chosenClass;

	public Stress(ChosenClass cclass) {
		this.totalStress = 0;
		this.current = null;
		this.chosenClass = cclass;

		this.setUpAffsVirts(cclass.getChosen().getName());
	}

	private void setUpAffsVirts(String playClass) {
		JSONParser parser = new JSONParser();

		try {
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("Affliction&VirtueData.json"));

			JSONArray afflictionData;
			System.out.println(playClass);
			if (playClass.equals("Flagellant")) {
				afflictionData = (JSONArray) ((JSONObject) jsonObject.get("Afflictions")).get("Flagellant");
			} else {
				afflictionData = (JSONArray) ((JSONObject) jsonObject.get("Afflictions")).get("Others");
			}
			JSONArray virtueData = (JSONArray) ((JSONObject) jsonObject.get("Virtues")).get("All");

			this.setUpAfflictions(afflictionData);
			this.setUpVirtues(virtueData);
		} catch (Exception ex) {

		}

	}

	private void setUpAfflictions(JSONArray afflictionData) {
		this.afflictions = new ArrayList<AffVirt>();

		for (Object o : afflictionData) {
			JSONObject aff = (JSONObject) o;

			String name = (String) aff.get("name");

			float sn = getFloatValue(aff, "STUN MOD");
			float mv = getFloatValue(aff, "MOVE MOD");
			float bt = getFloatValue(aff, "BLIGHT MOD");
			float bd = getFloatValue(aff, "BLEED MOD");
			float ds = getFloatValue(aff, "DISEASE MOD");
			float df = getFloatValue(aff, "DEBUFF MOD");
			float db = getFloatValue(aff, "DEATH BLOW MOD");
			float tp = getFloatValue(aff, "TRAP MOD");

			float hp = getFloatValue(aff, "HP MOD");
			float dg = getFloatValue(aff, "DODGE MOD");
			float da = getFloatValue(aff, "DAMAGE MOD");
			float sp = getFloatValue(aff, "SPEED MOD");
			float ac = getFloatValue(aff, "ACC MOD");
			float cr = getFloatValue(aff, "CRIT MOD");
			float pt = getFloatValue(aff, "PROT MOD");
			float st = getFloatValue(aff, "STRESS MOD");

			this.afflictions
					.add(new AffVirt(name, "aff", sn, mv, bt, bd, ds, df, db, tp, hp, dg, da, sp, ac, cr, pt, st));
		}
	}

	private void setUpVirtues(JSONArray virtueData) {
		this.virtues = new ArrayList<AffVirt>();

		for (Object o : virtueData) {
			JSONObject virt = (JSONObject) o;

			String name = (String) virt.get("name");

			float sn = getFloatValue(virt, "STUN MOD");
			float mv = getFloatValue(virt, "MOVE MOD");
			float bt = getFloatValue(virt, "BLIGHT MOD");
			float bd = getFloatValue(virt, "BLEED MOD");
			float ds = getFloatValue(virt, "DISEASE MOD");
			float df = getFloatValue(virt, "DEBUFF MOD");
			float db = getFloatValue(virt, "DEATH BLOW MOD");
			float tp = getFloatValue(virt, "TRAP MOD");

			float hp = getFloatValue(virt, "HP MOD");
			float dg = getFloatValue(virt, "DODGE MOD");
			float da = getFloatValue(virt, "DAMAGE MOD");
			float sp = getFloatValue(virt, "SPEED MOD");
			float ac = getFloatValue(virt, "ACC MOD");
			float cr = getFloatValue(virt, "CRIT MOD");
			float pt = getFloatValue(virt, "PROT MOD");
			float st = getFloatValue(virt, "STRESS MOD");

			this.virtues
					.add(new AffVirt(name, "virt", sn, mv, bt, bd, ds, df, db, tp, hp, dg, da, sp, ac, cr, pt, st));
		}
	}

	public float getFloatValue(JSONObject o, String key) {
		if (o.get(key) != null) {
			return (((Long) o.get(key)).floatValue());
		} else {
			return 0;
		}
	}

	public void addStress(int val) {
		this.totalStress += val * (200 - this.chosenClass.getAbility("stat", "STRESS RESIST")) / 100;

		this.stressCheck();
		this.heartAttack();
	}

	private void stressCheck() {
		if ((this.totalStress >= stressThreshold1) && (this.current == null)) {
			this.totalStress = stressThreshold1;

			Random rand = new Random();

			if ((rand.nextInt(100) + 1) > this.chosenClass.getAbility("stat", "VIRTUE CHANCE")) {
				this.current = this.afflictions.get(rand.nextInt(this.afflictions.size()));
			} else {
				this.current = this.virtues.get(rand.nextInt(this.virtues.size()));
				this.stressHeal(60);
			}
			this.applyAffVirt();
		}
	}

	private void heartAttack() {
		if (this.totalStress >= stressThreshold2) {
			this.totalStress = stressThreshold2;
			if (this.current.getType().equals("aff")) {
				this.stressHeal(40);
			} else {
				this.totalStress = 0;
				this.removeVirtue();
			}
		}
	}

	public void stressHeal(int val) {
		this.totalStress -= val;

		if (this.totalStress <= 0) {
			this.totalStress = 0;
			if (this.current != null && this.current.getType().equals("aff")) {
				this.removeAffliction();
			}
		}
	}

	private void applyAffVirt() {
		AffVirt c = this.current;
		this.chosenClass.alterResistances(c.getStunmod(), c.getMoveMod(), c.getBlightMod(), c.getBleedMod(),
				c.getDiseaseMod(), c.getDebuffMod(), c.getDeathMod(), c.getTrapMod());
		this.chosenClass.alterStatistics(c.getDamMod(), c.getCritMod(), c.getSpeedMod(), c.getDodgeMod(), c.getHpMod(),
				c.getAccMod(), c.getProtMod(), c.getStressMod());
	}

	private void removeVirtue() {
		AffVirt c = this.current;
		this.chosenClass.alterResistances(-c.getStunmod(), -c.getMoveMod(), -c.getBlightMod(), -c.getBleedMod(),
				-c.getDiseaseMod(), -c.getDebuffMod(), -c.getDeathMod(), -c.getTrapMod());
		this.chosenClass.alterStatistics(-c.getDamMod(), -c.getCritMod(), -c.getSpeedMod(), -c.getDodgeMod(),
				-c.getHpMod(), -c.getAccMod(), -c.getProtMod(), -c.getStressMod());
		this.current = null;
	}

	private void removeAffliction() {
		AffVirt c = this.current;
		this.chosenClass.alterResistances(-c.getStunmod(), -c.getMoveMod(), -c.getBlightMod(), -c.getBleedMod(),
				-c.getDiseaseMod(), -c.getDebuffMod(), -c.getDeathMod(), -c.getTrapMod());
		this.chosenClass.alterStatistics(-c.getDamMod(), -c.getCritMod(), -c.getSpeedMod(), -c.getDodgeMod(),
				-c.getHpMod(), -c.getAccMod(), -c.getProtMod(), -c.getStressMod());
		this.current = null;
	}

	public void display() {
		System.out.println("Current Stress: " + this.totalStress + "/" + stressThreshold2);
		if (!(this.current == null)) {
			System.out.print("Current ");
			if (this.current.getType().equals("virt")) {
				System.out.println("Virtue: " + this.current.getName());
			} else {
				System.out.println("Affliction: " + this.current.getName());
			}
		}
	}
}
