package risk.model;

public class Aggressive implements Strategy {
    @Override
    public void reinforcement(Node reinforcementNode) {
        System.out.println("Aggressive");
    }

    @Override
    public void fortification() {

    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Aggressive";
    }
}
