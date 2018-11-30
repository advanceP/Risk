package risk.strategy;

import risk.controller.GameDriverController;
import risk.model.GameWriter;
import risk.model.Graph;
import risk.model.Node;
import risk.model.Player;

import java.util.*;
import java.util.stream.Collectors;

public class Benevolent implements Strategy {


    @Override
    public String toString() {
        return "Benevolent";
    }


    @Override
    public void reinforcement(Node node) {
        GameWriter.getGameWriterInstance().Write("[Reinforcement]");
        if (node == null) {
            Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
            List<Node> weakCountries = weakestCountries();
            int lowestforce = weakCountries.get(0).getArmies();
            for (int i = 0; i < weakCountries.size(); i++) {
                if (weakCountries.get(i).getArmies() <= lowestforce) {
                    if (player.getReinforcement() > 0) {
                        lowestforce = weakCountries.get(i).getArmies();
                        player.increaseArmy(weakCountries.get(i));
                        GameWriter.getGameWriterInstance().Write("Reinforced Node: " + weakCountries.get(i).getName()
                                                                        + ",Number of armies: 1" + "\n");
                    } else {
                        break;
                    }
                } else {
                    lowestforce = weakCountries.get(i - 1).getArmies();
                    i -= 2;
                }
            }
        }
        GameWriter.getGameWriterInstance().flush();
    }

    @Override
    public void fortification(Node from, Node to, Integer armies) {
        GameWriter.getGameWriterInstance().Write("[Fortification]");
        if ((from == null) && (to == null) && (armies == null)) {
            List<Node> weakCountries = weakestCountries();
            Node strongcountry = weakCountries.get(weakCountries.size() - 1);
            List<Node> reachableweakcountries = weakestCountries(Graph.getGraphInstance().reachableNodes(strongcountry));
            Node weakcountry = null;
            if (!reachableweakcountries.isEmpty()) {
                weakcountry = reachableweakcountries.get(0);
            }
            weakcountry = strongcountry;
            int numberofarmies = (strongcountry.getArmies() - weakcountry.getArmies()) / 2;
            strongcountry.setArmies(strongcountry.getArmies() - numberofarmies);
            weakcountry.setArmies(weakcountry.getArmies() + numberofarmies);
            GameWriter.getGameWriterInstance().Write(strongcountry.getPlayer().getName() + " moved " + numberofarmies +
                    " army/armies from " + strongcountry.getName() + " to " + weakcountry.getName() + "\n");
            GameWriter.getGameWriterInstance().flush();
        }

    }

    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
        GameWriter.getGameWriterInstance().Write("[Attack]");
        GameWriter.getGameWriterInstance().Write("Do not attack any country\n");
        GameWriter.getGameWriterInstance().flush();
        return false;
    }

    public List<Node> weakestCountries() {
        Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        List<Node> weakcountries = Graph.getGraphInstance().getGraphNodes().stream().filter(item -> item.getPlayer() == player).collect(Collectors.toList());
        Collections.sort(weakcountries, new Comparator<Node>() {
            public int compare(Node first, Node second) {
                return first.getArmies() - second.getArmies();
            }
        });
        return weakcountries;
    }

    public List<Node> weakestCountries(List<Node> nodeList) {
        List<Node> weakcountries = new ArrayList<Node>();
        weakcountries = nodeList;
        System.out.println(1 + " " + weakcountries);
        Collections.sort(weakcountries, new Comparator<Node>() {
            public int compare(Node first, Node second) {
                return first.getArmies() - second.getArmies();
            }
        });
        System.out.println(2 + " " + weakcountries);
        return weakcountries;
    }

    @Override
    public List<Integer> Defend(Integer integers, Node defender) {
        int size = 1;
        List<Integer> results = new ArrayList<Integer>();
        Random rnd = new Random();
        for (int i = 0; i < integers && i < size; i++) {
            results.add(rnd.nextInt(6) + 1);
        }
        return results;
    }
}
