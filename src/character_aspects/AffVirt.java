package character_aspects;
/* AffVirt Class for holding information about Afflictions and Virtues */
public class AffVirt extends Condition {
	private String type;

	public AffVirt(String name, String type, float sn, float mv, float bt, float bd, float ds, float df, float db,
			float tp, float hp, float dg, float da, float sp, float ac, float cr, float pt, float st) {
		super(name, sn, mv, bt, bd, ds, df, db, tp, hp, dg, da, sp, ac, cr, pt, st);
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
