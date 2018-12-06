import java.lang.Math;


public class Paddle 
{
	public static int width;
	public static int height;
	public static int movementSpeed;
	public static int numZones;
	public static int windowWidth, windowHeight;
	public static Ball[] balls;
	public int xPos, yPos;
	public int points;
	
	public Paddle(int x, int y, int p)
	{
		xPos = x;
		yPos = y;
		points = p;
	}
	
	public static void setWindowSize(int windowW, int windowH)
	{
		windowWidth = windowW;
		windowHeight = windowH;
	}
	
	public int checkCollisionZone(int x, int y)
	{
		if (x <= xPos + width && x >= xPos - width)
		{
			int zoneSize = height / numZones;
			if (y >= yPos - 15 && y <= yPos + zoneSize)
			{
				//in current zone
				return 0;
			}
			for (int zone = 1; zone < numZones; zone++)
			{
				if (y >= yPos + (zoneSize * zone)&& y <= yPos + (zoneSize * (zone + 1)))
				{
					//in current zone
					return zone;
				}
			}
		}
		return -1;
	}
	public void MoveUp()
	{
		if (yPos - movementSpeed < 0)
		{
			return;
		}
		yPos -= movementSpeed;
	}
	public void MoveDown()
	{
		if (yPos + height + movementSpeed > windowHeight)
		{
			return;
		}
		yPos += movementSpeed;
	}
	public static int Distance(int x1, int y1, int x2, int y2)
	{
		return (int)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	
	

}
