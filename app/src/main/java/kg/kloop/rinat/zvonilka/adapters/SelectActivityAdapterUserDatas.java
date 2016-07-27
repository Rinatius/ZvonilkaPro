package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.UserData;

public class SelectActivityAdapterUserDatas extends BaseAdapter {

    private Context context;
    private List<UserData> userDatas;

    public SelectActivityAdapterUserDatas(Context context, List<UserData> userDatas) {
        this.context = context;
        this.userDatas = userDatas;
    }

    @Override
    public int getCount() {
        return userDatas.size();
    }

    @Override
    public UserData getItem(int i) {
        return userDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        UserData user = userDatas.get(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_data_list, null);
        }

        TextView name = (TextView) v.findViewById(R.id.user_data_list_name);
        TextView events = (TextView) v.findViewById(R.id.user_data_list_events);

        name.append(user.getFirstName() + " " + user.getSecondName());
        return v;
    }
}
