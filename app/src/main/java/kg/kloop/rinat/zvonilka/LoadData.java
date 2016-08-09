package kg.kloop.rinat.zvonilka;


import android.os.AsyncTask;

import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;

import java.util.List;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;

public class LoadData extends AsyncTask<Integer, Integer, List> {
    int offset, pageSize;
    Class type;
    BaseListAdapter adapter;
    boolean allLoaded, onBackground;

    public LoadData(int offset, int pageSize, Class type, BaseListAdapter adapter, boolean allLoaded) {
        this.offset = offset;
        this.pageSize = pageSize;
        this.type = type;
        this.adapter = adapter;
        this.allLoaded = allLoaded;
    }

    @Override
    protected void onPreExecute() {
        onBackground = true;
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Integer... integers) {
        BackendlessDataQuery dataQuery = new BackendlessDataQuery(new QueryOptions(pageSize, offset));
        return GetBackendlessData.getData(type, dataQuery);
    }

    @Override
    protected void onPostExecute(List list) {
        if (list.size() == 0){
            allLoaded = true;
        } else {
            adapter.add(list);
            adapter.notifyDataSetChanged();
        }
        super.onPostExecute(list);
    }
}