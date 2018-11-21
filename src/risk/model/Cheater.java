package risk.model;

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
    public boolean attack(Node attacker, Node defender, Integer attackerdice, Integer defenderdice) {
        return false;
    }
}
