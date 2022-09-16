package Data;

import java.io.Serializable;
import java.time.LocalDate;

public class VaccinationRecord implements Serializable {
LocalDate vaccinationDate;
String vaccineType;
String vaccineIdentifier;

    public VaccinationRecord(LocalDate vaccinationDate, String vaccineType, String vaccineIdentifier) {
        this.vaccinationDate = vaccinationDate;
        this.vaccineType = vaccineType;
        this.vaccineIdentifier = vaccineIdentifier;
    }

    @Override
    public String toString() {
        return "Vaccine Reccord: " +
                "\n" + "Vaccine Given: " + vaccinationDate +
                "\n" + "Vaccine Type: " + vaccineType +
                "\n" + "Vaccine Identifier: " + vaccineIdentifier;
    }

    public LocalDate getVaccinationDate() {
        return vaccinationDate;
    }

    public void setVaccinationDate(LocalDate vaccinationDate) {
        this.vaccinationDate = vaccinationDate;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public String getVaccineIdentifier() {
        return vaccineIdentifier;
    }

    public void setVaccineIdentifier(String vaccineIdentifier) {
        this.vaccineIdentifier = vaccineIdentifier;
    }
}
