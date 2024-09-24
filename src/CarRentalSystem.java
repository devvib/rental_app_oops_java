import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;
    private static final double LATE_PENALTY_RATE = 0.1; // 10% of the base price per day

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            Rental rental = new Rental(car, customer, days);
            rentals.add(rental);
            customer.addToRentalHistory(rental); // Add rental to customer's history

        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void viewCustomerRentalHistory(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                System.out.println("Rental History for " + customer.getName() + ":");
                for (Rental rental : customer.getRentalHistory()) {
                    System.out.println("Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() + 
                                       ", Days: " + rental.getDays());
                }
                return;
            }
        }
        System.out.println("Customer not found.");
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            LocalDate returnDate = LocalDate.now();
            LocalDate dueDate = rentalToRemove.getDueDate();
            long lateDays = ChronoUnit.DAYS.between(dueDate, returnDate);
            // lateDays=2;

            if (lateDays > 0) {
                double penalty = lateDays * rentalToRemove.getCar().getBasePricePerDay() * LATE_PENALTY_RATE;
                System.out.printf("Car returned late by %d days. Penalty: $%.2f%n", lateDays, penalty);
                System.out.printf("Total penalty price: $%.2f%n", penalty);
            } else {
                System.out.println("Car returned on time.");
            }

            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void updateCustomerName(String customerId, String newName) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                customer.updateName(newName);
                System.out.println("Customer name updated successfully.");
                return;
            }
        }
        System.out.println("Customer not found.");
    }



    public void viewAllRentals() {
        System.out.println("\n== All Rentals ==\n");
        if (rentals.isEmpty()) {
            System.out.println("No rentals available.");
            return;
        }
        
        for (Rental rental : rentals) {
            System.out.println("Customer ID: " + rental.getCustomer().getCustomerId() +
                               ", Customer Name: " + rental.getCustomer().getName() +
                               ", Car: " + rental.getCar().getBrand() + " " + rental.getCar().getModel() +
                               ", Rental Days: " + rental.getDays());
        }
    }


    public void addNewCar() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter Car ID: ");
        String carId = scanner.nextLine();
        
        System.out.print("Enter Brand: ");
        String brand = scanner.nextLine();
        
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        
        System.out.print("Enter Base Price per Day: ");
        double basePricePerDay = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        
        Car newCar = new Car(carId, brand, model, basePricePerDay);
        addCar(newCar);
        
        System.out.println("New car added successfully!");
    }

    public void showAllCars() {
        System.out.println("\n== All Cars ==\n");
        if (cars.isEmpty()) {
            System.out.println("No cars available.");
            return;
        }

        for (Car car : cars) {
            System.out.println("Car ID: " + car.getCarId() +
                               ", Brand: " + car.getBrand() +
                               ", Model: " + car.getModel() +
                               ", Price per Day: $" + car.calculatePrice(1) + 
                               ", Available: " + (car.isAvailable() ? "Yes" : "No"));
        }
    }



    



    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("===== Car Rental System =====");
                System.out.println("1. Rent a Car");
                System.out.println("2. Return a Car");
                System.out.println("3. Update Customer Name");
                System.out.println("4. View Rental History");
                System.out.println("5. View All Rentals");
                System.out.println("6. Add New Car");
                System.out.println("7. Show All Cars"); // New option for showing all cars
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 1) {
                    System.out.println("\n== Rent a Car ==\n");
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();

                    System.out.println("\nAvailable Cars:");
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
                        }
                    }

                    System.out.print("\nEnter the car ID you want to rent: ");
                    String carId = scanner.nextLine();

                    System.out.print("Enter the number of days for rental: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && car.isAvailable()) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar != null) {
                        double totalPrice = selectedCar.calculatePrice(rentalDays);
                        System.out.println("\n== Rental Information ==\n");
                        System.out.println("Customer ID: " + newCustomer.getCustomerId());
                        System.out.println("Customer Name: " + newCustomer.getName());
                        System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                        System.out.println("Rental Days: " + rentalDays);
                        System.out.printf("Total Price: $%.2f%n", totalPrice);

                        System.out.print("\nConfirm rental (Y/N): ");
                        String confirm = scanner.nextLine();

                        if (confirm.equalsIgnoreCase("Y")) {
                            rentCar(selectedCar, newCustomer, rentalDays);
                            System.out.println("\nCar rented successfully.");
                        } else {
                            System.out.println("\nRental canceled.");
                        }
                    } else {
                        System.out.println("\nInvalid car selection or car not available for rent.");
                    }
                } else if (choice == 2) {
                    System.out.println("\n== Return a Car ==\n");
                    System.out.print("Enter the car ID you want to return: ");
                    String carId = scanner.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equals(carId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully.");
                    } else {
                        System.out.println("Invalid car ID or car is not rented.");
                    }
                } else if (choice == 3) { // Updated option handling
                    System.out.println("\n== Update Customer Name ==\n");
                    System.out.print("Enter your Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = scanner.nextLine();

                    updateCustomerName(customerId, newName);
                }else if (choice == 4) { // New option handling
                    System.out.println("\n== View Rental History ==\n");
                    System.out.print("Enter your Customer ID: ");
                    String customerId = scanner.nextLine();
                    viewCustomerRentalHistory(customerId);
                } else if (choice == 5) { // New option handling for admin
                    viewAllRentals();
                } else if (choice == 6) { // New option handling for adding a car
                    addNewCar();
                } else if (choice == 7) { // New option handling for showing all cars
                    showAllCars();
                } else if (choice == 8) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }
}
