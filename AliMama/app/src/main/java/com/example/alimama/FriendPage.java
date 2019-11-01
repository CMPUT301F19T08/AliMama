package project.mood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import project.mood.fragment.ContactPageFragment;
import project.mood.fragment.FriendPageFragment;
import project.mood.fragment.RequestPageFragment;
import project.mood.ui.main.SectionPageAdapter;

public class FriendPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ViewPager viewContact;
    private ViewPager viewFriend;
    private ViewPager viewRequest;

    private ViewPager viewPager;

    private SectionPageAdapter sectionPageAdapter;

    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tmp_tab_view);

        sectionPageAdapter = new SectionPageAdapter(this,getSupportFragmentManager());

        viewPager = findViewById(R.id.view_pager);
        /*Fragments*/

        sectionPageAdapter.addFragment(new ContactPageFragment(),"Contact Page");
        sectionPageAdapter.addFragment(new FriendPageFragment(),"Friend Page");
        sectionPageAdapter.addFragment(new RequestPageFragment(),"Request Page");

        viewPager.setAdapter(sectionPageAdapter);



        tabLayout =findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);







         /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }*/


    }
}
