package Data;

import java.io.Serializable;

public class VaccineCentre implements Serializable {
    String name;
    String address;
    String Eircode;
    String ParkingSpaces;
    LinkedList<Booth> booths;

    public VaccineCentre(String name, String address, String eircode, String parkingSpaces /*BoothList booths*/) {
        this.name = name;
        this.address = address;
        this.Eircode = eircode;
        this.ParkingSpaces = parkingSpaces;
        this.booths = new LinkedList<Booth>();
    }

    @Override
    public String toString() {
        return "Vaccine Centre" + '\n' +
                "Name: " + name + '\n' +
                "Address: " + address + '\n' +
                "Eircode: " + Eircode + '\n' +
                "Parking Spaces: " + ParkingSpaces + '\n' +
                "No. of booths: " + booths.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEircode() {
        return Eircode;
    }

    public void setEircode(String eircode) {
        Eircode = eircode;
    }

    public String getParkingSpaces() {
        return ParkingSpaces;
    }

    public void setParkingSpaces(String parkingSpaces) {
        ParkingSpaces = parkingSpaces;
    }

    public LinkedList getBooths() {
        return booths;
    }

    public void setBooths(LinkedList booths) {
        this.booths = booths;
    }

}
