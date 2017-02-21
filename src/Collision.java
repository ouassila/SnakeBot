import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Collision {

	private int x, y;
	
	public Collision(int x, int y){		
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void writeFile(){
		
		BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = new File("file.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			System.out.println(this.getX()+"-"+this.getY());
			bw.write(this.getX()+"-"+this.getY());
			bw.newLine();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
