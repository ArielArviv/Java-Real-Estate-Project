package Model;

public class AirBnbForRent extends ApartmentForRent {

	private static final long serialVersionUID = 968344012246687399L;

	public AirBnbForRent(String adress, double area, int numOfRooms, int agentRating, int leaseDuration, double pricePerDay) {
		super(adress, area, numOfRooms, agentRating, leaseDuration, pricePerDay);
		type = Agency.eType.AirBnbForRent;
	}
	
	//Inherited Methods:
	@Override
	public double calculatePrice() {
		return getLeaseDuration() * getPrice();
	}

	@Override
	public String toString() {
		return super.toString() + "lease duration is: " + getLeaseDuration() + "\nPrice per day: " + getPrice() + "\n";
	}	
	
	
	
}
