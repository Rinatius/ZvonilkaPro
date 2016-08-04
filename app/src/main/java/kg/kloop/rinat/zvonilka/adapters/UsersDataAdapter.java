package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.Collection;
import java.util.List;

import kg.kloop.rinat.zvonilka.CallActivity;
import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.Resources;
import kg.kloop.rinat.zvonilka.data.UserData;
import kg.kloop.rinat.zvonilka.login.DefaultCallback;

public class UsersDataAdapter extends BaseListAdapter {

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

    public void add(List userDatas){
        this.userDatas.addAll(userDatas);
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view;
        final UserData user = userDatas.get(i);

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_data_list, null);
        }

        v.setFocusable(false);
        TextView name = (TextView) v.findViewById(R.id.user_data_list_name);
        name.setTextColor(context.getResources().getColor(R.color.textColor));
        String nameStr = user.getFirstName() + " " + user.getSecondName();
        name.setText(nameStr);

        TextView events = (TextView) v.findViewById(R.id.user_data_list_events);
        nameStr = Resources.EVENT + ": " + user.getEventUserStatus_ID().toString();
        events.setText(nameStr);

        ImageButton button = (ImageButton) v.findViewById(R.id.user_data_list_call);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CallActivity.class);
                intent.putExtra(Resources.PHONE_NUMBER_KEY, user.getPhoneNumber());
                intent.putExtra(Resources.USER_DATA_ID_KEY, user.getObjectId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        button.setFocusable(false);

        return v;
    }
}
