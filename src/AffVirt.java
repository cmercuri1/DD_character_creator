/* AffVirt Class for holding information about Afflictions and Virtues */
public class AffVirt {
	private String name;
	private String type;
	
	private float stunmod;
	private float moveMod;
	private float blightMod;
	private float bleedMod;
	private float diseaseMod;
	private float debuffMod;
	private float deathMod;
	private float trapMod;
	private float hpMod;
	private float dodgeMod;
	private float damMod;
	private float speedMod;
	private float accMod;
	private float critMod;

	public AffVirt(String name, String type, float sn, float mv, float bt, float bd, float ds, float df, float db,
			float tp, float hp, float dg, float da, float sp, float ac, float cr) {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public float getStunmod() {
		return stunmod;
	}

	public float getMoveMod() {
		return moveMod;
	}

	public float getBlightMod() {
		return blightMod;
	}

	public float getBleedMod() {
		return bleedMod;
	}

	public float getDiseaseMod() {
		return diseaseMod;
	}

	public float getDebuffMod() {
		return debuffMod;
	}

	public float getDeathMod() {
		return deathMod;
	}

	public float getTrapMod() {
		return trapMod;
	}

	public float getHpMod() {
		return hpMod;
	}

	public float getDodgeMod() {
		return dodgeMod;
	}

	public float getDamMod() {
		return damMod;
	}

	public float getSpeedMod() {
		return speedMod;
	}

	public float getAccMod() {
		return accMod;
	}

	public float getCritMod() {
		return critMod;
	}
}
