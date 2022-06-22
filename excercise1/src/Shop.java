import java.util.*;

public class Shop {
    Map<String, User> users;
    List<Product> products;

    public Shop() {
        this.users = new HashMap<>();
        this.products = new ArrayList<>();
    }

    public void showMenu(){
        Scanner scanner = new Scanner(System.in);

        int option = 0;
        while(option != 3) {
            System.out.println("Select an option");
            System.out.println("1. Create new user");
            System.out.println("2. Login to existing account");
            System.out.println("3. Exit");

            option =scanner.nextInt();

            if(option == 1){
                showNewUserMenu();
            }
            if(option == 2){
                showLoginMenu();
            }
        }
    }

    public void showNewUserMenu(){
        Scanner scanner = new Scanner(System.in);

        User user;

        boolean isWorker;
        System.out.println("Are you creating worker/customer account?");
        isWorker = scanner.nextLine().equals("worker");

        String firstname;
        do{
            System.out.println("Enter first name: (only letters)");
            firstname = scanner.nextLine();
        }while(!isContainsDigits(firstname));
        String lastname;
        do{
            System.out.println("Enter last name: (only letters)");
            lastname = scanner.nextLine();
        }while(!isContainsDigits(lastname));
        String username;
        do{
            System.out.println("Enter username: (must be unique)");
            username = scanner.nextLine();
        }while(!isUsernameExists(username));
        String password;
        do{
            System.out.println("Enter password: (must be more than 6 chars)");
            password = scanner.nextLine();
        }while(password.length() <= 6);

        if(isWorker){
            System.out.println("Enter role:");
            System.out.println("1. Regular");
            System.out.println("2. Manager");
            System.out.println("3. Top Manager");
            Role role;
            do{
                role = Worker.getRoleById(scanner.nextInt());
            }while(role == null);

            user = new Worker(firstname,lastname,username,password,role);
        } else{
            boolean isVip;
            System.out.println("Are you vip costumer?");
            isVip = scanner.nextLine().equals("yes");

            user = new Customer(firstname,lastname,username,password,isVip);
        }

        users.put(username,user);
        showUserMenu(user);
    }

    public void showLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean isWorker;
        System.out.println("Are you log into worker/customer account?");
        isWorker = scanner.nextLine().equals("worker");

        String username;
        System.out.println("Enter username:");
        username = scanner.nextLine();
        String password;
        System.out.println("Enter password:");
        password = scanner.nextLine();

        if(!isUsernameExists(username)){
            System.out.println("Invalid details");
            return;
        } else{
            User user = users.get(username);
            if(user.checkPassword(password)){
                System.out.println("Correct!");
                showUserMenu(user);
            } else{
                System.out.println("Invalid details");
                return;
            }
        }
    }

    public void showUserMenu(User user){
        System.out.println("Welcome +"+user+"!");
        if(user instanceof Worker){
            workerMenu(user);
        }
        if(user instanceof Customer){
            purchaseMenu(user);
        }
    }

    public void addProductMenu(){
        Scanner scanner = new Scanner(System.in);

        String description;
        System.out.println("Enter description:");
        description = scanner.nextLine();
        int price;
        System.out.println("Enter price:");
        price = scanner.nextInt();
        int vipDiscount;
        System.out.println("Enter vip discount:");
        vipDiscount = scanner.nextInt();

        Product p = new Product(description,price,vipDiscount);
        products.add(p);
    }

    public void changeStockMenu(){
        printAllProducts();

        Scanner scanner = new Scanner(System.in);
        int productIndex;
        System.out.println("Select product index:");
        productIndex = scanner.nextInt();

        if(productIndex > products.size()){
            System.out.println("Invalid product");
            return;
        }

        Product p = products.get(productIndex-1);
        boolean inStock;
        System.out.println("Is the product in stock?");
        inStock = scanner.nextLine().equals("yes");
        p.setInStock(inStock);
    }

    public void purchaseMenu(User u){
        Scanner scanner = new Scanner(System.in);

        Cart c = new Cart(u);
        int option = 0;
        while(option != -1) {
            System.out.println("Current cart:");
            System.out.println(c);
            System.out.println("Select product index:");
            printInStockProducts();

            option = scanner.nextInt();
            if(option > products.size()){
                System.out.println("Invalid index");
                continue;
            }

            System.out.println("How many products:");
            int amount = scanner.nextInt();
            if(amount <= 0){
                System.out.println("Invalid amount");
                continue;
            }

            c.addProduct(products.get(option-1),amount);
            System.out.println("Cart products:");
            c.printCartItems();
        }
        u.addCart(c);
    }

    public void workerMenu(User u){
        Scanner scanner = new Scanner(System.in);

        int option = 0;
        while(option != 8) {
            System.out.println("Select an option");
            System.out.println("1. Print all costumers");
            System.out.println("2. Print all VIP costumers");
            System.out.println("3. Print all costumers that purchased at least once");
            System.out.println("4. Print the highest buyer");
            System.out.println("5. Add new product");
            System.out.println("6. Change stock of product");
            System.out.println("7. Make purchase");
            System.out.println("8. Exit");

            option =scanner.nextInt();

            if(option == 1){
                printAllCostumers();
            }
            else if(option == 2){
                printAllVIPCostumers();
            }
            else if(option == 3){
                printAllPaidCostumers();
            }
            else if(option == 4){
                printMaxPaidCostumer();
            }
            else  if(option == 5){
                addProductMenu();
            }
            else if(option == 6){
                changeStockMenu();
            }
            else if(option == 7){
                purchaseMenu(u);
            }
        }
    }

    public void printWorkers(){
        for (User u : users.values()) {
            if(u instanceof Worker){
                System.out.println(u);
            }
        }
    }
    public void printAllCostumers(){
        for (User u : users.values()) {
            if(u instanceof Customer){
                System.out.println(u);
            }
        }
    }
    public void printAllVIPCostumers(){
        for (User u : users.values()) {
            if(u instanceof Customer && ((Customer)u).isVIP()){
                System.out.println(u);
            }
        }
    }
    public void printAllPaidCostumers(){
        for (User u : users.values()) {
            if(u instanceof Customer && ((Customer)u).hasPurchasedOnce()){
                System.out.println(u);
            }
        }
    }
    public void printMaxPaidCostumer(){
        int max = 0;
        Customer c = null;
        for (User u : users.values()) {
            if(u instanceof Customer && ((Customer)u).getTotalPurchaseAmount() > max){
                c = ((Customer)u);
                max = c.getTotalPurchaseAmount();
            }
        }
        System.out.println(c);
    }

    public void printAllProducts(){
        for(int i=0;i<products.size();i++){
            System.out.println((i+1)+". "+products.get(i));
        }
    }
    public void printInStockProducts(){
        for(int i=0;i<products.size();i++){
            if(products.get(i).isInStock()){
                System.out.println((i+1)+". "+products.get(i));
            }
        }
    }

    public boolean isContainsDigits(String str){
        return !str.matches(".*\\d.*");
    }
    public boolean isUsernameExists(String str){
        return !users.containsKey(str);
    }

    @Override
    public String toString() {
        return "";
    }
}
