package health.monitoring.system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email,pass;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
    }


    public void SignUp(View view) {
        checkUser();
    }

    private void checkUser() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.check,null);
        Button patient,doctor;
        doctor=view.findViewById(R.id.doctor);
        patient=view.findViewById(R.id.patient);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDoctorProfile();

            }
        });
        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePatientProfile();
            }
        });
        builder.setView(view).show();
    }

    private void makePatientProfile() {
        FirebaseAuth firebase=FirebaseAuth.getInstance();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.patientregestration,null);
        EditText name,phone,age,address,email,password;
        Button createAccount;
        name=view.findViewById(R.id.patientname);
        phone=view.findViewById(R.id.patientmobile);
        age=view.findViewById(R.id.patientage);
        address=view.findViewById(R.id.patientaddress);
        email=view.findViewById(R.id.patientemail);
        password=view.findViewById(R.id.patientpass);
        createAccount=view.findViewById(R.id.patientregsubmit);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name,Phone,Age,Address,Email,Password;
                Name=name.getText().toString();
                Age=age.getText().toString();
                Address=address.getText().toString();
                Email=email.getText().toString();
                Password=password.getText().toString();
                Phone=phone.getText().toString();
                firebase.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("PatientList");
                       DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference(firebase.getCurrentUser().getUid());
                       PatienProfile patienProfile=new PatienProfile(Name,Phone,Email,Address,"","","","","","");
                       databaseReference2.setValue(patienProfile);
                       databaseReference.child(Phone).setValue(patienProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(MainActivity.this,PatienViewActivity.class));
                            finish();
                           }
                       });

                   }else {
                       Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                   }
                    }
                });
            }
        });

        builder.setCancelable(false);
        builder.setView(view).show();
        return;
    }

    private void makeDoctorProfile() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=getLayoutInflater().inflate(R.layout.doctorregistration,null);
        EditText name,phone,speciality,time,email,pass;
        Button submit;
        name=view.findViewById(R.id.doctornamereg);
        email=view.findViewById(R.id.doctoremail);
        pass=view.findViewById(R.id.doctorpass);
        phone=view.findViewById(R.id.doctorphonereg);
        speciality=view.findViewById(R.id.specialityreg);
        time=view.findViewById(R.id.specialityreg);
        submit=view.findViewById(R.id.submitreg);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String e,p;
               e=email.getText().toString();
               p=pass.getText().toString();
               FirebaseAuth firebaseAuth1=FirebaseAuth.getInstance();
               firebaseAuth1.createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           String n,p,s,t;
                           n=name.getText().toString();
                           s=speciality.getText().toString();
                           t=time.getText().toString();
                           p=phone.getText().toString();
                           DoctorInfo doctorInfo=new DoctorInfo(n,p,s,t);
                           FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                           String reference=firebaseAuth.getCurrentUser().getUid().substring(0,8);
                           DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(reference);
                           databaseReference.setValue(doctorInfo);
                           DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("DoctorList");
                           databaseReference1.push().setValue(doctorInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       startActivity(new Intent(MainActivity.this,DoctorActivity.class));
                                       finish();
                                   }
                                   else {
                                       Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
                       }
                       else {
                           Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        });
        builder.setView(view).show();
        return;
    }

    public void SignIn(View view) {
        String e,p,check;
        e=email.getText().toString();
        p=pass.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String reference=firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference(reference);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if(snapshot.exists()){
                           startActivity(new Intent(MainActivity.this,PatienViewActivity.class));
                           finish();
                       }
                       else {
                           startActivity(new Intent(MainActivity.this,DoctorActivity.class));
                           finish();

                       }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
