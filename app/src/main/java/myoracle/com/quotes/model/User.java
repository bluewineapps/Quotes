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

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
