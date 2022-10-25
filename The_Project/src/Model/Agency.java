package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


public class Agency implements Serializable{

	public enum eType {
		ApartmentForSell,
		RegularForRent,
		AirBnbForRent
	};

	private static final long serialVersionUID = 1L;
	private final String FILE = "data.txt";
	private ArrayList<Apartment> allApartments;
	private StringBuffer sb;

	public Agency() throws IOException, ClassNotFoundException {
		this.allApartments = new ArrayList<>(0);
		sb = new StringBuffer();
		readFromFile();
	}

	//getter:

	public ArrayList<Apartment> getAllApartments() {
		return allApartments;
	}

	public boolean getKindOfApt(String aptAddress) {
		String aptKind = "";
		for(int i=0;i<allApartments.size();i++) {
			if(getAllApartments().get(i).getAdress().equals(aptAddress)) {
				aptKind = (getAllApartments().get(i)).getClass().getSimpleName();
			}
		}
		if(!(aptKind.equals("ApartmentForSell"))) {
			return true;
		}else{
			return false;
		}
	}

	//Methods:

	//adding apartment to the agency:
	public void addAptToAgency(Apartment apartment) {
		allApartments.add(apartment);
	}

	public void addAptToAgency(String address, double area, int numOfRooms, int rating, eType type, int price, int leaseDuration) {
		Apartment apt = null;
		if (type.name().equals(eType.AirBnbForRent.name()))
			apt = new AirBnbForRent(address, area, numOfRooms, rating, rating, rating);
		else if (type.name().equals(eType.ApartmentForSell.name()))
			apt = new ApartmentForSell(address, area, numOfRooms, rating, rating);
		else if (type.name().equals(eType.RegularForRent.name()))
			apt = new RegularForRent(address, area, numOfRooms, rating, rating, rating);
		allApartments.add(apt);
	}

	//search apartment by address:
	public boolean searchAptByAddress(String address){
		for(int i=0;i<allApartments.size();i++) {
			if(getAllApartments().get(i).getAdress().equalsIgnoreCase(address)) {  
				return true;
			}
		}
		return false;
	}

	//check if client exist:
	public boolean checkIfClientExist(String clientNumber) {
		for(int i=0;i< getAllApartments().size();i++) {
			for(int j=0;j<getAllApartments().get(i).getInterestedClients().size();j++) {
				if(getAllApartments().get(i).getInterestedClients().get(j).getNumber().equals(clientNumber)) {
					return true;
				}
			}
		}
		return false;
	}

	//check lease duration for apartment for rent:
	public String searchingInRentApt(int wantedLease) {
		ApartmentForRent tmp = null;
		String str = null;
		for(int i=0;i<allApartments.size();i++) {
			if(allApartments.get(i) instanceof ApartmentForRent) {
				if(tmp == null || ((ApartmentForRent)(getAllApartments().get(i))).getPrice() * wantedLease > tmp.getPrice() * wantedLease ) {
					tmp = (ApartmentForRent)(allApartments.get(i));
				}
			}
		}
		if(tmp == null) {
			str = "There are no apartment for this lease duration...";
		} else {
			str = "The most expensive apartment for duration of " + wantedLease + " is: \n" + tmp;
		}
		return str;
	}

	//add client to apartment:
	public void insertClientToApt(String aptAddress, String clientName, String clientNumber) {
		int count = 0;
		for(int i=0;i<allApartments.size();i++) {
			if(getAllApartments().get(i).getAdress().equals(aptAddress)) {
				count = i;
				break;
			}
		}
		//adding the client to the wanted apartment:
		InterestedClient newClient = new InterestedClient(clientName, clientNumber);
		getAllApartments().get(count).addClientToApt(newClient);
	}

	//check if apartment has a commission on it:
	public void checkCommission(Apartment apt) {
		if (apt instanceof Commissionable) {
			sb.append("Total Commision: " + ((Commissionable)(apt)).getTotalComm() + "\n");
		}
	}

	//show methods:
	public String showAptInfo() {
		sb.setLength(0);
		//StringBuffer sb = new StringBuffer();
		for(int i=0;i<getAllApartments().size();i++) {
			sb.append(allApartments.get(i).toString());
			checkCommission(allApartments.get(i));
			sb.append("\n");
			//System.out.println(getAllApartments().get(i).toString());
		}
		return sb.toString();
	}

	public String showAptInfoByKind(String aptKind) {
		sb.setLength(0);
		for(int i=0;i<allApartments.size();i++) {
			if(allApartments.get(i).getClass().getSimpleName().equals(aptKind)) {
				sb.append(allApartments.get(i).toString());
				checkCommission(allApartments.get(i));
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public double printFullPrice(String address) {
		double fullPrice = 0.0;
		for(int i=0;i<allApartments.size();i++) {
			if(getAllApartments().get(i).getAdress().equals(address)) {
				fullPrice = ((ApartmentForRent)(getAllApartments().get(i))).calculatePrice();
			}
		}
		return fullPrice;
	}

	public String printAllClients(String addres) {
		sb.setLength(0);
		for(int i=0; i<allApartments.size() ;i++) {
			if(getAllApartments().get(i).getAdress().equals(addres)) {
				for(int j=0; j<allApartments.get(i).getInterestedClients().size() ;j++) {
					sb.append(allApartments.get(i).getInterestedClients().get(j).toString());
				}
			}
		}
		return sb.toString();
	}

	public String[] printSortedCLients(String addres) {
		int numOfClients = 0;

		//getting the number of interested clients - for the wanted apartment:
		for(int i=0; i<allApartments.size() ;i++) {
			if(getAllApartments().get(i).getAdress().equals(addres)) {
				numOfClients = getAllApartments().get(i).getInterestedClients().size();
			}
		}

		String allNames[] = new String[numOfClients];

		//for every apartment - when getting to wanted apartment:
		for(int i=0; i<allApartments.size() ;i++) {
			if(getAllApartments().get(i).getAdress().equals(addres)) {
				//for all clients in wanted apartment - insert their names into an array: 
				for(int j=0; j<allApartments.get(i).getInterestedClients().size() ;j++) {
					allNames[j] = getAllApartments().get(i).getInterestedClients().get(j).getName();
				}
			}
		}
		String sortedNames[] = bubbleSort(allNames);
		return sortedNames;
	}
	private String[] bubbleSort(String[] allNames) {
		String sortedNames[] = new String[allNames.length];
		String temp;
		int count = 0;
		System.out.println("Names in sorted order:");
		for (int j = 0; j < allNames.length; j++) {
			for (int i = j + 1; i < allNames.length; i++) {
				// comparing adjacent strings
				if (allNames[i].compareTo(allNames[j]) < 0) {
					temp = allNames[j];
					allNames[j] = allNames[i];
					allNames[i] = temp;
				}
			}
			sortedNames[count++] = allNames[j];
		}
		return sortedNames;
	}

	public String printArray (String[] arr) {
		sb.setLength(0);
		for(int i=0; i<arr.length ;i++) {
			sb.append(arr[i].toString() + "\n");
		}
		return sb.toString();
	}
	
	public void exit() throws IOException {
		saveToFile();
	}
	
	//clone:
	public String duplicate(String address) {
		String type = getTypeByAdderess(address);
		System.out.println(type);
		int idx = getAptIndex(address);
		System.out.println(idx);
		eType tmp = eType.valueOf(type);
		Apartment from = allApartments.get(idx), to = null;
		ArrayList<InterestedClient> cCopy = new ArrayList<>();

		cCopy = from.getClientsCopy();

		if (tmp.equals(eType.AirBnbForRent)) {
			to = new AirBnbForRent(from.getAdress(), from.getArea(), from.getNumOfRooms()
					, from.getAgentRating(), ((AirBnbForRent)(from)).getLeaseDuration(),
					((AirBnbForRent)(from)).getPrice());
		} else if (tmp.equals(eType.ApartmentForSell)) {
			to = new ApartmentForSell(from.getAdress(), from.getArea(), from.getNumOfRooms()
					, from.getAgentRating(), ((ApartmentForSell)(from)).getAskingPrice());
		} else if (tmp.equals(eType.RegularForRent)) {
			to = new RegularForRent(from.getAdress(), from.getArea(), from.getNumOfRooms(),
					from.getAgentRating(), ((RegularForRent)(from)).getLeaseDuration(),
					((RegularForRent)(from)).getPrice());
		}

		to.setInterestedClients(cCopy);
		allApartments.add(idx+1,  to);
		return  to.toString();
	}
	private String getTypeByAdderess(String address) {
		for (Apartment a : allApartments) {
			if (a.adress.equalsIgnoreCase(address)) {
				return a.getClass().getSimpleName();
			}
		}
		return null;
	}
	private int getAptIndex(String address) {
		for (int i = 0; i < allApartments.size(); i++) {
			if (allApartments.get(i).adress.equalsIgnoreCase(address))
				return i;
		}
		return -1;
	}

	//save:
	private void saveToFile() throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(FILE)));
		out.writeInt(allApartments.size());
		for (Apartment a : allApartments) {
			a.saveToFile(out);	
		}
		out.close();
	}
	
	//read file:
	private void readFromFile() throws IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(FILE)));
		int size = in.readInt();
		Apartment apt;
		int maxId = Apartment.getIdGenerator();
		for (int i = 0; i < size; i++) {
			apt = readAptFromFile(in);
			addAptToAgency(apt);
			if (maxId < apt.getId())
				maxId = apt.getId();
		}
		Apartment.setIdGenerator(maxId);
	}

	
	private Apartment readAptFromFile(ObjectInputStream in) throws ClassNotFoundException, IOException {
		int type = in.readInt();
		int id = in.readInt();
		String address = in.readUTF();
		double area = in.readDouble();
		int numOfRooms = in.readInt();
		int agentRating = in.readInt();
		int cSize = in.readInt();
		InterestedClient c;
		ArrayList<InterestedClient> clients = new ArrayList<>();
		for (int i = 0; i < cSize; i++) {
			c = (InterestedClient) in.readObject();
			clients.add(c);
		}
		double price = in.readDouble();
		Apartment apt;
		if (type != 0) {
			int leaseDuration = in.readInt();
			if (type == 2) 
				apt = new AirBnbForRent(address, area, numOfRooms, agentRating, leaseDuration, price);
			else 
				apt = new RegularForRent(address, area, numOfRooms, agentRating, leaseDuration, price);
		} 
		else 
			apt = new ApartmentForSell(address, area, numOfRooms, agentRating, price); 
		apt.setId(id);
		apt.setClients(clients);
		return apt;
	}



}




