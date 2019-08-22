package sonacollegeoftechnology.csesonastore.com.sonahostelapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterProcess extends RecyclerView.Adapter<AdapterProcess.ViewProcessHolder> {

    Context context;
    private ArrayList<ModelData> item; //memanggil modelData

    public AdapterProcess(Context context, ArrayList<ModelData> item) {
        this.context = context;
        this.item = item;
    }

    @Override
    public ViewProcessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list, parent, false);

        ViewProcessHolder processHolder = new ViewProcessHolder(itemView);
        return processHolder;
    }

    @Override
    public void onBindViewHolder(AdapterProcess.ViewProcessHolder holder, int position) {
        ModelData m=item.get(position);
        holder.nama_data.setText("Comlpalint Id:"+ m.getNamaData());
        holder.tyoc.setText("Comlpalint On:"+m.gettyoc());
        holder.desc.setText("Detail:"+m.getdesc());
        holder.stat.setText("Status:"+m.getstat());
        holder.year.setText("Update on:"+m.getyear());
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class ViewProcessHolder extends RecyclerView.ViewHolder {

        TextView nama_data,tyoc,desc,stat,year;

        public ViewProcessHolder(View itemView) {
            super(itemView);

            nama_data = (TextView) itemView.findViewById(R.id.nama_data_list);
            tyoc=itemView.findViewById(R.id.tyoc);
            desc=itemView.findViewById(R.id.desc);
            stat=itemView.findViewById(R.id.stat);
            year=itemView.findViewById(R.id.year);

        }
    }
}
