package risk.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameThread extends  Thread{
    private List<String> strategies;
    private List<File> maps;
    int numberofplayer;
    int turns;

    public GameThread(List<String> strategies, List<File> maps, int numberofplayer, int turns) {
        this.strategies = strategies;
        this.maps = maps;
        this.numberofplayer = numberofplayer;
        this.turns = turns;
    }

    @Override
    public void run() {
        for(File file : maps) {

        }
    }
}
