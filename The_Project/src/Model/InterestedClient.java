package Model;

import java.io.Serializable;

public class InterestedClient implements Serializable {
	
	private static final long serialVersionUID = -4923223541884064565L;
	private String name;
	private String number;
	
	
	public InterestedClient(String name, String number) {
		super();
		this.name = name;
		this.number = number;
	}
	
	public InterestedClient(InterestedClient interestedClient) {
		this(interestedClient.name, interestedClient.number);
	}
	
	
	//getters:
	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		
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
		InterestedClient other = (InterestedClient) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Name: " + name + " | Number: " + number + "\n";
	}

		
	



	


	
	
	
	
	
	
}
