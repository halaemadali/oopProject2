import java.time.LocalDate;

public class Receptionist extends Staff {

    public Receptionist(String username, String password, LocalDate dateOfBirth, int workingHours) {

        super(username, password, dateOfBirth, Role.RECEPTIONIST, workingHours);
        HotelDatabase.receptionists.add(this);
    }


    public void checkIn(int reservationId) {
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {

                if (r.getStatus() == ReservationStatus.CANCELLED) {
                    System.out.println("Cannot check in a cancelled reservation.");
                    return;
                }

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("This reservation is already completed.");
                    return;
                }

                if (r.getStatus() == ReservationStatus.CHECKED_IN) {
                    System.out.println("Guest already checked in.");
                    return;
                }

                if (r.getStatus() != ReservationStatus.CONFIRMED) {
                    System.out.println("Reservation must be CONFIRMED before check-in.");
                    return;
                }

                // Add amenities guest chose during reservation
                for (int i = 0; i < r.getRequired_amenities().size(); i++) {
                    r.getRoom().addAmenity(r.getRequired_amenities().get(i));
                }


                r.getRoom().setAvailable(false);
                r.setStatus(ReservationStatus.CHECKED_IN);
                System.out.println("Guest checked in successfully.");
                return;
            }
        }
        System.out.println("No reservation found with this ID.");
    }




    public void checkoutGuest(int reservationId) {
        for (Reservation r : HotelDatabase.reservations) {

            if (r.getID() == reservationId) {

                if (r.getStatus() == ReservationStatus.COMPLETED) {
                    System.out.println("Guest already checked out.");
                    return;
                }

                if (r.getStatus() == ReservationStatus.CANCELLED) {
                    System.out.println("Cannot checkout a cancelled reservation.");
                    return;
                }

                if (r.getStatus() != ReservationStatus.CHECKED_IN) {
                    System.out.println("Guest has not checked in yet.");
                    return;
                }

                // Check payment
                if (r.getInvoice() == null) {
                    System.out.println("No invoice found for this reservation.");
                    return;
                }

                if (!r.getInvoice().getPaid()) {
                    System.out.println("Payment not completed. Cannot checkout.");
                    return;
                }

                // Finalize checkout
                r.setStatus(ReservationStatus.COMPLETED);
                r.getRoom().setAvailable(true);
                r.getRoom().getAmenities().clear();  // remove amenities added at checkIn
                System.out.println("Checkout successful. Room is now available.");
                return;
            }
        }
        System.out.println("No reservation found with this ID.");
    }

}
