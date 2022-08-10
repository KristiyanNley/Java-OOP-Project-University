import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        Cashier Andrew = new Cashier("Andrew", UUID.randomUUID());

        Cashier Bakli = new Cashier ("Bakli", UUID.randomUUID());

        Andrew.setMonthlySalary(new BigDecimal(3500));

        Bakli.setMonthlySalary(new BigDecimal(3700));

        Product milk1 = new Product(UUID.randomUUID(), "Milk 1", ProductCategory.FOOD_PRODUCT, LocalDate.of(2022, 6,30));
        Product milk2 = new Product(UUID.randomUUID(), "Milk 2", ProductCategory.FOOD_PRODUCT, LocalDate.of(2022, 6,25)); //About to expire

        ArrayList<Product> milkArr = new ArrayList<>();
        milkArr.add(milk1);
        milkArr.add(milk2);

        Product orangeJuice1 = new Product(UUID.randomUUID(), "OrangeJuice 1", ProductCategory.FOOD_PRODUCT, LocalDate.of(2022, 6, 28));
        Product orangeJuice2 = new Product(UUID.randomUUID(), "OrangeJuice 2", ProductCategory.FOOD_PRODUCT, LocalDate.of(2022, 6, 25)); // About to expire

        ArrayList<Product> orangeJuiceArr = new ArrayList<>();
        orangeJuiceArr.add(orangeJuice1);
        orangeJuiceArr.add(orangeJuice2);

        Product toiletPaper1 = new Product(UUID.randomUUID(), "ToiletPaper 1", ProductCategory.NON_FOOD_PRODUCT, LocalDate.of(2022, 7,1));
        Product toiletPaper2 = new Product(UUID.randomUUID(), "ToiletPaper 2", ProductCategory.NON_FOOD_PRODUCT, LocalDate.of(2022, 7,2));

        ArrayList<Product> toiletPaperArr = new ArrayList<>();
        toiletPaperArr.add(toiletPaper1);
        toiletPaperArr.add(toiletPaper2);

        Product toothPaste1 = new Product(UUID.randomUUID(), "ToothPaste 1", ProductCategory.NON_FOOD_PRODUCT, LocalDate.of(2022,7,15));
        Product toothPaste2 = new Product(UUID.randomUUID(), "ToothPaste 2", ProductCategory.NON_FOOD_PRODUCT, LocalDate.of(2022,7,17));

        ArrayList <Product> toothPasteArr = new ArrayList<>();
        toothPasteArr.add(toothPaste1);
        toothPasteArr.add(toothPaste2);

        Store Tmarket = new Store("Tmarket");

        Tmarket.addATypeOfProduct(milkArr);
        Tmarket.addATypeOfProduct(orangeJuiceArr);
        Tmarket.addATypeOfProduct(toiletPaperArr);
        Tmarket.addATypeOfProduct(toothPasteArr);

        Tmarket.setDaysToExpiry(5);

        Tmarket.setPercentDiscount(new BigDecimal(5.5));

        Tmarket.applyOvercharge();

        Tmarket.setPriceForAllProductsFromASpecificType(milkArr, new BigDecimal(2));
        Tmarket.setPriceForAllProductsFromASpecificType(orangeJuiceArr, new BigDecimal(2.5));
        Tmarket.setPriceForAllProductsFromASpecificType(toiletPaperArr, new BigDecimal(3));
        Tmarket.setPriceForAllProductsFromASpecificType(toiletPaperArr, new BigDecimal(1));


        CashRegister cashRegister1 = new CashRegister("One", Andrew);
        CashRegister cashRegister2 = new CashRegister("Two", Bakli);

        Tmarket.addACashRegister(cashRegister1);
        Tmarket.addACashRegister(cashRegister2);

        Customer Kris = new Customer("Kris", new BigDecimal(9000));

        Customer Zeldris = new Customer("Zeldris", new BigDecimal(9999));

        cashRegister1.addACustomer(Kris);

        cashRegister1.addACustomer(Zeldris);

        Customer Chittoge = new Customer("Chittoge", new BigDecimal(8000));
        cashRegister2.addACustomer(Chittoge);

        Tmarket.run();

        Tmarket.howManyReceiptsThusFar();
        Tmarket.howMuchMoneyHasBeenMade();

//        Receipt r1 = cashRegister1.sellProducts(Tmarket.chooseProductsForYourBasket(Kris), Tmarket);
//
//        Receipt r2 = cashRegister2.sellProducts(Tmarket.chooseProductsForYourBasket(Chittoge), Tmarket);
//
//        String filePath1 = "KrisReceipt/Receipt.ser";
//        String filePath2 = "ChittogeReceipt/Receipt.ser";
//
//        Receipt.serializeReceipt(filePath1, r1);
//        Receipt.serializeReceipt(filePath2, r2);
//
//        System.out.println(Receipt.deserializeReceipt(filePath1));
//        System.out.println(Receipt.deserializeReceipt(filePath2));


    }
}
