import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
public class Main {
    private static BusReservationSystem reservationSystem = new BusReservationSystem();
    private static RegisteredCustomers registeredCustomers = new RegisteredCustomers();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n==========================");
            System.out.println("   Bus Reservation System");
            System.out.println("==========================");
            System.out.println("1. Register Customer");
            System.out.println("2. Register Bus");
            System.out.println("3. Search Buses");
            System.out.println("4. Reserve Seat");
            System.out.println("5. Cancel Reservation");
            System.out.println("6. Request Seat Change");
            System.out.println("7. Display Reservations");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerCustomer();
                    break;
                case 2:
                    registerBus();
                    break;
                case 3:
                    searchBuses();
                    break;
                case 4:
                    reserveSeatWithRoute();
                    break;
                case 5:
                    cancelReservation();
                    break;
                case 6:
                    requestSeatChange();
                    break;
                case 7:
                    reservationSystem.displayReservations();
                    break;
                case 8:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerCustomer() {
        System.out.println("\n===============================");
        System.out.println("       Register Customer");
        System.out.println("===============================");
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        String mobileNumber;
        String email;
        boolean isUnique = false;

        do {
            System.out.print("Enter mobile number: ");
            mobileNumber = scanner.nextLine();
            if (!isValidPhoneNumber(mobileNumber)) {
                System.out.println("Invalid phone number format. Please enter a valid phone number.");
            } else if (reservationSystem.isMobileNumberRegistered(mobileNumber)) {
                System.out.println("Mobile number already registered. Please enter a different mobile number.");
            } else {
                isUnique = true;
            }
        } while (!isUnique);

        isUnique = false;
        do {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (!isValidEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            } else if (reservationSystem.isEmailRegistered(email)) {
                System.out.println("Email already registered. Please enter a different email address.");
            } else {
                isUnique = true;
            }
        } while (!isUnique);

        System.out.print("Enter city: ");
        String city = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Customer customer = new Customer(name, mobileNumber, email, age);
        reservationSystem.registerCustomer(customer);
        System.out.println("Customer registered successfully.");
    }

    private static void registerBus() {
        System.out.println("\n=======================");
        System.out.println("      Register Bus");
        System.out.println("=======================");
        System.out.print("Enter bus number: ");
        String busNumber = scanner.nextLine();
        System.out.print("Enter total seats: ");
        int totalSeats = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter starting point: ");
        String startingPoint = scanner.nextLine();
        System.out.print("Enter ending point: ");
        String endingPoint = scanner.nextLine();
        System.out.print("Enter starting time: ");
        String startingTime = scanner.nextLine();
        System.out.print("Enter fare: ");
        float fare = scanner.nextFloat();
        scanner.nextLine();

        Bus bus = new Bus(busNumber, totalSeats, startingPoint, endingPoint, startingTime, fare);
        reservationSystem.registerBus(bus);
        System.out.println("Bus registered successfully.");

        // Display seat numbers
        System.out.println("Seat Numbers:");
        for (int i = 1; i <= totalSeats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void searchBuses() {
        System.out.println("\n=====================");
        System.out.println("      Search Buses");
        System.out.println("=====================");
        System.out.print("Enter starting point: ");
        String startingPoint = scanner.nextLine();
        System.out.print("Enter ending point: ");
        String endingPoint = scanner.nextLine();

        List<Bus> matchingBuses = reservationSystem.searchBuses(startingPoint, endingPoint);
        if (matchingBuses.isEmpty()) {
            System.out.println("No buses found for the given route.");
        } else {
            System.out.println("=====================================");
            System.out.printf("%-15s %-15s %-15s %-15s %-10s\n", "Bus Number", "Starting Point", "Ending Point", "Starting Time", "Fare");
            System.out.println("=====================================");
            for (Bus bus : matchingBuses) {
                System.out.printf("%-15s %-15s %-15s %-15s %-10.2f\n", bus.getBusNumber(), bus.getStartingPoint(), bus.getEndingPoint(), bus.getStartingTime(), bus.getFare());
            }
        }
    }

    private static void reserveSeatWithRoute() {
        System.out.println("\n=====================================");
        System.out.println("      Reserve Seat ");
        System.out.println("=====================================");
        System.out.print("Enter starting point: ");
        String startingPoint = scanner.nextLine();
        System.out.print("Enter ending point: ");
        String endingPoint = scanner.nextLine();

        List<Bus> matchingBuses = reservationSystem.searchBuses(startingPoint, endingPoint);
        if (matchingBuses.isEmpty()) {
            System.out.println("No buses found for the given route.");
        } else {
            System.out.println("=====================================");
            System.out.printf("%-15s %-15s %-15s %-15s %-10s\n", "Bus Number", "Starting Point", "Ending Point", "Starting Time", "Fare");
            System.out.println("=====================================");
            for (Bus bus : matchingBuses) {
                System.out.printf("%-15s %-15s %-15s %-15s %-10.2f\n", bus.getBusNumber(), bus.getStartingPoint(), bus.getEndingPoint(), bus.getStartingTime(), bus.getFare());
            }


            System.out.println("=====================================");
            System.out.println("      Seat Reservation");
            System.out.println("=====================================");
            System.out.print("Enter customer mobile number: ");
            String customerMobile = scanner.nextLine();
            System.out.print("Enter bus number: ");
            String busNumber = scanner.nextLine();
            for (Bus bus : matchingBuses) {
                if (bus.getBusNumber().equals(busNumber)) {

                    List<Integer> availableSeats = bus.getAvailableSeats();

                    Map<Integer, Boolean> seatAvailabilityMap = new HashMap<>();
                    for (Integer seat : availableSeats) {
                        seatAvailabilityMap.put(seat, true);
                    }
                    reservationSystem.reserveSeat(customerMobile, busNumber, seatAvailabilityMap);
                    break;
                }
            }
        }
    }

    private static void cancelReservation() {
        System.out.println("\n=============================");
        System.out.println("      Cancel Reservation");
        System.out.println("=============================");
        System.out.print("Enter customer mobile number: ");
        String customerMobile = scanner.nextLine();
        System.out.print("Enter bus number: ");
        String busNumber = scanner.nextLine();

        reservationSystem.cancelReservation(customerMobile, busNumber);
    }

    private static void requestSeatChange() {
        System.out.println("\n==============================");
        System.out.println("      Request Seat Change");
        System.out.println("==============================");
        System.out.print("Enter customer mobile number: ");
        String customerMobile = scanner.nextLine();
        System.out.print("Enter bus number: ");
        String busNumber = scanner.nextLine();

        reservationSystem.requestSeatChange(customerMobile, busNumber);
    }


    private static boolean isValidEmail(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    private static boolean isValidPhoneNumber(String phoneNumber) {

        String phoneRegex = "^\\d{10}$"; // 10 digits phone number
        return phoneNumber.matches(phoneRegex);
    }

    //public List<Bus> getAllBuses() {
    //  return new ArrayList<>(buses);
    //}
}
