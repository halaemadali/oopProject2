
import java.time.LocalDate;

public class Receptionist extends Staff {
  
    public Receptionist(String username, String password , LocalDate dateOfBirth , int workingHours){
        super(username, password, dateOfBirth,Role.RECEPTIONIST,workingHours);
    }


    public void checkIn(){
      
    }

  
    public void checkOut(){

    }

}
