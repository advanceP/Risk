package risk.strategy;

import risk.controller.GameDriverController;
import risk.model.Card;
import risk.model.GameWriter;
import risk.model.Node;
import risk.model.Player;

import javax.swing.*;
import java.util.*;

public class Human implements Strategy {

	@Override
	public void reinforcement(Node node) {

		node.increaseArmy();
		node.getPlayer().decreaseReinforcement();
		GameWriter.getGameWriterInstance().Write("Reinforced Node: " + node.getName() +
				",Number of armies: 1 " + "\n");
	}

	@Override
	public void fortification(Node from, Node to, Integer armies) {

        from.setArmies(from.getArmies() - armies);
        to.setArmies(to.getArmies() + armies);
        GameWriter.getGameWriterInstance().Write(from.getPlayer().getName() + " moved " + armies +
											" army/armies from " + from.getName() + " to " + to.getName());
    }

	@Override
	public boolean attack(Node attacker, Node defender, List<Integer> attackerDiceList, List<Integer> defenderDiceList) {
		Player player= GameDriverController.getGameDriverInstance().getCurrentPlayer();
		GameWriter.getGameWriterInstance().Write(player.getName() + ": Attacking Country: " + attacker.getName() +
				", Defending Country: " + defender.getName() + "");
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
				player.increaseNumberOfCountries();
				defender.getPlayer().decreaseNumberOfCountries();
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
	public List<Integer> Defend(Integer integers,Node defender) {
		
		
		int size=0;
		if(defender.getArmies()>1)
		{
			size=2;
		}
		else
		{
			size=1;
		}
		size=Math.min(size,integers);
        Object[] possibilities=new Object[2];
        if(size==2)
        {
        	possibilities[0]=1;
        	possibilities[1]=2;
        }
        else
        {
        	possibilities[0]=1;
        	possibilities[1]=1;
        }
		
		Integer s = (Integer)JOptionPane.showInputDialog(
		                    null,
		                    "Select",
		                    defender.getName(),
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    possibilities,
		                    "1");
		while(s==null)
		{
			s = (Integer)JOptionPane.showInputDialog(
                    null,
                    "Select",
                    defender.getName(),
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities,
                    "1");
		}
		size=s;
        List<Integer>results=new ArrayList<Integer>();
        Random rnd=new Random();
        for(int i=0;i<integers&&i<size;i++) {
            results.add(rnd.nextInt(6) + 1);
        }
        return results;
	}

}
