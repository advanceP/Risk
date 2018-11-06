package risk.model;

public enum Card {
Artillery(1),
Infantry(2),
Cavalry(3);
	private int value;
	private Card(int value)
	{
		this.value=value;
	}
	public int getValue()
	{
		return value;
	}

}