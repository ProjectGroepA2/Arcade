package audio;

import java.util.ArrayList;
import java.util.List;

public class SongInstance {

	private String difficulty;
	
	private List<ObjectInstance> objects;
	
	public SongInstance(String difficulty)
	{
		this.difficulty = difficulty;
		
		objects = new ArrayList<ObjectInstance>();
	}
	
	public void addObjectInstance(ObjectInstance obj)
	{
		objects.add(obj);
	}

	public List<ObjectInstance> getObjects() {
		return objects;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
}
