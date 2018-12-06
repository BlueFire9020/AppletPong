import java.awt.Color;
import java.lang.Math;

public class Ball 
{
	public static int width, height;
	public static int maxVelocity;
	public static int defaultVelocity;
	public static int windowWidth, windowHeight;
	public static int velocityMult;
	
	public int xPos, yPos;
	public int velocity;
	public int direction;
	public Color color;
	public Ball (int x, int y, Color c)
	{
		xPos = x;
		yPos = y;
		color = c;
	}
	public static void setScale(int scale)
	{
		width = scale;
		height = scale;
	}
	public static void setWindowSize(int windowW, int windowH)
	{
		windowWidth = windowW;
		windowHeight = windowH;
	}
	public boolean outOfBounds()
	{
		if (touchingHorBorder())
		{
			return true;
		}
		return false;
	}
	public void Move()
	{
		int polarity = (int) (Math.random() * 2);
		if (polarity == 1)
		{
			polarity = -1;
		}
		else if (polarity == 0)
		{ 
			polarity = 1;
		}
		if (outOfBounds())
		{
			xPos = windowWidth / 2;
			yPos = windowHeight / 2;
			velocity = defaultVelocity * polarity;
			//direction = (int) (Math.random() * 10) - 5;
			direction = 0;
		}
		else if (velocity == 0)
		{
			velocity = defaultVelocity * polarity;
			direction = (int) (Math.random() * 10) - 5;
			
		}
		else if (touchingVerBorder())
		{
			BounceVert();
			if (yPos >= height)
			{
				yPos -= 10;
			}
			if (yPos <= 0)
			{
				yPos += 10;
			}
		}
		else
		{
			xPos += velocity;
			yPos += direction;	
		}
	}
	public void BounceVert()
	{
		direction = (direction - direction - direction);
	}
	public void BounceHoriz(int zone)
	{
		int distToEdge = (Math.abs(zone - (int)(Paddle.numZones / 2)));
		if (xPos > width / 2)
		{
			xPos -= 5;
		}
		else if (xPos < width / 2)
		{
			xPos += 5;
		}
		velocity += 1;
		velocity = velocity - velocity - velocity;
		if (direction < 0)
		{
			direction -= distToEdge;
		}
		else if (direction > 0)
		{
			direction += distToEdge;
		}
		else if (direction == 0)
		{
			int rand = (int)Math.random();
			if (rand == 0 && distToEdge >= 2)
			{
				direction -= distToEdge;
			}
			else if (rand == 1 && distToEdge >= 2)
			{
				direction += distToEdge;
			}
		}
	}
	public boolean touchingHorBorder()
	{
		if (xPos <= 0 || xPos >= windowWidth)
		{
			return true;
		}
		return false;
	}
	public boolean touchingVerBorder()
	{
		if (yPos <= 0 || yPos >= windowHeight)
		{
			return true;
		}
		return false;
	}
}
