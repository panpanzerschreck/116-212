import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = SiteType, propOrder = {
        title,
        type,
        chars,
        authorize
})
public class InternetPage {

    @XmlElement(name = Title, required = true)
    private String title;

    @XmlElement(name = Type, required = true)
    private String type;

    @XmlElement(name = Chars, required = true)
    private CharsType chars;

    @XmlElement(name = Authorize, required = true)
    private String authorize;

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CharsType getChars() {
        return chars;
    }

    public void setChars(CharsType chars) {
        this.chars = chars;
    }

    public String getAuthorize() {
        return authorize;
    }

    public void setAuthorize(String authorize) {
        this.authorize = authorize;
    }
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = CharsType, propOrder = {
        email,
        news,
        archives,
        voting,
        paid
})
public class CharsType {

    @XmlElement(name = Email)
    private String email;

    @XmlElement(name = News)
    private String news;

    @XmlElement(name = Archives)
    private String archives;

    @XmlElement(name = Voting)
    private String voting;

    @XmlElement(name = Paid)
    private String paid;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getArchives() {
        return archives;
    }

    public void setArchives(String archives) {
        this.archives = archives;
    }

    public String getVoting() {
        return voting;
    }

    public void setVoting(String voting) {
        this.voting = voting;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
