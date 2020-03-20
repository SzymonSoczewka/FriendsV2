package dk.easv.friendsv2.Model;



import java.io.Serializable;

public class BEFriend implements Serializable {

    private String name;
    private String address;
    private String phone;
    private String email;
    private String birthday;
    private String url;
    private String thumbnailFilePath;

    BEFriend(String name, String phone , String email, String url) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }


    public String getName() {
        return name;
    }


    public String getEmail() { return email; }

    public void setURL(String url) {
        this.url = url;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() { return url; }


    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
