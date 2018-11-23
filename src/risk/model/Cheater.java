package risk.model;

import java.util.List;

public class Cheater implements Strategy {


    @Override
    public String toString() {
        return "Cheater";
    }

    @Override
    public void reinforcement(Node node) {

    }

    @Override
    public void fortification(Node from, Node to, Integer armies) {

    }

    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
        return false;
    }

	@Override
	public List<Integer> Defend() {
		// TODO Auto-generated method stub
		return null;
	}
}
