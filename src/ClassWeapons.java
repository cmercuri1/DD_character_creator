import java.util.ArrayList;

public class ClassWeapons {

	private ArrayList<Weapon> weapons;

	public ClassWeapons() {
		this.weapons = new ArrayList<Weapon>();
	}

	public void addWeapon(String nm, int lvl, float min, float max, float crt, float spd) {
		weapons.add(new Weapon(nm, lvl, min, max, crt, spd));
	}

	public void display() {
		for (int i = 0; i < weapons.size(); i++) {
			weapons.get(i).display();
		}

	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}

}
