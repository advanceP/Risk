package risk.model;

public class Human implements Strategy {

/*
	public void setReinforcementNode(Node reinforcement)
	{
		this.reinforcementNode=reinforcement;
	}
*/

	@Override
	public void reinforcement(Node reinforcementNode) {
		
		reinforcementNode.increaseArmy();
		reinforcementNode.getPlayer().decreaseReinforcement();
		System.out.println("Human");
	}

	@Override
	public void fortification() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Human";
	}

}
