package dnr.dnr;

/**
 * Created by IFEANYI on 4/4/2018.
 */

//incomplete functionality
public class Cart {
    MenuItems items;
    String Quantity;


    public Cart() {
    }

    public Cart(MenuItems items, String quantity) {
        this.items = items;
        Quantity = quantity;
    }

    public MenuItems getItems() {
        return items;
    }

    public void setItems(MenuItems items) {
        this.items = items;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
