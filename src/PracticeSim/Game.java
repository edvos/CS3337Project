package PracticeSim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import PracticeSim.AnimalList.AnimalList;
import PracticeSim.Assets.Assets;
import PracticeSim.Menus.CreationMenu;
import PracticeSim.Menus.Menu;
import PracticeSim.actions.ActionSection;
import PracticeSim.background.GameObject;
import PracticeSim.background.Handler;
import PracticeSim.background.ID;
import PracticeSim.background.KeyManager;
import PracticeSim.background.Spawn;
import PracticeSim.background.Time;


public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -5495016350964169983L;
	public static final int WIDTH = 1200, HEIGHT = WIDTH / 12 * 9;
	//this will hold the users info and do all the actions with names.
	public humanOwner user;
	
	public Thread thread;
	private boolean running =false;
	private int Ccounter=0;
	private Random r;
	public Window window;
	private Handler handler;
	private AnimalList aList;
	public ActionSection action;
	
	private boolean picked=false;
	private KeyManager keyManager;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private boolean collision;
	private Spawn spawn;
	private Time time;
	
	private Menu menu;
	private CreationMenu creation;
	public enum STATE{
		Menu, Creation, GameHome, GamePark
	};
	
	public STATE gameState = STATE.Menu;

	public Game() {
		handler = new Handler(this);
		menu = new Menu(this,handler);
		aList = new AnimalList(handler);
		creation = new CreationMenu(this,handler, aList);
		keyManager = new KeyManager();
		
		this.addMouseListener(menu);
		this.addMouseListener(creation);
		
		
		collision = false;
		
		window = new Window(WIDTH, HEIGHT, "PETS Simulator", this);
		//window.getFrame().addKeyListener(keyManager);
		//window.getCanvas().addKeyListener(keyManager);
		this.addKeyListener(getKeyManager());
		
		action = new ActionSection(this);
		
		time = new Time();
		spawn = new Spawn(handler,aList,time);
		
		r = new Random();
		
	}

	public synchronized  void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
		Assets.init();
	}
	
	public synchronized  void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		int fps = 60;
		double timePerTick= 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick; 
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >=1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000) {
				System.out.println("Ticks and Frames: "+ ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
		
	}
	
	public void pickAanimal() {
		if(user.pets.size()==1) {
			user.activePet = user.pets.get(0);
		}
		else {
			Object[] options = new Object[user.pets.size()];
			for (int i = 0; i < user.pets.size(); i++) {
				options[i] = user.pets.get(i).getName();
			}
			int choice = JOptionPane.showOptionDialog(null, "Which animal would you like to play with?",
					"Chose who to play with?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					options, options[0]);

			user.activePet = user.pets.get(choice);
		}
		
		picked = true;
	}
	
	private void tick() {
		handler.tick();
		keyManager.tick();
		
		
		if(gameState == STATE.GameHome) {
			aList.tick();
			addTextArea();
			//action.tick();
			collision();
			time.addMin(1);
		}
		else if(gameState == STATE.GamePark) {
			
			
			aList.tick();
			spawn.tick();
			time.addMin(1);
			if(user.pets.size()>=1 && !picked) {
				pickAanimal();
			}
			collision();
			if(collision) {
				window.area.append("Animals interacted\n");
			}
		}
		else if(gameState == STATE.Menu) {
			menu.tick();
		}
		else if(gameState == STATE.Creation) {
			creation.tick();
		}
		
	}
	
	private void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == STATE.GameHome) {
			
			g.drawImage(Assets.home, 0, 0, null);
			aList.render(g);
			action.render(g);
			time.render(g);
			
		}
		else if(gameState == STATE.GamePark) {
			
			g.drawImage(Assets.park, 0, 0, null);
			aList.render(g);
			action.render(g);
			time.render(g);
			if(collision) {
				System.out.println("animal interaction");
				window.area.append("animal interaction\n");
			}
			
			
		}
		else if(gameState == STATE.Menu) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			menu.render(g);
		}
		else if(gameState == STATE.Creation) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
			creation.render(g);
		}
		
		g.dispose();
		bs.show();
		
	}
	public void collision() {
		for (int i = 0; i < handler.object.size()-1; i++) {
			GameObject tempObject = handler.object.get(i);
			GameObject tempObject2 = handler.object.get(i+1);

			if (tempObject.getId() == ID.WildAnimal && tempObject2.getId() == ID.WildAnimal) {
				if (tempObject2.getBounds().intersects(tempObject.getBounds())) {
					// collision code
					collision = true;
					
				}
				else 
					collision = false;
			}

		}
	}

	public static int clamp(int var, int min, int max) {
		if(var >= max) {
			return var = max;
		}
		else if(var <= min) {
			return var = min;
		}
		else {
			return var;
		}
	}
	public Window getWindow() {
		return window;
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	public humanOwner getHuman() {
		return user;
	}
	public void addTextArea() {
		JTextArea textarea = new JTextArea(5,25);
		textarea.setEditable(false);
		textarea.setBackground(Color.gray);
		//textarea.setBounds(50, 600, 800, 280);
		DefaultCaret caret = (DefaultCaret) textarea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		JScrollPane scrollBar = new JScrollPane(textarea);
		scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		window.getFrame().getContentPane().add(scrollBar);
		
	}
	

	public static void main(String args[]) {
		new Game();
	}

}
