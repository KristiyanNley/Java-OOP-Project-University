import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Product implements Serializable {
    private final String name;
    private final UUID uuid;
    private BigDecimal sellingPrice = new BigDecimal(0);
    private ProductCategory productCategory;
    private int daysToExpiry;
    private BigDecimal percentDiscount;
    private LocalDate expiryDate;

    public String getName() {
        return name;
    }

    public int getDaysToExpiry() {
        return daysToExpiry;
    }

    public void setDaysToExpiry(int daysToExpiry) {
        this.daysToExpiry = daysToExpiry;
    }

    public void setSellingPrice(BigDecimal sellingPrice, Store store) {

        if (this.productCategory == ProductCategory.FOOD_PRODUCT) {
            this.sellingPrice = sellingPrice.add(store.getOverChargeFoodProducts());
        }

        if (this.productCategory == ProductCategory.NON_FOOD_PRODUCT) {
            this.sellingPrice = sellingPrice.add(store.getOverChargeNonFoodProducts());
        }

        int numDays = Period.between(expiryDate, LocalDate.now()).getDays();

        numDays = numDays *-1;

        double doubleSellingPrice = this.sellingPrice.doubleValue();
        double doublePercentDiscount = percentDiscount.doubleValue();
        double result = doubleSellingPrice - ((doubleSellingPrice/100) * doublePercentDiscount);

        if (numDays <= daysToExpiry || daysToExpiry == 0) {
            System.out.println("There are " + numDays + " days until " + this.getName() + " expires and as a result it is discounted from " + this.sellingPrice + " to " + result + "BGN!");
            this.sellingPrice = new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public void setSellingPriceNoText(BigDecimal sellingPrice, Store store) {

        if (this.productCategory == ProductCategory.FOOD_PRODUCT) {
            this.sellingPrice = sellingPrice.add(store.getOverChargeFoodProducts());
        }

        if (this.productCategory == ProductCategory.NON_FOOD_PRODUCT) {
            this.sellingPrice = sellingPrice.add(store.getOverChargeNonFoodProducts());
        }

        int numDays = Period.between(expiryDate, LocalDate.now()).getDays();

        numDays = numDays *-1;

        double doubleSellingPrice = this.sellingPrice.doubleValue();
        double doublePercentDiscount = percentDiscount.doubleValue();
        double result = doubleSellingPrice - ((doubleSellingPrice/100) * doublePercentDiscount);

        if (numDays <= daysToExpiry || daysToExpiry == 0) {
            this.sellingPrice = new BigDecimal(result).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public BigDecimal getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(BigDecimal percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public Product(UUID uuid, String name, ProductCategory productCategory, LocalDate expiryDate) {

        int numDays = Period.between(expiryDate, LocalDate.now()).getDays();
        this.setDaysToExpiry(numDays);
        this.uuid = uuid;
        this.name = name;
        this.productCategory = productCategory;
        this.expiryDate = expiryDate;

    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", sellingPrice=" + sellingPrice.setScale(2, BigDecimal.ROUND_HALF_UP) +
                ", productCategory=" + productCategory +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
