
public class BotPaddle extends Paddle
{
	public static int behaviorType;
	private int direction = 1;

	public BotPaddle(int x, int y, int p) {
		super(x, y, p);
		// TODO Auto-generated constructor stub
	}

	public void Move()
	{
		if (behaviorType == 1)
		{
			//Move up and down methodically
			if (yPos < 0 || yPos >= windowHeight - height)
			{
				direction *= -1;
			}
			yPos += direction * movementSpeed;
		}
		else if (behaviorType == 2)
		{
			//Try to follow the ball old school style
			int closestDistance = 999;
			Ball closestBall = null;

			for (Ball b : balls)
			{
				int ballDist = Distance(xPos, yPos, b.xPos, b.yPos);

				if (ballDist < closestDistance)
				{
					closestBall = b;
					closestDistance = ballDist;
				}
			}
			//closestBall = balls[0];
			if (Math.abs(closestBall.xPos - xPos) < 500)
			{
				if (closestBall.yPos < yPos)
				{
					MoveUp();
				}
				else if (closestBall.yPos > yPos + height)
				{
					MoveDown();
				}	
			}
		}
		else if (behaviorType == 3)
		{
			//Try to follow the ball old school style
			int closestDistance = 999;
			Ball closestBall = null;

			for (Ball b : balls)
			{
				int ballDist = Distance(xPos, yPos, b.xPos, b.yPos);

				if (ballDist < closestDistance)
				{
					closestBall = b;
					closestDistance = ballDist;
				}
			}
			//closestBall = balls[0];
			if (closestBall.yPos < yPos)
			{
				MoveUp();
			}
			else if (closestBall.yPos > yPos + height)
			{
				MoveDown();
			}	
		}
	}
}
