package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.Call;

public class CallAdapter extends BaseListAdapter{

    Context context;
    List<Call> callList;

    public CallAdapter(Context context, List<Call> callList) {
        this.context = context;
        this.callList = callList;
    }

    @Override
    public void add(List list) {
        callList.addAll(list);
    }

    @Override
    public int getCount() {
        return callList.size();
    }

    @Override
    public Call getItem(int i) {
        return callList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        Call call = getItem(i);
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.call_data_list, null);
        }

        TextView date = (TextView) v.findViewById(R.id.call_data_list_date);
        TextView notes = (TextView) v.findViewById(R.id.call_data_list_notes);

        date.setText(call.getDateCall().toString());
        notes.setText(call.getNotes());
        return v;
    }
}
