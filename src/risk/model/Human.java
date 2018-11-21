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
	public boolean attack(Node attacker, Node defender, Integer attackerdice, Integer defenderdice) {
		Player player= GameDriverController.getGameDriverInstance().getCurrentPlayer();
		List<List<Integer>> resultList = new ArrayList<>();
		List<Integer> attackerList = new ArrayList<>();
		List<Integer> defenderList = new ArrayList<>();
		resultList.add(attackerList);
		resultList.add(defenderList);
		Random random = new Random();
		for (int i = 0; i < attackerdice; i++) {
			attackerList.add(random.nextInt(6) + 1);
		}
		for (int j = 0; j < defenderdice; j++) {
			defenderList.add(random.nextInt(6) + 1);
		}
		if (attackerList.size() > 1) {
			Collections.sort(attackerList, Collections.reverseOrder());
		}
		if (defenderList.size() > 1) {
			Collections.sort(defenderList, Collections.reverseOrder());
		}

		attackerList = resultList.get(0);
		defenderList = resultList.get(1);
		if (attackerList.isEmpty() || defenderList.isEmpty()) {
			return false;
		}
		for (int i = 0; i < attackerList.size() && i < defenderList.size() && i < 2; i++) {
			if (attackerList.get(i) > defenderList.get(i)) {
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

}
