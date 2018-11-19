package risk.model;

public class RandomPlayer implements Strategy{
    @Override
    public void reinforcement(Node reinforcementNode) {
        System.out.println("Random");
    }

    @Override
    public void fortification() {

    }

    @Override
    public void attack() {

    }

    @Override
    public String toString() {
        return "RandomPlayer";
    }
}
