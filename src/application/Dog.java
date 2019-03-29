package application;

import java.awt.image.BufferedImage;

import application.gfx.Assets;

public class Dog extends Animal implements AnimalActions  {
	String name;
	String breed;
	private BufferedImage image = Assets.dog;
	
	public Dog(String type, String name, String breed) {
		super(type);
		this.name = name;
		this.breed = breed;
	}
	
	@Override
	public BufferedImage getAsset() {
		return image;
	}
	
	public String getName() {
		return name;
	}
	public String getBreed() {
		return breed;
	}

	@Override
	public String Fly() {
		String action="Dog looks at you Funny!";
		return action;
	}
	@Override
	public String Catch( ) {
		String action="Dog get a hold of ";
		return action;
	}
	@Override
	public String Speak() {
		String action="Braks!!";
		return action;
	}
	@Override
	public String Chase() {
		String action="";
		return action;
	}
	@Override
	public String Flee() {
		String action="";
		return action;
	}
	@Override
	public String Bite() {
		String action="";
		return action;
	}
	@Override
	public String Play() {
		String action="";
		return action;
	}
	@Override
	public String Fetch() {
		String action="";
		return action;
	}
	@Override
	public String Lick() {
		String action="";
		return action;
	}
	@Override
	public String Rest() {
		String action="";
		return action;
	}

}
