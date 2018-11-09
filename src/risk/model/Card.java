package risk.model;

/**
 * @author Farzad Omarzadeh
 *
 */
public enum Card {
    Artillery(1),
    Infantry(2),
    Cavalry(3);
    private int value;

    /**
     * @param value the value of a card
     */
    private Card(int value) {
        this.value = value;
    }

    /**
     * @return the value of the enum
     */
    public int getValue() {
        return value;
    }

}