package risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
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
    	if((from==null)&&(to==null)&& (armies==null))
    	{
    		List<Node>weakCountries=weakestCountries();
    		Node strongcountry=weakCountries.get(weakCountries.size()-1);
    		List<Node>reachableweakcountries=weakestCountries(Graph.getGraphInstance().reachableNodes(strongcountry));
			Node weakcountry=reachableweakcountries.get(0);
			int numberofarmies=(strongcountry.getArmies()-weakcountry.getArmies())/2;
			strongcountry.setArmies(strongcountry.getArmies()-numberofarmies);
			weakcountry.setArmies(weakcountry.getArmies()+numberofarmies);
    	}
    	
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
    public List<Node> weakestCountries(List<Node>nodeList)
    {
    	List<Node>weakcountries=new ArrayList<Node>();
    	weakcountries=nodeList;
		System.out.println(1+" "+weakcountries);
    	Collections.sort(weakcountries, new Comparator<Node>() {
    		public int compare(Node first,Node second)
    		{
    			return first.getArmies()-second.getArmies();
    		}
		});
		System.out.println(2+" "+weakcountries);
    	return weakcountries;
    }

	@Override
	public List<Integer> Defend(Integer integers,Node defender) {
		int size=1;	
        List<Integer>results=new ArrayList<Integer>();
        Random rnd=new Random();
        for(int i=0;i<integers&&i<size;i++) {
            results.add(rnd.nextInt(6) + 1);
        }
        return results;
	}
}
