/* AffVirt Class for holding information about Afflictions and Virtues */
public class AffVirt {
	private String name;
	private String type;
	private String effects;

	public AffVirt(String name, String type, String effects) {
		this.name = name;
		this.type = type;
		this.effects = effects;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getEffects() {
		return effects;
	}

}
