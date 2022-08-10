import java.math.BigDecimal;

public class Customer {
    private final String name;
    private BigDecimal cashAvailable;

    public Customer(String name, BigDecimal cashAvailable) {
        this.name = name;
        this.cashAvailable = cashAvailable;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(BigDecimal cashAvailable) {
        this.cashAvailable = cashAvailable;
    }
}
