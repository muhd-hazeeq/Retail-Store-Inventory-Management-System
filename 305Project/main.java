import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedReader;
import java.io.FileReader;

public class main
{
    static Scanner in = new Scanner(System.in);
    static Scanner inLine = new Scanner(System.in);
    static String wrongOutput = "";
    
    public static void main(String[] args) throws Exception {
        Inventory inv = new Inventory(readFileProd());
        boolean exit = false;
        while(!exit){
            boolean repeat = true;
            System.out.println("\f" + wrongOutput + "\tINVENTORY MANAGEMENT SYSTEM");
            wrongOutput = "";
            System.out.println("\n**********MENU**********");
            System.out.println("\nA - Manage products\nB - Manage customers\nC - Manage sales\nD - Generate reports\nE - Exit");
            System.out.print("\nAction (A/B/C/D/E): ");
            char action = in.next().charAt(0);
            if(action == 'A' || action == 'a'){
                while(repeat){
                    repeat = manageProducts(inv);
                }
            }
            else if(action == 'B' || action == 'b'){
                while(repeat){
                    repeat = manageCustomers();
                }
            }
            else if(action == 'C' || action == 'c'){
                while(repeat){
                    repeat = manageSales(inv);
                }
            }
            else if(action == 'D' || action == 'd'){
                while(repeat){
                    repeat = generateReports(inv);
                }
            }
            else if(action == 'E' || action == 'e'){
                exit = true;
            }
            else{
                wrongOutput = "!!!INVALID INPUT, PLEASE ONLY ENTER A/B/C/D/E FOR YOUR ACTION!!!\n\n";
            }
        }
    }
    
    public static boolean manageProducts(Inventory inv) throws Exception {
        System.out.println("\f" + wrongOutput + "\tINVENTORY MANAGEMENT SYSTEM");
        wrongOutput = "";
        System.out.println("\n**********MANAGE PRODUCTS**********");
        System.out.println("\nA - Add new products\nB - Update product information\nC - Check product availability\nD - Return");
        System.out.print("\nAction (A/B/C/D): ");
        char action = in.next().charAt(0);
        if(action == 'A' || action == 'a'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********ADD NEW PRODUCTS**********");
            System.out.println("\nPlease enter the information accordingly:-");
            System.out.print("\nProduct ID: ");
            String prodID = inLine.nextLine();
            System.out.print("\nProduct Name: ");
            String prodName = inLine.nextLine();
            System.out.print("\nPrice (RM): ");
            double price = in.nextDouble();
            System.out.print("\nQuantity in Stock: ");
            int qty = in.nextInt();
            inv.addProduct(prodID, prodName, price, qty);
        }
        else if(action == 'B' || action == 'b'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********UPDATE PRODUCT INFORMATION**********");
            String keyID = prodIDErrorHandling(inv, false);
            System.out.println("\nPlease enter the updated information (Enter -1 to keep current values):-");
            System.out.print("\nNew Product ID: ");
            String prodID = inLine.nextLine();
            System.out.print("\nNew Product Name: ");
            String prodName = inLine.nextLine();
            System.out.print("\nNew Price (RM): ");
            double price = in.nextDouble();
            System.out.print("\nNew Quantity in Stock: ");
            int qty = in.nextInt();
            inv.updateProduct(keyID, prodID, prodName, price, qty);
        }
        else if(action == 'C' || action == 'c'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********CHECK PRODUCT AVAILABILITY**********");
            String keyID = prodIDErrorHandling(inv, false);
            for(Product p: inv.getProdInventory()){
                if(p.getProdID().equalsIgnoreCase(keyID)){
                    if(p.isAvailable())
                        System.out.println("\nThe product with ID " + keyID + " is available in stock");
                    else 
                        System.out.println("\nSorry, the product with ID " + keyID + " is not available in stock");
                    break;
                }
            }
        }
        else if(action == 'D' || action == 'd'){
            return false;
        }
        else{
            wrongOutput = "!!!INVALID INPUT, PLEASE ONLY ENTER A/B/C/D FOR YOUR ACTION!!!\n\n";
            if(manageProducts(inv))
                return true;
            else
                return false;
        }
        return repeatProcess();
    }
    
    public static boolean manageCustomers() throws Exception {
        System.out.println("\f" + wrongOutput + "\tINVENTORY MANAGEMENT SYSTEM");
        wrongOutput = "";
        System.out.println("\n**********MANAGE CUSTOMERS**********");
        System.out.println("\nA - Register new customers\nB - View registered customers\nC - Search registered customers\nD - Return");
        System.out.print("\nAction (A/B/C/D): ");
        char action = in.next().charAt(0);
        Customer cust = new Customer();
        if(action == 'A' || action == 'a'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********REGISTER NEW CUSTOMER**********");
            System.out.println("\nPlease enter the information accordingly:-");
            System.out.print("\nCustomer Name: ");
            String custName = inLine.nextLine();
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String numbers = "0123456789";
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for(int i=0; i<8; i++){
                if(i<2)
                    sb.append(characters.charAt(random.nextInt(characters.length())));
                else
                    sb.append(numbers.charAt(random.nextInt(numbers.length())));
            }
            String custID = sb.toString();
            System.out.println("This customer's ID is " + custID);
            cust = new Customer(custID, custName, new ArrayList<String>());
            cust.registerNewCust();
        }
        else if(action == 'B' || action == 'b'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********VIEW REGISTERED CUSTOMERS**********");
            ArrayList<Customer> custList = readFileCust();
            for(Customer c: custList){
                System.out.println(c.toString());
            }
        }
        else if(action == 'C' || action == 'c'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********SEARCH REGISTERED CUSTOMERS**********");
            ArrayList<Customer> custList = readFileCust();    
            System.out.print("\nEnter customer name to be searched: ");
            String custName = inLine.nextLine();
            boolean found = false;
            for(Customer c: custList){
                if(c.getCustName().equalsIgnoreCase(custName)){
                    System.out.println(c.toString());
                    if(c.isInLoyaltyProgram())
                        System.out.println("\nThis customer is eligible for the loyalty programme");
                    found = true;
                }
            }
            if(!found)
                System.out.println("\nSorry there is no customer with such name found");
        }
        else if(action == 'D' || action == 'd'){
            return false;
        }
        else{
            wrongOutput = "!!!INVALID INPUT, PLEASE ONLY ENTER A/B FOR YOUR ACTION!!!\n\n";
            if(manageCustomers())
                return true;
            else
                return false;
        }
        return repeatProcess();
    }
    
    public static boolean manageSales(Inventory inv) throws Exception {
        System.out.println("\f" + wrongOutput + "\tINVENTORY MANAGEMENT SYSTEM");
        wrongOutput = "";
        System.out.println("\n**********MANAGE SALES**********");
        System.out.println("\nA - Make new transaction\nB - Return");
        System.out.print("\nAction (A/B): ");
        char action = in.next().charAt(0);
        Customer cust = new Customer();
        if(action == 'A' || action == 'a'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********MAKE NEW TRANSACTION**********");
            ArrayList<Customer> custList = readFileCust();
            String custID = custIDErrorHandling(custList);
            for(Customer c: custList){
                if(c.getCustID().equalsIgnoreCase(custID)){
                    cust = c;
                    break;
                }
            }
            ArrayList<Product> prodSoldList = new ArrayList<Product>();
            ArrayList<Integer> qtySoldList = new ArrayList<Integer>();
            Product prod = new Product();
            System.out.println("\nPlease enter the Product ID and the quantity to be sold:-");
            char cont = ' ';
            while(cont != 'n' && cont != 'N'){
                String prodID = prodIDErrorHandling(inv, true);
                for(Product p: inv.getProdInventory()){
                    if(p.getProdID().equalsIgnoreCase(prodID)){
                        prod = p;
                        break;
                    }
                }
                prodSoldList.add(prod);
                boolean invalid = true;
                int qty = 0;
                while(invalid){
                    System.out.print("\nQuantity: ");
                    qty = in.nextInt();
                    if(qty<=prod.getQtyInStock())
                        invalid = false;
                    if(invalid)
                        System.out.println("\n!!!QUANTITY EXCEEDED CURRENT STOCK, PLEASE ENTER NEW QUANTITY!!!");
                }
                qtySoldList.add(qty);
                System.out.print("\nContinue? (Enter N to stop, any other keys to continue): ");
                cont = in.next().charAt(0);
            }
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String dateTime = LocalDateTime.now().format(dateFormat);
            double discount = 0;
            if(cust.isInLoyaltyProgram()) 
                discount = 10;
            inv.handleSales(cust, prodSoldList, qtySoldList, dateTime, discount);
        }
        else if(action == 'B' || action == 'b'){
            return false;
        }
        else{
            wrongOutput = "!!!INVALID INPUT, PLEASE ONLY ENTER A/B FOR YOUR ACTION!!!\n\n";
            if(manageSales(inv))
                return true;
            else
                return false;
        }
        return repeatProcess();
    }
    
    public static boolean generateReports(Inventory inv) throws Exception {
        System.out.println("\f" + wrongOutput + "\tINVENTORY MANAGEMENT SYSTEM");
        wrongOutput = "";
        System.out.println("\n**********GENERATE REPORTS**********");
        System.out.println("\nA - Inventory report\nB - Sales report (by customer)\nC - Sales report (by date)\nD - Revenue report\nE - Return");
        System.out.print("\nAction (A/B/C/D/E): ");
        char action = in.next().charAt(0);
        if(action == 'A' || action == 'a'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********INVENTORY REPORT**********");
            System.out.println(inv.generateInventoryReport());
        }
        else if(action == 'B' || action == 'b'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********SALES REPORT (BY CUSTOMER)**********");
            ArrayList<Customer> custList = readFileCust();
            String keyID = custIDErrorHandling(custList);
            inv.generateSalesReportCust(custList, keyID);
        }
        else if(action == 'C' || action == 'c'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********SALES REPORT (BY DATE)**********");
            System.out.print("\nEnter date to be searched (DD-MM-YYYY): ");
            String keyDate = inLine.nextLine();
            ArrayList<String> salesList = readFileSales();
            inv.generateSalesReportDate(salesList, keyDate);
        }
        else if(action == 'D' || action == 'd'){
            System.out.println("\f\tINVENTORY MANAGEMENT SYSTEM");
            System.out.println("\n**********REVENUE REPORT**********");
            System.out.print("\nEnter month to be searched (01/02/03/.../12): ");
            String keyMonth = inLine.nextLine();
            ArrayList<String> salesList = readFileSales();
            inv.generateRevenueReport(salesList, keyMonth);
        }
        else if(action == 'E' || action == 'e'){
            return false;
        }    
        else{
            wrongOutput = "!!!INVALID INPUT, PLEASE ONLY ENTER A/B/C/D FOR YOUR ACTION!!!\n\n";
            if(generateReports(inv))
                return true;
            else
                return false;
        }
        return repeatProcess();
    }
    
    public static ArrayList<Product> readFileProd() throws Exception{
        ArrayList<Product> list = new ArrayList<Product>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("Products.txt"));
            String line = br.readLine();
            while(line != null){
                StringTokenizer st = new StringTokenizer(line, "-");
                String prodID = st.nextToken();
                String prodName = st.nextToken();
                double price = Double.parseDouble(st.nextToken());
                int qty = Integer.parseInt(st.nextToken());
                Product p = new Product(prodID, prodName, price, qty);
                list.add(p);
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<Customer> readFileCust() throws Exception{
        ArrayList<Customer> list = new ArrayList<Customer>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("Customers.txt"));
            String line = br.readLine();
            while(line != null){
                StringTokenizer st = new StringTokenizer(line, "-");
                String custID = st.nextToken();
                String custName = st.nextToken();
                ArrayList<String> salesList = readFileSales();
                ArrayList<String> purchaseHistory = new ArrayList<String>();
                for(int i=0; i<salesList.size(); i++){
                    String ph = "";
                    String l = salesList.get(i);
                    if(l.substring(13).equalsIgnoreCase(custID)){
                        ph = l + "\n";
                        i++;
                        l = salesList.get(i);
                        ph += l + "\n";
                        i++;
                        l = salesList.get(i);
                        ph += l + "\n";
                        while(!l.equals("========================================================")){
                            i++;
                            l = salesList.get(i);
                            ph += l + "\n";
                        }
                        purchaseHistory.add(ph);
                    }
                }
                Customer c = new Customer(custID, custName, purchaseHistory);
                list.add(c);
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return list;
    }
    
    public static ArrayList<String> readFileSales() throws Exception{
        ArrayList<String> list = new ArrayList<String>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("Sales.txt"));
            String line = br.readLine();
            while(line != null){
                if(!line.equals(""))
                    list.add(line);
                line = br.readLine();
            }
            br.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return list;
    }
    
    public static String prodIDErrorHandling(Inventory inv, boolean outOfStock){
        boolean invalid = true;
        String keyID = "";
        while(invalid || outOfStock){
            boolean notFound = true;
            System.out.print("\nPlease enter the ID for the product to be searched: ");
            keyID = inLine.nextLine();
            for(Product p: inv.getProdInventory()){
                if(p.getProdID().equals(keyID)){
                    if(!outOfStock){
                        invalid = false;
                        notFound = false;
                    }
                    else{
                        if(p.getQtyInStock() == 0){
                            System.out.println("\n!!!THE PRODUCT IS OUT OF STOCK, PLEASE ENTER ANOTHER PRODUCT ID!!!");
                            notFound = false;
                        }
                        else{
                            invalid = false;
                            outOfStock = false;
                            notFound = false;
                        }
                    }
                    break;
                }
            }
            if(notFound)
                System.out.println("\n!!!NO PRODUCT MATCHED WITH THE INPUTTED ID, PLEASE ENTER AGAIN!!!");
        }
        return keyID;
    }
    
    public static String custIDErrorHandling(ArrayList<Customer> custList){
        boolean invalid = true;
        String keyID = "";
        while(invalid){
            System.out.print("\nPlease enter the ID for the customer to be searched: ");
            keyID = inLine.nextLine();
            for(Customer c: custList){
                if(c.getCustID().equals(keyID)){
                    invalid = false;
                    break;
                }
            }
            if(invalid)
                System.out.println("\n!!!NO CUSTOMER MATCHED WITH THE INPUTTED ID, PLEASE ENTER AGAIN!!!");
        }
        return keyID;
    }
    
    public static boolean repeatProcess(){
        char input;
        System.out.println("\nPlease enter N to return to menu, otherwise enter any keys to go repeat this process");
        System.out.print("\nRepeat? : ");
        input = in.next().charAt(0);
        if(input == 'N' || input == 'n')
            return false;
        else
            return true;
    }  
}
