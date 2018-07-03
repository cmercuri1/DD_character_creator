package setup;
import java.util.ArrayList;

public class ClassArmors {

	private ArrayList<Armor> armors;

	public ClassArmors() {
		this.armors = new ArrayList<Armor>();
	}

	public void addArmor(String nm, int lvl, float hp, float ddg) {
		this.armors.add(new Armor(nm, lvl, hp, ddg));
	}

	public void display() {
		for (int i = 0; i < armors.size(); i++) {
			armors.get(i).display();
		}

	}

	public ArrayList<Armor> getArmors() {
		return armors;
	}

}
