package risk.model;

import java.io.FileWriter;
import java.io.IOException;

public class GameWriter {
	FileWriter fw;
	static GameWriter gm;
	
	private GameWriter() {
		try {
			fw=new FileWriter("out.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static GameWriter getGameWriterInstance()
	{
		if(gm==null)
		{
			gm=new GameWriter();
		}
		return gm;
	}
	public void Write(String message)
	{
		try {
			fw.append("\r\n"+message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void flush(){
		try{
			fw.flush();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	public void close()
	{
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void erase()
	{
		try {
			fw.write("");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
