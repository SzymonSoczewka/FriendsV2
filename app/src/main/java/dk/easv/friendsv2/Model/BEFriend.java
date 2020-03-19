package dk.easv.friendsv2.Model;



import java.io.Serializable;
import java.util.Date;

public class BEFriend implements Serializable {

    private String m_name;
    private String m_address;
    private String m_phone;
    private String m_email;
    private Date m_date;
    private String m_URL;
    private String thumbnailFilePath;

    BEFriend(String name, String phone, Boolean isFavorite, String email, String url) {
        m_name = name;
        m_phone = phone;
        m_email = email;
        m_URL = url;
    }

    public String getPhone() {
        return m_phone;
    }


    public String getName() {
        return m_name;
    }


    public String getEmail() { return m_email; }

    public void setURL(String m_URL) {
        this.m_URL = m_URL;
    }

    public void setEmail(String m_email) {
        this.m_email = m_email;
    }


    public void setPhone(String m_phone) {
        this.m_phone = m_phone;
    }

    public void setName(String m_name) {
        this.m_name = m_name;
    }

    public String getURL() { return  m_URL; }


    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Date getM_date() {
        return m_date;
    }

    public void setM_date(Date m_date) {
        this.m_date = m_date;
    }

    public String getM_address() {
        return m_address;
    }

    public void setM_address(String m_address) {
        this.m_address = m_address;
    }
}
