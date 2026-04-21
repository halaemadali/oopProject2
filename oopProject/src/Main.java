

import java.util.Date;

public class Main {

    public static void main(String[] args) {

        // Room Type
        RoomType t1 = new RoomType("Single", 1, 500);

        // Amenities
        Amenity wifi = new Amenity("WiFi", 50);
        Amenity tv = new Amenity("TV", 100);

        // Room
        Room r1 = new Room();
        r1.setRoomNumber(101);
        r1.setType(t1);
        r1.setAvailable(true);

        r1.addAmenity(wifi);
        r1.addAmenity(tv);

        System.out.println("Room Number: " + r1.getRoomNumber());
        System.out.println("Available: " + r1.isAvailable());
        System.out.println("Price: " + r1.calculateRoomPrice());

        // Guest
        Guest g1 = new Guest();

        // Reservation
        try {
            Date in = new Date();
            Date out = new Date(System.currentTimeMillis() + 86400000);

            Reservation res = new Reservation(r1, g1, in, out);

            res.confirm();
            System.out.println("Reservation status: " + res.getStatus());

            // Invoice test
            System.out.println("Invoice total: " + res.getInvoice().calculateTotal());

            res.getInvoice().pay(PaymentMethod.CASH);

            res.complete();

            System.out.println("Final status: " + res.getStatus());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
