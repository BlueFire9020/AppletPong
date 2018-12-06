
public class MenuManager 
{
	public MenuItem[] items;
	public int currentIndex;
	public boolean menuUnlocked = true;
	
	public MenuManager(MenuItem[] i)
	{
		items = i;
		currentIndex = 0;
	}
	
	public void GotoNextItem()
	{
		if (currentIndex < items.length && menuUnlocked)
		{
			currentIndex++;
		}
	}
	public void GotoPreviousItem()
	{
		if (currentIndex > 0 && menuUnlocked)
		{
			currentIndex--;
		}
	}
	
	public void SetMovementLock()
	{
		menuUnlocked = !menuUnlocked;
	}
	public void EditCurrentValue(int increment)
	{
		if (items[currentIndex].value + increment < 0)
		{
			return;
		}
		items[currentIndex].value += increment;	
	}
	public int GetValueAtIndex(int index)
	{
		return items[index].value;
	}
}
