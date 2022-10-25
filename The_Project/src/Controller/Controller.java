package Controller;

import java.io.IOException;

import Model.Agency;
import View.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Controller {
	private Agency theModel;
	private View theView;
	private boolean fieldsAdded = false, isRent = false;

	public Controller(Agency model, View view) {
		theModel = model;
		theView = view;

		//All Events:
		
		// add apartment process: ---------------------------------------------------------------------
		EventHandler<ActionEvent> addApartmentProcess = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showAddAptGrp();
				fieldsAdded = false;
			}
		};
		theView.getAddApartmentButton().setOnAction(addApartmentProcess);

		EventHandler<ActionEvent> addAptSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit add apartment");
				try {
					String address = theView.getAddressText();
					if (address.isEmpty() || address.equals("")) {
						theView.showAlert("Please enter an address", "error");
						return;
					}
					double area = Double.parseDouble(theView.getAreaText());	
					int numOfRooms = Integer.parseInt(theView.getNumOfRoomsText());
					int rating = theView.getRating();
					String aptType = theView.getAptTypeText();
					if (fieldsAdded) {
						int price = 0, leaseDuration = 0;
						if (isRent) {
							leaseDuration = Integer.parseInt(theView.getLeaseDurationText());
							if (leaseDuration <= 0) {
								theView.showAlert("Please enter a positive number in lease duration", "error");
								return;
							}
							price = Integer.parseInt(theView.getRentPriceText());
							if (price <= 0) {
								theView.showAlert("Please enter a positive number in rent price", "error");
								return;
							}
						} else if (!isRent) {
							price = Integer.parseInt(theView.getSellPriceText());
							if (price <= 0) {
								theView.showAlert("Please enter a positive number in sell price", "error");
								return;
							}
							//							theModel.addAptToAgency(address, area, numOfRooms, rating, Agency.eType.ApartmentForSell, price, 0);
							//							System.out.println(address + area + numOfRooms + rating + price);
						}
						theModel.addAptToAgency(address, area, numOfRooms, rating, Agency.eType.valueOf(aptType), price, leaseDuration);
						System.out.println(address + area + numOfRooms + rating + price + leaseDuration);
						theView.showAlert("Apartment added successfully", "confirmation");
						theView.clear();
					} else {
						if (aptType.equals(Agency.eType.ApartmentForSell.name())) {
							theView.showSellFields();
							fieldsAdded = true;
						} else {
							theView.showRentFields();
							fieldsAdded = true;
							isRent = true;
						}					
					}
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}	
		};		
		theView.getSubmitAddApartmentButton().setOnAction(addAptSubmit);

		
		
		// add client process: -------------------------------------------------------------------------
		EventHandler<ActionEvent> addClient = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showAddClientGrp();
			}
		};
		theView.getAddClientButton().setOnAction(addClient);

		EventHandler<ActionEvent> addClientSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit add client");
				boolean isClientExist = false, isAptExist = false;
				try {
					String address = theView.getAptAddressText();
					if (address.isEmpty() || address.equals("")) {
						theView.showAlert("Please enter an address", "error");
						return;
					}
					String clientName = theView.getClientNameText();
					if (clientName.isEmpty() || clientName.equals("")) {
						theView.showAlert("Please enter a name", "error");
						return;
					}
					String clientNumber = theView.getClientNumberText();
					if (clientNumber.isEmpty() || clientNumber.equals("")) {
						theView.showAlert("Please enter a number", "error");
						return;
					}
					
					isClientExist = theModel.checkIfClientExist(clientNumber);
					if(isClientExist) {
						theView.showAlert("This Client Already Exist!", "error");
						return;
					} else if(!isClientExist) {
						isAptExist = theModel.searchAptByAddress(address);
						if(!isAptExist) {
							theView.showAlert("This Address Does Not Exist!", "error");
							return;
						}
						theModel.insertClientToApt(address, clientName, clientNumber);
						System.out.println(address + " "+ clientName + " " + clientNumber);
						//theView.clientAddedMessage();
						theView.showAlert("Client added successfully", "confirmation");
					}
					theView.clear();
					
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}	
		};		
		theView.getSubmitAddClientButton().setOnAction(addClientSubmit);
		
		
		
		// show all apartments process: -----------------------------------------------------------------
		EventHandler<ActionEvent> showAllApartments = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.showInfo(theModel.showAptInfo());
			}
		};
		theView.getShowAllApartmentsButton().setOnAction(showAllApartments);	
		
		
		
		//show apartment by kind process: ---------------------------------------------------------------
		EventHandler<ActionEvent> showAptByKind = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showAptByKindGrp();
			}
		};
		theView.getShowAptByKindButton().setOnAction(showAptByKind);
		
		EventHandler<ActionEvent> showAptByKindSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				String type = theView.getAptTypeKindText();
				theView.showInfo(theModel.showAptInfoByKind(type));
				theView.clear();

			}
		};		
		theView.getShowAptByKindSubmitButton().setOnAction(showAptByKindSubmit);

		
		
		//show full price per apartment process: --------------------------------------------------------
		EventHandler<ActionEvent> showFullPrice = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showFullPriceGrp();
			}
		};
		theView.getShowFullPriceButton().setOnAction(showFullPrice);
		
		EventHandler<ActionEvent> showFullPriceSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit full price");
				boolean isAptExist = false, isForRent = false;
				double fullPrice = 0.0;
				try {
					String address = theView.getAddressPriceText();
					if (address.isEmpty() || address.equals("")) {
						theView.showAlert("Please enter an address", "error");
						return;
					}
					
					isAptExist = theModel.searchAptByAddress(address);
					if(!isAptExist) {
						theView.showAlert("This Address Does Not Exist!", "error");
						return;
					} else {
						isForRent = theModel.getKindOfApt(address);
						if(!isForRent) {
							theView.showAlert("This kind of apartment is an apartment For Sell, not For Rent... \n", "error");
						} else {
							fullPrice = theModel.printFullPrice(address);
							String str = "The price for this apartment is: " + fullPrice + " shekels.\n";
							theView.showInfo(str);
						}
					}
					theView.clear();

					
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}	
		};
		theView.getShowFullPriceSubmitButton().setOnAction(showFullPriceSubmit);
		
		
		
		//show most expensive for rent process: ---------------------------------------------------------
		EventHandler<ActionEvent> showExpRent = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showExpRentGrp();
			}
		};
		theView.getShowExpRentButton().setOnAction(showExpRent);
		
		EventHandler<ActionEvent> showExpRentSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit most expensive");
				int leaseDuration = 0;
				try {
					leaseDuration = Integer.parseInt(theView.getLeaseText());
					if (leaseDuration <= 0) {
						theView.showAlert("Please enter a positive number in lease duration", "error");
						return;
					}
					theView.showInfo(theModel.searchingInRentApt(leaseDuration));
					theView.clear();

					
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}
		};
		theView.getShowExpRentSubmitButton().setOnAction(showExpRentSubmit);

		
		
		//show all client for specific apartment process: -----------------------------------------------
		EventHandler<ActionEvent> showClientByApt = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showClientByAptGrp();
			}
		};
		theView.getShowClientByAptButton().setOnAction(showClientByApt);
		
		EventHandler<ActionEvent> showClientByAptSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit show client by apartment");
				boolean isAptExist = false;
				try {
					String address = theView.getAddressClientText();
					if (address.isEmpty() || address.equals("")) {
						theView.showAlert("Please enter an address", "error");
						return;
					}
					isAptExist = theModel.searchAptByAddress(address);
					if(!isAptExist) {
						theView.showAlert("This Address Does Not Exist!", "error");
						return;
					}
					theView.showInfo(theModel.printAllClients(address)); 
					theView.clear();
					
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}
				
		};
		theView.getShowClientByAptSubmitButton().setOnAction(showClientByAptSubmit);

		
		
		//show all sorted client for specific apartment process: ----------------------------------------
		EventHandler<ActionEvent> showSortedClient = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				theView.clear();
				theView.showSortedClientGrp();
			}
		};
		theView.getShowSortedClientButton().setOnAction(showSortedClient);
		
		EventHandler<ActionEvent> showSortedClientSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit show client by apartment");
				boolean isAptExist = false;
				try {
					String address = theView.getAddressClientSortedText();
					if (address.isEmpty() || address.equals("")) {
						theView.showAlert("Please enter an address", "error");
						return;
					}
					isAptExist = theModel.searchAptByAddress(address);
					if(!isAptExist) {
						theView.showAlert("This Address Does Not Exist!", "error");
						return;
					}
					String sortedNames[] = theModel.printSortedCLients(address);
					theView.showInfo(theModel.printArray(sortedNames)); 
					theView.clear();
					
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}
		};
		theView.getShowSortedClientSubmitButton().setOnAction(showSortedClientSubmit);
		
		
		
		//clone apartment and show it -------------------------------------------------------------------
		EventHandler<ActionEvent> showCloneApt = new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						theView.clear();
						theView.showCloneAptGrp();
					}
				};
		theView.getCloneButton().setOnAction(showCloneApt);
		
		EventHandler<ActionEvent> showCloneAptSubmit = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("submit show cloned apartment");
				boolean isAptExist = false;
				try {
					String address = theView.getAddressCloneText();
					if (address.isEmpty() || address.equals("")) {
						theView.showAlert("Please enter an address", "error");
						return;
					}
					isAptExist = theModel.searchAptByAddress(address);
					if(!isAptExist) {
						theView.showAlert("This Address Does Not Exist!", "error");
						return;
					}
					//logic:
					theView.showInfo(theModel.duplicate(address));
					theView.clear();
					
				} catch(IllegalArgumentException e) {
					theView.showAlert(e.getMessage(), "error");
				} catch(Exception e) {
					theView.showAlert(e.getMessage(), "error");
				}
			}
		};
		theView.getCloneSubmitButton().setOnAction(showCloneAptSubmit);
		
		
		
		//exit: ----------------------------------------------------------------------------------------- 
		EventHandler<ActionEvent> exitProgram = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				try {
					theModel.exit();
					theView.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		theView.getExitButton().setOnAction(exitProgram);



	}



}
