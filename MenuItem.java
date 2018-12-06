import java.awt.Color;

public class MenuItem 
{
	public String name;
	public String description;
	public Color highlightColor;
	public int value;
	public int valueMax;
	
	public MenuItem(String n, String d, Color highlight, int v, int vm)
	{
		name = n;
		description = d;
		highlightColor = highlight;
		value = v;
		valueMax = vm;
	}
	
}

