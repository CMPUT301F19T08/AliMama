//package project.mood.page;
//
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//import project.mood.R;
//import project.mood.model.Contact;
//
//import project.mood.ui.main.FriendPageAdapter;
//
//public class FriendPage extends AppCompatActivity {
//    RecyclerView recyclerView;
//    FriendPageAdapter friendPageAdapter;
//    ArrayList<Contact> contactDataList;
//
//
//    public static final String[] contactList = {"ALPHA", "BETA", "GAMMA", "DELTA", "THETA", "OMEGA", "ZETA"};
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);
//
//        /*Recycler view setup*/
//        ArrayList<Contact> contactDataList = new ArrayList<Contact>();
//        for (int i = 0; i < contactList.length; i++) {
//            Contact contact = new Contact();
//
//            contact.setContactName(contactList[i]);
//
//            contactDataList.add(contact);
//        }
//        /*Setting up recycler view and card view from adapter*/
//        friendPageAdapter = new FriendPageAdapter(contactDataList);
//
//
//
//        recyclerView = findViewById(R.id.my_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(friendPageAdapter);
//
//
//    }
//}
