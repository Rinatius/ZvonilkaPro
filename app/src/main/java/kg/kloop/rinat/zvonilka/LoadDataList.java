package kg.kloop.rinat.zvonilka;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;
import kg.kloop.rinat.zvonilka.data.BackendAction;

public class LoadDataList extends AsyncTask<Integer, Integer, List> {
    int offset, pageSize;
    Class type;
    BaseListAdapter adapter;
    boolean allLoaded, onBackground;
    String query;
    ProgressDialog dialog;
    Context context;

    public LoadDataList(Context context, int offset, int pageSize, Class type, BaseListAdapter adapter, boolean allLoaded, String query) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.type = type;
        this.adapter = adapter;
        this.allLoaded = allLoaded;
        this.query = query;
        this.context = context;
    }

    public LoadDataList(int offset, int pageSize, Class type, BaseListAdapter adapter, boolean allLoaded, String query) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.type = type;
        this.adapter = adapter;
        this.allLoaded = allLoaded;
        this.query = query;
    }

    @Override
    protected void onPreExecute() {
        onBackground = true;
        if (context != null)
            dialog = ProgressDialog.show(context, "", context.getString(R.string.loading), false);
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Integer... integers) {
        BackendlessDataQuery dataQuery = new BackendlessDataQuery(new QueryOptions(pageSize, offset));
        if (query != null) {
            dataQuery.setWhereClause(query);
        }
        return BackendAction.getData(type, dataQuery);
    }

    @Override
    protected void onPostExecute(List list) {
        if (list.size() == 0) {
            allLoaded = true;
        } else {
            if (offset == 0) {
                adapter.replaceAdapter(list);
            } else {
                adapter.add(list);
            }
            adapter.notifyDataSetChanged();
            Log.d("Loaded", type.toString() + " " + list.size());
        }
        if (context != null)
            dialog.cancel();
        super.onPostExecute(list);
    }
}