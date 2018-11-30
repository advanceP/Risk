package risk.strategy;

import risk.controller.GameDriverController;
import risk.model.*;

import java.util.*;

public class Cheater implements Strategy {


    @Override
    public String toString() {
        return "Cheater";
    }
    /**
     * aggressive attack
     *
     * @param node if it's computer ,pass null
     */
    @Override
    public void reinforcement(Node node) {
        if (node != null){
            throw new RuntimeException("It is cheater's turn");
        }

        Player cheater = GameDriverController.getGameDriverInstance().getCurrentPlayer();
        for (Node country : cheater.getNodeList()){
            country.setArmies(country.getArmies() * 2);
            GameWriter.getGameWriterInstance().Write("Reinforced Node: " + country.getName() +
                                                    ",Number of armies:" + country.getArmies() + "\n");
        }
        GameWriter.getGameWriterInstance().flush();
        System.out.println("End Cheater Reinforcement");
    }
    /**
     * fortification phase
     *
     * @param from   the country gonna fortify,null if it's a computer
     * @param to     the country gonna  receive fortify,null if it's a computer
     * @param armies the army gonna fortify,null if it's a computer
     */
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
            GameWriter.getGameWriterInstance().Write(cheater.getName() + " doubled armies of " + country.getName() + "\n");
            GameWriter.getGameWriterInstance().flush();
        }

        System.out.println("End Cheater fortification");

    }
    /**
     * aggressive attack
     *
     * @param attacker     the attack node
     * @param defender     the defend node
     * @param attackerdice attacker node
     * @param defenderdice defender node
     * @return is the attack win all the map
     */
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
                    Player another = neighbor.getPlayer();
                    another.decreaseNumberOfCountries();
                    cheater.increaseNumberOfCountries();
                    neighbor.setPlayer(cheater);
                    List<Card> list = Collections.unmodifiableList(Arrays.asList(Card.values()));
                    int size = list.size();
                    Random rnd = new Random();
                    cheater.addCards(list.get(rnd.nextInt(size)));

                    GameWriter.getGameWriterInstance().Write(cheater.getName() + ": attack " + neighbor.getName() + "\n");
                    break;
                }
            }
            System.out.println("End Cheater Attack");
            return true;
        }
        System.out.println("Failed/End Cheater Attack");
        return false;
    }
    /**
     * the way this strategy how to defend
     *
     * @param integers attacker's number of dices
     * @return number of dice
     */
	@Override
	public List<Integer> Defend(Integer integers,Node defender) {

        List<Integer> list = new ArrayList<>();
        list.add(6);
        if (integers.equals(2)){
            list.add(6);
        }
		return list;
	}
    /**
     * find country list which connect country of another player
     *
     * @param player computer player
     */
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
