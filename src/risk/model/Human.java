package risk.model;

public class Human implements Strategy {
	private Node reinforcementNode;
	
	public void setReinforcementNode(Node reinforcement)
	{
		this.reinforcementNode=reinforcement;
	}

	@Override
	public void reinforcement() {
		
		reinforcementNode.increaseArmy();
		reinforcementNode.getPlayer().decreaseReinforcement();
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
