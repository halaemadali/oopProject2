
import java.time.LocalDate;

public class Admin extends Staff {

     public Admin(String username, String password, LocalDate dateOfBirth, int workingHours){
         super(username, password,dateOfBirth,Role.ADMIN, workingHours);

     }
