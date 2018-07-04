package character_aspects;

/* Ability class for handling individual aspects of character's abilities,
 *  such as Resistances, Critical Chance, Accuracy, etc */
public class Ability {
	private String name;
	private float baseValue;
	private float modifier;
	private final int BASE = 0;

	private float lowCap;
	private float highCap;

	public Ability(String name, float val, float lcap, float hcap) {
		this.name = name;
		this.baseValue = val;
		this.modifier = BASE;
		this.lowCap = lcap;
		this.highCap = hcap;
	}

	public int getFinal() {
		float test = baseValue + modifier;

		if (test < this.lowCap) {
			test = this.lowCap;
		} else if (test > this.highCap) {
			test = this.highCap;
		}

		return ((int) Math.ceil(test / 1.00));
	}

	public float getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(float baseValue) {
		this.baseValue = baseValue;
	}

	public float getModifier() {
		return modifier;
	}

	public void setModifier(float modifier) {
		this.modifier = modifier;
	}

	public void resetModifier() {
		this.modifier = BASE;
	}

	public String getName() {
		return name;
	}

	public float getLowCap() {
		return lowCap;
	}

	public float getHighCap() {
		return highCap;
	}

	public void display() {
		System.out.print(this.name + ": " + this.getFinal());
	}
}