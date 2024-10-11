package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{

	public static final int WIDTH= 160 ;
	public static final int HEIGHT = WIDTH / 12*9;
	public static final int SCALE = 3;
	public static final String NAME ="JAVA Multi Adventure";
	
	private JFrame frame;
	public boolean running = false;
	private static final int FPS = 60;
	InputHandler inputHandle;
	int x=100;
	int y=100;
	int speed = 4;
	
	
	Game(){
		setPreferredSize(new Dimension(WIDTH *SCALE, HEIGHT*SCALE));
		frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		setFocusable(true);
		requestFocus();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS; //0.0167s
		double delta = 0;
		long lastTime = System.nanoTime(); // 1초는 10의 9제곱
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		
		while(running) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if (delta >= 0) {
				//1. UPDATE 캐릭터 포지션
				tick();				
				//2. DRAW	
				repaint();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				//System.out.println("FPS "+drawCount);
				drawCount =0;
				timer = 0;
			}
		}
	}
	private void init() {
		inputHandle = new InputHandler();
		this.addKeyListener(inputHandle);
		
	}
	
	private void tick() {
		if (inputHandle != null) {
            if (inputHandle.up.isPressed()) {
                y -= speed;
            }
            if (inputHandle.down.isPressed()) {
            	y += speed;
            }
            if (inputHandle.left.isPressed()) {
            	x -= speed;
            }
            if (inputHandle.right.isPressed()) {
            	x += speed;
            }
        }
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(x,y,50,50);
		g.dispose();
	}

	public void start() {
		init();
		new Thread(this).start();
		running = true;
	}
	
	public void stop() {
		running = false;
	}
	
	public static void main(String[] args) {
		new Game().start();
	}
}
