import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDate;

class HotelDatabase {

  public static ArrayList<Guest> guests = new ArrayList<>();
  public static ArrayList<Room> rooms = new ArrayList<>();
  public static ArrayList<Reservation> reservations = new ArrayList<>();
  public static ArrayList<RoomType> roomTypes = new ArrayList<>();
  public static ArrayList<Amenity> amenities = new ArrayList<>();
  public static ArrayList<Admin> admins = new ArrayList<>();
  public static ArrayList<Receptionist> receptionists = new ArrayList<>();

  static Random rand = new Random();

  public static void initializeData() {
    createRoomTypes();
    createAmenities();
    createRooms();
    createGuests();
    createStaff();
    createReservations();
  }

  private static void createRoomTypes() {
    // RoomType constructor adds itself to HotelDatabase.roomTypes automatically
    new RoomType("Single", 1, 500);
    new RoomType("Double", 2, 800);
    new RoomType("Triple", 3, 1100);
    new RoomType("Suite", 4, 2000);
  }

  private static void createAmenities() {
    // Amenity constructor adds itself to HotelDatabase.amenities automatically
    new Amenity("WiFi", 50);
    new Amenity("TV", 30);
    new Amenity("Mini Bar", 100);
    new Amenity("Air Conditioning", 80);
    new Amenity("Room Service", 120);
  }

  private static void createRooms() {
    // Room constructor does NOT add itself — must add manually
    View[] views = View.values();
    for (int i = 0; i < 130; i++) {
      int roomNumber = 100 + i;
      RoomType type = HotelDatabase.roomTypes.get(rand.nextInt(HotelDatabase.roomTypes.size()));
      boolean available = rand.nextBoolean();
      View view = views[rand.nextInt(views.length)];
      int floor = rand.nextInt(6);
      Room room = new Room(roomNumber, type, available, view, floor);
      HotelDatabase.rooms.add(room);
    }
  }

  private static void createGuests() {
    // Guest constructor does NOT add itself — must add manually
    String[] firstNames = {
            "Hana", "Mohamed", "Sara", "Ali", "Fatma", "Youssef", "Nour",
            "Omar", "Layla", "Hassan", "Salma", "Karim", "Rana", "Tarek",
            "Reem", "Khaled", "Lina", "Ibrahim", "Jana", "Farah",
            "Mostafa", "Yasmin", "Amr", "Dina", "Mahmoud", "Hala", "Tamer", "Mariam", "Ahmed"
    };

    String[] lastNames = {
            "Hassan", "Ali", "Mahmoud", "Ibrahim", "Khalil", "Youssef",
            "Mostafa", "Fathy", "Nasser", "Saad", "Adel", "Hamdy",
            "Zaki", "Farouk", "Tawfik", "Rashad", "Shawky", "Aziz"
    };

    boolean[] isFemale = {
            true, false, true, false, true, false, true,
            false, true, false, true, false, true, false,
            true, false, true, false, true, false,
            false, true, false, true, false, true, false, true, false
    };

    for (int i = 0; i < 29; i++) {
      try {
        int index = i % firstNames.length;

        String first = firstNames[index];
        String last = lastNames[rand.nextInt(lastNames.length)];
        String username = (first + "_" + last + "_" + i).toLowerCase();
        String password = "pass0" + i;

        LocalDate dob = LocalDate.of(
                1970 + rand.nextInt(30),
                1 + rand.nextInt(12),
                1 + rand.nextInt(28)
        );

        double balance = 1000 + rand.nextInt(5000);
        String address = "Cairo";
        Gender gender = isFemale[index] ? Gender.FEMALE : Gender.MALE;

        Guest g = new Guest(username, password, dob, balance, address, gender);
        HotelDatabase.guests.add(g);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void createStaff() {
    // Admin constructor does NOT add itself — must add manually
    // Receptionist constructor DOES add itself automatically
    try {
      Admin a1 = new Admin("admin1", "admin123", LocalDate.of(1980, 5, 10), 8);
      Admin a2 = new Admin("admin2", "admin456", LocalDate.of(1978, 3, 15), 8);
      HotelDatabase.admins.add(a1);
      HotelDatabase.admins.add(a2);
    } catch (Exception e) {
      e.printStackTrace();
    }

    try {
      new Receptionist("receptionist1", "recep123", LocalDate.of(1995, 6, 20), 8);
      new Receptionist("receptionist2", "recep456", LocalDate.of(1998, 9, 25), 8);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void createReservations() {
    for (int i = 0; i < 40; i++) {
      try {
        Guest g = HotelDatabase.guests.get(rand.nextInt(HotelDatabase.guests.size()));
        Room r = HotelDatabase.rooms.get(rand.nextInt(HotelDatabase.rooms.size()));

        LocalDate in = LocalDate.now().plusDays(1 + rand.nextInt(10));
        LocalDate out = in.plusDays(1 + rand.nextInt(5));

        Reservation res = new Reservation(r, g, in, out);
        g.getReservations().add(res);

        ReservationStatus[] statuses = {
                ReservationStatus.PENDING,
                ReservationStatus.CONFIRMED,
                ReservationStatus.CANCELLED
        };
        res.setStatus(statuses[rand.nextInt(statuses.length)]);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}