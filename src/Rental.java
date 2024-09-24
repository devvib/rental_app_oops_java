import java.time.LocalDate;

public class Rental {
    private static int idCounter = 1; // to generate unique rental IDs
    private String rentalId;
    private Car car;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate dueDate;
    private int rentalDays;

    public Rental(Car car, Customer customer, int rentalDays) {
        this.rentalId = "RENT" + idCounter++;
        this.car = car;
        this.customer = customer;
        this.rentalDays = rentalDays;
        this.rentalDate = LocalDate.now();
        this.dueDate = rentalDate.plusDays(rentalDays); // due date based on rental days
    }

    // Getter for rental ID
    public String getRentalId() {
        return rentalId;
    }

    // Getter for the car
    public Car getCar() {
        return car;
    }

    // Getter for customer
    public Customer getCustomer() {
        return customer;
    }

    // Getter for rental days
    public int getRentalDays() {
        return rentalDays;
    }

    // Getter for due date
    public LocalDate getDueDate() {
        return dueDate;
    }

    // Getter for rental date
    public LocalDate getRentalDate() {
        return rentalDate;
    }
}
