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

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public float getHp() {
		return hp;
	}

	public float getDodge() {
		return dodge;
	}

	public void display() {
		System.out.println(name);
	}

}
