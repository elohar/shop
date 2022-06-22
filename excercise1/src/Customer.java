
public class Customer extends  User{
    public boolean isVIP() {
        return isVIP;
    }

    public boolean hasPurchasedOnce(){
        return this.cartList.size() > 0;
    }
    public int getTotalPurchaseAmount(){
        int amount = 0;
        for (int i=0;i<this.cartList.size();i++){
            amount += this.cartList.get(i).getTotalSum();
        }
        return amount;
    }

    private boolean isVIP;

    public Customer(String firstname, String lastname, String username, String password, boolean isVIP) {
        super(firstname, lastname, username, password);

        this.isVIP = isVIP;
    }

    @Override
    public String toString() {
        String s = super.toString()+(isVIP ? "(VIP)" : "" );
        return s;
    }

}
