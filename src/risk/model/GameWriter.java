package risk.model;

import java.io.FileWriter;
import java.io.IOException;

/**
 * write game onformation
 */
public class GameWriter {
    FileWriter fw;
    static GameWriter gm;

    /**
     * contructor
     */
    private GameWriter() {
        try {
            fw = new FileWriter("out.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * singleton mode
     *
     * @return gamewriter
     */
    public static GameWriter getGameWriterInstance() {
        if (gm == null) {
            gm = new GameWriter();
        }
        return gm;
    }

    /**
     * write message
     *
     * @param message message
     */
    public void Write(String message) {
        try {
            fw.append("\r\n" + message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * refresh
     */
    public void flush() {
        try {
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * close stream
     */
    public void close() {
        try {
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
