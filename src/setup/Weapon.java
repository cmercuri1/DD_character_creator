package setup;

public class Weapon {

	private String name;
	private int level;
	private float minDam;
	private float maxDam;
	private float crit;
	private float speed;

	public Weapon(String nm, int level, float min, float max, float crit, float speed) {
		this.name = nm;
		this.level = level;
		this.minDam = min;
		this.maxDam = max;
		this.crit = crit;
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public float getMinDam() {
		return minDam;
	}

	public float getMaxDam() {
		return maxDam;
	}

	public float getCrit() {
		return crit;
	}

	public float getSpeed() {
		return speed;
	}

	public void display() {
		System.out.println(name);
	}

}
