package tempmonitor.PatrolManagerHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import tempmonitor.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Alan on 3/8/2017.
 */

public class TempLogListAdapter extends BaseAdapter {

    private List<TempLog> logs;
    private LayoutInflater inflater;

    public TempLogListAdapter(Context context, TempLog[] logs){
        this.logs = Arrays.asList(logs);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return logs.size();
    }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            //convertView = inflater.inflate(R.layout.log_list_row, parent, false);
            holder = new ViewHolder();
            holder.tv_id = (TextView) convertView.findViewById(R.id.log_tv_id);
            holder.tv_location = (TextView) convertView.findViewById(R.id.log_tv_location);
            holder.tv_temp = (TextView) convertView.findViewById(R.id.log_tv_temp);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_id.setText(logs.get(position).getId());
        //holder.tv_location.setText(logs.get(position).getLocation());
        //holder.tv_temp.setText(logs.get(position).getTemp());

        return convertView;
    }

    private class ViewHolder{
        TextView tv_id, tv_location, tv_temp;
    }
}
