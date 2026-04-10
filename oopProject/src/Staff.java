import java.time.LocalDate;

public abstract class Staff {

    private String username;
    private String password;
    private LocalDate dateOfBirth;
    private Role role;  //ADMIN or RECEPTIONIST
    private int workingHours;

      //Constructor
    public Staff(String username, String password, LocalDate dateOfBirth, Role role, int workingHours) {

        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.workingHours = workingHours;
      
    }

    

   //Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    

    //Setters
    public void setUsername(String username) {

        if (username == null || username.isEmpty()) {
            System.out.println("username cannot be empty");
            
        }
            this.username = username;

    }

    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            System.out.println("INVALID PASSWORD");
            
        } else if (password.length() < 6) {
            System.out.println("PASSWORD MUST BE AT LEAST 6 CHARACTERS");
            
        } else {
            this.password = password;
        }
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {

        if (dateOfBirth == null) {
            System.out.println("INVALID DATE OF BIRTH");
        
        }
        this.dateOfBirth = dateOfBirth;

    }

    public void setWorkingHours(int workingHours) {
        if(workingHours <= 0){
            System.out.print("INVALID WORKING HOURS");
            
        }

        this.workingHours = workingHours;
    }


          //Common Behaviors
      
      //Prints all guests in the system
       public void viewAllGuests(){
     System.out.println("============== View All Guests ============");
        for(Guest g : HotelDatabase.guests) {

           System.out.println("Username: " + g.getUsername());
           System.out.println("password: " + g.getPassword());
            System.out.println("Date of Birth: " + g.getDateOfBirth() );
            System.out.println("Balance: " + g.getBalance());

            System.out.println("---------------------------------------");

        }
    }

    
    // Prints all rooms in the system
     public void viewAllRooms() {

        System.out.println("============== View All Rooms ============");
        for (Room r : HotelDatabase.rooms) {

            System.out.println("Room Number: " + r.getroomnumber());
            System.out.println("Price: " + r.getPrice());
            System.out.println("Available: " + r.getisavailable());

            // RoomType details
            System.out.println("NO3: " + r.gettype().getNO3());
            System.out.println("Capacity: " + r.gettype().getCapacity());
            System.out.println("Base Price: " + r.gettype().getBasePrice());
            System.out.println("Amenities: " + r.getAmenities());

            System.out.println("---------------------------------------");

        }
    }
    

      // Prints all reservations in the system
     public void viewAllReservations() {
      System.out.println("=========== View All Reservations ===========");

            for (Reservation r : HotelDatabase.reservations) {

                System.out.println("Reservation ID: " + r.getID());
                System.out.println("Guest username: " +r.getGuest().getUsername() );
                System.out.println("Room Number: " + r.getRoom().getroomnumber());
                System.out.println("Checkin: " + r.getCheckin());
                System.out.println("Checkout: " + r.getCheckout());
                System.out.println("Status: " + r.getStatus());
                System.out.println("Duration : " + r.getDuration());

                System.out.println("-------------------------------------------");


            }
    }
    




}

    


  
