package character_aspects;
/* Ability class for handling individual aspects of character's abilities,
 *  such as Damage, and HP, which are affected by percentage increases */
public class AbilityMulti extends Ability {
	public AbilityMulti(String name, float val, float lcap, float hcap) {
		super(name, val, lcap, hcap);
	}

	public float getFinal() {
		float test = this.getBaseValue() * (100 + this.getModifier()) / 100;
		
		if(test < this.getLowCap()) {
			test = this.getLowCap();
		} else if (test > this.getHighCap()) {
			test = this.getHighCap();
		}
		
		return test;
	}
}