package Model;

import java.io.Serializable;

public class RegularForRent extends ApartmentForRent implements Commissionable, Serializable{
	
	private static final long serialVersionUID = -2092468339984269086L;
	private static final int comForRent = 4000;
	
	public RegularForRent(String adress, double area, int numOfRooms, int agentRating, int leaseDuration, double pricePerMonth) {
		super(adress, area, numOfRooms, agentRating, leaseDuration, pricePerMonth);
		type = Agency.eType.RegularForRent;
	}

	//Inherited Methods:
	@Override
	public double calculatePrice() {
		return getLeaseDuration() * getPrice();
	}

	@Override
	public double getTotalComm() {
		
		return comForRent;
	}
	
	//toString:
	@Override
	public String toString() {
		return super.toString() + "lease duration is: " + getLeaseDuration() + "\nPrice per day: " + getPrice() + "\n";
	}
	
}
