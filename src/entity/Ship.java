package entity;

import java.util.HashSet;
import java.util.Set;

import screen.Screen;
import engine.Cooldown;
import engine.DrawManager;

/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Ship extends Entity {

	private int speed;
	private Cooldown shootingCooldown;
	private Set<Bullet> bullets;

	/**
	 * Constructor, establishes the bullet's properties.
	 * 
	 * @param screen
	 *            Screen where the ship will be drawn.
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 * @param speed
	 *            Absolute speed of the ship, when ordered to move.
	 */
	public Ship(Screen screen, int positionX, int positionY, int speed) {
		super(screen, positionX, positionY, 13 * 2, 8 * 2);
		
		this.speed = speed;
		this.bullets = new HashSet<Bullet>();
		this.shootingCooldown = new Cooldown(350);
		this.bullets = new HashSet<Bullet>();
		

	}

	/**
	 * Draws the bullets shot by this ship.
	 */
	public void drawBullets() {
		// TODO Move bullets and bullet logic to GameScreen.
		Set<Bullet> recyclable = new HashSet<Bullet>();
		for (Bullet bullet : this.bullets) {
			bullet.update();
			DrawManager.drawEntity(bullet, bullet.getPositionX(), bullet.positionY);
			if (bullet.positionY < 0
					|| bullet.positionY > this.screen.getHeight())
				recyclable.add(bullet);
		}
		BulletPool.recycle(recyclable);
	}

	/**
	 * Moves the ship speed units right, or until the right screen border is
	 * reached.
	 */
	public void moveRight() {
		this.positionX += this.speed;
		if (this.positionX > this.screen.getWidth() - this.width - 1)
			this.positionX = this.screen.getWidth() - this.width - 1;
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 */
	public void moveLeft() {
		this.positionX -= this.speed;
		if (this.positionX < 1)
			this.positionX = 1;
	}

	/**
	 * Shoots a bullet upwards.
	 */
	public void shoot() {
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();
			this.bullets.add(BulletPool.getBullet(this.screen, positionX + this.width / 2,
					positionY, -5));
		}
	}
}