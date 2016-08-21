package kg.kloop.rinat.zvonilka;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.backendless.persistence.BackendlessDataQuery;

import java.util.concurrent.ExecutionException;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;
import kg.kloop.rinat.zvonilka.data.BackendlessData;

class OnItemClick implements AdapterView.OnItemClickListener {

    Context context;
    Class activity;

    public OnItemClick(Context context, Class activity) {
        this.context = context;
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, activity);
        BackendlessData data = (BackendlessData) adapterView.getAdapter().getItem(i);
        Log.d("Click!", activity.toString() + " " + data.getObjectId());
        intent.putExtra(Resources.OBJECT_ID, data.getObjectId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

class OnScrollGetAllList implements AbsListView.OnScrollListener{
    BaseListAdapter adapter;
    Class type;
    String query;

    public OnScrollGetAllList(BaseListAdapter adapter, Class type, String query) {
        this.adapter = adapter;
        this.type = type;
        this.query = query;
    }

    public OnScrollGetAllList(BaseListAdapter adapter, Class type) {
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if ((i+i1 - i2 == 0) && !adapter.allLoaded && (adapter.loadDataList == null || (adapter.loadDataList.getStatus() == AsyncTask.Status.FINISHED))){
            adapter.loadDataList = new LoadDataList(adapter.getCount(), 10, type,
                                                    adapter, adapter.allLoaded, query);
            adapter.loadDataList.execute();
        }
    }
}

class OnScrollGetUserData implements AbsListView.OnScrollListener{

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

}

class OnRefresh{
    BaseListAdapter adapter;
    Class type;
    String query;
    Context context;

    public OnRefresh(Context context, BaseListAdapter adapter, Class type, String query) {
        this.adapter = adapter;
        this.type = type;
        this.query = query;
        this.context = context;
    }

    public void Refresh() {
        adapter.loadDataList = new LoadDataList(context, 0, 10, type, adapter, false, query);
        adapter.loadDataList.execute();
    }
}

