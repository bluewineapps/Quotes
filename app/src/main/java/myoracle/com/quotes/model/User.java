package myoracle.com.quotes.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by CL Accounts on 06-03-2018.
 */

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String username;
    public String email;
    public String mobile;
    public String bio;
    public String profilePic;
    public   boolean male;
    public String country;



    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email,String mobile,String bio,String profilePic,boolean male,String country) {
        this.username = username;
        this.email = email;
        this.mobile=mobile;
        this.bio=bio;
        this.profilePic=profilePic;
        this.male=male;
        this.country=country;
    }

    public  User(String username, String email){
        this.username = username;
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}
