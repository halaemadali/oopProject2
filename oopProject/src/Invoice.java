

import java.util.Date;

public class Invoice implements Payable {

    private int id;
    private Reservation reservation;
    private double amount;
    private boolean paid;
    private Date date;
    private PaymentMethod method;

    public Invoice(Reservation r) {
        this.reservation = r;
        this.id = r.getId();
        this.amount = calculateTotal();
    }

    public double calculateTotal() {
        if (reservation == null || reservation.getRoom() == null)
            return 0;

        long diff = reservation.getCheckout().getTime() - reservation.getCheckin().getTime();
        int days = (int) (diff / (1000 * 60 * 60 * 24));

        return days * reservation.getRoom().getPrice();
    }

    public void pay(PaymentMethod method) {
        this.method = method;
        this.paid = true;
        this.date = new Date();
    }
}
