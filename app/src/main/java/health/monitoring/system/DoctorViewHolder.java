package health.monitoring.system;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorViewHolder extends RecyclerView.ViewHolder {
    TextView c1,c2,c3;
    Button button;
    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);
        c1=itemView.findViewById(R.id.patientnameshow);
        c2=itemView.findViewById(R.id.patientsymptom);
        button=itemView.findViewById(R.id.givetrtment);
    }
}