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
            while (reinforcement > 0) {
                strongest.increaseArmy();
                strongest.getPlayer().decreaseReinforcement();
                reinforcement = strongest.getPlayer().getReinforcement();
            }
        }
        System.out.println("End Agressive Reinforcement");
    }

    private Node getStrongestNode(List<Node> nodeList) {
        Node strongest = nodeList.get(0);
        for (Node node : nodeList) {
            if (node.getArmies() > strongest.getArmies()) {
                strongest = node;
            }
        }
        return strongest;
    }


    @Override
    public boolean attack(Node attacker, Node defender, Integer attackerdice, Integer defenderdice) {
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
                        List<List<Integer>> diceNumList = player.getDiceNumList(attacker, defender);
                        boolean flag=false;
                        //attack
                        while(!flag) {
                            flag=attackResult(attacker, defender, diceNumList);
                            if(attacker.getArmies()==1) {
                                break;
                            }
                        }
                        if(flag) {
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
            return checkAllcountryBelongToPlayer;
        }
        System.out.println("End Aggressive Attack");
        return false;
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
        }else{
            conquestCountry.setArmies(1);
            node.setArmies(node.getArmies()-1);
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
            List<Node> reachableNodes = Graph.getGraphInstance().reachableNodes(to);
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

    @Override
    public String toString() {
        return "Aggressive";
    }
}
