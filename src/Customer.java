import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String customerId;
    private String name;
    private List<Rental> rentalHistory; // New field

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
        this.rentalHistory = new ArrayList<>(); // Initialize rental history
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public List<Rental> getRentalHistory() {
        return rentalHistory; // Getter for rental history
    }

    public void addToRentalHistory(Rental rental) { // New method to add to rental history
        rentalHistory.add(rental);
    }

    public void updateName(String newName) {
        this.name = newName;
    }
}
