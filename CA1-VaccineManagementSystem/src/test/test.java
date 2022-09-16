package test;

import Data.*;
import Layout.App;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class test {

    LinkedList<VaccineCentre> centres = new LinkedList<VaccineCentre>();
    LinkedList<Patient> patients = new LinkedList<Patient>();
    VaccineCentre c1 = null;

    @BeforeEach
    void setUp() {
        c1 = new VaccineCentre("Centre 1", "Address1", "AAAAAAA", "5");
        c1.getBooths().addElement(new Booth("A1", "1", ""));
        Booth b1 = (Booth) c1.getBooths().get(0);
        LocalDate today = LocalDate.now();
        patients.addElement(new Patient("1111111A", "Patient1", today, "AAAAAAA", ""));
    }

    @org.junit.jupiter.api.Test
    void addCentreTest() {
        centres.addElement(c1);

        assertEquals("Centre 1", centres.get(0).getName());
        assertEquals("5", centres.get(0).getParkingSpaces());
    }

    @org.junit.jupiter.api.Test
    void deleteCentreTest() {
        centres.addElement(c1);
        centres.deleteNode(0);

        assertEquals(null, centres.get(0));
    }

    @org.junit.jupiter.api.Test
    void addBoothTest() {
        centres.addElement(c1);
        c1.getBooths().addElement(new Booth("B2", "1", ""));
        Booth b1 = (Booth) c1.getBooths().get(1);
        Booth b2 = (Booth) c1.getBooths().get(0);

        assertEquals("B2", b2.getIdentifier());
        assertEquals("A1", b1.getIdentifier());
    }

    @org.junit.jupiter.api.Test
    void deleteBoothTest() {
        centres.addElement(c1);
        Booth b1 = (Booth) c1.getBooths().get(0);
        System.out.println(c1.getBooths().get(0));
        c1.getBooths().deleteNode(0);

        assertEquals(null, c1.getBooths().get(0));
    }

    @org.junit.jupiter.api.Test
    void addAppointmentTest() {
        LocalDate today = LocalDate.now();
        Booth b1 = (Booth) c1.getBooths().get(0);
        Appointment appointment = new Appointment(today, 10, 15, "J&J", "111111", "", "1111111A");
        b1.getAppointments().addElement(appointment);

        assertEquals("J&J", b1.getAppointments().get(0).getType());
        assertEquals("111111", b1.getAppointments().get(0).getVaccineIdentifier());
    }

    @org.junit.jupiter.api.Test
    void deleteAppointmentTest() {
        LocalDate today = LocalDate.now();
        Booth b1 = (Booth) c1.getBooths().get(0);
        Appointment appointment = new Appointment(today, 10, 15, "J&J", "111111", "", "1111111A");
        b1.getAppointments().addElement(appointment);
        b1.getAppointments().deleteNode(0);

        assertEquals(null, b1.getAppointments().get(0));
    }

    @org.junit.jupiter.api.Test
    void getIndexTest() {
        LinkedList<Integer> test = new LinkedList<>();
        test.addElement(1);
        test.addElement(2);
        test.addElement(3);

        assertEquals(3, test.get(0));
        assertEquals(2, test.get(1));
        assertEquals(1, test.get(2));
    }
}