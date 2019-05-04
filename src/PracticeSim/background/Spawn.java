package PracticeSim.background;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import PracticeSim.Animal;
import PracticeSim.WildAnimal;
import PracticeSim.humanOwner;
import PracticeSim.AnimalList.AnimalList;
import PracticeSim.Assets.Assets;

public class Spawn {
	
	private Handler handler;
	private AnimalList animalList;
	private Time time;
	private Random r= new Random();
	private boolean added= false;
	private ArrayList<Animal> RandomPets= new ArrayList<Animal>();
	private String[] type = {"Dog", "Cat", "Bird"};
	private String[] breedD = {"Pitbull", "Rat Terrier", "Poodle", "Husky", "Maltese"};
	private String[] breedC = {"Siamese", "Tabicat", "Persian", "Sphinx"};
	private String[] breedB = {"Pigeon", "Hawk", "Parrot", "Parakeet"};
	private String[] PetNames = {"Max","Fluffy","Dragon","Pat","Million","Billion","Boss","King","Queen","Prince","Royal","Being","Love","Drake","Sam","Bella","Grace","Luke","Vader","Star"};
	private String[] HumanName = {"Olivia","Oliver","Amelia","Harry","Isla","Jack","Emily","George","Ava","Noah","Lily","Charlie","Mia","Jacob","Sophia","Alfie","Isabella","Freddie","Grace","Oscar"};
	
	public Spawn(Handler handler,AnimalList animalList,Time time) {
		this.handler = handler;
		this.animalList = animalList;
		this.time = time;
	}
	public void tick() {
		if(time.getHour() % 2 == 1 && time.getMinutes() == 59) {
			if(!added) {
				Animal an = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
				Animal an2 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
				//for testing animalList
//				Animal an3 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an23 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an4 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an24 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an5 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an25 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an6 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an26 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an7 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an27 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an8 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
//				Animal an28 = new Animal(ID.WildAnimal,r.nextInt(900),r.nextInt(900));
				handler.addObject(an);
				handler.addObject(an2);
				//For testing animalList
//				handler.addObject(an3);
//				handler.addObject(an23);
//				handler.addObject(an4);
//				handler.addObject(an24);
//				handler.addObject(an5);
//				handler.addObject(an25);
//				handler.addObject(an6);
//				handler.addObject(an26);
//				handler.addObject(an7);
//				handler.addObject(an27);
//				handler.addObject(an8);
//				handler.addObject(an28);
				added = true;
			}
		}
		if(time.getHour() % 2 == 0 && time.getMinutes() == 0 && time.getSeconds() == 1) {
			added = false;
		}
	}
	public ArrayList<Animal> getRandomPets() {
		return RandomPets;
	}
	
	public String getType(int e) {
		return type[e];
	}
	
	public String getBreedD(int e) {
		return breedD[e];
	}
	
	public String getBreedC(int e) {
		return breedC[e];
	}
	
	public String getBreedB(int e) {
		return breedB[e];
	}
	
	public String getHName(int e) {
		return HumanName[e];
	}
	
	public String getPName(int e) {
		return PetNames[e];
	}
	
	public humanOwner getComHuman() {
		int NumberOfPets= r.nextInt(3)+1;
		int NameSelection = r.nextInt(HumanName.length-1);
		for(int i =0;i<NumberOfPets;i++) {
			int x=r.nextInt(900);
			int y=r.nextInt(900);
			int t = r.nextInt(type.length);
			int PetSelection = r.nextInt(PetNames.length-1);
		//	int breedInt = r.nextInt(breeds.length);
			if(t == 0) {
				int breedInt = r.nextInt(breedD.length);
				RandomPets.add(new Animal(getType(t),getBreedD(breedInt),getPName(PetSelection),ID.Pet,x,y,getImage(getBreedD(breedInt))));
			}
			if(t == 1) {
				int breedInt = r.nextInt(breedC.length);
				RandomPets.add(new Animal(getType(t),getBreedC(breedInt),getPName(PetSelection),ID.Pet,x,y,getImage(getBreedC(breedInt))));
			}
			if(t == 2) {
				int breedInt = r.nextInt(breedB.length);
				RandomPets.add(new Animal(getType(t),getBreedB(breedInt),getPName(PetSelection),ID.Pet,x,y,getImage(getBreedB(breedInt))));
			}
		}
		humanOwner computer=new humanOwner(getHName(NameSelection),RandomPets);
		return computer;
	}
	public BufferedImage getImage(String e) {
		BufferedImage pic=null;
		if(e=="Pitbull") {
			pic = Assets.Pitbull;
		}
		else if(e == "Rat Terrier") {
			pic = Assets.RatTerrier;
		}
		else if(e == "Poodle") {
			pic = Assets.Poodle;
		}
		else if(e == "Parakeet") {
			pic = Assets.Parakeet;
		}
		else if(e == "Parrot") {
			pic = Assets.Parrot;
		}
		else if(e == "Hawk") {
			pic = Assets.Hawk;
		}
		else if(e == "Pigeon") {
			pic = Assets.Pigeon;
		}
		else if(e == "Sphinx") {
			pic = Assets.Sphinx;
		}
		else if(e == "Persian") {
			pic = Assets.Persian;
		}
		else if(e == "Tabicat") {
			pic = Assets.Tabicat;
		}
		else if(e == "Siamese") {
			pic = Assets.Siamese;
		}
		else if(e == "Maltese") {
			pic = Assets.Maltese;
		}
		else if(e == "Husky") {
			pic = Assets.Husky;
		}
		return pic;
	}
	public String getPetNames(int e) {
		return PetNames[e];
	}
	public String getHumanName(int e) {
		return HumanName[e];
	}
	
	
}
