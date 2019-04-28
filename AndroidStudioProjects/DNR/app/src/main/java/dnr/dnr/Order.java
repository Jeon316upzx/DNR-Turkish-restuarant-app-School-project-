package dnr.dnr;

/**
 * Created by IFEANYI on 4/5/2018.
 */
public class Order {
    //order class and attributes
      DNRuser us;
      MenuItems menuItems;
      String orderdate;
      String status;
      String total;
//empty constuctor
    public Order() {
    }
//main constructor
    public Order(DNRuser us, MenuItems menuItems, String orderdate, String status, String total) {
        this.us = us;
        this.menuItems = menuItems;
        this.orderdate = orderdate;
        this.status = status;
        this.total = total;
    }
//getters and setters
    public DNRuser getUs() {
        return us;
    }

    public void setUs(DNRuser us) {
        this.us = us;
    }

    public MenuItems getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(MenuItems menuItems) {
        this.menuItems = menuItems;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}


