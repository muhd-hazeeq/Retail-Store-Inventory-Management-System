import java.util.ArrayList;
import java.util.Scanner;

public class Inventory
{
    private ArrayList<Product> prodInventory;
    
    public Inventory(){}
    
    public Inventory(ArrayList<Product> pi){
        prodInventory = pi;
    }
    
    public void setProdInventory(ArrayList<Product> pi){prodInventory = pi;}
    
    public ArrayList<Product> getProdInventory(){return this.prodInventory;}
    
    public void addProduct(String prodID, String prodName, double price, int qty){
        Product prod = new Product(prodID, prodName, price, qty);
        ArrayList<Product> pList = this.getProdInventory();
        pList.add(prod);
        this.setProdInventory(pList);
        prod.addProduct(true);
    }
    
    public void updateProduct(String keyID, String prodID, String prodName, double price, int qty){
        ArrayList<Product> pList = this.getProdInventory();
        for(int i=0; i<pList.size(); i++){
            Product p = pList.get(i);
            if(p.getProdID().equalsIgnoreCase(keyID))
                p.updateProduct(prodID, prodName, price, qty);
            if(i == 0)
                p.addProduct(false);
            else
                p.addProduct(true);
        }
        this.setProdInventory(pList);
    }
    
    public void handleSales(Customer cust, ArrayList<Product> prodSoldList, ArrayList<Integer> qtySoldList, String dateTime, double discount){
        Scanner in = new Scanner(System.in);
        Transaction tran = new Transaction(prodSoldList, qtySoldList, dateTime, 0, discount);
        System.out.println("\nYour total is RM" + String.format("%.2f",tran.calcTotPrice()));
        boolean invalid = true;
        double payment = 0;
        while(invalid){
            System.out.print("\nEnter amount of payment (RM): ");
            payment = in.nextDouble();
            if(payment>=tran.calcTotPrice())
                invalid = false;
            if(invalid)
                System.out.println("\n!!!PAYMENT AMOUNT IS INSUFFICIENT, PLEASE MAKE NEW PAYMENT!!!");
        }
        tran.setPayment(payment);
        System.out.println(tran.toString());
        cust.recordSales(tran);
        for(int i=0; i<prodSoldList.size(); i++){
            Product p = prodSoldList.get(i);
            Integer q = qtySoldList.get(i);
            int newQty = p.getQtyInStock()-q;
            this.updateProduct(p.getProdID(), "-1", "-1", -1, newQty);
        }
    }
    
    public String isLowOnStock(Product p){    
        if(p.getQtyInStock()<20)
            return "YES";
        else
            return "NO";
    }
    
    public String generateInventoryReport(){
        String report = String.format("%-15s %-40s %-5s %-12s %-17s %-12s %n", "\nProduct ID", "Product Name", "Qty", "Price(RM)", "Stock Value(RM)", "Low On Stock?");
        report += "===========================================================================================================\n";
        for(Product p: this.getProdInventory()){
            String price = String.format("%.2f", p.getPrice());
            String stockValue = String.format("%.2f", p.getPrice()*p.getQtyInStock());
            report += String.format("%-15s %-40s %-5d %-12s %-17s %-12s %n", p.getProdID(), p.getProdName(), p.getQtyInStock(), price, stockValue, this.isLowOnStock(p));
        }
        return report;
    }
    
    public void generateSalesReportCust(ArrayList<Customer> custList, String keyID){
        System.out.println("\nResults:\n");
        for(Customer c: custList){
            if(c.getCustID().equalsIgnoreCase(keyID)){
                for(String ph: c.getPurchaseHistory())
                    System.out.println(ph);
            }
        }
    }
    
    public void generateSalesReportDate(ArrayList<String> salesList, String keyDate){
        System.out.println("\nResults:");
        for(int i=0; i<salesList.size(); i++){
            String l = salesList.get(i);
            if(l.length()<25){}
            else if(l.substring(15, 25).equalsIgnoreCase(keyDate)){
                i = i-3;
                l = salesList.get(i);
                System.out.println("\n" + l);
                i++;
                l = salesList.get(i);
                System.out.println(l);
                i++;
                l = salesList.get(i);
                System.out.println(l);
                while(!l.equals("========================================================")){
                    i++;
                    l = salesList.get(i);
                    System.out.println(l);
                }
            }
        }
    }
    
    public void generateRevenueReport(ArrayList<String> salesList, String keyMonth){
        int qty = 0;
        double totPrice = 0;
        System.out.println("\nResults: ");
        for(int i=0; i<salesList.size(); i++){
            String l = salesList.get(i);
            if(l.length()<20){}
            else if(l.substring(18, 20).equals(keyMonth)){
                i += 4;
                l = salesList.get(i);
                String num = l.substring(41);
                char[] a = num.toCharArray();
                String qtyStr = "";
                for(char b : a){
                    if(b == ' '){
                        break;
                    }
                    qtyStr += b;
                }
                qty += Integer.parseInt(qtyStr);
                i++;
                l = salesList.get(i);
                while(!l.equalsIgnoreCase("-------------------------------------------------------")){
                    num = l.substring(41);
                    a = num.toCharArray();
                    qtyStr = "";
                    for(char b : a){
                        if(b == ' '){
                            break;
                        }
                        qtyStr += b;
                    }
                    qty += Integer.parseInt(qtyStr);
                    i++;
                    l = salesList.get(i);
                    if(l.equalsIgnoreCase("-------------------------------------------------------"))
                        break;
                }
                i += 2;
                l = salesList.get(i);
                totPrice += Double.parseDouble(l.substring(15));
            }
        }
        System.out.println("\nUnits Sold: " + qty);
        System.out.println("Sales Revenue: RM" + String.format("%.2f", totPrice));
    }
}
