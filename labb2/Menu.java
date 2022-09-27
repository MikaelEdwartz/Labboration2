package se.iths.labborationer.labb2;

//TODO

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

import static java.math.BigDecimal.*;

public class Menu {
    private InventoryBalance balance;
    private ArrayList<ProductCategory> categories;
    private Scanner scanner;

    public Menu(InventoryBalance balance, ArrayList<ProductCategory> categories, Scanner scanner) {
        this.balance = balance;
        this.categories = categories;
        this.scanner = scanner;
    }

    public void start() {
        boolean loop = true;
        startUpGreeting();
        startUpMenu(loop);

    }

    private void startUpMenu(boolean loop) {
        while(loop){
            var input = scanner.next();
            switch(input){
                case "1" -> adminMenu(loop);
                case "2" -> customerMenu(loop);
                case "e" -> loop = false;
            }
        }
    }

    private void customerMenu(boolean loop) {

        while(loop){
            var input = scanner.next();

            switch(input){
                case "1" -> loopThroughCategories();
                case "2" ->
            }
        }


    }

    private void adminMenu(boolean loop) {
        while (loop) {
            var input = scanner.nextLine();

            switch (input) {
                case "1" -> startupkategorier();//addProductToInventoryBalance(addProductsAndCategories());
                case "2" -> removeProduct();
                case "3" -> printInventoryBalance();
                case "4" -> searchByCategory();
                case "5" -> searchBetweenPrices();
                case "e" -> loop = false;
            }
            adminMenuGreeting();
        }
    }

    private void startUpGreeting() {
        System.out.println("Tryck 1 för admin meny");
        System.out.println("Tryck 2 för kund meny");
        System.out.println("Tryck e för att avsluta.");
    }
    private void costumerMenuGreeting(){
        System.out.println("1. Vill gå igenom alla kategorier en i taget?");
        System.out.println("2. Välj en specifik kategori");
        System.out.println("e. Gå till kassan och betala");
    }
    private void adminMenuGreeting() {
        System.out.println("-----------------------------------------");
        System.out.println("Hej och välkommen till Kortedala mataffär");
        System.out.println("1: Lägg till vara/skapa kategori");
        System.out.println("2: ta bort varor");
        System.out.println("3: Printa ut lagersaldo");
        System.out.println("4: Sök via kategori");
        System.out.println("5: Sök inom ett prisintervall");
        System.out.println("e: Avsluta");

    }

    public void addProductToInventoryBalance(ProductCategory category) {
        this.balance.add(new Product(category, getProductName(), getProductPrice(), getProductSerialCode()));
    }

    public void createNewCategory(Scanner scanner) {
        System.out.println("Vänligen skriv in namn på ny kategori.");
        this.categories.add(new ProductCategory(scanner.next()));

    }

    public ProductCategory addProductsAndCategories() {
        addFirstCategory();
        listCategoriesToAdd();

        while (true) {

            int userChoice = Integer.parseInt(scanner.next());

            if (userChoice == 1)
                createNewCategory(scanner);
            else
                return this.categories.get(userChoice - 2);

            listCategoriesToAdd();

        }

    }

    private void addFirstCategory() {
        if (this.categories.isEmpty()) {
            System.out.println("Det verkar inte finnas någon kategori för matvaror än.");
            createNewCategory(scanner);
        }
    }

    private ProductCategory getCategoryAtIndex(int i) {
        return this.categories.get(i);
    }

    private void listCategoriesToAdd() {

        System.out.println("1. Lägg till ytterligare kategori");

        if (this.categories.size() >= 1) {
            for (int i = 0; i < this.categories.size(); i++) {
                System.out.println((i + 2) + ". " + getCategoryAtIndex(i) + ".");
            }
        }
    }

    private int getProductSerialCode() {
        System.out.println("Vänligen skriv in streckkod på varan.");
        return scanner.nextInt();
    }

    private BigDecimal getProductPrice() {
        System.out.println("Vänligen skriv in pris på varan.");
        return scanner.nextBigDecimal();
    }

    private String getProductName() {
        System.out.println("Vänligen skriv in namn på varan");
        return scanner.next();
    }

    private void removeProduct() {
        System.out.println("Välj vilken kategori du vill radera en vara från.");

        var category = userCategoryChoice();
        var tempList = new ArrayList<Integer>();

        int count = 1;
        for (int i = 0; i < this.balance.size(); i++) {
            if (categoryMatch(category, i)) {
                System.out.println(count + " " + this.balance.printBalance(i));
                tempList.add(i);
                count++;
            }
        }
        System.out.println("Välj vilken vara du vill ta bort från lagret");

        int productToRemove = scanner.nextInt();
        this.balance.remove(tempList.get(productToRemove-1));



    }

    private boolean categoryMatch(ProductCategory category, int i) {
        return this.balance.getCategory(i).equals(category);
    }

    public void searchByCategory() {
        var category = userCategoryChoice();
        for (int i = 0; i < this.balance.size(); i++) {
            if(categoryMatch(category, i))
                System.out.println(this.balance.printBalance(i));
        }
    }

    private ProductCategory userCategoryChoice() {

        for (int i = 0; i < this.categories.size(); i++) {
            System.out.println((i + 1) + " " + getCategoryAtIndex(i));
        }

        return this.categories.get(scanner.nextInt() - 1);
    }



    public void printInventoryBalance() {
        for (int i = 0; i < this.balance.size(); i++) {
            System.out.println(this.balance.printBalance(i));
        }

    }

    public void searchBetweenPrices() {
        System.out.println("Vad är det lägsta priset");
        var lowestPrice = scanner.nextBigDecimal();
        System.out.println("Vad är det högsta priset");
        var highestPrice = scanner.nextBigDecimal();

        for (int i = 0; i < this.balance.size(); i++) {
            if(this.balance.getProduct(i).price().compareTo(lowestPrice) >= 0 && (this.balance.getProduct(i).price().compareTo(highestPrice) <= 0)) {
                System.out.println(this.balance.printBalance(i));
            }

        }
    }


    public void startupkategorier() {



        this.balance.add(new Product(new ProductCategory("Dairy"), "Milk", valueOf(199), 10938));
        this.balance.add(new Product(new ProductCategory("Dairy"), "Cream", valueOf(11), 1098));
        this.balance.add(new Product(new ProductCategory("Meat"), "Chicken", valueOf(12), 10938));
        this.balance.add(new Product(new ProductCategory("Meat"), "Beef", valueOf(1), 10938));
        this.balance.add(new Product(new ProductCategory("Fruit"), "Apples", valueOf(11), 10938));
        this.balance.add(new Product(new ProductCategory("Vegetable"), "Carrot", valueOf(17), 10938));
        this.balance.add(new Product(new ProductCategory("Fruit"), "Banana", valueOf(14), 10938));

        for (int i = 0; i < this.balance.size(); i++) {
            if(!(this.categories.contains(this.balance.getCategory(i))))
                this.categories.add(this.balance.getCategory(i));

        }

        }
    }



