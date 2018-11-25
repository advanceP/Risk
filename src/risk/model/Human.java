package risk.model;

import risk.controller.GameDriverController;

import java.util.*;

public class Human implements Strategy {

	@Override
	public void reinforcement(Node node) {
		node.increaseArmy();
		node.getPlayer().decreaseReinforcement();
	}

	@Override
	public void fortification(Node from, Node to, Integer armies) {
        from.setArmies(from.getArmies() - armies);
        to.setArmies(to.getArmies() + armies);
    }

	@Override
	public boolean attack(Node attacker, Node defender, List<Integer> attackerDiceList, List<Integer> defenderDiceList) {
		Player player= GameDriverController.getGameDriverInstance().getCurrentPlayer();
		List<List<Integer>> resultList = new ArrayList<>();
		resultList.add(attackerDiceList);
		resultList.add(defenderDiceList);
		if (attackerDiceList.size() > 1) {
			Collections.sort(attackerDiceList, Collections.reverseOrder());
		}
		if (defenderDiceList.size() > 1) {
			Collections.sort(defenderDiceList, Collections.reverseOrder());
		}
		if (attackerDiceList.isEmpty() || defenderDiceList.isEmpty()) {
			return false;
		}
		for (int i = 0; i < attackerDiceList.size() && i < defenderDiceList.size() && i < 2; i++) {
			if (attackerDiceList.get(i) > defenderDiceList.get(i)) {
				defender.setArmies(defender.getArmies() - 1);
			} else {
				attacker.setArmies(attacker.getArmies() - 1);
			}
			if (attacker.getArmies() == 1) {
				return false;
			}
			if(defender.getArmies()==0) {
				player.increaseNumberOfCountries();;
                defender.setPlayer(player);
                List<Card> list = Collections.unmodifiableList(Arrays.asList(Card.values()));
                int size = list.size();
                Random rnd = new Random();
                attacker.getPlayer().addCards(list.get(rnd.nextInt(size)));

                if(defender.getPlayer().getNodeList().size()<1)
                {
                    attacker.getPlayer().addCards(defender.getPlayer().getCards());
                    defender.getPlayer().setCards(null);
                }
                return true;
			}
		}

		return false;
	}




	@Override
	public String toString() {
		return "Human";
	}

	@Override
	public List<Integer> Defend(Integer integers) {
		List<Integer>results=new ArrayList<Integer>();
		Random rnd=new Random();
		for(int i=0;i<integers;i++) {
			results.add(rnd.nextInt(6) + 1);
		}
		return results;
	}

}
