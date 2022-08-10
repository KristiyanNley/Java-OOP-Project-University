import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Receipt implements Serializable {
    private final UUID serial_number;
    private String Cashier_Name;
    private LocalDate timeAndDateOfTheTransaction;
    private List<Product> productsSold = new ArrayList<>();
    private BigDecimal finalAmountToBePaid;

    public Receipt(UUID serial_number) {
        this.serial_number = serial_number;
    }

    public Receipt(UUID serial_number, String cashier_name, LocalDate timeAndDateOfTheTransaction, BigDecimal finalAmountToBePaid) {
        this.serial_number = serial_number;
        Cashier_Name = cashier_name;
        this.timeAndDateOfTheTransaction = timeAndDateOfTheTransaction;
        this.finalAmountToBePaid = finalAmountToBePaid;
    }

    public UUID getSerial_number() {
        return serial_number;
    }

    public String getCashier_Name() {
        return Cashier_Name;
    }

    public void setCashier_Name(String cashier_Name) {
        Cashier_Name = cashier_Name;
    }

    public LocalDate getTimeAndDateOfTheTransaction() {
        return timeAndDateOfTheTransaction;
    }

    public void setTimeAndDateOfTheTransaction(LocalDate timeAndDateOfTheTransaction) {
        this.timeAndDateOfTheTransaction = timeAndDateOfTheTransaction;
    }

    public List<Product> getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(List<Product> productsSold) {
        this.productsSold = productsSold;
    }

    public BigDecimal getFinalAmountToBePaid() {
        return finalAmountToBePaid;
    }

    public void setFinalAmountToBePaid(BigDecimal finalAmountToBePaid) {
        this.finalAmountToBePaid = finalAmountToBePaid;
    }

    public void addToProductsSoldArray (Product p) {
        productsSold.add(p);
    }

    public static void serializeReceipt(String file, Receipt r) {

        try (FileOutputStream fos = new FileOutputStream(file);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);) {
            objectOutputStream.writeObject(r);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Receipt deserializeReceipt(String fileName) {

        Receipt receipt = null;
        try (FileInputStream fis = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fis); ) {
            receipt = (Receipt) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return receipt;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "serial_number=" + serial_number +
                ", Cashier_Name='" + Cashier_Name + '\'' +
                ", timeAndDateOfTheTransaction=" + timeAndDateOfTheTransaction +
                ", productsSold=" + productsSold +
                ", finalAmountToBePaid=" + finalAmountToBePaid +
                '}';
    }
}
