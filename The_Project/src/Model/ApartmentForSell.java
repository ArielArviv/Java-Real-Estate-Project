package Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ApartmentForSell extends Apartment implements Commissionable, Serializable {

	private static final long serialVersionUID = 5341694688753214042L;
	private double askingPrice;
	private static final double pctForSell = 0.05;

	public ApartmentForSell(String adress, double area, int numOfRooms, int agentRating, double askingPrice) {
		super(adress, area, numOfRooms, agentRating);
		setAskingPrice(askingPrice);
		type = Agency.eType.ApartmentForSell;
	}

	//getters:
	public double getAskingPrice() {
		return askingPrice;
	}

	//setters:
	public void setAskingPrice(double askingPrice) throws NumberFormatException {
		if(askingPrice < 0) {
			throw new NumberFormatException("You need to insert a number between 1 and 10 (both included)...");
		}
		this.askingPrice = askingPrice;

	}

	//Inherited Methods:
	@Override
	public double getTotalComm() {
		System.out.println();
		return pctForSell * getAskingPrice();
	}
	
	@Override
	public void saveToFile(ObjectOutputStream out) throws IOException {
		super.saveToFile(out);
		out.writeDouble(askingPrice);
	}


	@Override
	public String toString() {
		return super.toString() + "Asking Price: " + askingPrice + "\n" ;
	}








}
