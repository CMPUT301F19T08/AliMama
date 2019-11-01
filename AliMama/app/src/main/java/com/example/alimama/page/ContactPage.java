//package project.mood.page;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
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
//import project.mood.ui.main.ContactPageAdapter;
//
//public class ContactPage extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    ContactPageAdapter contactPageAdapter;
//    ArrayList<Contact> contactDataList;
//
//
//    public static final String[] contactList = {"ALPHA","BETA","GAMMA","DELTA","THETA","OMEGA","ZETA"};
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main_activity);
//
//        /*Recycler view setup*/
//        ArrayList<Contact> contactDataList = new ArrayList<Contact>();
//        for (int i=0;i<contactList.length;i++){
//            Contact contact = new Contact();
//
//            contact.setContactName(contactList[i]);
//
//            contactDataList.add(contact);
//        }
//
//        contactPageAdapter = new ContactPageAdapter(contactDataList);
//
//        recyclerView = findViewById(R.id.my_recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(contactPageAdapter);
//
//
//
//
//
//        }
//
//    //ToolBar Configuration
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.contact_page_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case R.id.goto_request_page:
//                Intent intent1 = new Intent(this, RequestPage.class);
//                startActivity(intent1);
////                startRequestPage();
//                return true;
//            case R.id.goto_friend_page:
//                Intent intent2 = new Intent(this, FriendPage.class);
//                startActivity(intent2);
////                startFriendPage();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//
//
//        }
//
//
//    }
//
//
//
//    public void startRequestPage(){
//        Intent intent = new Intent(this,RequestPage.class);
//        startActivity(intent);
//    }
//    public void startFriendPage(){
//        Intent intent = new Intent(this,FriendPage.class);
//        startActivity(intent);
//    }
//
//}
