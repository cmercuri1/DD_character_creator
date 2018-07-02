
public class Resistance {
	private String name;
	private int value;

	public Resistance(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public void display() {
		System.out.print(name + " Resist: " + value);
	}

	public int getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
}
