package dk.easv.friendsv2.Model;



import android.support.annotation.Nullable;


import java.io.Serializable;

public class BEFriend implements Serializable {

    private String name;
    private String address;
    private String phone;
    private String email;
    private String birthday;
    private String url;
    private String thumbnailFilePath;

    public BEFriend(String name, String phone ,@Nullable String email,@Nullable String url,@Nullable String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }


    public String getName() {
        return name;
    }


    public String getEmail() { return email; }



    public String getURL() { return url; }


    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public String getBirthday() {
        return birthday;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setName(String name){
        if(!isNullOrEmpty(name))
            this.name = name;

    }
    public void setPhone(String phone){
        if(!isNullOrEmpty(phone))
            this.phone = phone;
    }
    public void setEmail(String email){
        if(!isNullOrEmpty(email))
            this.email = email;
    }
    public void setURL(String url){
        if(!isNullOrEmpty(url))
            this.url = url;
    }
    public void setThumbnailFilePath(String filePath){
        if(!isNullOrEmpty(filePath))
            this.thumbnailFilePath = filePath;
    }
    public void setBirthday(String birthday) {
        if (!isNullOrEmpty(birthday))
            this.birthday = birthday;
    }
    private static boolean isNullOrEmpty(String param) {
        return param == null || param.length() == 0;
    }
}
