package health.monitoring.system;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorViewHolder> {
    Context context;
    ArrayList<PatienProfile> arrayList;
    public DoctorAdapter(Context context, ArrayList<PatienProfile> arrayList) {
        this.context=context;
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.patientview,parent,false);
        return new DoctorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        holder.c1.setText(arrayList.get(position).getName());
        holder.c1.setText(arrayList.get(position).getPhone());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                View view=LayoutInflater.from(context).inflate(R.layout.trtmnt,null,false);
                TextView n,a,s,t,b;
                EditText trtmnt;
                Button button;
                button=view.findViewById(R.id.pbtn);
                n=view.findViewById(R.id.patientname22);
                a=view.findViewById(R.id.patientage22);
                s=view.findViewById(R.id.patientdescription22);
                t=view.findViewById(R.id.patienttemp22);
                b=view.findViewById(R.id.patientbloodpressure22);
                trtmnt=view.findViewById(R.id.pescription);
                n.setText(arrayList.get(position).getName());
                s.setText(arrayList.get(position).getSymptoms());
                t.setText(arrayList.get(position).getTemprature());
                b.setText(arrayList.get(position).getBloodpressure());
                a.setText(arrayList.get(position).getPhone());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(arrayList.get(position).getPhone());
                        String string=trtmnt.getText().toString();
                        databaseReference.setValue(string);
                        JavaMailAPI javaMailAPI=new JavaMailAPI(context,arrayList.get(position).getEmail(),"Tretment From App",string);
                        javaMailAPI.execute();
                    }
                });

                builder.setView(view).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
