package risk.model;

public class Benevolent implements Strategy {
    @Override
    public void reinforcement(Node reinforcementNode) {
        System.out.println("Benevolent");
    }

    @Override
    public void fortification() {

    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Benevolent";
    }
}
