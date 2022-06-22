import java.util.ArrayList;
import java.util.List;

public class User {
    protected String firstname;
    protected String lastname;
    protected String username;
    protected String password;
    protected List<Cart> cartList;

    public User(String firstname, String lastname, String username, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.cartList = new ArrayList<>();
    }

    public boolean checkPassword(String p){
        return p == this.password;
    }

    public void addCart(Cart c){
        this.cartList.add(c);
    }

    @Override
    public String toString() {
        String s = firstname+" "+lastname;
        return s;
    }

}
