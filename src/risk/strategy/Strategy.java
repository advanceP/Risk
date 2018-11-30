package risk.strategy;

import risk.model.Node;

import java.io.Serializable;
import java.util.List;

public interface Strategy {
	/**
	 * strategy reinforcement
	 * @param node if it's computer ,pass null
	 */
	public void reinforcement(Node node);

	/**
	 *
	 * @param from  the country gonna fortify,null if it's a computer
	 * @param to  the country gonna  receive fortify,null if it's a computer
	 * @param armies the army gonna fortify,null if it's a computer
	 */
	public void fortification(Node from, Node to, Integer armies);

	/**
	 * attack move
	 * @param attacker the attack node
	 * @param defender the defend node
	 * @param attackerDiceList the number of dice and the number of each roll
	 * @param defenderDiceList the number of dice and the number of each roll
	 * @return is this attack win the whole game or not
	 */
	public boolean attack(Node attacker, Node defender, List<Integer> attackerDiceList, List<Integer> defenderDiceList);

    /**
     * @param integers attacker's number of dices
	 * @param Defender the defender
     * @return defender's dice
     */
	public List<Integer> Defend(Integer integers,Node Defender);
}
