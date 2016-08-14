package kg.kloop.rinat.zvonilka;


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
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Integer... integers) {
        BackendlessDataQuery dataQuery = new BackendlessDataQuery(new QueryOptions(pageSize, offset));
        if (query != null){
            dataQuery.setWhereClause(query);
        }
        return BackendAction.getData(type, dataQuery);
    }

    @Override
    protected void onPostExecute(List list) {
        if (list.size() == 0){
            allLoaded = true;
        } else {
            adapter.add(list);
            adapter.notifyDataSetChanged();
            Log.d("Loaded", type.toString() + " " + list.size());
        }
        super.onPostExecute(list);
    }
}