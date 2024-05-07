import java.util.*;

public class BusReservationSystem {
    private Map<String, Customer> customers;
    private Map<String, Bus> buses;
    private Queue<Reservation> reservationQueue; // Queue for seat reservations
    private Stack<Reservation> seatChangeQueue; // Stack for seat change requests
    private Map<String, List<Reservation>> reservations;
    private Scanner scanner;

    public BusReservationSystem() {
        customers = new HashMap<>();
        buses = new HashMap<>();
        reservationQueue = new LinkedList<>();
        seatChangeQueue = new Stack<>(); // Using Stack class for seat change requests
        reservations = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Customer registration
    public void registerCustomer(Customer customer) {
        customers.put(customer.getMobileNumber(), customer);
    }

    // Bus registration
    public void registerBus(Bus bus) {
        buses.put(bus.getBusNumber(), bus);
    }

    // Search for buses
    public List<Bus> searchBuses(String startingPoint, String endingPoint) {
        List<Bus> matchingBuses = new ArrayList<>();
        for (Bus bus : buses.values()) {
            if (bus.getStartingPoint().equals(startingPoint) && bus.getEndingPoint().equals(endingPoint)) {
                matchingBuses.add(bus);
            }
        }
        return matchingBuses;
    }

    // Reserve seat
    // Reserve seat
    public void reserveSeat(String customerMobile, String busNumber, Map<Integer, Boolean> seatAvailabilityMap) {
        Customer customer = customers.get(customerMobile);
        Bus bus = buses.get(busNumber);
        if (customer != null && bus != null) {
            System.out.println("==============================================");
            System.out.println("             Seat Reservation");
            System.out.println("==============================================");
            System.out.println("Available seats: ");
            for (Map.Entry<Integer, Boolean> entry : seatAvailabilityMap.entrySet()) {
                if (entry.getValue()) {
                    System.out.print(entry.getKey() + " "); // Display available seat numbers
                }
            }
            System.out.println();
            System.out.println();
            System.out.print("Enter the number of seats you want to reserve: ");
            int numSeats = scanner.nextInt();
            if (numSeats <= 0) {
                System.out.println("Invalid number of seats. Please enter a positive number.");
                return;
            }
            List<Integer> availableSeats = bus.getAvailableSeats();
            if (numSeats > availableSeats.size()) {
                System.out.println("Not enough seats available.");
                return;
            }
            int[] seatNumbers = new int[numSeats];
            System.out.println("Enter the seat numbers:");
            for (int i = 0; i < numSeats; i++) {
                seatNumbers[i] = scanner.nextInt();
                if (seatNumbers[i] < 1 || seatNumbers[i] > bus.getTotalSeats() || !bus.reserveSeat(seatNumbers[i] - 1)) {
                    System.out.println("Invalid seat number or seat already reserved. Please try again.");
                    return;
                }
            }
            List<Reservation> customerReservations = new ArrayList<>();
            for (int seatNumber : seatNumbers) {
                Reservation reservation = new Reservation(customer, bus, seatNumber);
                reservationQueue.offer(reservation);
                customerReservations.add(reservation);
            }
            reservations.put(customerMobile, customerReservations);
            notifyCustomer(customer, numSeats + " seats reserved successfully.");
        } else {
            System.out.println("Reservation failed: Customer or bus not found.");
        }
    }



    // Cancel reservation
    public void cancelReservation(String customerMobile, String busNumber) {
        Customer customer = customers.get(customerMobile);
        Bus bus = buses.get(busNumber);
        if (customer != null && bus != null) {
            List<Reservation> customerReservations = reservations.get(customerMobile);
            if (customerReservations != null) {
                Reservation reservationToRemove = null;
                for (Reservation reservation : customerReservations) {
                    if (reservation.getBus().equals(bus)) {
                        System.out.println("Found matching reservation: " + reservation);
                        if (bus.cancelReservation(reservation.getSeatNumber())) {
                            reservationToRemove = reservation;
                            System.out.println("Reservation canceled successfully.");
                            notifyCustomer(customer, "Reservation canceled successfully.");
                            break;
                        } else {
                            System.out.println("Cancellation failed: Invalid seat number or seat not reserved.");
                            return;
                        }
                    }
                }
                // Remove the reservation from the list after the loop to avoid ConcurrentModificationException
                if (reservationToRemove != null) {
                    customerReservations.remove(reservationToRemove);
                    System.out.println("Reservation removed from the list.");
                } else {
                    System.out.println("Cancellation failed: Reservation not found for the specified bus.");
                }
            } else {
                System.out.println("Cancellation failed: No reservations found for the customer.");
            }
        } else {
            System.out.println("Cancellation failed: Customer or bus not found.");
        }
    }

    public void displayReservations() {
        System.out.println("==============================================");
        System.out.println("              Reservations                    ");
        System.out.println("==============================================");
        System.out.println("Reservations:");
        for (Map.Entry<String, List<Reservation>> entry : reservations.entrySet()) {
            String customerMobile = entry.getKey();
            List<Reservation> customerReservations = entry.getValue();
            System.out.println("Customer Mobile Number: " + customerMobile);
            for (Reservation reservation : customerReservations) {
                System.out.println("Bus Number: " + reservation.getBus().getBusNumber());
                System.out.println("Seat Number: " + reservation.getSeatNumber());

            }
        }
    }

    public void notifyCustomer(Customer customer, String message) {

        System.out.println("Notification sent to " + customer.getName() + ": " + message);
    }

    // Request seat change
    public void requestSeatChange(String customerMobile, String busNumber) {
        Customer customer = customers.get(customerMobile);
        Bus bus = buses.get(busNumber);
        if (customer != null && bus != null) {
            List<Reservation> customerReservations = reservations.get(customerMobile);
            if (customerReservations != null) {
                for (Reservation reservation : customerReservations) {
                    if (reservation.getBus().equals(bus)) {
                        seatChangeQueue.push(reservation); // Using Stack for seat change requests
                        notifyCustomer(customer, "Your seat change request is in the queue.");
                        break;
                    }
                }
            }
        } else {
            System.out.println("Seat change request failed: Customer or bus not found.");
        }
    }
    public List<Bus> getAllBuses() {
        return new ArrayList<>(buses.values());
    }

    public boolean isMobileNumberRegistered(String mobileNumber) {
        return customers.containsKey(mobileNumber);
    }

    public boolean isEmailRegistered(String email) {
        for (Customer customer : customers.values()) {
            if (customer.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
