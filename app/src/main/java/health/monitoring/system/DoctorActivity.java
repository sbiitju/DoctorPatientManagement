package health.monitoring.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String phone;
    RecyclerView recyclerView;
    DoctorAdapter doctorAdapter;
    ArrayList<PatienProfile> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        recyclerView=findViewById(R.id.recayclerview2);
        profiles=new ArrayList<>();
        firebaseAuth=FirebaseAuth.getInstance();
        String reference=firebaseAuth.getCurrentUser().getUid().substring(0,8);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(reference);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           if(snapshot.exists()){
               DoctorInfo doctorInfo=snapshot.getValue(DoctorInfo.class);
              phone=doctorInfo.getPhone();
              DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference(phone);
              databaseReference1.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      profiles.clear();
                      if(snapshot.exists()){
                          for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                              PatienProfile patienProfile=dataSnapshot1.getValue(PatienProfile.class);
                              profiles.add(patienProfile);
                          }
                          LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DoctorActivity.this);
                          linearLayoutManager.setSmoothScrollbarEnabled(true);
                          linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                          recyclerView.setLayoutManager(linearLayoutManager);
                          doctorAdapter=new DoctorAdapter(DoctorActivity.this,profiles);
                          recyclerView.setAdapter(doctorAdapter);
                      }

                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });
           }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.sigout) {
            firebaseAuth.signOut();
            startActivity(new Intent(DoctorActivity.this,MainActivity.class));
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}