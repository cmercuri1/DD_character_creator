package character_aspects;

/* AffVirt Class for holding information about Afflictions and Virtues */
public class AffVirt extends Condition {
	@SuppressWarnings("unused")
	private AffVirtType type;

	public AffVirt(String name, AffVirtType type, float sn, float mv, float bt, float bd, float ds, float df, float db,
			float tp, float hp, float dg, float da, float sp, float ac, float cr, float pt, float st) {
		super(name, type, 1, sn, mv, bt, bd, ds, df, db, tp, hp, dg, da, sp, ac, cr, pt, st);
		this.type = type;
	}

	public void trigger() {

	}

}
