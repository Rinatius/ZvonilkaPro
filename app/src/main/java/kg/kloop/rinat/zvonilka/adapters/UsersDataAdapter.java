package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.Collection;
import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.Resources;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class UsersDataAdapter extends BaseAdapter {

    private Context context;
    private List<UserData> userDatas;


    public UsersDataAdapter(Context context, List<UserData> userDatas) {
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

    public void add(List<UserData> userDatas){
        this.userDatas.addAll(userDatas);
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
        name.setTextColor(context.getResources().getColor(R.color.textColor));
//        TextView events = (TextView) v.findViewById(R.id.user_data_list_events);

        String nameStr = user.getFirstName() + " " + user.getSecondName();
        name.setText(nameStr);

        return v;
    }
}
