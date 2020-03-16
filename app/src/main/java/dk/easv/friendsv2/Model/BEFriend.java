package dk.easv.friendsv2.Model;



import java.io.Serializable;

public class BEFriend implements Serializable {

    private String m_name;
    private String m_phone;
    private Boolean m_isFavorite;
    private String m_email;
    private String m_URL;
    private String thumbnailFileName;

    BEFriend(String name, String phone, Boolean isFavorite, String email, String url) {
        m_name = name;
        m_phone = phone;
        m_isFavorite = isFavorite;
        m_email = email;
        m_URL = url;
    }

    public String getPhone() {
        return m_phone;
    }


    public String getName() {
        return m_name;
    }

    public Boolean isFavorite() { return m_isFavorite; }

    public String getEmail() { return m_email; }

    public String getURL() { return  m_URL; }


    public String getThumbnailFileName() {
        return thumbnailFileName;
    }

    public void setThumbnailFileName(String thumbnailFileName) {
        this.thumbnailFileName = thumbnailFileName;
    }
}
