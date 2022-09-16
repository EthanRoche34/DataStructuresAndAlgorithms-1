package Data;

import java.io.Serializable;

public class Booth implements Serializable {
    String identifier;
    String floorLevel;
    String accessibility;
    LinkedList<Appointment> appointments;

    public Booth(String identifier, String floorLevel, String accessibility) {
        this.identifier = identifier;
        this.floorLevel = floorLevel;
        this.accessibility = accessibility;
        this.appointments = new LinkedList<Appointment>();
    }

    public String toString(){
        return "Vaccine Booth: " + identifier
                + "\n" + "Floor: " + floorLevel
                + "\n" + "Accessibility Info: " + accessibility
                + "\n" + "No. of Appointments " + appointments.size();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFloorLevel() {
        return floorLevel;
    }

    public void setFloorLevel(String floorLevel) {
        this.floorLevel = floorLevel;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public LinkedList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(LinkedList<Appointment> appointments) {
        this.appointments = appointments;
    }
}
