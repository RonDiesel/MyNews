package diesel.app.mynews;

/**
 * Created by Rinat Galiev on 20.10.2016.
 */
public class RssItem {


    private String title;

    private String link;

    private String text;

    private String imgLink;

    private String pubDate;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public String getImglink() {
        return imgLink;
    }

    public void setImglink(String imglink) {
        this.imgLink= imglink;
    }
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

}