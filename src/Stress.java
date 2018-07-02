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
	private int virtueChance;

	private static int stressThreshold1 = 100;
	private static int stressThreshold2 = 200;

	private AffVirt current;

	private ArrayList<AffVirt> afflictions;
	private ArrayList<AffVirt> virtues;
	
	private ChosenClass chosenClass;

	public Stress(String playClass, ChosenClass cclass) {
		this.totalStress = 0;
		this.current = null;
		this.chosenClass = cclass;
		
		this.setUpAffsVirts(playClass);
		this.setVirtue(virtueTotal);
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
				String effect = linebreak[2];

				this.afflictions.add(new AffVirt(name, type, effect));
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
				String effect = linebreak[2];

				this.virtues.add(new AffVirt(name, type, effect));
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

	public void addStress(int val) {
		this.totalStress += val;

		this.stressCheck();
		this.heartAttack();
	}

	private void stressCheck() {
		if ((this.totalStress >= stressThreshold1) && (this.current == null)) {
			this.totalStress = 100;

			Random rand = new Random();

			if ((rand.nextInt(100) + 1) > this.virtueChance) {
				this.current = this.afflictions.get(rand.nextInt(this.afflictions.size()));
			} else {
				this.current = this.virtues.get(rand.nextInt(this.virtues.size()));
				this.stressHeal(60);
			}
		}
	}

	private void heartAttack() {
		if (this.totalStress >= stressThreshold2) {
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

		if (this.totalStress < 0) {
			this.totalStress = 0;
			if (this.current != null && this.current.getType().equals("aff")) {
				this.removeAffliction();
			}
		}
	}
	
	private void removeVirtue() {
		this.current = null;
	}

	private void removeAffliction() {
		this.current = null;
	}

	public void setVirtue(int virtueTotal) {
		this.virtueChance = virtueTotal;

		if (this.virtueChance > 95) {
			this.virtueChance = 95;
		} else if (this.virtueChance < 1) {
			this.virtueChance = 1;
		}
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
