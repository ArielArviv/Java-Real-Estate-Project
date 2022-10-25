package Model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Apartment implements Cloneable, Serializable {
	
	private static final long serialVersionUID = 9070721259302140056L;
	private static int idGenerator = 1000;
	protected int id;
	protected String adress;
	protected double area;
	protected int numOfRooms;
	protected int agentRating;
	protected ArrayList<InterestedClient> interestedClients;
	protected Agency.eType type;
	
	public Apartment(String adress, double area, int numOfRooms, int agentRating) throws IllegalArgumentException{
		super();
		this.id = ++idGenerator;
		setAdress(adress);
		this.area = area;
		this.numOfRooms = numOfRooms;
		setAgentRating(agentRating);
		this.interestedClients = new ArrayList<InterestedClient>(0);
	}
	
	//getters:
	public String getAdress() {
		return adress;
	}
	
	public int getId() {
		return id;
	}

	public double getArea() {
		return area;
	}
	
	public int getNumOfRooms() {
		return numOfRooms;
	}

	public int getAgentRating() {
		return agentRating;
	}
	
	public ArrayList<InterestedClient> getInterestedClients() {
		return interestedClients;
	}
	
	public int getType() {
		return type.ordinal();
	}

	
	//setters:
	public void setInterestedClients(ArrayList<InterestedClient> interestedClients) {
		this.interestedClients = interestedClients;
	}

	public static int getIdGenerator() {
		return idGenerator;
	}

	public static void setIdGenerator(int idGenerator) {
		Apartment.idGenerator = idGenerator;
	}

	public void setAdress(String adress) throws IllegalArgumentException {
		if(adress == null || adress.length() == 0) {
			throw new IllegalArgumentException("you need to insert a valid address for an apartment...");
		}
		this.adress = adress;
	}

	public void setArea(double area) {
		if(area <= 0) {
			throw new IllegalArgumentException("You need to insert a valid number for the area of the apartment...");
		}		
		this.area = area;
	}

	public void setNumOfRooms(int numOfRooms) {
		if(numOfRooms <= 0) {
			throw new IllegalArgumentException("You need to insert valid number for the number of rooms...");
		}
		
		this.numOfRooms = numOfRooms;
	}

	public void setAgentRating(int agentRating) {
		this.agentRating = agentRating;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setClients(ArrayList<InterestedClient> clients) {
		this.interestedClients = clients;
	}

	
	//Methods:
	public Apartment addClientToApt(InterestedClient client) {
		if (interestedClients.add(client))
			return this;
		return null;
	}
	
	public ArrayList<InterestedClient> getClientsCopy(){
		ArrayList<InterestedClient> copy = new ArrayList<>();
		for (int i = 0; i < interestedClients.size(); i++) {
			copy.add(new InterestedClient(interestedClients.get(i)));			
		}
		if (copy.isEmpty())
			return null;
		return copy;
	}
	
	//save:
	public void saveToFile(ObjectOutputStream out) throws IOException {
		out.writeInt(getType());
		out.writeInt(id);
		out.writeUTF(adress);
		out.writeDouble(area);
		out.writeInt(numOfRooms);
		out.writeInt(agentRating);
		out.writeInt(interestedClients.size());
		for (InterestedClient c : interestedClients) 
			out.writeObject(c);
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apartment other = (Apartment) obj;
		if (adress == null) {
			if (other.adress != null)
				return false;
		} else if (!adress.equals(other.adress))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Type: " + getClass().getSimpleName() + "\n");
		sb.append("Apartment's ID: " + id);
		sb.append("\nAdress: " + adress + "\n");
		sb.append("Area: " + area + "\n");
		sb.append("number of Rooms: " + numOfRooms + "\n");
		sb.append("Agent's Rating: " + agentRating + "\n");
		sb.append("Interested Clients:\n");
		//for loop for extra info about apartments - use casting.
		
		for(int i=0;i<interestedClients.size();i++) {
			sb.append( (i+1) + ") " + interestedClients.get(i).toString() + "\n");
		}
		return sb.toString();
		
	
	}
	
//	@Override
//    public Apartment clone() throws CloneNotSupportedException {
//        Apartment tmp = (Apartment)super.clone();
//        tmp.interestedClients = new ArrayList<>(getInterestedClients());
//		return tmp;
//    }
	
	
	
	
	
}
