import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;

import javax.swing.JApplet;

public class Pong extends JApplet implements KeyListener
{
	public int width = 1000, height = 500;
	public Ball[] balls = new Ball[1];
	public Paddle player;
	public BotPaddle bot;
	public int moveUp, moveDown;
	public Timer keyTimer;

	public int delay = 10; //1 second
	public int period = 20; //3 seconds
	public int clearRate = 55;

	private int currentMovement = 0;
	private boolean menuVisible = false;
	private MenuItem[] menu = new MenuItem[6];
	private MenuManager options = new MenuManager(menu);
	private int tick;
	private Timer refreshTimer;


	public void init()
	{
		Paddle.height = 100;
		Paddle.width = 20;
		Paddle.movementSpeed = 5;
		Paddle.numZones = 5;

		Paddle.setWindowSize(width, height);

		BotPaddle.behaviorType = 2;

		Ball.defaultVelocity = 15;
		Ball.velocityMult = 3;
		Ball.maxVelocity = 25;

		Ball.setScale(15);
		Ball.setWindowSize(width, height);

		player = new Paddle(0,height / 2 - (Paddle.height / 2),0);
		bot = new BotPaddle(width - Paddle.width,height / 2 - (Paddle.height / 2),0);

		Ball ball = new Ball(width / 2, height / 2, Color.blue);
		//Ball ball2 = new Ball(width / 2, height / 3, Color.orange);

		balls[0] = ball;
		//balls[1] = ball2;

		Paddle.balls = balls;
		Timer refreshTimer = new Timer();
		refreshTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() 
			{   
				repaint();
			}
		}, delay, period);
		moveUp = KeyEvent.VK_W;
		moveDown = KeyEvent.VK_S;
		addKeyListener(this);

		setFocusable(true);
		
		MenuItem paddleMovement = new MenuItem("Paddle Movement", "How fast each paddle can move", Color.blue, 5, 100);
		menu[0] = paddleMovement;
		MenuItem numBalls = new MenuItem("Number of Balls", "How many balls to generate", Color.blue, 1, 100);
		menu[1] = numBalls;
		MenuItem maxVelocity = new MenuItem("Max Velocity", "The max speed a ball can travel at", Color.blue, 25, 500);
		menu[2] = maxVelocity;
		MenuItem numZones = new MenuItem("Paddle Zones", "Number of zones for each paddle", Color.blue, 5, 25);
		menu[3] = numZones;
		MenuItem playerBot = new MenuItem("Bot Player", "Set whether or not the left paddle is a bot (0 = Player, 1 = Bot)", Color.blue, 0, 1);
		menu[4] = playerBot;
		MenuItem botBehavior = new MenuItem("Bot Behavior", "Set how the bots behave (1 = Methodical, 2 = Track, 3 = Predict)", Color.blue, 1, 3);
		menu[5] = botBehavior;
	}


	public void paint(Graphics page)
	{
		GetConfigValues();
		
		if (!menuVisible)
		{
			page.setColor(Color.black);
			page.fillRect(0, 0, width, height);
			tick = 0;
		}
		else if (tick == 5 && menuVisible)
		{
			page.setColor(Color.black);
			page.fillRect(0, 0, width, height);
			tick = 0;
		}
		else if (tick >= 5)
		{
			tick = 0;
		}
		else
		{
			tick++;
		}
		if (menuVisible)
		{
			DisplayMenu(page);
		}
		else
		{
			DisplayGame(page);
		}

	}
	
	public void GetConfigValues()
	{
		Paddle.movementSpeed = options.items[0].value;
		Ball.maxVelocity = options.items[2].value;
		Paddle.numZones = options.items[3].value;
		BotPaddle.behaviorType = Math.max(1, options.items[5].value);
		if (options.items[4].value == 1)
		{
			player = new BotPaddle(0,player.yPos,player.points);
		}
		
	}
	
	public void DisplayMenu(Graphics page)
	{
		setBackground(Color.white);

		//draw title
		page.setColor(Color.white);
		page.drawRect(10, 10, width - 20, height - 20);
		page.drawString("Pong Options", 15, height / 13);
		page.drawLine(15, height / 10, width - 15, height / 10);

		int startPos = (0 + (height / 10)) + 10;
		int boxScale = height / 9;

		for (int i = 0; i < options.items.length; i++)
		{
			if (i == options.currentIndex)
			{
				page.setColor(options.items[i].highlightColor);
			}
			else
			{
				page.setColor(Color.white);
			}
			page.drawRect(15, startPos, width - 30, boxScale);
			page.drawString(options.items[i].name, 20, startPos + (boxScale / 3));
			page.drawString("Description: " + options.items[i].description, 25, (int) (startPos + (boxScale * 0.65)));
			page.drawString("State: " + options.GetValueAtIndex(i), 25, (int) (startPos + (boxScale * 0.95)));
			startPos += boxScale + 10;
		}



	}
	public void DisplayGame(Graphics page)
	{
		int playerZone = 0;
		int botZone = 0;

		if (Paddle.balls.length != options.items[1].value)
		{
			Paddle.balls = new Ball[options.items[1].value];
			for (int i = 0; i < options.items[1].value; i++)
			{
				Paddle.balls[i] = new Ball(width / 2, (int)(height / 2 + ((Math.random() * 30) - 15)), new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			}
		}
		for (Ball b : Paddle.balls)
		{
			for (int x = b.xPos; x < b.xPos + (Ball.width * 2); x += Ball.width)
			{
				for (int y = b.yPos; y < b.yPos + (Ball.height * 2); y += Ball.height)
				{
					if (player.checkCollisionZone(x, y) != -1)
					{
						break;
					}
					if (bot.checkCollisionZone(x, y) != -1)
					{
						break;
					}
					playerZone = player.checkCollisionZone(b.xPos, b.yPos);
					botZone = bot.checkCollisionZone(b.xPos, b.yPos);
				}	
			}

			if (playerZone != -1)
			{
				b.BounceHoriz(playerZone);
			}
			else if (botZone != -1)
			{
				b.BounceHoriz(botZone);
			}
			b.Move();

			page.setColor(b.color);

			page.fillRect(b.xPos, b.yPos, Ball.width, Ball.height);
		}

		bot.Move();
		if (options.items[4].value == 1)
		{
			((BotPaddle) player).Move();
		}

		page.setColor(Color.white);

		page.fillRect(player.xPos, player.yPos, Paddle.width, Paddle.height);
		page.fillRect(bot.xPos, bot.yPos, Paddle.width, Paddle.height);
	}
	@Override
	public void keyPressed(KeyEvent kev) {
		// TODO Auto-generated method stub
		int key = kev.getKeyCode();
		if (key == moveUp)
		{
			if (menuVisible && !options.menuUnlocked) 
			{
				options.EditCurrentValue(1);
			}
			else if (menuVisible)
			{
				options.GotoPreviousItem();
			}
			else if (currentMovement < 1)
			{
				currentMovement = 1;
				keyTimer = new Timer();
				keyTimer.scheduleAtFixedRate(new TimerTask() {
					public void run() 
					{   
						player.MoveUp();
					}
				}, delay, period);		
			}
		}
		else if (key == moveDown)
		{
			if (menuVisible && !options.menuUnlocked) 
			{
				options.EditCurrentValue(-1);
			}
			else if (menuVisible)
			{
				options.GotoNextItem();
			}
			else if (currentMovement >= 0)
			{
				currentMovement = -1;
				keyTimer = new Timer();
				keyTimer.scheduleAtFixedRate(new TimerTask() {
					public void run() 
					{   
						player.MoveDown();
					}
				}, delay, period);		
			}	
		}
		else if (key == KeyEvent.VK_ESCAPE)
		{
			menuVisible = !menuVisible;
		}
		else if (key == KeyEvent.VK_E && menuVisible) 
		{
			options.SetMovementLock();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int key = arg0.getKeyCode();
		if (key == moveUp)
		{
			currentMovement = 0;
			if (keyTimer != null)
			{
				keyTimer.cancel();	
			}
		}
		else if (key == moveDown)
		{
			currentMovement = 0;
			if (keyTimer != null)
			{
				keyTimer.cancel();	
			}
		}

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}