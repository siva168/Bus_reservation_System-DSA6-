import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bus {
    private String busNumber;
    private int totalSeats;
    private List<Boolean> seatAvailability;
    private String startingPoint;
    private String endingPoint;
    private String startingTime;
    private float fare;
    private boolean []seats;

    public Bus(String busNumber, int totalSeats, String startingPoint, String endingPoint, String startingTime, float fare) {
        this.busNumber = busNumber;
        this.totalSeats = totalSeats;
        this.seatAvailability = new ArrayList<Boolean>(totalSeats);
        for(int i = 0; i < totalSeats; i++) {
            seatAvailability.add(true);
        }
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.startingTime = startingTime;
        this.fare = fare;
        this.seats = new boolean[totalSeats +1];
        //Arrays.fill(seats, true);
    }

    public String getBusNumber() {
        return busNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public float getFare() {
        return fare;
    }

    public boolean reserveSeat(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= totalSeats && seatAvailability.get(seatNumber - 1)) {
            seatAvailability.set(seatNumber - 1, false);
            return true;
        } else {
            return false;
        }
    }

    public boolean cancelReservation(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= totalSeats && !seatAvailability.get(seatNumber - 1)) {
            seatAvailability.set(seatNumber - 1, true);
            return true; // Reservation canceled successfully
        }
        return false; // Invalid seat number or seat already available
    }

    public List<Integer> getAvailableSeats() {
        List<Integer> availableSeats = new ArrayList<>();
        for (int i = 0; i < totalSeats; i++) {
            if (seatAvailability.get(i)) {
                availableSeats.add(i + 1);
            }
        }
        return availableSeats;
    }
}
