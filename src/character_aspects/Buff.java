package character_aspects;

public class Buff extends Condition {
	@SuppressWarnings("unused")
	private BuffType type;

	public Buff(String name, BuffType type, int dur, float sn, float mv, float bt, float bd, float ds, float df,
			float db, float tp, float hp, float dg, float da, float sp, float ac, float cr, float pt, float st) {
		super(name, type, dur, sn, mv, bt, bd, ds, df, db, tp, hp, dg, da, sp, ac, cr, pt, st);
		this.duration = dur;
		this.type = type;
	}

}
