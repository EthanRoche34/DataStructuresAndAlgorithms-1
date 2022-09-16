package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class Patient implements Serializable {
    String PPSN;
    String name;
    LocalDate dob;
    String contactInfo;
    String accessibility;
    LinkedList<VaccinationRecord> vaccineRecords;

    public Patient(String PPSN, String name, LocalDate dob, String contactInfo, String accessibility) {
        this.PPSN = PPSN;
        this.name = name;
        this.dob = dob;
        this.contactInfo = contactInfo;
        this.accessibility = accessibility;
        this.vaccineRecords = new LinkedList<VaccinationRecord>();
    }

    public String getPPSN() {
        return PPSN;
    }

    public void setPPSN(String PPSN) {
        this.PPSN = PPSN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public LinkedList<VaccinationRecord> getVaccineRecords() {
        return vaccineRecords;
    }

    public void setVaccineRecords(LinkedList<VaccinationRecord> vaccineRecords) {
        this.vaccineRecords = vaccineRecords;
    }

    @Override
    public String toString() {
        return "Patient:" +
                "\n" + "PPSN: " + PPSN +
                "\n" + "Patient Name: " + name +
                "\n" + "Patient Date Of Birth: " + dob +
                "\n" + "Home Eircode: " + contactInfo +
                "\n" + "Accessibility: " + accessibility;
    }
}
