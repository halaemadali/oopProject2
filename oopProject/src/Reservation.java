
import java.time.LocalDate;

public class Reservation {

    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Reservation(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
