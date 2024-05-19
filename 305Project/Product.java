import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.File;

public class Product
{
    private String prodID;
    private String prodName;
    private double price;
    private int qtyInStock;
    
    public Product(){}
    
    public Product(String id, String n, double p, int q){
        prodID = id;
        prodName = n;
        price = p;
        qtyInStock = q;
    }
    
    public void setProdID(String id){prodID = id;}
    public void setProdName(String n){prodName = n;}
    public void setPrice(double p){price = p;}
    public void setQtyInStock(int q){qtyInStock = q;}
    
    public String getProdID(){return prodID;}
    public String getProdName(){return prodName;}
    public double getPrice(){return price;}
    public int getQtyInStock(){return qtyInStock;}
    
    public void addProduct(boolean append){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File("Products.txt"), append));
            String input = this.getProdID() + "-" + this.getProdName() + "-" + this.getPrice() + "-" + this.getQtyInStock();
            pw.println(input);
            pw.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public void updateProduct(String id, String n, double p, int q){
        if(!id.equals("-1"))
            this.setProdID(id);
        if(!n.equals("-1"))
            this.setProdName(n);
        if(p != -1)
            this.setPrice(p);
        if(q != -1)
            this.setQtyInStock(q);
        
    }
    
    public boolean isAvailable(){
        if(this.getQtyInStock()>0)
            return true;
        else 
            return false;
    }
    
    public String toString(){
        return 
        "\nProduct ID: " + this.getProdID() +
        "\nProduct Name: " + this.getProdName() +
        "\nPrice: " + this.getPrice() +
        "\nQuantity In Stock: " + this.getQtyInStock();
    }
}
