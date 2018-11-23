package risk.model;

import java.util.List;

public class RandomPlayer implements Strategy{


    @Override
    public String toString() {
        return "RandomPlayer";
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
}
