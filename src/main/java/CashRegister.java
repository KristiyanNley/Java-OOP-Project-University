import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CashRegister extends Store implements Runnable {

    private Cashier cashier;
    private List<Customer> lineOfCustomers = new ArrayList<>();
    private List<Product> productsOnCashRegister = new ArrayList<>();


    public Cashier getCashier() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier = cashier;
    }

    public List<Customer> getLineOfCustomers() {
        return lineOfCustomers;
    }

    public void setLineOfCustomers(List<Customer> lineOfCustomers) {
        this.lineOfCustomers = lineOfCustomers;
    }

    public List<Product> getProductsOnCashRegister() {
        return productsOnCashRegister;
    }

    public void setProductsOnCashRegister(List<Product> productsOnCashRegister) {
        this.productsOnCashRegister = productsOnCashRegister;
    }

    public void addACustomer (Customer customer) {
        lineOfCustomers.add(customer);
    }

    public CashRegister(String name, Cashier cashier) {
        super(name);
        this.cashier = cashier;
    }

    public BigDecimal totalAmount (ArrayList<Product> products) {

        BigDecimal total = new BigDecimal(0);

        for (Product p : products) {
            total = total.add(p.getSellingPrice());
        }
        return total;
    }

    public Receipt sellProducts(ArrayList<Product> products, Store store) {

        Receipt r = new Receipt(UUID.randomUUID());

        r.setFinalAmountToBePaid(totalAmount(products));

        r.setTimeAndDateOfTheTransaction(LocalDate.now());

        r.setCashier_Name(cashier.getName());

        System.out.println("Welcome to the cash register!");
        System.out.println(cashier.getName() + " will be at your service!");
        System.out.println(lineOfCustomers.get(0).getName() + " has a balance of: " + lineOfCustomers.get(0).getCashAvailable() + " BGN.");
        System.out.println("The total amount to be paid in this case would be: " + totalAmount(products) + " BGN.");

        if (totalAmount(products).compareTo(new BigDecimal(0)) > 0) {

            if (lineOfCustomers.get(0).getCashAvailable().compareTo(totalAmount(products)) >= 0) {

                try {

                    for (int i = 0; i < products.size(); i++) {
                        r.addToProductsSoldArray(products.get(i));
                    }

                } catch (Exception InsufficientProductsAvailable) {
                    System.out.println("There are not enough products in stock in order to continue the transaction.");
                }

                lineOfCustomers.get(0).setCashAvailable(lineOfCustomers.get(0).getCashAvailable().subtract(totalAmount(products)));
                System.out.println(lineOfCustomers.get(0).getName() + ", the transaction has been completed successfully!");
                System.out.println(lineOfCustomers.get(0).getName() + " now has a balance of " + lineOfCustomers.get(0).getCashAvailable().setScale(2, BigDecimal.ROUND_HALF_UP) + " BGN.");

            } else {
                System.out.println("You do not have enough funds in order to process the payment.");
            }

        } else System.out.println("This is void transaction, however we will provide you with a receipt nonetheless!");

        store.addAReceipt(r);

        this.lineOfCustomers.remove(lineOfCustomers.get(0));

        if (this.lineOfCustomers.size() >= 1) {
            System.out.println("Next Customer!");
        }

        else System.out.println("No more customers are present on this cash register.");

        return r;
    }
}
