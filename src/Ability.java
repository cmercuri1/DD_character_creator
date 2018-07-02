/* Ability class for handling individual aspects of character's abilities,
 *  such as Resistances, Critical Chance, Accuracy, etc */
public class Ability {
	private String name;
	private float baseValue;
	private float modifier;
	private final int BASE = 0;

	public Ability(String name, float val) {
		this.name = name;
		this.baseValue = val;
		this.modifier = BASE;
	}
	
	public float getFinal() {
		return baseValue + modifier;
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
}