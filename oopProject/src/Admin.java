
import java.time.LocalDate;

public class Admin extends Staff {

     public Admin(String username, String password, LocalDate dateOfBirth, int workingHours){
         super(username, password,dateOfBirth,Role.ADMIN, workingHours);

     }

     
       //add room
     public void addRoom(Room room){

         for(Room r: HotelDatabase.rooms){
             if(r.getroomnumber()== room.getroomnumber()){
                 System.out.println(" Room already exists");
                 return;
             }
         }

        HotelDatabase.rooms.add(room);
        System.out.println("Room added successfully");
     }

       
     //view rooms
    public void viewRooms(){

         for(Room r: HotelDatabase.rooms){
             System.out.println(r);
         }
     }

     //update room
     public void updateRoom(){
          
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
