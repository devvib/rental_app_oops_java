import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private RentalManager rentalManager;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentalManager = new RentalManager(cars);
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
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

    public void viewAllRentals() {
        rentalManager.viewAllRentals();
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
                System.out.println("7. Show All Cars");
                System.out.println("8. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: rentCarMenu(scanner); break;
                    case 2: returnCarMenu(scanner); break;
                    case 3: updateCustomerMenu(scanner); break;
                    case 4: viewRentalHistoryMenu(scanner); break;
                    case 5: viewAllRentals(); break;
                    case 6: addNewCarMenu(scanner); break;
                    case 7: showAllCars(); break;
                    case 8: return;
                    default: System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        }
        // System.out.println("\nThank you for using the Car Rental System!");
    }

    private void rentCarMenu(Scanner scanner) {
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

        rentalManager.rentCar(carId, newCustomer, rentalDays);
    }

    private void returnCarMenu(Scanner scanner) {
        System.out.println("\n== Return a Car ==\n");
        System.out.print("Enter the car ID you want to return: ");
        String carId = scanner.nextLine();

        rentalManager.returnCar(carId);
    }

    private void updateCustomerMenu(Scanner scanner) {
        System.out.println("\n== Update Customer Name ==\n");
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        updateCustomerName(customerId, newName);
    }

    private void viewRentalHistoryMenu(Scanner scanner) {
        System.out.println("\n== View Rental History ==\n");
        System.out.print("Enter your Customer ID: ");
        String customerId = scanner.nextLine();
        rentalManager.viewCustomerRentalHistory(customerId);
    }

    private void addNewCarMenu(Scanner scanner) {
        System.out.println("\n== Add New Car ==\n");
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

    public static void main(String[] args) {
        CarRentalSystem carRentalSystem = new CarRentalSystem();
        carRentalSystem.menu();
    }
}
