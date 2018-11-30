package risk.strategy;

import risk.controller.GameDriverController;
import risk.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class RandomPlayer implements Strategy {


    @Override
    public String toString() {
        return "RandomPlayer";
    }

    @Override
    public void reinforcement(Node node) {
        Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        GameWriter.getGameWriterInstance().Write("[Reinforcement]");
        Random rnd = new Random();
        while (player.getReinforcement() > 0) {
            Node reinNode = player.getNodeList().get(rnd.nextInt(player.getNodeList().size()));
            GameWriter.getGameWriterInstance().Write("Reinforced Node: " +
                                                reinNode.getName() + ",Number of armies:" + 1 + "\n");
            player.increaseArmy(reinNode);
        }
        System.out.println("End Random Reinforcement");
    }

    @Override
    public void fortification(Node from, Node to, Integer armies) {
        GameWriter.getGameWriterInstance().Write("[Fortification]");
        if ((from == null) && (to == null) && (armies == null)) {
            Random rnd = new Random();
            Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
            List<Node> countries = Graph.getGraphInstance().getGraphNodes().stream().filter(item ->
                                    item.getPlayer() == player).collect(Collectors.toList());
            Node firstnode = countries.get(rnd.nextInt(countries.size()));

            int max = firstnode.getArmies() - 1;
            int numberofarmies = 0;
            if (max > 0) {
                numberofarmies = rnd.nextInt(max);
            }
            List<Node> reachable = Graph.getGraphInstance().reachableNodes(firstnode);
            Node secondnode = null;
            if (!reachable.isEmpty()) {
                secondnode = reachable.get(rnd.nextInt(reachable.size()));
            }
            secondnode = firstnode;
            firstnode.setArmies(firstnode.getArmies() - numberofarmies);
            secondnode.setArmies(secondnode.getArmies() + numberofarmies);
            GameWriter.getGameWriterInstance().Write(player.getName() + " moved " + numberofarmies +
                    " army/armies from " + firstnode.getName() + " to " + secondnode.getName() + "\r\n");
        }

    }

    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
        GameWriter.getGameWriterInstance().Write("[Attack]");
        Player player = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        Random rnd = new Random();
        Node attacknode = player.getNodeList().get(rnd.nextInt(player.getNodeList().size()));
        if (attacknode.getHostileNodes().size() == 0){
            GameWriter.getGameWriterInstance().Write("There is no more country can be attack\n");
            GameWriter.getGameWriterInstance().flush();
            System.out.println("End Random Attack");
            return false;
        }

        Node defendernode = attacknode.getHostileNodes().get(rnd.nextInt(attacknode.getHostileNodes().size()));
        Player defenderplayer = defendernode.getPlayer();
        List<Integer> attackerdicelist = Defend(null, attacknode);
        List<Integer> defenderdicelist = defenderplayer.Defend(attackerdicelist.size(), defendernode);
        if (attackerdicelist.size() > 1) {
            Collections.sort(attackerdicelist, Collections.reverseOrder());
        }
        if (defenderdicelist.size() > 1) {
            Collections.sort(defenderdicelist, Collections.reverseOrder());
        }
        if (attackerdicelist.isEmpty() || defenderdicelist.isEmpty()) {
            System.out.println("End Random Attack");
            return false;
        }
        GameWriter.getGameWriterInstance().Write(player.getName() + ": Attacking Country: " + attacknode.getName() +
                                            ", Defending Country: " + defendernode.getName());
        for (int i = 0; i < attackerdicelist.size() && i < defenderdicelist.size() && i < 2; i++) {
            if (attackerdicelist.get(i) > defenderdicelist.get(i)) {
                defendernode.setArmies(defendernode.getArmies() - 1);

            } else {
                attacknode.setArmies(attacknode.getArmies() - 1);
                GameWriter.getGameWriterInstance().Write(defendernode.getName() + " got defeated\n");
            }
            if (attacknode.getArmies() == 1) {
                GameWriter.getGameWriterInstance().Write("There is no more army can be attack\n");
                GameWriter.getGameWriterInstance().flush();
                System.out.println("End Random Attack");

                return false;
            }
            if (defendernode.getArmies() == 0) {
                player.increaseNumberOfCountries();
                defendernode.getPlayer().decreaseNumberOfCountries();
                defendernode.setPlayer(player);
                List<Card> list = Collections.unmodifiableList(Arrays.asList(Card.values()));
                int size = list.size();
                attacknode.getPlayer().addCards(list.get(rnd.nextInt(size)));

                if (defendernode.getPlayer().getNodeList().size() < 1) {
                    attacknode.getPlayer().addCards(defendernode.getPlayer().getCards());
                    defendernode.getPlayer().setCards(null);
                }
                System.out.println("End Random Attack");
                return true;
            }
        }
        System.out.println("End Random Attack");
        return false;
    }

    @Override
    public List<Integer> Defend(Integer integers, Node defender) {
        Random rnd = new Random();
        int size = 0;
        if (defender.getArmies() > 2) {
            size = 2;
        } else {
            size = 1;
        }
        int dicenumber = rnd.nextInt(size) + 1;
        List<Integer> results = new ArrayList<Integer>();
        for (int i = 0; i < dicenumber; i++) {
            results.add(rnd.nextInt(6) + 1);
        }
        return results;
    }
}
