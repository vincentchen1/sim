import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Cell {
	//add instance variables to track position
	private int x;
	private int y;
	
	private int vx;
	private int vy;
	
	private int status = 0; //0-healthy
	
	private int time = 5000;
	private int size;
	
	//add a default constructor
	//it should randomize the position within an 800 x 600 window
	public Cell() {
		//assign vals to the instance variables
		x = (int) (Math.random() * 799);
		y = (int) (Math.random() * 599);
		
		//between -4 and 4 inclusive
		vx = (int) (Math.random() * 9) - 4;
		vy = (int) (Math.random() * 9) - 4;
		
		size = (int)(Math.random() * (25-10+1)) + 10;
	}
	
	//add a constructor that would allow the following line of code
	//Cell cell = new Cell(50, 150);
	
	public Cell(int newX, int newY) {
		
		this(); //call default constructor to set vx, vy too
		status = 1; //infected
		
		x = newX;
		y = newY;
	}
	
	public void intersects (Cell other) {
		
		//use Rectangle class to check for intersection
		Rectangle og = new Rectangle(x, y, size, size);
		Rectangle otherCell = new Rectangle(other.x, other.y, other.size, other.size);
		
		//use built-in intersect method
		if (og.intersects(otherCell)) {
			
			//check if a cell gets infected!
			if (this.status == 1 && other.status == 0) {
				other.status = 1;
			} else if (other.status == 1 && this.status == 0) {
				this.status = 1;
			}
			
			//don't let dead cells eat each other
			if (other.status == 3 || this.status == 3) {
				return;
			}
			
			if (this.size > other.size) {
				
				//this cells eats the other cell
				this.size += other.size/4;
				
				//other cell dies off-screen
				other.x = -100;
				other.y = -100;
				other.status = 3;
				other.vx = 0;
				other.y = 0;
			} else {
				other.size += this.size / 4;
				
				//the cell dies off-screen
				this.x = -100;
				this.y = -100;
				this.vx = 0;
				this.vy = 0;
				this.status = 3;
			}
			
		}
		

		
	}
	
	//add the method below
	public void paint(Graphics g) {
		
		if (status == 0) {
			g.setColor(Color.green); //healthy
		} else if (status == 1) {
			g.setColor(Color.red); //infected
			
			time -= 16;
			if (time <= 0) {
				
				//write an if-statement to simulate a 50% chance that something will happen
				if(Math.random() < .50) {
					status = 2;
				} else {
					//dead
					status = 3;
					vx = 0;
					vy = 0;
				}
				time = 5000;
			}
			
		} else if (status == 2) {
			g.setColor(Color.blue); //recovered
			
			//a recovered cell eventually becomes healthy again
			time -= 16;
			if (time <= 0) {
				status = 0;
				time = 5000;
			}
			
		} else if (status == 3) {
			g.setColor(Color.black); //dead
		}
		
		g.fillOval(x, y, this.size, this.size);
		//velocity affects position
		
		//velocity affects position
		x += vx;
		y += vy;
		
		//keep the cells in the frame view
		if (x < 0 || x >= 775) {
			vx *= -1;
		}
		
		if (y < 0 || y >= 575) {
			vy *= - 1;
		}
	}
}
