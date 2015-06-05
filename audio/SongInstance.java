package audio;

import java.util.ArrayList;
import java.util.List;

public class SongInstance {

	private String difficulty;
	
	private List<ObjectInstance> objects;
	private List<ButtonInstance> buttons;
	
	public SongInstance(String difficulty)
	{
		this.difficulty = difficulty;
		
		objects = new ArrayList<ObjectInstance>();
		buttons = new ArrayList<ButtonInstance>();
	}
	
	public void addObjectInstance(ObjectInstance obj)
	{
		objects.add(obj);
	}
	public void addButtonInstance(ButtonInstance btn)
	{
		buttons.add(btn);
	}

	public List<ObjectInstance> getObjects() {
		return objects;
	}
	
	public List<ButtonInstance> getButtons() {
		return buttons;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public List<ObjectInstance> getObjectsBetween(long oldProgress, long progress) {
		List<ObjectInstance> b = new ArrayList<ObjectInstance>();
		
		for(ObjectInstance i : objects)
		{
			if(i.getTime() >= progress)
			{
				return b;
			}
			if(i.getTime() >= oldProgress && i.getTime() < progress)
			{
				b.add(i);
			}
		}
		
		return b;
	}
	
	public List<ButtonInstance> getButtonsBetween(long oldProgress, long progress) {
		List<ButtonInstance> b = new ArrayList<ButtonInstance>();
		
		for(ButtonInstance i : buttons)
		{
			if(i.getTime() >= progress)
			{
				return b;
			}
			if(i.getTime() >= oldProgress && i.getTime() < progress)
			{
				b.add(i);
			}
		}
		
		return b;
	}
}
