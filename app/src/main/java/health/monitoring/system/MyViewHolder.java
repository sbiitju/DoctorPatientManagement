package health.monitoring.system;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView c1,c2,c3;
    Button button;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        c1=itemView.findViewById(R.id.doctorname);
        c2=itemView.findViewById(R.id.speciality);
        button=itemView.findViewById(R.id.makeappoinment);
    }
}
