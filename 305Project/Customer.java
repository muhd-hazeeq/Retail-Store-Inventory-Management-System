import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Customer
{
    private String custID;
    private String custName;
    private ArrayList<String> purchaseHistory;
    
    public Customer(){}
    
    public Customer(String id, String n, ArrayList<String> ph){
        custID = id;
        custName = n;
        purchaseHistory = ph;
    }
    
    public void setCustID(String id){custID = id;}
    public void setCustName(String n){custName = n;}
    public void setPurchaseHistory(ArrayList<String> ph){purchaseHistory = ph;}
    
    public String getCustID(){return custID;}
    public String getCustName(){return custName;}
    public ArrayList<String> getPurchaseHistory(){return purchaseHistory;}
    
    public void registerNewCust(){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File("Customers.txt"), true));
            String input = this.getCustID() + "-" + this.getCustName();
            pw.println(input);
            pw.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public void recordSales(Transaction tran){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File("Sales.txt"), true));
            String input = "CUSTOMER ID: " + this.getCustID() + tran.toString();
            pw.println(input);
            pw.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public boolean isInLoyaltyProgram(){
        boolean isLoyal = false;
        if(this.getPurchaseHistory().size() >= 5)
            isLoyal = true;
        return isLoyal;
    }
    
    public String toString(){
        return 
        "\nCustomer ID: " + this.getCustID() +
        "\nCustomer Name: " + this.getCustName();
    }
}
