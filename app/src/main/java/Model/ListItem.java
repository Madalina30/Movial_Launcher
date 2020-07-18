package Model;


import android.widget.ImageView;

public class ListItem {

    private String title;
    private String description;
    private String image;

    //constructor
    public ListItem(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }


    //getter and setter for name and description


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
