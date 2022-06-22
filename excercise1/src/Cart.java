import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<Product> products;
    User user;

    public int getTotalSum(){
        int sum = 0;
        for (int i=0;i<products.size();i++){
            sum += products.get(i).getPrice(this.user);
        }
        return sum;
    }

    public void addProduct(Product p,int amount){
        for (int i=1;i<=amount;i++){
            products.add(p);
        }
    }

    public Cart(User user) {
        this.user = user;
        this.products = new ArrayList<>();
    }

    public void printCartItems(){
        for(int i=0;i<this.products.size();i++){
            System.out.println((i+1)+". "+ this.products.get(i));
        }
    }

    @Override
    public String toString() {
        return products.size()+" items, total "+getTotalSum();
    }
}
