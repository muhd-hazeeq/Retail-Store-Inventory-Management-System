import java.util.ArrayList;

public class Transaction
{
    private ArrayList<Product> productsSold;
    private ArrayList<Integer> qtySold;
    private String date_time;
    private double payment;
    private double discount;
    
    public Transaction(){}
    
    public Transaction(ArrayList<Product> ps, ArrayList<Integer> qs, String d, double p, double dis){
        productsSold = ps;
        qtySold = qs;
        date_time = d;
        payment = p;
        discount = dis;
    }
    
    public void setProductsSold(ArrayList<Product> ps){productsSold = ps;}
    public void setQtySold(ArrayList<Integer> qs){qtySold = qs;}
    public void setDateTime(String d){date_time = d;}
    public void setPayment(double p){payment = p;}
    public void setDiscount(double dis){discount = dis;}
    
    public ArrayList<Product> getProductsSold(){return productsSold;}
    public ArrayList<Integer> getQtySold(){return qtySold;}
    public String getDateTime(){return date_time;}
    public double getPayment(){return payment;}
    public double getDiscount(){return discount;}
    
    public double calcTotPrice(){
        double totPrice = 0;
        for(int i=0; i<this.getProductsSold().size(); i++){
            Product p = this.getProductsSold().get(i);
            Integer q = this.getQtySold().get(i);
            totPrice += (p.getPrice()*q);
        }
        totPrice *= (1 - (this.getDiscount()/100));
        return totPrice;
    }
    
    public double calcBalance(){
        return this.getPayment() - this.calcTotPrice();
    }
    
    public String toString(){
        String receipt = "\n========================================================";
        receipt += "\nTransaction Details:\nDate and time: " + this.getDateTime() + "\n";
        receipt += "-------------------------------------------------------\n";
        receipt += String.format("%-40s %-5s %-10s %n", "Product", "Qty", "Price");
        receipt += "-------------------------------------------------------\n";
        for(int i=0; i<this.getProductsSold().size(); i++){
            Product p = this.getProductsSold().get(i);
            Integer q = this.getQtySold().get(i);
            String price = "RM" + String.format("%.2f", p.getPrice()*q);
            receipt += String.format("%-40s %-5d %-10s %n", p.getProdName(), q, price);
        }
        receipt += "-------------------------------------------------------\n";
        receipt += "DISCOUNT: " + this.getDiscount() + "%";
        receipt += "\nTOTAL PRICE: RM" + String.format("%.2f", this.calcTotPrice());
        receipt += "\nPAYMENT: RM" + String.format("%.2f", this.getPayment());
        receipt += "\nBALANCE: RM" + String.format("%.2f", this.calcBalance());
        receipt += "\n========================================================\n\n";
        return receipt;
    }
}
