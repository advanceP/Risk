package risk.model;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import risk.controller.GameDriverController;

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
        return false;
    }

	@Override
	public List<Integer> Defend() {
		// TODO Auto-generated method stub
		return null;
	}
}
