package dnr.dnr;

/**
 * Created by IFEANYI on 3/8/2018.
 */

//menuitems class and attributes
public class MenuItems {
    String img_url;
    String menuitem_title;
    String description;
    int price;
    String fkey;

    //empty constructor
    public MenuItems() {
    }
//main cinstructor
    public MenuItems(String img_url, String menuitem_title, String description, int price, String fkey) {
        this.img_url = img_url;
        this.menuitem_title = menuitem_title;
        this.description = description;
        this.price = price;
        this.fkey = fkey;
    }


    //getters and setters
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getMenuitem_title() {
        return menuitem_title;
    }

    public void setMenuitem_title(String menuitem_title) {
        this.menuitem_title = menuitem_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getFkey() {
        return fkey;
    }

    public void setFkey(String fkey) {
        this.fkey = fkey;
    }
}
