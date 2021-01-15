package health.monitoring.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatienViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DoctorInfo> doctorInfos;
    MyAdapter myAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patien_view);
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recayclerview);
        doctorInfos=new ArrayList<>();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("DoctorList");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             doctorInfos.clear();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        DoctorInfo doctorInfo=dataSnapshot1.getValue(DoctorInfo.class);
                        doctorInfos.add(doctorInfo);
                    }
                    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(PatienViewActivity.this);
                    linearLayoutManager.setSmoothScrollbarEnabled(true);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    myAdapter=new MyAdapter(PatienViewActivity.this,doctorInfos);
                    recyclerView.setAdapter(myAdapter);

             }
             else {
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void DoctorList(View view) {
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
            startActivity(new Intent(PatienViewActivity.this,MainActivity.class));
            finish();
            return true;
        }
        if(id==R.id.trt){
            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
               PatienProfile patienProfile=snapshot.getValue(PatienProfile.class);
               DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference(patienProfile.getPhone());
               databaseReference1.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                  if(snapshot.exists()){
                      AlertDialog.Builder builder=new AlertDialog.Builder(PatienViewActivity.this);
                      builder.setTitle("Pescription");
                      builder.setMessage(snapshot.getValue().toString());
                      builder.show();
                  }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        return super.onOptionsItemSelected(item);
    }
}