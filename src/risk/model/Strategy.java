package risk.model;

public interface Strategy {
	public void reinforcement(Node node);
	public void fortification(Node from, Node to, Integer armies);
	public boolean attack(Node attacker, Node defender, Integer attackerdice, Integer defenderdice);
}
