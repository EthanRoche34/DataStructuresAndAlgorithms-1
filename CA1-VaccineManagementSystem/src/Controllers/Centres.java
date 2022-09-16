package Controllers;

import Data.*;
import Layout.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import java.io.*;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Centres {

    //Setting up all the JavaFX components
    @FXML
    private TabPane Tabs;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Tab Centres;
    @FXML
    private ListView<String> CList;
    @FXML
    private ListView<String> BList;
    @FXML
    private ListView<?> appointmentList2;
    @FXML
    private TextField VCName;
    @FXML
    private TextField VCAddress;
    @FXML
    private TextField VCEircode;
    @FXML
    private TextField Parking;
    @FXML
    private Button AddCentre;
    @FXML
    private Label vaccineCount;
    @FXML
    private TextField BoothIdentifier;
    @FXML
    private TextField BoothFloor;
    @FXML
    private TextArea BoothAccessibility;
    @FXML
    private Button AddBooth;
    @FXML
    static LinkedList<VaccineCentre> VaccineCentres = new LinkedList<VaccineCentre>();
    @FXML
    static LinkedList<Patient> patients = new LinkedList<Patient>();
    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    private Slider timeHours;
    @FXML
    private Slider timeMinutes;
    @FXML
    private TextField vaccBatch;
    @FXML
    private TextField vaccDetails;
    @FXML
    private TextField appointmentPPSN;
    @FXML
    private ChoiceBox<String> vaccType;
    @FXML
    private ListView<?> appointments;
    @FXML
    private TextArea patientAccessibility;
    @FXML
    private TextField patientContact;
    @FXML
    private DatePicker patientDOB;
    @FXML
    private ListView<?> patientLV;
    @FXML
    private TextField patientName;
    @FXML
    private TextField patientPPSN;
    @FXML
    private ChoiceBox<String> centresChoics;
    @FXML
    private ChoiceBox<String> boothChoice;
    @FXML
    private ListView<Object> patientRecords;
    @FXML
    private ListView<Object> allAppts;
    @FXML
    private ChoiceBox<String> patientIDs;
    @FXML
    private Label total;
    @FXML
    private ChoiceBox<String> searchType;
    @FXML
    private ListView<?> vaccines;

    //Setting up all the choiceboxes that have actions on an option being selected
    public void initialize() {
        vaccType.setValue("Select a vaccine type:");
        vaccType.getItems().addAll("Pfizer", "J&J", "Moderna");

        searchType.getItems().addAll("Pfizer", "J&J", "Moderna");

        centresChoics.setOnAction(event -> {
                    boothChoice.getItems().clear();
                    boothChoice.getSelectionModel().clearSelection();
                    populateBoothChoice();
                }
        );
        boothChoice.setOnAction(event -> {
            appointmentList2.getItems().clear();
            populateAppointments();
        });
        patientIDs.setOnAction(event -> {
            populatePatientRecords();
        });
    }

    //Adding a Vaccine Centre
    public void add(javafx.event.ActionEvent actionEvent) {

        //Checking all the inputs are valid before adding the centre
        if (Utilities.validName(VCName.getText()) && Utilities.validEircode(VCEircode.getText()) && Utilities.onlyContainsNumbers(Parking.getText())) {
            VaccineCentre vc = new VaccineCentre(VCName.getText(), VCAddress.getText(), VCEircode.getText(), Parking.getText());
            VaccineCentres.addElement(vc);
            addToListView();
        } else {
            //Makes a message to tell the user what inputs are missing or invalid
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label("");
            errorAlert.getDialogPane().setContent(label);
            if (!Utilities.validName(VCName.getText()))
                label.setText(label.getText() + "Enter a valid name(Up to 30 characters)" + "\n");
            if (!Utilities.validEircode(VCEircode.getText()))
                label.setText(label.getText() + "Enter a valid Eircode(Up to 7 characters)" + "\n");
            if (!Utilities.onlyContainsNumbers(Parking.getText()))
                label.setText(label.getText() + "Enter a valid number of parking spaces" + "\n");
            errorAlert.setHeaderText("One or more invalid inputs entered");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        VCName.clear();
        VCAddress.clear();
        VCEircode.clear();
        Parking.clear();
    }

    //Adds a booth to the selected vaccine centre
    @FXML
    public void addBooth(ActionEvent actionEvent) {
        int index = CList.getSelectionModel().getSelectedIndex();
        VaccineCentre selectedVC = VaccineCentres.get(index);
        //Ensuring all of the inputs are valid before adding a booth
        if (Utilities.validIdentifier(BoothIdentifier.getText()) && Utilities.onlyContainsNumbers(BoothFloor.getText())) {
            Booth b = new Booth(BoothIdentifier.getText(), BoothFloor.getText(), BoothAccessibility.getText());
            boolean test = false ;
            //If there's already no booths in the centre, just add the booth
            if (selectedVC.getBooths().size() < 1) {
                selectedVC.getBooths().addElement(b);
                addToBoothListView();
            } else {
                //Checks to see if a booth already has the inputted identifier
                for (int i = 0; i < selectedVC.getBooths().size(); i++) {
                    Booth iterator = (Booth) selectedVC.getBooths().get(i);
                    if (b.getIdentifier().matches(iterator.getIdentifier())) {
                        test = true;
                    }
                }
                //Adds the booth if the identifier doesn't belong to a booth already, otherwise tells the user
                //That a booth with the ID exists in the centre
                if (test == false) {
                    selectedVC.getBooths().addElement(b);
                    addToBoothListView();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    Label label = new Label("Booth id exists already");
                    errorAlert.getDialogPane().setContent(label);
                    errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    errorAlert.showAndWait();
                }
            }
        }
        else {
            //Dialogue notifying the user if they have any invalid inputs
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label("");
            errorAlert.getDialogPane().setContent(label);
            if (!Utilities.validIdentifier(BoothIdentifier.getText())) {
                label.setText(label.getText() + "Enter a valid identifier(Captial Letter followed by a number)" + "\n");
            }
            if (!Utilities.onlyContainsNumbers(BoothFloor.getText())) {
                label.setText(label.getText() + "Enter a valid floor" + "\n");
            }
            errorAlert.setHeaderText("One or more invalid inputs entered");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        BoothIdentifier.clear();
        BoothFloor.clear();
        BoothAccessibility.clear();
    }

    //Populates the Vaccine Centre choice box on the Appointments Tab
    @FXML
    void populateChoice() {
        centresChoics.getItems().clear();
        centresChoics.getSelectionModel().clearSelection();
        for (int i = 0; i < VaccineCentres.size(); i++) {
            centresChoics.getItems().add(VaccineCentres.get(i).getName());
        }
    }

    //Populates the Patients Choicebox on the Patient tab with the PPSN of each patient
    void populatePPSN() {
        patientIDs.getItems().clear();
        patientIDs.getSelectionModel().clearSelection();
        for (int i = 0; i < patients.size(); i++) {
            patientIDs.getItems().add(patients.get(i).getPPSN());
        }
    }

    //Searches through the system for appointments and appointment records belonging to the patient who's PPSN was selected
    void populatePatientRecords() {
        LinkedList<Object> Records = new LinkedList<Object>();
        int index = patientIDs.getSelectionModel().getSelectedIndex();
        Patient chosenPatient = patients.get(index);
        String patPPSN = chosenPatient.getPPSN();
        if (chosenPatient.getVaccineRecords().size() > 0) {
            for (int i = 0; i < chosenPatient.getVaccineRecords().size(); i++) {
                Records.addElement(chosenPatient.getVaccineRecords().get(i));
            }
        }
        getAllAppointments(Records, patPPSN);

        patientRecords.getItems().clear();
        Records.populateList(patientRecords);
    }

    //Populates the booth choicebox based on the chosen Vaccine Centre
    void populateBoothChoice() {
        boothChoice.getItems().clear();
        boothChoice.getSelectionModel().clearSelection();
        int centreIndex = centresChoics.getSelectionModel().getSelectedIndex();
        VaccineCentre chosenVC = VaccineCentres.get(centreIndex);
        for (int i = 0 ; i < chosenVC.getBooths().size(); i++) {
            Booth b = (Booth) chosenVC.getBooths().get(i);
            boothChoice.getItems().add("Booth: " + b.getIdentifier() + " " + "Appts: " +b.getAppointments().size());
        }
    }

    //Loads the appointments from the selected booth onto the listview
    void populateAppointments() {
        VaccineCentre chosenVC = VaccineCentres.get(centresChoics.getSelectionModel().getSelectedIndex());
        Booth selectedBooth = (Booth) chosenVC.getBooths().get(boothChoice.getSelectionModel().getSelectedIndex());
        selectedBooth.getAppointments().populateList(appointmentList2);
    }

    //Shows all the appointments across all the booths in the chosen Vaccine Centre
    @FXML
    void apptsByCentre(ActionEvent actionEvent) {
        VaccineCentre selectedCentre = VaccineCentres.get(centresChoics.getSelectionModel().getSelectedIndex());
        LinkedList<Object> appts = new LinkedList<>();
        if (centresChoics.getSelectionModel().isEmpty()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label("Please select a vaccine centre");
            errorAlert.getDialogPane().setContent(label);
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        } else {
            for (int i = 0; i < selectedCentre.getBooths().size(); i++) {
                Booth tempBooth = (Booth) selectedCentre.getBooths().get(i);
                for(int j = 0; j < tempBooth.getAppointments().size(); j++) {
                    appts.addElement("Booth: " + tempBooth.getIdentifier() + "\n" + tempBooth.getAppointments().get(j));
                }
            }
            allAppts.getItems().clear();
            appts.populateList(allAppts);
            total.setText("Total Appointments in Centre: " + appts.size());
        }

    }

    //Uses the getAllAppointments method to show every appointment in the system
    @FXML
    void getAll(ActionEvent event) {
        LinkedList<Object> appts = new LinkedList<>();
        getAllAppointments(appts);

        allAppts.getItems().clear();
        appts.populateList(allAppts);
        total.setText("Total Appointments in System: " + appts.size());
    }

    //Takes in a LinkedList then proceeds to go through all the booths in each vaccine centre and adds each appointment and its relevant centre and booth to the list
    void getAllAppointments(LinkedList<Object> appts) {
        for (int c = 0 ; c < VaccineCentres.size(); c++) {
            VaccineCentre tempCentre = VaccineCentres.get(c);
            for (int b = 0; b < tempCentre.getBooths().size(); b++) {
                Booth tempBooth = (Booth) tempCentre.getBooths().get(b);
                for (int a = 0; a < tempBooth.getAppointments().size(); a++) {
                    appts.addElement("Centre: " + tempCentre.getName() + "    " + "Booth: " + tempBooth.getIdentifier() + "\n" + tempBooth.getAppointments().get(a));
                }
            }
        }
    }


    Boolean boothSuccess;
    //Deletes the selected booth, and if possible, transfers it's appointments to another booth in the centre
    @FXML
    void deleteBooth(ActionEvent event) {
        boothSuccess = false;
        VaccineCentre chosenVC = VaccineCentres.get(CList.getSelectionModel().getSelectedIndex());
        LinkedList<Appointment> boothAppointments = null;
        Booth chosenBooth = (Booth) chosenVC.getBooths().get(BList.getSelectionModel().getSelectedIndex());
        //Making a new list of appointments and setting it's contents to the appointments of the chosen booth
        boothAppointments = chosenBooth.getAppointments();
        //Checking if the chosen booth has any appointments, if not it's just deleted
        if (chosenBooth.getAppointments().size() > 0) {
            //If the current vaccine centre has multiple booths, go through them and see if any are empty to transfer the appointments to
            if (chosenVC.getBooths().size() > 1) {
                for (int i = 0; i < chosenVC.getBooths().size(); i++) {
                    Booth tempBooth = (Booth) chosenVC.getBooths().get(i);
                    //If there is an available booth, the appointments are transferred to it and then the old booth is removed from the centre
                    if (tempBooth.getAppointments().size() == 0) {
                        tempBooth.setAppointments(boothAppointments);
                        boothSuccess = true;
                        break;
                    }
                }
                if (boothSuccess) {
                    chosenVC.getBooths().deleteNode(BList.getSelectionModel().getSelectedIndex());
                    addToBoothListView();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    Label label = new Label("Could not delete this booth as there is no extra available booths");
                    errorAlert.getDialogPane().setContent(label);
                    errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    errorAlert.showAndWait();
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                Label label = new Label("Could not delete this booth as there is no other booths in this centre");
                errorAlert.getDialogPane().setContent(label);
                errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                errorAlert.showAndWait();
            }
        } else {
            chosenVC.getBooths().deleteNode(BList.getSelectionModel().getSelectedIndex());
            addToBoothListView();
        }
    }

    //Modified version of get all appointments used for searching for appointments matching the chosen patient's PPSN
    void getAllAppointments(LinkedList<Object> appts, String ppsn) {
        for (int c = 0; c < VaccineCentres.size(); c++) {
            VaccineCentre tempCentre = VaccineCentres.get(c);
            for (int b = 0; b < tempCentre.getBooths().size(); b++) {
                Booth tempBooth = (Booth) tempCentre.getBooths().get(b);
                for (int a = 0; a < tempBooth.getAppointments().size(); a++) {
                    if (tempBooth.getAppointments().get(a).getPatientID().equals(ppsn)) {
                        appts.addElement("Centre: " + tempCentre.getName() + "    " + "Booth: " + tempBooth.getIdentifier() + "\n" + tempBooth.getAppointments().get(a));
                    }
                }
            }
        }
    }

    Boolean success;
    //This method attempts to automatically make an appointment for the chosen patient using the prefix of the chosen patient's eircode
    //And the prefixes of the Vaccine Centres
    @FXML
    void smartAdd(ActionEvent event) {
        success = false;
        if (patientLV.getSelectionModel() != null) {
            Patient chosenPatient = patients.get(patientLV.getSelectionModel().getSelectedIndex());
            String patientPPSN = chosenPatient.getPPSN();
            String patEircodePrefix = chosenPatient.getContactInfo().substring(0,3);
            LocalDate today = LocalDate.now();

            VaccineCentre centre = null;
            Booth booth = null;
            //Setting up randoms to have a random vaccine identifier and time used for the appointment
            Random rand = new Random();
            int randomNum = rand.nextInt(999999);
            String random = String.format("%06d", randomNum);
            int[] hours = new int[] {8,9,10,11,12,13,14,15,16};
            int randHour = hours[rand.nextInt(hours.length)];
            int[] mins = new int[] {0,15,30,45};
            int randMinute = mins[rand.nextInt(mins.length)];

            for (int c = 0; c < VaccineCentres.size(); c++) {
                VaccineCentre tempVC = VaccineCentres.get(c);
                String centreEircodePrefix = tempVC.getEircode().substring(0,3);
                if (patEircodePrefix.equals(centreEircodePrefix)) {
                    for (int b = 0; b < tempVC.getBooths().size(); b++) {
                        Booth tempBooth = (Booth) tempVC.getBooths().get(b);
                        //If there is a booth without any appointments, an appointment is made for the patient there
                        if (tempBooth.getAppointments().size() == 0) {
                            tempBooth.getAppointments().addElement(new Appointment(today.plusDays(14), randHour, randMinute, "Pfizer", random,"", patientPPSN));
                            success = true;
                            centre = tempVC;
                            booth = tempBooth;
                            break;
                        }
                    }
                } else {
                    System.out.println("AA");
                }
            }
            if (success) {
                Alert appointmentAlert = new Alert(Alert.AlertType.INFORMATION);
                Label label = new Label("Appointment Successfully made in: " + centre.getName() + "\nIn Booth: " + booth.getIdentifier());
                appointmentAlert.getDialogPane().setContent(label);
                appointmentAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                appointmentAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                Label label = new Label("Appointment could not be made" + "\nPlease manually add the appointment");
                errorAlert.getDialogPane().setContent(label);
                errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                errorAlert.showAndWait();
            }
            chosenPatient = null;
            patientPPSN = null;
            patEircodePrefix = null;
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label("Please select a patient");
            errorAlert.getDialogPane().setContent(label);
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        patientLV.getSelectionModel().clearSelection();
    }


    //This searches for all appointments and vaccine records that have the vaccine type selected on the choicebox
    @FXML
    void vaccineSearch(ActionEvent event) {
        vaccines.getItems().clear();
        String vaccSearchType = searchType.getValue();
        LinkedList<Object> allVaccinesOfType = new LinkedList<>();
        if (vaccSearchType != ""){
            for (int p = 0; p < patients.size(); p++) {
                //Iterating through all the patients and their vaccination records,
                //adding their name, PPSN and record if they have a vaccine of the chosen type
                Patient tempPatient = patients.get(p);
                for (int r = 0; r < tempPatient.getVaccineRecords().size(); r++) {
                    if( vaccSearchType.equals(tempPatient.getVaccineRecords().get(r).getVaccineType())) {
                        allVaccinesOfType.addElement("Patient: " + tempPatient.getName() + "  PPSN: " + tempPatient.getPPSN() + "\n" + tempPatient.getVaccineRecords().get(r));
                    }
                }
            }
            //Going through all the appointments in each booth of each centre and adding
            //The appointment to the list if it has the selected vaccine type
            for (int c = 0; c < VaccineCentres.size(); c++) {
                VaccineCentre tempCentre = VaccineCentres.get(c);
                for (int b = 0; b < tempCentre.getBooths().size(); b++) {
                    Booth tempBooth = (Booth) tempCentre.getBooths().get(b);
                    for (int a = 0; a < tempBooth.getAppointments().size(); a++) {
                        if (vaccSearchType.equals(tempBooth.getAppointments().get(a).getType())) {
                            Appointment tempAppt = tempBooth.getAppointments().get(a);
                            allVaccinesOfType.addElement("Patient: " + tempAppt.getPatientID() + "\n" + tempAppt);
                        }
                    }
                }
            }
        }
        allVaccinesOfType.populateList(vaccines);
        vaccineCount.setText("Total Vaccines of Selected Type: " + allVaccinesOfType.size());
    }

    boolean apptDateVerification = false;
    boolean existingPPSN = false;
    //This adds an appointment to the chosen booth as long as the PPSN given belongs to a patient in the system
    //and that the selected date and time aren't already being used for an appointment
    @FXML
    void addAppt(ActionEvent event) {
        int centreIndex = CList.getSelectionModel().getSelectedIndex();
        int index = BList.getSelectionModel().getSelectedIndex();
        Booth selectedBooth = (Booth) VaccineCentres.get(centreIndex).getBooths().get(index);
        int hours = (int) timeHours.getValue();
        int mins = (int) timeMinutes.getValue();
        Appointment apt = new Appointment(datePicker.getValue(), hours, mins, vaccType.getValue(), vaccBatch.getText(), vaccDetails.getText(), appointmentPPSN.getText());
        if (datePicker.getValue() != null && (vaccType.getValue() != "Select a vaccine type:") && Utilities.validPPSN(appointmentPPSN.getText()) && Utilities.validBatch(vaccBatch.getText())) {

            for (int i = 0; i < patients.size(); i++) {
                if (patients.get(i).getPPSN().matches(appointmentPPSN.getText())) {
                    existingPPSN = true;
                }
            }
            if (selectedBooth.getAppointments().size() < 1 && existingPPSN) {
                selectedBooth.getAppointments().addElement(apt);
                addToAppointmentListView();
            } else {
                for (int j = 0; j < selectedBooth.getAppointments().size(); j++) {
                    Appointment a = selectedBooth.getAppointments().get(j);
                    LocalDate tempDate = a.getApptDate();
                    Integer tempHour = a.getHour();
                    Integer tempMins = a.getMinutes();
                    LocalDate chosenDate = datePicker.getValue();
                    if (tempDate.equals(chosenDate) && tempHour == hours && tempMins == mins) {
                        apptDateVerification = true;
                    }
                }
                if (!apptDateVerification && existingPPSN) {
                    selectedBooth.getAppointments().addElement(apt);
                    addToAppointmentListView();
                }
            }

        }

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label("");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.getDialogPane().setContent(label);
            if (!existingPPSN) {
                label.setText(label.getText() + "Please enter a valid PPSN that is in the system" + "\n");
            }
            if (datePicker.getValue() == null) {
                label.setText(label.getText() + "Please select an appointment date" + "\n");
            }
            if (apptDateVerification == true) {
                label.setText(label.getText() + "The selected date and time are already used. Please pick another date/time" + "\n");
                System.out.println("THE DATE AND TIME ARE THE SAME");
            }
            if (vaccType.getValue() == "Select a vaccine type:") {
                label.setText(label.getText() + "Please select a vaccination type" + "\n");
            }
            if (!Utilities.validBatch(vaccBatch.getText())) {
                label.setText(label.getText() + "Please enter a vaccine batch number" + "\n");
            }
        if (label.getText() != "") {
            errorAlert.setHeaderText("One or more issues occurred");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        apptDateVerification = false;
        existingPPSN = false;
//        selectedBooth.getAppointments().addElement(apt);
//        addToAppointmentListView();
//        System.out.println(getFormattedDate(datePicker));
//        System.out.println(vaccType.getValue());
    }

    //Simply deletes the chosen appointment from it's booth and refreshes the booth list view
    @FXML
    void cancelAppt(ActionEvent event) {
        VaccineCentre chosenVC = VaccineCentres.get(centresChoics.getSelectionModel().getSelectedIndex());
        Booth chosenBooth = (Booth) chosenVC.getBooths().get(boothChoice.getSelectionModel().getSelectedIndex());
        Appointment chosenAppt = chosenBooth.getAppointments().get(appointmentList2.getSelectionModel().getSelectedIndex());
        chosenBooth.getAppointments().deleteNode(appointmentList2.getSelectionModel().getSelectedIndex());
        populateBoothChoice();
        addToBoothListView();
    }

    Patient apptPatient;
    //This method finds the patient who's PPSN the appointment has and then completes the appointment
    //This adds it to the patient's vaccine record while also deleting the appointment
    @FXML
    void completeAppt(ActionEvent event) {
        VaccineCentre chosenVC = VaccineCentres.get(centresChoics.getSelectionModel().getSelectedIndex());
        Booth chosenBooth = (Booth) chosenVC.getBooths().get(boothChoice.getSelectionModel().getSelectedIndex());
        Appointment chosenAppt = chosenBooth.getAppointments().get(appointmentList2.getSelectionModel().getSelectedIndex());

        for (int i = 0; i < patients.size(); i++) {
            Patient temp = patients.get(i);
            String patientPPSN = temp.getPPSN();
            if (patientPPSN.equals(chosenAppt.getPatientID())) {
                apptPatient = temp;
                break;
            }
        }
        VaccinationRecord vaccineRecord = new VaccinationRecord(chosenAppt.getApptDate(), chosenAppt.getType(), chosenAppt.getVaccineIdentifier());
        apptPatient.getVaccineRecords().addElement(vaccineRecord);
        chosenBooth.getAppointments().deleteNode(appointmentList2.getSelectionModel().getSelectedIndex());
        populateBoothChoice();
        apptPatient.getVaccineRecords().get(0);
        apptPatient = null;
    }

    //Adds a patient to the system as long as a unique PPSN has been provided
    @FXML
    void addPatient(ActionEvent event) {
        String patPPSN = patientPPSN.getText();
        String patName = patientName.getText();
        LocalDate patDOB = patientDOB.getValue();
        String patContact = patientContact.getText();
        String patAccess = patientAccessibility.getText();

        if (Utilities.validPPSN(patPPSN) && Utilities.validName(patName) && Utilities.validEircode(patContact) && (patientDOB.getValue() != null)){
            Patient p = new Patient(patPPSN, patName, patDOB, patContact, patAccess);

            Boolean nonUnique = false;
            if (patients.size() < 1) {
                patients.addElement(p);
                addtoPatientListView();
            } else {
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getPPSN().matches(patPPSN)) {
                        nonUnique = true;
                    }
                }
                if (nonUnique == false) {
                    patients.addElement(p);
                    addtoPatientListView();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    Label label = new Label("This PPSN number is already in the system");
                    errorAlert.getDialogPane().setContent(label);
                    errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    errorAlert.showAndWait();
                }
            }

        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Label label = new Label("");
            errorAlert.getDialogPane().setContent(label);
            if (!Utilities.validPPSN(patPPSN))
                label.setText(label.getText() + "Enter a valid PPSN(7 numbers followed by a letter" + "\n");
            if (!Utilities.validName(patName))
                label.setText(label.getText() + "Enter a valid name(30 characters max" + "\n");
            if (patientDOB.getValue() == null)
                label.setText(label.getText() + "Choose your date of birth" + "\n");
            if (!Utilities.validEircode(patContact))
                label.setText(label.getText() + "Please enter a valid Eircode" + "\n");
            errorAlert.setHeaderText("One or more invalid inputs entered");
            errorAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            errorAlert.showAndWait();
        }
        patientPPSN.clear();
        patientName.clear();
        patientContact.clear();
        patientAccessibility.clear();
        patientDOB.getEditor().clear();
        patientDOB.setValue(null);
    }

    //Adds all the centres in the Vaccine Centre LinkedList to the Vaccine Centre ListView and populates the choice box
    public void addToListView() {
        CList.getItems().clear();
        VaccineCentres.populateList(CList);
        populateChoice();
    }

    //Adds all the patients on the patients LinkedList to the Patients listView and populates the PPSN choicebox
    public void addtoPatientListView() {
        patientLV.getItems().clear();
        patients.populateList(patientLV);
        populatePPSN();
    }

    //Adds all the booths from the chosen centre to the booths ListView
    public void addToBoothListView() {
        int index = CList.getSelectionModel().getSelectedIndex();
        BList.getItems().clear();
        VaccineCentres.get(index).getBooths().populateList(BList);
        CList.getItems().clear();
        VaccineCentres.populateList(CList);

    }

    //Adds all the appointments from the chosen booth to the appointments listView
    public void addToAppointmentListView() {
        int centreIndex = CList.getSelectionModel().getSelectedIndex();
        int index = BList.getSelectionModel().getSelectedIndex();
        Booth selectedBooth = (Booth) VaccineCentres.get(centreIndex).getBooths().get(index);
        appointments.getItems().clear();
        ((Booth) VaccineCentres.get(centreIndex).getBooths().get(index)).getAppointments().populateList(appointments);
        BList.getItems().clear();
        VaccineCentres.get(centreIndex).getBooths().populateList(BList);
    }

    //Fills in the Vaccine Centre input boxes with the information from the selected centre
    //Also populates the Booths LinkedList with the booths from the current vaccine centre
    public void getCentre(){
        int index = CList.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        VaccineCentre selectedVC = VaccineCentres.get(index);
        BList.getItems().clear();
        VaccineCentres.get(index).getBooths().populateList(BList);
        VCName.setText(selectedVC.getName());
        VCAddress.setText(selectedVC.getAddress());
        VCEircode.setText(selectedVC.getEircode());
        Parking.setText(String.valueOf(selectedVC.getParkingSpaces()));

//        System.out.println(VaccineCentres.get(index));
    }

    //Gets the information from the current booth and sets the appointments list to the appointments of the selected booth
    public void getBooth(){
        int centreIndex = CList.getSelectionModel().getSelectedIndex();
        int boothIndex = BList.getSelectionModel().getSelectedIndex();
        Booth selectedBooth = (Booth) VaccineCentres.get(centreIndex).getBooths().get(boothIndex);
        BoothIdentifier.setText(selectedBooth.getIdentifier());
        BoothFloor.setText(selectedBooth.getFloorLevel());
        appointments.getItems().clear();
        ((Booth) VaccineCentres.get(centreIndex).getBooths().get(boothIndex)).getAppointments().populateList(appointments);
    }

    //This simply allows the information of the selected vaccine centre to be updated
    @FXML
    void updateCentre(ActionEvent event) {
        if (Utilities.validName(VCName.getText()) && Utilities.validEircode(VCEircode.getText()) && Utilities.onlyContainsNumbers(Parking.getText())) {
            VaccineCentre temp = new VaccineCentre(VCName.getText(), VCAddress.getText(), VCEircode.getText(), Parking.getText());
            getCentre();

            int index = CList.getSelectionModel().getSelectedIndex();
            VaccineCentre selectedVC = VaccineCentres.get(index);
            selectedVC.setName(temp.getName());
            selectedVC.setAddress(temp.getAddress());
            selectedVC.setEircode(temp.getEircode());
            selectedVC.setParkingSpaces(temp.getParkingSpaces());
            temp = null;

        }
        CList.getItems().clear();
        VaccineCentres.populateList(CList);
    }

    //Uses GSON to save the Vaccine Centres and Patients to their own JSON files
    void save() {
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter("Centres.json")) {
            gson.toJson(VaccineCentres, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fileWriter = new FileWriter("Patients.json")) {
            gson.toJson(patients, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Uses GSON to load the Centres and Patients JSON files
    void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(Reader reader = new FileReader("Centres.json")) {
            Type listType = new TypeToken<LinkedList<VaccineCentre>>() {}.getType();
            VaccineCentres = gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(Reader reader = new FileReader("Patients.json")) {
            Type listType = new TypeToken<LinkedList<Patient>>() {}.getType();
            patients = gson.fromJson(reader, listType);
        }   catch (IOException e) {
            e.printStackTrace();
        }
    }

    //After loading the patients and centres, populates the relevant list views
    @FXML
    void loadAll(ActionEvent event) {
        load();
        addToListView();
        addtoPatientListView();

    }

    @FXML
    void saveAll(ActionEvent event) {
        save();
    }

    //Sets all the LinkedLists to null, clears all ListViews and all choiceBoxes
    @FXML
    void clearFacility(ActionEvent event) {
        VaccineCentres = null;
        patients = null;
        CList.getItems().clear();
        CList.getSelectionModel().clearSelection();
        BList.getItems().clear();
        BList.getSelectionModel().clearSelection();
        appointments.getItems().clear();
        appointments.getSelectionModel().clearSelection();
        vaccType.getSelectionModel().clearSelection();
        centresChoics.getItems().clear();
        centresChoics.getSelectionModel().clearSelection();
        boothChoice.getItems().clear();
        boothChoice.getSelectionModel().clearSelection();
        appointmentList2.getSelectionModel().clearSelection();
        appointmentList2.getItems().clear();
        allAppts.getItems().clear();
        patientIDs.getItems().clear();
        patientIDs.getSelectionModel().clearSelection();
        patientLV.getItems().clear();
        searchType.getSelectionModel().clearSelection();
        vaccines.getItems().clear();
        patientRecords.getItems().clear();
    }
}

//Attempt at formatting the dates in a specific way
/*
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
private String getFormattedDate(DatePicker dp){
LocalDate date = dp.getValue();
return date.format(formatter);
}
*/
//Idea for adding 28 days for a 2nd appointment
/*
Add 28 days for 2nd appt ???
LocalDate date = datePicker.getValue();
LocalDate newDate = date.plusDays(28);
System.out.println(newDate.format(formatter));
*/