package character_aspects;

public abstract class Condition {
	protected String name;
	
	protected float stunMod;
	protected float moveMod;
	protected float blightMod;
	protected float bleedMod;
	protected float diseaseMod;
	protected float debuffMod;
	protected float deathMod;
	protected float trapMod;
	
	protected float hpMod;
	protected float dodgeMod;
	protected float damMod;
	protected float speedMod;
	protected float accMod;
	protected float critMod;
	protected float protMod;
	protected float stressMod;
	
	public Condition(String name, float sn, float mv, float bt, float bd, float ds, float df, float db,
			float tp, float hp, float dg, float da, float sp, float ac, float cr, float pt, float st) {
		this.name = name;
		this.stunMod = sn;
		this.moveMod = mv;
		this.blightMod = bt;
		this.bleedMod = bd;
		this.diseaseMod = ds;
		this.debuffMod = df;
		this.deathMod = db;
		this.trapMod = tp;
		
		this.hpMod = hp;
		this.dodgeMod = dg;
		this.damMod = da;
		this.speedMod = sp;
		this.accMod = ac;
		this.critMod = cr;
		this.protMod = pt;
		this.stressMod = st;
	}
	
	public String getName() {
		return name;
	}

	public float getStunMod() {
		return stunMod;
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

	public float getProtMod() {
		return protMod;
	}

	public float getStressMod() {
		return stressMod;
	}

}
