package health.monitoring.system;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    ArrayList<DoctorInfo> arrayList;
    public MyAdapter(Context context, ArrayList<DoctorInfo> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.childview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.c1.setText(arrayList.get(position).getName());
        holder.c2.setText(arrayList.get(position).getSpeciality());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value=arrayList.get(position).getPhone();
                UpdateMyProfile(value);

            }
        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public void UpdateMyProfile(String value) {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view1;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater.inflate(R.layout.editpatientinfo, null);
        EditText name,phone,age,address,email,password,bloodpressure,tempareature,description;
        Button update;
        Spinner symptoms;
        name=view1.findViewById(R.id.patientname2);
        bloodpressure=view1.findViewById(R.id.patientbloodpressure2);
        tempareature=view1.findViewById(R.id.patienttemp2);
        description=view1.findViewById(R.id.patientdescription2);
        phone=view1.findViewById(R.id.patientmobile2);
        age=view1.findViewById(R.id.patientage2);
        address=view1.findViewById(R.id.patientaddress2);
        email=view1.findViewById(R.id.patientemail2);
        symptoms=view1.findViewById(R.id.patiensymptoms2);
        update=view1.findViewById(R.id.patientupdate2);
        DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid());
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    PatienProfile profile=snapshot.getValue(PatienProfile.class);
                    name.setText(profile.getName());
                    phone.setText(profile.getPhone());
                    email.setText(profile.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebase=FirebaseAuth.getInstance();
                String Name,Phone,Age,Address,Email,Password,Bloodpressure,Temparature,Description,Symptoms;
                Name=name.getText().toString();
                Age=age.getText().toString();
                Address=address.getText().toString();
                Email=email.getText().toString();
                Bloodpressure=bloodpressure.getText().toString();
                Description=description.getText().toString();
                Temparature=tempareature.getText().toString();
                Symptoms=symptoms.getSelectedItem().toString();
                Phone=phone.getText().toString();
                PatienProfile patienProfile=new PatienProfile(Name,Phone,Email,Address,Symptoms,Bloodpressure,Temparature,Description);
                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(value);
                DatabaseReference databaseReference2= FirebaseDatabase.getInstance().getReference(firebase.getCurrentUser().getUid());
                databaseReference2.setValue(patienProfile);
                databaseReference.push().setValue(patienProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Your Task Is Done", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setView(view1).show();
    }

}
