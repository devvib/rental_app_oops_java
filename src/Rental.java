import java.time.LocalDate;
class Rental {
    private Car car;
    private Customer customer;
    private int days;
    private LocalDate rentalDate;
    private LocalDate dueDate;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.rentalDate = LocalDate.now();
        this.dueDate = rentalDate.plusDays(days); // Due date is current date + rental days
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }
}