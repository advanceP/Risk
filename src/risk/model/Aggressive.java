package risk.model;

import risk.controller.GameDriverController;

import java.util.*;

public class Aggressive implements Strategy {
    @Override
    public void reinforcement(Node node) {
        Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
        if(node==null) {
            List<Node> nodeList = player.getNodeList();
            Node strongest = getStrongestNode(nodeList);
            int reinforcement = player.getReinforcement();
            GameWriter.getGameWriterInstance().Write("Player: "+strongest.getPlayer().getName()+", Reinforced Node: "+strongest.getName()+",Number of armies:"+reinforcement+"\n");
            while (reinforcement > 0) {
                strongest.increaseArmy();
                strongest.getPlayer().decreaseReinforcement();
                reinforcement = strongest.getPlayer().getReinforcement();
            }
        }
        System.out.println("End Aggressive Reinforcement");
    }

    public Node getStrongestNode(List<Node> nodeList) {
        Node strongest = nodeList.get(0);
        for (Node node : nodeList) {
            if (node.getArmies() > strongest.getArmies()) {
                strongest = node;
            }
        }
        return strongest;
    }


    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
        Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
        if (attacker == null && defender == null) {
            List<Node> nodes = selectNodesForAttack(player);
            if (nodes.isEmpty()) {
                return false;
            }
            List<Node> allNodes = Graph.getGraphInstance().getGraphNodes();
            //begin attack
            int index=0;
            while(index<nodes.size()) {
                attacker=nodes.get(index);
                while (attacker.getArmies() > 1 ) {
                    defender = chooseDefender(attacker, allNodes, player);
                    if (defender != null && defender.getPlayer()!= player) {
                    	
                        List<List<Integer>> diceNumList = getDiceNumList(attacker);
                        List<Integer> diceresult = defender.getPlayer().getStrategy().Defend(diceNumList.get(0).size(),defender);
                        diceNumList.add(diceresult);//add Defender
                        GameWriter.getGameWriterInstance().Write("Attacking Country: "+attacker.getName()+
                        		", Defending Country: "+defender.getName()+"");
                        boolean flag=false;
                        //attack
                        while(!flag) {
                            flag=attackResult(attacker, defender, diceNumList);
                            if(attacker.getArmies()==1) {
                            	GameWriter.getGameWriterInstance().Write(attacker.getName()+" retreated\n");
                                break;
                            }
                        }
                        if(flag) {
                        	GameWriter.getGameWriterInstance().Write(defender.getName()+" got defeated\n");
                            //move army
                            defender.setPlayer(player);
                            player.increaseNumberOfCountries();
                            moveArmyToConquestCountry(attacker,defender);
                            nodes.add(defender);
                        }
                    }else {
                        break;
                    }
                }
                index++;
            }
            boolean checkAllcountryBelongToPlayer=true;
            for(Node n:allNodes) {
                if(n.getPlayer()!=player) {
                    checkAllcountryBelongToPlayer=false;
                }
            }
            System.out.println("End Aggressive Attack");
            GameWriter.getGameWriterInstance().close();
            return checkAllcountryBelongToPlayer;
        }
        System.out.println("End Aggressive Attack");
        return false;
    }

    private List<List<Integer>> getDiceNumList(Node attacker) {
        List<List<Integer>> resultList = new ArrayList<>();
        List<Integer> attackerList = new ArrayList<>();
        resultList.add(attackerList);
        Random random = new Random();
        for (int i = 0; i < attacker.getArmies() && i<4; i++) {
            attackerList.add(random.nextInt(6) + 1);
        }

        if (attackerList.size() > 1) {
            Collections.sort(attackerList, Collections.reverseOrder());
        }
        return resultList;
    }


    private boolean attackResult(Node attacker, Node defender, List<List<Integer>> diceNumList) {
        if (diceNumList.isEmpty()) {
            return false;
        }
        List<Integer> attackerList = diceNumList.get(0);
        List<Integer> defenderList = diceNumList.get(1);
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
            if (defender.getArmies() == 0) {
                return true;
            }
        }
        return false;

    }

    private void moveArmyToConquestCountry(Node node, Node conquestCountry) {
        if(node.getArmies()>2){
            conquestCountry.setArmies(2);
            node.setArmies(node.getArmies()-2);
        	GameWriter.getGameWriterInstance().Write("moved armies: "+2+"\n");
        }else{
            conquestCountry.setArmies(1);
            node.setArmies(node.getArmies()-1);
            GameWriter.getGameWriterInstance().Write("moved armies: "+1+"\n");
        }
    }


    private Node chooseDefender(Node node, List<Node> allNodes, Player player) {
        List<String> adjacencyList = node.getAdjacencyList();
        for(String name : adjacencyList) {
            for(Node country:allNodes) {
                if(country.getName().equals(name)) {
                    if(country.getPlayer()!=player) {
                        return country;
                    }
                }
            }
        }
        return null;
    }

    private List<Node> selectNodesForAttack(Player player) {
        List<Node> nodes=new ArrayList<>();
        for(Node node:player.getNodeList()) {
            if(node.getArmies()>1) {
                nodes.add(node);
            }
        }
        return nodes;
    }


    @Override
    public void fortification(Node from, Node to, Integer armies) {
        Player player=GameDriverController.getGameDriverInstance().getCurrentPlayer();
        if(from==null && to==null){
            List<Node> list = player.getNodeList();
            to = getStrongestNode(list);
            List<Node> reachableNodes = new ArrayList<>();
            reachableNodes=Graph.getGraphInstance().reachableNodes(to);
            reachableNodes.retainAll(list);
            for(Node node:reachableNodes) {
                from=node;
                if(from.getArmies()>1){
                   while(from.getArmies()>1) {
                       from.setArmies(from.getArmies()-1);
                       to.setArmies(to.getArmies()+1);
                   }
                }
            }
        }
    }

    public void reachableNodes(Node to, List<Node> list,List<Node> reachablenodes) {
        to.setVisited(true);
        List<String> adjacencyList = to.getAdjacencyList();
        for (String name : adjacencyList) {
            //name match
            for (Node node : list) {
                if (name.equals(node.getName())) {
                    if(!node.isVisited()) {
                        node.setVisited(true);
                        reachablenodes.add(node);
                        reachableNodes(node, list, reachablenodes);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Aggressive";
    }

	@Override
	public List<Integer> Defend(Integer integers,Node defender) {
		int size=0;
		if(defender.getArmies()>2)
		{
			size=2;
		}
		else
		{
			size=1;
		}
        List<Integer>results=new ArrayList<Integer>();
        Random rnd=new Random();
        for(int i=0;i<integers&&i<size;i++) {
            results.add(rnd.nextInt(6) + 1);
        }
        return results;
	}
}
