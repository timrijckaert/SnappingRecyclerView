package be.tim.rijckaert.snaprecyclerview;

/**
 * Created by tim on 25.09.16.
 */
public class Article {
    private String title;
    private String summary;
    private String image;

    public Article(final String title, final String summary, final String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getImage() {
        return image;
    }
}