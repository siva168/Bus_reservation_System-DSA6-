public class Reservation {
    private Customer customer;
    private Bus bus;
    private int seatNumber;

    public Reservation(Customer customer, Bus bus, int seatNumber) {
        this.customer = customer;
        this.bus = bus;
        this.seatNumber = seatNumber;
    }

    public Customer getCustomer() {
        return  customer;
    }
    public Bus getBus() {
        return bus;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
}
