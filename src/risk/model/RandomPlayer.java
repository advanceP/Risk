package risk.model;

import risk.controller.GameDriverController;

import java.util.*;
import java.util.stream.Collectors;

public class RandomPlayer implements Strategy{


    @Override
    public String toString() {
        return "RandomPlayer";
    }

    @Override
    public void reinforcement(Node node) {
    	Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
    	Random rnd=new Random();
    	while(player.getReinforcement()>0)
    	{
    		Node reinforcementnode=player.getNodeList().get(rnd.nextInt(player.getNodeList().size()));
    		player.increaseArmy(reinforcementnode);
    	}
    }

    @Override
    public void fortification(Node from, Node to, Integer armies) {
    	if((from==null)&&(to==null)&& (armies==null))
    	{
    		Random rnd=new Random();
    		Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
    		List<Node> countries=Graph.getGraphInstance().getGraphNodes().stream().filter(item->item.getPlayer()==player).collect(Collectors.toList());
    		Node firstnode=countries.get(rnd.nextInt(countries.size()));
    		int min=0;
    		int max=firstnode.getArmies()-1;
    		int numberofarmies=rnd.nextInt(max-min)+min;
    		List<Node> reachable=Graph.getGraphInstance().reachableNodes(firstnode);
    		Node secondnode=reachable.get(rnd.nextInt(reachable.size()));
    		firstnode.setArmies(firstnode.getArmies()-numberofarmies);
    		secondnode.setArmies(secondnode.getArmies()+numberofarmies);	
    	}

    }

    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
    	Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
    	Random rnd=new Random();
    	Node attacknode=player.getNodeList().get(rnd.nextInt(player.getNodeList().size()));
    	Node defendernode=attacknode.getHostileNodes().get(rnd.nextInt(attacknode.getHostileNodes().size()));
    	Player defenderplayer=defendernode.getPlayer();
    	List<Integer>attackerdicelist=Defend(null,attacknode);
    	List<Integer>defenderdicelist=defenderplayer.Defend(attackerdicelist.size(),defendernode);
    	if (attackerdicelist.size() > 1) {
			Collections.sort(attackerdicelist, Collections.reverseOrder());
		}
		if (defenderdicelist.size() > 1) {
			Collections.sort(defenderdicelist, Collections.reverseOrder());
		}
		if (attackerdicelist.isEmpty() || defenderdicelist.isEmpty()) {
			return false;
		}
		for (int i = 0; i < attackerdicelist.size() && i < defenderdicelist.size() && i < 2; i++) {
			if (attackerdicelist.get(i) > defenderdicelist.get(i)) {
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
	public List<Integer> Defend(Integer integers,Node defender) {
		Random rnd=new Random();
		int size=0;
		if(defender.getArmies()>2)
		{
			size=2;
		}
		else
		{
			size=1;
		}
		int dicenumber=rnd.nextInt(size)+1;
		List<Integer>results=new ArrayList<Integer>();
		for(int i=0;i<dicenumber;i++)
		{
			results.add(rnd.nextInt(6)+1);
		}
		return results;
	}
}
