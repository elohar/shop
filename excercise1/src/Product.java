public class Product {
    private String description;
    private int price;
    private int vipDiscount;

    public boolean isInStock() {
        return inStock;
    }

    private boolean inStock;

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public Product(String description, int price, int vipDiscount) {
        this.description = description;
        this.price = price;
        this.vipDiscount = vipDiscount;
        this.inStock = true;
    }

    @Override
    public String toString() {
        return this.description+" ("+price+") "+(this.inStock ? "IN STOCK" : "OUT OF STOCK");
    }

    public int getPrice(User u) {
        int discount = 0;
        if(u instanceof Worker){
           discount = ((Worker) u).getDiscount();
        }
        if(u instanceof Customer && ((Customer) u).isVIP()){
            discount = vipDiscount;
        }
        double discountAmount = ((double) price)/100;
        discountAmount = discountAmount*discount;
       int p = (int) (price - discountAmount);
        return p;
    }
}
