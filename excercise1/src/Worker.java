public class Worker extends User{
    private Role role;
    public Worker(String firstname, String lastname, String username, String password, Role role) {
        super(firstname, lastname, username, password);
        this.role = role;
    }


    @Override
    public String toString() {
        String s = super.toString()+" ("+this.role+")";
        return s;
    }

    public static Role getRoleById(int id){
        if(id == 1)return Role.REGULAR;
        if(id == 2)return Role.MANAGER;
        if(id == 3)return Role.TOP_MANAGER;
        return null;
    }
    public int getDiscount(){
        if(this.role == Role.REGULAR)return 10;
        if(this.role == Role.MANAGER)return 20;
        if(this.role == Role.TOP_MANAGER)return 30;
        return 0;
    }
}

enum Role {
    REGULAR,
    MANAGER,
    TOP_MANAGER
}