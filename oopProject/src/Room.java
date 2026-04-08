public class Room {
private int roomnumber;
private RoomType type;
private ArrayList<Amenity> amenities;
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
    public void addamenity()
    {
        
    }
}
