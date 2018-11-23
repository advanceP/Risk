package risk.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import risk.controller.GameDriverController;

public class Benevolent implements Strategy {


    @Override
    public String toString() {
        return "Benevolent";
    }

    @Override
    public void reinforcement(Node node) {
    	if(node==null)
    	{
    		Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
    		List<Node>weakCountries=weakestCountries();
    		int lowestforce=weakCountries.get(0).getArmies();
    		for(int i=0;i<weakCountries.size();i++)
    		{
    			if(weakCountries.get(i).getArmies()<=lowestforce)
    			{
    				if(player.getReinforcement()>0)
    				{
    		    	   lowestforce=weakCountries.get(i).getArmies();
    		    	   player.increaseArmy(weakCountries.get(i));
    				}
    				else
    				{
    					break;
    				}
    			}
    			else
    			{
    				lowestforce=weakCountries.get(i-1).getArmies();
    				i-=2;
    			}
    		}    		
    	}
    	
    }

    @Override
    public void fortification(Node from, Node to, Integer armies) {

    }

    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
        return false;
    }
    public List<Node> weakestCountries()
    {
    	Player player= GameDriverController.getGameDriverInstance().getCurrentPlayer();
    	List<Node>weakcountries=Graph.getGraphInstance().getGraphNodes().stream().filter(item->item.getPlayer()==player).collect(Collectors.toList());
    	Collections.sort(weakcountries, new Comparator<Node>() {
    		public int compare(Node first,Node second)
    		{
    			return first.getArmies()-second.getArmies();
    		}
		});
    	return weakcountries;
    }
}
