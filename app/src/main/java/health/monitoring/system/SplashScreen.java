package health.monitoring.system;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SplashScreen extends AppCompatActivity {
    ProgressBar progressBar;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar=findViewById(R.id.progress);
        getSupportActionBar().hide();

        final Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                dowork();
                //boolean state=isNetworkAvailable();
                //if(state==true){
                    newact();


            }
        });

        thread.start();
    }
    public void dowork(){
        for(i=0;i<=100;i=i+25) {
            try {
                Thread.sleep(500);
                progressBar.setProgress(i);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void newact(){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null ){
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        startActivity(new Intent(SplashScreen.this,PatienViewActivity.class));
                        finish();
                    }
                    else {
                        startActivity(new Intent(SplashScreen.this,DoctorActivity.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//            checkUserStatus();
        }
        else {
            startActivity(new Intent(SplashScreen.this,MainActivity.class));
            finish();
        }

    }
}
