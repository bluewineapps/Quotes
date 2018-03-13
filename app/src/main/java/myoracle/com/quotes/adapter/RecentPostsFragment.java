package myoracle.com.quotes.adapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by CL Accounts on 06-03-2018.
 */

public class RecentPostsFragment extends PostListFragment {



    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // [START recent_posts_query]
        // Last 100 posts, these are automatically the 100 most recent
        // due to sorting by push() keys
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);
        // [END recent_posts_query]

        return recentPostsQuery;
    }
}
