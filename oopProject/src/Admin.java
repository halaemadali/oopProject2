
import java.time.LocalDate;

public class Admin extends Staff {

     public Admin(String username, String password, LocalDate dateOfBirth, int workingHours){
         super(username, password,dateOfBirth,Role.ADMIN, workingHours);

     }

     
       //add room
     public void addRoom(int roomNumber, double price, boolean isAvailable, String NO3, int capacity, double basePrice) {

        RoomType type = new RoomType(NO3, capacity, basePrice);

        Room newRoom = new Room();
        newRoom.setroomnumber(roomNumber);
        newRoom.setPrice(price);
        newRoom.setisavailable(isAvailable);
        newRoom.settype(type);

        HotelDatabase.rooms.add(newRoom);

        System.out.println("Room " + roomNumber + " added successfully!");
    }

     
       //view room
    public void viewRoom(int roomNumber) {
        for (Room r : HotelDatabase.rooms) {
            if (r.getroomnumber() == roomNumber) {

                System.out.println("\n=============VIEW ROOM ==============" );

                System.out.println("Room Number: " + r.getroomnumber());
                System.out.println("Price: " + r.getPrice());
                System.out.println("Available: " + r.getisavailable());

                // RoomType details
                System.out.println("NO3: " + r.gettype().getNO3());
                System.out.println("Capacity: " + r.gettype().getCapacity());
                System.out.println("Base Price: " + r.gettype().getBasePrice());
                System.out.println("Amenities: " + r.getAmenities());

                System.out.println("======================================= \n");
              return;
            }
        }
        System.out.println("Room not found");
    }

  

     //update room
    public void updateRoom(int roomnumber, double newPrice, boolean isAvailable, String NO3 , int capacity , double basePrice ) {
       for (Room r : HotelDatabase.rooms) {
           if (r.getroomnumber() == roomnumber) {

               r.setPrice(newPrice);
               r.setisavailable(isAvailable);

               r.gettype().setNO3(NO3);
               r.gettype().setCapacity(capacity);
               r.gettype().setBasePrice(basePrice);


               System.out.println("Room updated successfully");
            return;
           }
       }
       System.out.println("Room not found");

   }


     
      //delete room
     public void deleteRoom(int roomnumber){
         for (int i=0 ; i < HotelDatabase.rooms.size() ; i++){
             if(HotelDatabase.rooms.get(i).getroomnumber() == roomnumber) {
                 HotelDatabase.rooms.remove(i);
                 System.out.println(" Room deleted successfully");
                 return;
             }
         }

         System.out.println ("Room not found");

     }
     



  


}
