package character_aspects;

public class DoTEffect extends Condition {
	@SuppressWarnings("unused")
	private DOTType type; // Bleed, Blight, or Horror
	private float effectPerRound;

	public DoTEffect(String name, DOTType type, int dur, float sn, float mv, float bt, float bd, float ds, float df,
			float db, float tp, float hp, float dg, float da, float sp, float ac, float cr, float pt, float st,
			float epr) {
		super(name, type, dur, sn, mv, bt, bd, ds, df, db, tp, hp, dg, da, sp, ac, cr, pt, st);
		this.type = type;
		this.effectPerRound = epr;
		this.duration = dur;
	}

	public float getEffectPerRound() {
		return effectPerRound;
	}
}
