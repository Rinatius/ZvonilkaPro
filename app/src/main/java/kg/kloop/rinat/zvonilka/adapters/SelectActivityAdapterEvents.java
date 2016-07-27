package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.Event;

public class SelectActivityAdapterEvents extends BaseAdapter{

    Context context;
    List<Event> events;

    public SelectActivityAdapterEvents(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        Event event = events.get(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_data_list, null);
        }

        TextView name = (TextView) v.findViewById(R.id.user_data_list_name);
        TextView city = (TextView) v.findViewById(R.id.user_data_list_events);

        String nameStr = context.getResources().getString(R.string.name) + event.getName();
        name.setText(nameStr);
        String cityStr = context.getResources().getString(R.string.city) + event.getCity();
        city.setText(cityStr);
        return v;
    }
}
