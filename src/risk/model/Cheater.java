package risk.model;

public class Cheater implements Strategy {
    @Override
    public void reinforcement(Node reinforcementNode) {
        System.out.println("cheater");
    }

    @Override
    public void fortification() {

    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "Cheater";
    }
}
