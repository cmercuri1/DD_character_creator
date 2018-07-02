/* Ability class for handling individual aspects of character's abilities,
 *  such as Dmaage, and HP, which are affected by percentage increases */
public class AbilityMulti extends Ability {
	public AbilityMulti(String name, float val) {
		super(name, val);
	}

	public float getFinal() {
		return this.getBaseValue() * (100 + this.getModifier()) / 100;
	}
}