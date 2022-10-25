package View;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class View {
	private Button addApartment, addClient,showApt, showByKind, showPrice, showExpRent, showClientByApt, showSortedClient,cloneApt,
				   exitProgram, submittors[];

	private Group root, grpAddApartment, grpSell, grpRent, grpAddClient, grpShow, 
	grpShowByKind, grpShowPrice,grpShowExpRent, grpShowClientByApt, grpShowSortedClient, grpCloneApt;

	private Label lblAddress, lblArea, lblNumOfRooms, lblRating, lblType, lblLeaseDuration, lblRentPrice, lblAptPrice,lblAptAddress, 
	lblName, lblnumber,lblAddressPrice,lblLease,lblWantedAddress,lblAddressForSort,lblAddressForClone;

	private TextField tfAddress, tfArea, tfNumOfRooms, tfLeaseDuration, tfRentPrice, tfAptPrice,tfAptAddress, tfName, tfNumber,tfAddressPrice,
	tfLease,tfWantedAddress,tfAddressForSort,tfAddressForClone;
	
	private Stage stage;
	private Label title,subTitle;
	
	private ComboBox<Integer> cmRating;
	private ComboBox<String> cmType;
	private ComboBox<String> cmTypeKind;
	

	//setting the view:
	public View(Stage stage) {
		root = new Group();
		submittors = new Button[8];
		for (int i = 0; i < submittors.length; i++) {
			submittors[i] = new Button("Submit");
			submittors[i].setVisible(false);
			root.getChildren().add(submittors[i]);
		}

		//Titles:
		setTitles();

		//13. add apartment handling
		addApartmentProcess();

		//14. add client handling:
		addClientProcess();

		//15. show apartments and their kind handling:
		showAptProcess();

		//16. show apartments by kind handling:
		showAptByKindProcess();

		//17. showing the full price-per-lease-duration, for a specific apartment:
		showFullPriceProcess();

		//18. showing the most expensive apartment, out of all the ones for rent - for a specific lease duration:
		showExpRentProcess();

		//19. showing all the interested clients, for a specific apartment:
		showClientsProcess();

		//20. showing all the interested client for a specific apartment - but sorted by name:
		showSortedClientsProcess();
		
		//25. clone an apartment:
		cloneApt();

		//exit program:
		exitProgram();

		//setting final view:
		root.getChildren().addAll(title,subTitle, 
				addApartment, grpAddApartment, 
				addClient, grpAddClient, 
				showApt, grpShow, 
				showByKind ,grpShowByKind,
				showPrice, grpShowPrice,
				showExpRent,grpShowExpRent,
				showClientByApt, grpShowClientByApt,
				showSortedClient, grpShowSortedClient,
				grpSell,grpRent,
				grpCloneApt,cloneApt,
				exitProgram);
		Scene scene = new Scene(root, 800, 500);
		this.stage = stage;
		this.stage.setScene(scene);
		this.stage.show();
	}
	
	// Generic Methods:
	
	//set titles:
	// environment:
	private void setTitles() {
		title = new Label("Hello, and welcome to 'Real Housing' agency!");
		titleLabelSet(title, 50, 10);
		subTitle = new Label("Please, click your choice of execution:");
		subTitleLabelSet(subTitle, 50, 50);
	}
	
	// setting x,y functions:
	//for setting (x,y) easily:
	private void titleLabelSet(Label l, int x, int y) {
		l.setFont(new Font(30));
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
	private void subTitleLabelSet(Label l, int x, int y) {
		l.setFont(new Font(20));
		l.setLayoutX(x);
		l.setLayoutY(y);
	}
	private void buttonSetXY(Button b, int x, int y) {
		b.setLayoutX(x);
		b.setLayoutY(y);
	}
	private void tfSetXY(TextField tf, int x, int y) {
		tf.setPrefWidth(100);
		tf.setLayoutX(x);
		tf.setLayoutY(y);
	}
	private void cbRSetXY(ComboBox<Integer> cb, int x, int y) {
		cb.setLayoutX(x);
		cb.setLayoutY(y);
	}
	private void cbTSetXY(ComboBox<String> cb, int x, int y) {
		cb.setLayoutX(x);
		cb.setLayoutY(y);
	}

	//generic message:
	//show:
	public void showAlert(String msg, String type) {
		// type options: confirmation, error, none, warning, information
		Alert.AlertType t = Alert.AlertType.valueOf(type.toUpperCase());
		Alert error = new Alert(t, msg);
		error.show();
	}

	//clear:
	public void clear() {
		clearAddApartment();
		clearAddClient();
		clearShowAptByKind();
		clearShowFullPrice();
		clearShowExpRent();
		clearShowClientByApt();
		clearShowSortedClient();
		clearCloneApt();
		
	}
	
	//print data:
	//printing to screen:
	public void showInfo(String toPrint) {
		clear();
		if (toPrint.isEmpty() || toPrint.equals("")) {
			showAlert("No data to show", "error");
			return;
		}
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(400, 400);
		
		Label print = new Label(toPrint);
		sp.setContent(print);
		
		VBox v = new VBox(10);
		v.getChildren().addAll(sp, print);
		v.setAlignment(Pos.CENTER);
		
		Scene tmpScene = new Scene(v, 400, 400);
		Stage tmpStage = new Stage();
		tmpStage.setScene(tmpScene);
		tmpStage.show();
	}

	//-----------------------------------------------------------------------------------------
	
	//add apartment:
	// add apartment: 
	private void addApartmentProcess() {
		grpAddApartment = new Group();
		grpSell = new Group();
		grpRent = new Group();
		addApartment = new Button("Add Apartment");
		buttonSetXY(addApartment, 50, 100);

		//address:
		lblAddress = new Label("Add Apartment's address:");
		subTitleLabelSet(lblAddress, 320, 160);
		tfAddress = new TextField();
		tfSetXY(tfAddress, 580, 160);

		//area:
		lblArea = new Label("Add Apartment's area:");
		subTitleLabelSet(lblArea, 320, 200);
		tfArea = new TextField();
		tfSetXY(tfArea, 580, 200);

		//number of rooms:
		lblNumOfRooms = new Label("Add NumberOf Rooms:");
		subTitleLabelSet(lblNumOfRooms, 320, 240);
		tfNumOfRooms = new TextField();
		tfSetXY(tfNumOfRooms, 580, 240);

		//rating:
		lblRating = new Label("Choose Rating: ");
		subTitleLabelSet(lblRating, 320, 280);
		cmRating = new ComboBox<>();
		cmRating.getItems().addAll(0, 1,2,3,4,5,6,7,8,9,10);
		cmRating.getSelectionModel().selectFirst();
		cbRSetXY(cmRating, 580, 280);

		//apartment type:
		lblType = new Label("Choose Apartment's type: ");
		subTitleLabelSet(lblType, 320, 320);
		cmType = new ComboBox<>();
		cmType.getItems().addAll("ApartmentForSell","RegularForRent", "AirBnbForRent");
		cmType.getSelectionModel().selectFirst();
		cbTSetXY(cmType, 580, 320);

		//apartment to sell's price:
		lblAptPrice = new Label("Add Apartment's Price:");
		subTitleLabelSet(lblAptPrice, 320, 360);
		tfAptPrice = new TextField();
		tfSetXY(tfAptPrice, 580, 360);

		//apartment for rent's price:
		lblRentPrice = new Label("Add Rent Price:");
		subTitleLabelSet(lblRentPrice, 320, 360);
		tfRentPrice = new TextField();
		tfSetXY(tfRentPrice, 580, 360);

		//lease duration:
		lblLeaseDuration = new Label("Add Lease Duration:");
		subTitleLabelSet(lblLeaseDuration, 320, 400);
		tfLeaseDuration = new TextField();
		tfSetXY(tfLeaseDuration, 580, 400);

		//submit button:
		buttonSetXY(submittors[0], 460, 460);

		//grouping:
		grpAddApartment.getChildren().addAll(lblAddress, tfAddress, 
				lblArea, tfArea, lblNumOfRooms, tfNumOfRooms, lblRating, cmRating, lblType, cmType);
		grpAddApartment.setVisible(false);

		grpSell.getChildren().addAll(lblAptPrice, tfAptPrice);
		grpSell.setVisible(false);

		grpRent.getChildren().addAll(lblLeaseDuration, lblRentPrice, tfLeaseDuration, tfRentPrice);
		grpRent.setVisible(false);


	}

	//buttons:
	//getters:
	public Button getAddApartmentButton() {
		return addApartment;
	}
	public Button getSubmitAddApartmentButton() {
		return submittors[0];
	}
	
	//getters:

	public String getAddressText() {
		return tfAddress.getText();
	}
	public String getAreaText() {
		return tfArea.getText();
	}
	public String getNumOfRoomsText() {
		return tfNumOfRooms.getText();
	}
	public int getRating() {
		return cmRating.getSelectionModel().getSelectedItem();
	}

	public String getLeaseDurationText() {
		return tfLeaseDuration.getText();
	}
	public String getRentPriceText() {
		return tfRentPrice.getText();
	}
	public String getSellPriceText() {
		return tfAptPrice.getText();
	}
	public String getAptTypeText() {
		return cmType.getSelectionModel().getSelectedItem();
	}
	
	//show:
	//show:
	public void showSellFields() {
		grpSell.setVisible(true);
		grpAddApartment.setDisable(true);
	}

	public void showRentFields() {
		grpRent.setVisible(true);
		grpAddApartment.setDisable(true);
	}

	public void showAddAptGrp(){
		grpAddApartment.setVisible(true);
		submittors[0].setVisible(true);
	}
	
	//clear:
	//clear:
	public void clearAddApartment() {
		grpAddApartment.setVisible(false);
		grpAddApartment.setDisable(false);

		grpSell.setVisible(false);
		grpSell.setDisable(false);

		grpRent.setVisible(false);
		grpRent.setDisable(false);

		tfAddress.clear();
		tfArea.clear();
		tfNumOfRooms.clear();
		tfAptPrice.clear();
		tfLeaseDuration.clear();
		tfRentPrice.clear();

		submittors[0].setVisible(false);
	}
	//final message:


	//-------------------------------------------------------------------------------------------

	//add client:
	private void addClientProcess() {
		grpAddClient = new Group();
		addClient = new Button("Add Client");
		buttonSetXY(addClient, 50, 140);

		//wanted apartment's address:
		lblAptAddress = new Label("Add Apartment's address:");
		subTitleLabelSet(lblAptAddress, 320, 160);
		tfAptAddress = new TextField();
		tfSetXY(tfAptAddress, 580, 160);

		//client's name:
		lblName = new Label("Add Client's Name:");
		subTitleLabelSet(lblName, 320, 200);
		tfName = new TextField();
		tfSetXY(tfName, 580, 200);

		//client's number:
		lblnumber = new Label("Add Client's number:");
		subTitleLabelSet(lblnumber, 320, 240);
		tfNumber = new TextField();
		tfSetXY(tfNumber, 580, 240);

		//submit button:
		buttonSetXY(submittors[1], 460, 460);

		grpAddClient.getChildren().addAll(lblAptAddress, tfAptAddress, lblName, tfName, lblnumber, tfNumber);
		grpAddClient.setVisible(false);


	}
	
	//buttons:
	//connection to action:
	public Button getAddClientButton() {
		return addClient;
	}
	public Button getSubmitAddClientButton() {
		return submittors[1];
	}
	
	//getters:
	public String getAptAddressText() {
		return tfAptAddress.getText();
	}

	public String getClientNameText() {
		return tfName.getText();
	}

	public String getClientNumberText() {
		return tfNumber.getText();
	}

	
	//show:
	public void showAddClientGrp(){
		grpAddClient.setVisible(true);
		submittors[1].setVisible(true);
	}
	
	//clear:

	public void clearAddClient() {
		grpAddClient.setVisible(false);
		grpAddClient.setDisable(false);

		tfAptAddress.clear();
		tfName.clear();
		tfNumber.clear();

		submittors[1].setVisible(false);
	}


	//-------------------------------------------------------------------------------------------
	
	//show all apartments:
	// show apartment: 
	private void showAptProcess() {
		grpShow = new Group();
		showApt = new Button("Show Apartments");
		buttonSetXY(showApt, 50, 180);
	}
	
	//button:

	public Button getShowAllApartmentsButton() {
		return showApt;
	}

	//-----------------------------------------------------------------------------------

	//show apartment by kind: 
	private void showAptByKindProcess() {
		grpShowByKind = new Group();
		showByKind = new Button("Show Apartments By Kind");
		buttonSetXY(showByKind, 50, 220);

		//apartment type:
		lblType = new Label("Choose Apartment's type: ");
		subTitleLabelSet(lblType, 320, 160);
		cmTypeKind = new ComboBox<>();
		cmTypeKind.getItems().addAll("ApartmentForSell","RegularForRent", "AirBnbForRent");
		cmTypeKind.getSelectionModel().selectFirst();
		cbTSetXY(cmTypeKind, 580, 160);

		//submit button:
		buttonSetXY(submittors[2], 460, 460);

		grpShowByKind.getChildren().addAll(lblType,cmTypeKind);
		grpShowByKind.setVisible(false);
	}

	
	//buttons:
	public Button getShowAptByKindButton() {
		return showByKind;
	}

	public Button getShowAptByKindSubmitButton() {
		return submittors[2];
	}

	
	//getter:
	public String getAptTypeKindText() {
		return cmTypeKind.getSelectionModel().getSelectedItem();
	}
	
	//show:
	public void showAptByKindGrp(){
		grpShowByKind.setVisible(true);
		submittors[2].setVisible(true);
	}
	
	//clear:
	public void clearShowAptByKind() {
		grpShowByKind.setVisible(false);
		grpShowByKind.setDisable(false);

		submittors[2].setVisible(false);
	}

	//------------------------------------------------------------------------------------

	//show full price per apartment: 
	private void showFullPriceProcess() {
		grpShowPrice = new Group();
		showPrice = new Button("Show Full Price For Rent");
		buttonSetXY(showPrice, 50, 260);

		//wanted apartment's address:
		lblAddressPrice = new Label("Add Apartment's address:");
		subTitleLabelSet(lblAddressPrice, 320, 160);
		tfAddressPrice = new TextField();
		tfSetXY(tfAddressPrice, 580, 160);

		//submit button:
		buttonSetXY(submittors[3], 460, 460);

		grpShowPrice.getChildren().addAll(lblAddressPrice, tfAddressPrice);
		grpShowPrice.setVisible(false);

	}
	
	//buttons:

	public Button getShowFullPriceButton() {
		return showPrice;
	}
	public Button getShowFullPriceSubmitButton() {
		return submittors[3];
	}

	
	//getter:
	public String getAddressPriceText() {
		return tfAddressPrice.getText();
	}
	
	//show:
	public void showFullPriceGrp(){
		grpShowPrice.setVisible(true);
		submittors[3].setVisible(true);
	}
	
	//clear:
	public void clearShowFullPrice() {
		grpShowPrice.setVisible(false);
		grpShowPrice.setDisable(false);

		tfAddressPrice.clear();

		submittors[3].setVisible(false);
	}

	//------------------------------------------------------------------------------------

	//show most expensive: 
	private void showExpRentProcess() {
		grpShowExpRent = new Group();
		showExpRent = new Button("Show Most expensive For Rent");
		buttonSetXY(showExpRent, 50, 300);

		//lease duration for rent:
		lblLease = new Label("Add Lease Duration:");
		subTitleLabelSet(lblLease, 320, 160);
		tfLease = new TextField();
		tfSetXY(tfLease, 580, 160);

		//submit button:
		buttonSetXY(submittors[4], 460, 460);

		grpShowExpRent.getChildren().addAll(lblLease, tfLease);
		grpShowExpRent.setVisible(false);
	}
	
	
	//buttons:
	public Button getShowExpRentButton() {
		return showExpRent;
	}
	public Button getShowExpRentSubmitButton() {
		return submittors[4];
	}
	
	//getter:
	public String getLeaseText() {
		return tfLease.getText();
	}

	//show:
	public void showExpRentGrp(){
		grpShowExpRent.setVisible(true);
		submittors[4].setVisible(true);
	}

	//clear:
	public void clearShowExpRent() {
		grpShowExpRent.setVisible(false);
		grpShowExpRent.setDisable(false);

		tfLease.clear();

		submittors[4].setVisible(false);
	}

	//------------------------------------------------------------------------------------


	//show clients per apartment: 
	private void showClientsProcess() {
		grpShowClientByApt = new Group();
		showClientByApt = new Button("Show Clients");
		buttonSetXY(showClientByApt, 50, 340);

		//wanted apartment's address:
		lblWantedAddress = new Label("Add Apartment's address:");
		subTitleLabelSet(lblWantedAddress, 320, 160);
		tfWantedAddress = new TextField();
		tfSetXY(tfWantedAddress, 580, 160);

		//submit button:
		buttonSetXY(submittors[5], 460, 460);

		grpShowClientByApt.getChildren().addAll(lblWantedAddress, tfWantedAddress);
		grpShowClientByApt.setVisible(false);
	}

	//buttons:
	public Button getShowClientByAptButton() {
		return showClientByApt;
	}
	public Button getShowClientByAptSubmitButton() {
		return submittors[5];
	}
	
	//getter:
	public String getAddressClientText() {
		return tfWantedAddress.getText();
	}
	
	//show:
	public void showClientByAptGrp(){
		grpShowClientByApt.setVisible(true);
		submittors[5].setVisible(true);
	}
	
	//clear:
	public void clearShowClientByApt() {
		grpShowClientByApt.setVisible(false);
		grpShowClientByApt.setDisable(false);

		tfWantedAddress.clear();

		submittors[5].setVisible(false);
	}

	//------------------------------------------------------------------------------------

	//show clients per apartment - sorted: 
	private void showSortedClientsProcess() {
		grpShowSortedClient = new Group();
		showSortedClient = new Button("Show Clients - Sorted");
		buttonSetXY(showSortedClient, 50, 380);

		//wanted apartment's address:
		lblAddressForSort = new Label("Add Apartment's address:");
		subTitleLabelSet(lblAddressForSort, 320, 160);
		tfAddressForSort = new TextField();
		tfSetXY(tfAddressForSort, 580, 160);

		//submit button:
		buttonSetXY(submittors[6], 460, 460);

		grpShowSortedClient.getChildren().addAll(lblAddressForSort, tfAddressForSort);
		grpShowSortedClient.setVisible(false);
	}
	
	//buttons:
	public Button getShowSortedClientButton() {
		return showSortedClient;
	}
	public Button getShowSortedClientSubmitButton() {
		return submittors[6];
	}
	
	//getter:
	public String getAddressClientSortedText() {
		return tfAddressForSort.getText();
	}
	
	//show:
	public void showSortedClientGrp(){
		grpShowSortedClient.setVisible(true);
		submittors[6].setVisible(true);
	}

	//clear:
	public void clearShowSortedClient() {
		grpShowSortedClient.setVisible(false);
		grpShowSortedClient.setDisable(false);

		tfAddressForSort.clear();

		submittors[6].setVisible(false);
	}

	//------------------------------------------------------------------------------------
	
	//clone an apartment:
	public void cloneApt() {
		grpCloneApt = new Group();
		cloneApt = new Button("Clone Apartment & Show");
		buttonSetXY(cloneApt, 50, 420);

		//wanted apartment's address:
		lblAddressForClone = new Label("Add Apartment's address:");
		subTitleLabelSet(lblAddressForClone, 320, 160);
		tfAddressForClone = new TextField();
		tfSetXY(tfAddressForClone, 580, 160);

		//submit button:
		buttonSetXY(submittors[7], 460, 460);

		grpCloneApt.getChildren().addAll(lblAddressForClone, tfAddressForClone);
		grpCloneApt.setVisible(false);
	}
	
	//buttons:
	public Button getCloneButton() {
		return cloneApt;
	}
	public Button getCloneSubmitButton() {
		return submittors[7];
	}

	//getter:
	public String getAddressCloneText() {
		return tfAddressForClone.getText();
	}
	
	//show:
	public void showCloneAptGrp(){
		grpCloneApt.setVisible(true);
		submittors[7].setVisible(true);
	}

	//clear:
	public void clearCloneApt() {
		grpCloneApt.setVisible(false);
		grpCloneApt.setDisable(false);

		tfAddressForClone.clear();

		submittors[7].setVisible(false);
	}
	
	//------------------------------------------------------------------------------------
	
	//exit program:
	private void exitProgram() {
		exitProgram = new Button("Exit Here");
		buttonSetXY(exitProgram, 710, 460);
	}
	
	//buttons:
	//exit button:
	public Button getExitButton() {
		return exitProgram;
	}
	
	//close:
	public void close() {
		stage.close();
	}

}
