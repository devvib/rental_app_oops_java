import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class RentalManager {
    private List<Car> cars;
    private List<Rental> rentals;
    private static final double LATE_PENALTY_RATE = 0.1; // 10% of the base price per day

    public RentalManager(List<Car> cars) {
        this.cars = cars;
        this.rentals = new ArrayList<>();
    }

    public void rentCar(String carId, Customer customer, int days) {
        Car selectedCar = null;
        for (Car car : cars) {
            if (car.getCarId().equals(carId) && car.isAvailable()) {
                selectedCar = car;
                break;
            }
        }

        if (selectedCar != null) {
            selectedCar.rent();
            Rental rental = new Rental(selectedCar, customer, days);
            rentals.add(rental);
            customer.addToRentalHistory(rental); // Add rental to customer's history

            double totalPrice = selectedCar.calculatePrice(days);
            System.out.println("\n== Rental Information ==\n");
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Customer Name: " + customer.getName());
            System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
            System.out.println("Rental Days: " + days);
            System.out.printf("Total Price: $%.2f%n", totalPrice);
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(String carId) {
        Car carToReturn = null;
        for (Car car : cars) {
            if (car.getCarId().equals(carId) && !car.isAvailable()) {
                carToReturn = car;
                break;
            }
        }

        if (carToReturn != null) {
            Rental rentalToRemove = null;
            for (Rental rental : rentals) {
                if (rental.getCar() == carToReturn) {
                    rentalToRemove = rental;
                    break;
                }
            }

            if (rentalToRemove != null) {
                LocalDate returnDate = LocalDate.now();
                LocalDate dueDate = rentalToRemove.getDueDate();
                long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);

                if (lateDays > 0) {
                    double penalty = lateDays * rentalToRemove.getCar().getBasePricePerDay() * LATE_PENALTY_RATE;
                    System.out.printf("Car returned late by %d days. Penalty: $%.2f%n", lateDays, penalty);
                } else {
                    System.out.println("Car returned on time.");
                }

                rentals.remove(rentalToRemove);
            } else {
                System.out.println("Car was not rented.");
            }
        } else {
            System.out.println("Invalid car ID or car is not rented.");
        }
    }

    public void viewAllRentals() {
        System.out.println("\n== All Rentals ==\n");
        if (rentals.isEmpty()) {
            System.out.println("No rentals available.");
            return;
        }

        for (Rental rental : rentals) {
            System.out.println("Rental ID: " + rental.getRentalId() +
                               ", Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() +
                               ", Customer: " + rental.getCustomer().getName() +
                               ", Days Rented: " + rental.getRentalDays());
        }
    }

    public void viewCustomerRentalHistory(String customerId) {
        System.out.println("\n== Rental History for Customer ID: " + customerId + " ==\n");
        for (Rental rental : rentals) {
            if (rental.getCustomer().getCustomerId().equals(customerId)) {
                System.out.println("Rental ID: " + rental.getRentalId() +
                                   ", Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() +
                                   ", Days Rented: " + rental.getRentalDays());
            }
        }
    }
}
