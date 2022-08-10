import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Store implements Runnable{
    private final String name;
    private List<CashRegister> cashRegisters = new ArrayList<>();
    private List<Product> productsAvailable = new ArrayList<>();
    private List <Receipt> receiptsFinalized = new ArrayList<>();
    private List <ArrayList<Product>> productTypesAvailable = new ArrayList<>();
    private BigDecimal overChargeFoodProducts;
    private BigDecimal overChargeNonFoodProducts;
    private int daysToExpiry;

    public void howMuchMoneyHasBeenMade() {
        BigDecimal result = new BigDecimal(0);
        for (Receipt receipt : receiptsFinalized){
            result = result.add(receipt.getFinalAmountToBePaid());
        }
        System.out.println(this.getName() + " has made a total of: " + result.setScale(2, BigDecimal.ROUND_HALF_UP) + " from sells!");
    }

    public void howManyReceiptsThusFar (){
        System.out.println("Until now, " + receiptsFinalized.size() + " receipts have been finalized in " + this.name +"!");
    }

    public void addAReceipt (Receipt receipt) {
        this.receiptsFinalized.add(receipt);
    }

    public void addATypeOfProduct (ArrayList<Product> arrayList) {
        this.productTypesAvailable.add(arrayList);
    }

    public int getDaysToExpiry() {
        return daysToExpiry;
    }

    public String getName() {
        return name;
    }

    void addACashRegister (CashRegister cashRegister) {
        cashRegisters.add(cashRegister);
    }

    public void setDaysToExpiry (int days) {

        for (ArrayList<Product> arrayList : this.productTypesAvailable) {
            for (int i = 0; i<arrayList.size(); i++) {
                arrayList.get(i).setDaysToExpiry(days);
            }
        }
        daysToExpiry = days;
    }

    public void setPercentDiscount(BigDecimal discount) {

        for (ArrayList<Product> arrayList : this.productTypesAvailable) {
            for (int i = 0; i<arrayList.size(); i++) {
                arrayList.get(i).setPercentDiscount(discount);
            }
        }
    }

    public Store(String name) {

        this.name = name;
    }

    void addAProduct(Product p) {

        if (p.getDaysToExpiry() != 0) {
            productsAvailable.add(p);
        }
        else System.out.println(p.getName() + " has expired and can not be added to " + this.getName());
    }

    void removeAProduct (Product product) { this.productsAvailable.remove(product); }

    public List<CashRegister> getCashRegisters() {
        return cashRegisters;
    }

    public void setCashRegisters(List<CashRegister> cashRegisters) {
        this.cashRegisters = cashRegisters;
    }

    public List<Product> getProducts() {
        return productsAvailable;
    }

    public void setProducts(List<Product> products) {
        this.productsAvailable = products;
    }

    public List<Product> getProductsAvailable() {
        return productsAvailable;
    }

    public void setProductsAvailable(List<Product> productsAvailable) {
        this.productsAvailable = productsAvailable;
    }

    public void applyOvercharge () {

        System.out.println("Welcome to: " + this.getName() + "!");

        double increaseFOOD = 0.0;
        double increaseNonFood = 0.0;

        System.out.println("What would you like the overcharge for food products to be? In BGN: ");

        try {

            Scanner scanner = new Scanner(System.in);

            while ((increaseFOOD = scanner.nextDouble()) <= 0.0D) {
                System.out.println("Enter a positive amount!");
            }

        } catch (InputMismatchException var12) {
            var12.printStackTrace();
        }

        BigDecimal increaseBIGFood = new BigDecimal(increaseFOOD);
        overChargeFoodProducts = new BigDecimal(increaseFOOD);

        System.out.println("What would you like the overcharge for for non-food products to be? In BGN: ");

        try {
            Scanner scannerNon = new Scanner(System.in);

            while ((increaseNonFood = scannerNon.nextDouble()) <= 0.0D) {
                System.out.println("Enter a positive amount!");
            }

        } catch (InputMismatchException var12) {
            var12.printStackTrace();
        }

        BigDecimal increaseBIGNonFood = new BigDecimal(increaseNonFood);
        overChargeNonFoodProducts = new BigDecimal(increaseFOOD);

        for (ArrayList<Product> arrayList : this.productTypesAvailable) {

            for (int i = 0; i < arrayList.size(); i++) {

                if (arrayList.get(i).getProductCategory() == ProductCategory.FOOD_PRODUCT) {

                    arrayList.get(i).setSellingPriceNoText(arrayList.get(i).getSellingPrice().add(increaseBIGFood), this);

                } else if (arrayList.get(i).getProductCategory() == ProductCategory.NON_FOOD_PRODUCT) {

                    arrayList.get(i).setSellingPrice(arrayList.get(i).getSellingPrice().add(increaseBIGNonFood), this);

                }
            }
        }
    }

    public ArrayList<Product> chooseProductsForYourBasket (Customer customer) {

        System.out.println(customer.getName() + ", choose your products!");

        ArrayList<Product> productsChosen = new ArrayList<>();

        String question = "Would you like to add this product to your basket?";

        String yes = "yes";
        String no = "no";

        String reply;

        if (this.productTypesAvailable.get(0).size() >= 1 || this.productTypesAvailable.get(1).size() >= 1 || this.productTypesAvailable.get(2).size() >= 1 || this.productTypesAvailable.get(3).size() >= 1) {

            System.out.println("The products currently available in our store are as follows: ");

            for (ArrayList<Product> productTypes : productTypesAvailable) {
            for (int i = 0; i < productTypes.size(); i++) {
                System.out.println(productTypes.get(i));

                try {

                    Scanner scanner = new Scanner(System.in);

                    do {
                        System.out.println(question + " Please reply with yes or no.");
                        scanner = new Scanner(System.in);
                        reply = scanner.next();
                    }

                    while (!reply.equalsIgnoreCase(yes) && !reply.equalsIgnoreCase(no));

                    if (reply.equalsIgnoreCase(yes)) {

                        productsChosen.add(productTypes.get(i));
                        System.out.println(customer.getName() + ", you have added " + productTypes.get(i).getName() + " to your basket!");
                    }

                    if (reply.equalsIgnoreCase(no)) {
                        System.out.println(customer.getName() + ", you have not added " + productTypes.get(i).getName() + " to your basket!");
                    }

                } catch (InputMismatchException var12) {
                    var12.printStackTrace();
                }

            }
        }
        }

       else if (this.productsAvailable.size() == 0) {
            System.out.println("There are no more products available in our store!");
        }

        if (productsChosen.size() >= 1) {
            System.out.println(customer.getName() + " the products available in your basket are:");
            for (Product p : productsChosen) {
                System.out.println(p.getName());
            }
        }

        if (productsChosen.size() == 0 ) {
            System.out.println(customer.getName() + " you have not added any products to your basket.");
        }

        for (Product p : productsChosen) {
            for (ArrayList<Product> products : this.productTypesAvailable) {
                products.remove(p);
            }
        }

        return productsChosen;
    }

    public BigDecimal getOverChargeFoodProducts() {
        return overChargeFoodProducts;
    }

    public void setOverChargeFoodProducts(BigDecimal overChargeFoodProducts) {
        this.overChargeFoodProducts = overChargeFoodProducts;
    }

    public BigDecimal getOverChargeNonFoodProducts() {
        return overChargeNonFoodProducts;
    }

    public void setOverChargeNonFoodProducts(BigDecimal overChargeNonFoodProducts) {
        this.overChargeNonFoodProducts = overChargeNonFoodProducts;
    }

    public void setPriceForAllProductsFromASpecificType (ArrayList<Product> arrayList, BigDecimal price) {

        for (Product p : arrayList) {
            p.setSellingPrice(price, this);
        }

    }

    @Override
    public synchronized void run() {

        String filePath3 = "AllgemeinReceipt/Receipt.ser";

        for (CashRegister cashRegister : this.cashRegisters) {

            Thread thread = new Thread(new Store(this.getName()));
            thread.run(); // Start

                if (cashRegister.getLineOfCustomers().size() >= 1) {

                    for (int i = 0; i <= cashRegister.getLineOfCustomers().size(); i++) {

                        Receipt r = cashRegister.sellProducts(this.chooseProductsForYourBasket(cashRegister.getLineOfCustomers().get(0)), this);
                        Receipt.serializeReceipt(filePath3, r);
                        System.out.println(Receipt.deserializeReceipt(filePath3));

                    }
                }
        }
    }

}
