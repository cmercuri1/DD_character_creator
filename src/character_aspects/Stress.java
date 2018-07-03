package character_aspects;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

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

		this.setUpAffsVirts(cclass.getClass().getName());
	}

	private void setUpAffsVirts(String playClass) {
		this.setUpAfflictions(playClass);
		this.setUpVirtues();
	}

	private void setUpAfflictions(String playClass) {
		Charset charset = Charset.forName("US-ASCII");
		Path file;

		this.afflictions = new ArrayList<AffVirt>();

		if (playClass.equals("Flagellant")) {
			file = FileSystems.getDefault().getPath("", "FlagellantAfflictions.txt");
		} else {
			file = FileSystems.getDefault().getPath("", "Afflictions.txt");
		}

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			while ((line = reader.readLine()) != null) {
				linebreak = line.split(",");
				String name = linebreak[0];
				String type = linebreak[1];
				float sn = Float.parseFloat(linebreak[2]);
				float mv = Float.parseFloat(linebreak[3]);
				float bt = Float.parseFloat(linebreak[4]);
				float bd = Float.parseFloat(linebreak[5]);
				float ds = Float.parseFloat(linebreak[6]);
				float df = Float.parseFloat(linebreak[7]);
				float db = Float.parseFloat(linebreak[8]);
				float tp = Float.parseFloat(linebreak[9]);
				
				float hp = Float.parseFloat(linebreak[10]);
				float dg = Float.parseFloat(linebreak[11]);
				float da = Float.parseFloat(linebreak[12]);
				float sp = Float.parseFloat(linebreak[13]);
				float ac = Float.parseFloat(linebreak[14]);
				float cr = Float.parseFloat(linebreak[15]);
				float pt = Float.parseFloat(linebreak[16]);
				float st = Float.parseFloat(linebreak[17]);

				this.afflictions.add(new AffVirt(name, type, sn, mv, bt, bd, ds, df, db, tp,
						hp, dg, da, sp, ac, cr, pt, st));
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	private void setUpVirtues() {
		Charset charset = Charset.forName("US-ASCII");
		Path file;

		this.virtues = new ArrayList<AffVirt>();

		file = FileSystems.getDefault().getPath("", "Virtues.txt");

		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			String line = null;
			String linebreak[];

			while ((line = reader.readLine()) != null) {
				linebreak = line.split(",");
				String name = linebreak[0];
				String type = linebreak[1];
				float sn = Float.parseFloat(linebreak[2]);
				float mv = Float.parseFloat(linebreak[3]);
				float bt = Float.parseFloat(linebreak[4]);
				float bd = Float.parseFloat(linebreak[5]);
				float ds = Float.parseFloat(linebreak[6]);
				float df = Float.parseFloat(linebreak[7]);
				float db = Float.parseFloat(linebreak[8]);
				float tp = Float.parseFloat(linebreak[9]);
				
				float hp = Float.parseFloat(linebreak[10]);
				float dg = Float.parseFloat(linebreak[11]);
				float da = Float.parseFloat(linebreak[12]);
				float sp = Float.parseFloat(linebreak[13]);
				float ac = Float.parseFloat(linebreak[14]);
				float cr = Float.parseFloat(linebreak[15]);
				float pt = Float.parseFloat(linebreak[16]);
				float st = Float.parseFloat(linebreak[17]);

				this.virtues.add(new AffVirt(name, type, sn, mv, bt, bd, ds, df, db, tp,
						hp, dg, da, sp, ac, cr, pt, st));
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	public void addStress(int val) {
		this.totalStress += val*(200 - this.chosenClass.getAbility("stat", "STRESS RESIST"))/100;

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
		this.chosenClass.alterStatistics(c.getDamMod(), c.getCritMod(), c.getSpeedMod(), c.getDodgeMod(),
				c.getHpMod(), c.getAccMod(), c.getProtMod(), c.getStressMod());
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
