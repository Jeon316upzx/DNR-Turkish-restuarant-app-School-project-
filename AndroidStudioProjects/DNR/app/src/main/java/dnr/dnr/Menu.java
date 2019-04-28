package dnr.dnr;

/**
 * Created by IFEANYI on 3/8/2018.
 */
public class Menu {
    //main menu class and attributes
    String img_url;
    String title;

    //empty constructor
    public Menu() {
    }
//main constructor
    public Menu(String img_url, String title) {
        this.img_url = img_url;
        this.title = title;
    }
//getters and setters
    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
