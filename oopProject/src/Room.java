import java.util.ArrayList;

public class Room {
    private int roomnumber;
    private RoomType type;
   public ArrayList<Amenity> amenities=new ArrayList<>();

    private boolean isavailable ;
    private double price;

    public Room(){

    }
    public boolean checkavailability (boolean isavailable)
    { if (this.isavailable==true)
        return true;
    else
        return false;

    }
    public ArrayList<Amenity> getAmenities() {
        return this.amenities;
    }
    public void addAmenity(Amenity amenity) {
        amenities.add(amenity);
        System.out.println(amenity + " added successfully!");
    }
    public void removeAmenity(Amenity amenity) {
        amenities.remove(amenity);
        System.out.println(amenity + " removed successfully!");
    }
}
