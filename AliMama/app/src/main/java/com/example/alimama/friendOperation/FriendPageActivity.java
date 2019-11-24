package com.example.alimama.friendOperation;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.example.alimama.R;
import com.example.alimama.friendOperation.acceptFriendRequest.RequestPageFragment;
import com.example.alimama.friendOperation.addFriend.AddFriendPageFragment;
import com.example.alimama.friendOperation.contact.ContactPageFragment;
import com.google.android.material.tabs.TabLayout;

public class FriendPageActivity extends AppCompatActivity{

    /**
     * Create view for the whole page with tabs and fragments
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tmp_tab_view);


        TabPageAdapter tabPageAdapter = new TabPageAdapter( getSupportFragmentManager());

        ViewPager viewPager = findViewById(R.id.view_pager);
        String currentLoggedInUser = getIntent().getStringExtra("USERNAME");
        /*Fragments*/
        tabPageAdapter.addFragment(new ContactPageFragment(currentLoggedInUser),"Contacts");
        tabPageAdapter.addFragment(new AddFriendPageFragment(currentLoggedInUser),"Friends");
        tabPageAdapter.addFragment(new RequestPageFragment(currentLoggedInUser),"Requests");

        viewPager.setAdapter(tabPageAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

}
