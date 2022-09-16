package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class Appointment implements Serializable {
    LocalDate apptDate;
    Integer hour;
    Integer minutes;
    String type;
    String vaccineIdentifier;
    String details;
    String patientID;

    public Appointment(LocalDate apptDate, Integer hour, Integer minutes, String type, String vaccineIdentifier, String details, String patientID) {
        this.apptDate = apptDate;
        this.hour = hour;
        this.minutes = minutes;
        this.type = type;
        this.vaccineIdentifier = vaccineIdentifier;
        this.details = details;
        this.patientID = patientID;
    }

    @Override
    public String toString() {
        return "Vaccine appointment on:" + apptDate +
                "\n" + "At: " + hour + ":" + minutes +
                "\n" + "Vaccine Type: " + type +
                "\n" + "Vaccine ID: " + vaccineIdentifier +
                "\n" + "Vaccinator Details: " + details +
                "\n" + "Patient PPSN: " + patientID;
    }

    public LocalDate getApptDate() {
        return apptDate;
    }

    public void setApptDate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVaccineIdentifier() {
        return vaccineIdentifier;
    }

    public void setVaccineIdentifier(String vaccineIdentifier) {
        this.vaccineIdentifier = vaccineIdentifier;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}


