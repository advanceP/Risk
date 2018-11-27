package risk.model;

import java.util.List;

public interface Strategy {
	public void reinforcement(Node node);
	public void fortification(Node from, Node to, Integer armies);
	public boolean attack(Node attacker, Node defender, List<Integer> attackerDiceList, List<Integer> defenderDiceList);

    /**
     *
     * @param integers attacker's number of dices
     * @return defender's dice
     */
	public List<Integer> Defend(Integer integers,Node Defender);
}
