package risk.view;

import risk.model.Continent;
import risk.model.Graph;

import javax.swing.*;
import java.util.List;

/**
 * This is the panel for map editor<br/>
 * using free layout for the whole project
 *
 * @author Hao Chen
 */
public class RiskGame extends JPanel {

    private JButton buttonForEdit;
    private JButton buttonForGame;
    private JButton createNewMap;
    private JButton loadExistFile;
    private JButton createContinent;
    private JTextField continentInformation;
    private JButton singleGameMode;
    private JButton tournamentMode;
    private JButton back;
    private JButton loadGame;
    /**
     * overwrite the constructor<br/>
     * use free layout for the whole project
     */
    public RiskGame() {
        super();
        setLayout(null);
        initial();
    }

    /**
     * get the button for edit
     * @return JButton
     */
    public JButton getButtonForEdit() {
        return buttonForEdit;
    }

    /**
     * get the button for playing game
     * @return jbutton
     */
    public JButton getButtonForGame() {
        return buttonForGame;
    }

    /**
     * get the button for create map
     * @return jbutton
     */
    public JButton getCreateNewMap() {
        return createNewMap;
    }

    /**
     * get the button for load a map
     * @return jbutton
     */
    public JButton getLoadExistFile() {
        return loadExistFile;
    }

    /**
     * get the button for create continent
     * @return jbutton
     */
    public JButton getCreateContinent() {
        return createContinent;
    }

    /**
     * get the button for continent information
     * @return JTextField
     */
    public JTextField getContinentInformation() {
        return continentInformation;
    }

    /**
     * return singlegamemode button
     * @return jbutton
     */
    public JButton getSingleGameMode() {
        return singleGameMode;
    }

    /**
     * return bakbutton
     * @return Jbutton
     */
    public JButton getBack() {
        return back;
    }

    /**
     * return tournamentMode button
     * @return JButton
     */
    public JButton getTournamentMode() {
        return tournamentMode;
    }

    /**
     * @return jButton button for load a game process
     */
    public JButton getLoadGame() {
        return loadGame;
    }

    /**
     * initialise some buttons,and show them on the menu
     */
    private void initial() {
        buttonForEdit = new JButton("map editor");
        buttonForEdit.setBounds(280, 100, 400, 100);
        buttonForGame = new JButton("start game");
        buttonForGame.setBounds(280, 280, 400, 100);
        createNewMap = new JButton("create a new map");
        createNewMap.setBounds(280, 100, 400, 100);
        loadExistFile = new JButton("load from existing file");
        loadExistFile.setBounds(280, 280, 400, 100);
        createContinent = new JButton("create now");
        createContinent.setBounds(730, 250, 150, 30);
        continentInformation = new JTextField();
        continentInformation.setBounds(280, 250, 320, 30);
        singleGameMode = new JButton("Single Game Mode");
        singleGameMode.setBounds(280, 150, 320, 50);
        tournamentMode=new JButton("TournamentMode");
        tournamentMode.setBounds(280, 220,320 , 50);
        back=new JButton("Back");
        back.setBounds(800, 600, 120, 50);
        loadGame=new JButton("load Game");
        loadGame.setBounds(280, 320, 320, 50);
        add(buttonForEdit);
        add(buttonForGame);

    }

    /**
     *  redraw buttons
     */
    public void intoMapEditor() {
        remove(buttonForEdit);
        remove(buttonForGame);
        add(createNewMap);
        add(loadExistFile);
        add(back);
        repaint();
    }

    /**
     * draw continent
     */
    public void showInputContinent() {
        JLabel label = new JLabel("please create your continent and separate by ','");
        label.setBounds(280, 100, 400, 100);
        remove(createNewMap);
        remove(loadExistFile);
        add(label);
        add(continentInformation);
        add(createContinent);
        repaint();
    }

    /**
     * show choose mode view
     */
    public void chooseMode(){
        remove(buttonForEdit);
        remove(buttonForGame);
        add(singleGameMode);
        add(tournamentMode);
        add(loadGame);
        add(back);
        repaint();
    }


    public void backtoMainMenu() {
        removeAll();
        repaint();
        add(buttonForEdit);
        add(buttonForGame);
    }
}
