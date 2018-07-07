package setup;

/* Armor class for holding information about armors */
public class Armor {

	private String name;
	private int level;
	private float hp;
	private float dodge;

	public Armor(String nme, int level, float hp, float dodge) {
		this.name = nme;
		this.level = level;
		this.hp = hp;
		this.dodge = dodge;
	}

	/**
	 * @return this.name - a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return this.level - an int
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return this.hp - a float
	 */
	public float getHp() {
		return hp;
	}

	/**
	 * @return this.dodge - a float
	 */
	public float getDodge() {
		return dodge;
	}

	/**
	 * Prints out the name of this armor (this.name)
	 */
	public void display() {
		System.out.println(name);
	}

}
