package risk.model;

import risk.controller.GameDriverController;

import java.util.*;

public class Cheater implements Strategy {


    @Override
    public String toString() {
        return "Cheater";
    }

    @Override
    public void reinforcement(Node node) {
        if (node != null){
            throw new RuntimeException("It is cheater's turn");
        }

        Player cheater = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        for (Node country : cheater.getNodeList()){
            country.setArmies(country.getArmies() * 2);
        }
        System.out.println("End Cheater Reinforcement");
    }

    @Override
    public void fortification(Node from, Node to, Integer armies) {
        if(from != null || to != null){
            throw new RuntimeException("It is cheater's turn");
        }

        Player cheater = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        List<Node> neighborList = findNeighbors(cheater);
        if (!neighborList.isEmpty()){
            Random random = new Random();
            int index = random.nextInt(neighborList.size());
            Node country = neighborList.get(index);
            country.setArmies(country.getArmies()*2);
        }

        System.out.println("End Cheater fortification");

    }

    @Override
    public boolean attack(Node attacker, Node defender, List<Integer> attackerdice, List<Integer> defenderdice) {
        if (attacker != null || defender != null){
            throw new RuntimeException("It is cheater's turn.");
        }

        Player cheater = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        List<Node> neighborList = findNeighbors(cheater);
        if (!neighborList.isEmpty()){
            Random random = new Random();
            int index = random.nextInt(neighborList.size());
            Node country = neighborList.get(index);

            List<Node> allCountries = Graph.getGraphInstance().getGraphNodes();
            for (String name : country.getAdjacencyList()){
                Node neighbor = allCountries.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findAny().get();
                if (!neighbor.getPlayer().equals(cheater)){
                    neighbor.setPlayer(cheater);
                    break;
                }
            }
            System.out.println("End Cheater Attack");
            return true;
        }
        System.out.println("Failed/End Cheater Attack");
        return false;
    }

	@Override
	public List<Integer> Defend(Integer integer,Node defender) {

        List<Integer> list = new ArrayList<>();
        list.add(6);
        if (integer.equals(2)){
            list.add(6);
        }
		return list;
	}

	private List<Node> findNeighbors(Player player){
        List<Node> countryList = player.getNodeList();
        List<Node> neighborList = new ArrayList<>();
        Set<String> list = new HashSet<>();
        for (Node country : countryList){
            list.add(country.getName());
        }
        for (Node country : countryList){
            if (!list.containsAll(country.getAdjacencyList())){
                neighborList.add(country);
            }
        }
        return neighborList;
    }
}
