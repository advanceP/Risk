package risk.model;

public class Benevolent implements Strategy {


    @Override
    public String toString() {
        return "Benevolent";
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
