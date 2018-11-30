package risk.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.io.Serializable;

/**
 * this show the label display the country's name on UI,and it will react by click the label
 *
 * @author Hao Chen
 */
public class GameLabel extends JLabel implements Serializable {
    /**
     * constructor<br/>
     * initialise the member in this class
     *
     * @param text this will give the country name for
     */
    public GameLabel(String text) {
        super(text);
    }

}
