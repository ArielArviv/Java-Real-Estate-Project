package Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class ApartmentForRent extends Apartment implements Serializable {
	
	private static final long serialVersionUID = -3938816306420206885L;
	protected int leaseDuration;
	protected double price;
	
	public ApartmentForRent(String adress, double area, int numOfRooms, int agentRating, int leaseDuration,double price) {
		super(adress, area, numOfRooms, agentRating);
		this.leaseDuration = leaseDuration;
		setPrice(price);
	}
	
	//getters:
	public double getPrice() {
		return price;
	}
	
	public int getLeaseDuration() {
		return leaseDuration;
	}
	
	//setters:
	public void setPrice(double price) throws NumberFormatException {
		if(price < 0) {
			throw new NumberFormatException("You need to insert a number between 1 and 10 (both included)...");
		}
		this.price = price;
	}

	public void setLeaseDuration(int leaseDuration) throws IllegalAccessException {
		if(leaseDuration <= 0) {
			throw new IllegalArgumentException("you entered a negative number, instead of a positive one...");
		}
		this.leaseDuration = leaseDuration;
	}
	
	//Abstract Methods:
	public abstract double calculatePrice();
	
	@Override
	public void saveToFile(ObjectOutputStream out) throws IOException {
		super.saveToFile(out);
		out.writeDouble(price);
		out.writeInt(leaseDuration);
	}

	
	
}
