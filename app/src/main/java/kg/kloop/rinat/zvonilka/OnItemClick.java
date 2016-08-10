package kg.kloop.rinat.zvonilka;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;
import kg.kloop.rinat.zvonilka.data.BackendlessData;
import kg.kloop.rinat.zvonilka.data.Event;

class OnItemClick implements AdapterView.OnItemClickListener {

    Context context;
    Class type;

    public OnItemClick(Context context, Class type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(context, type);
        BackendlessData data = (BackendlessData) adapterView.getAdapter().getItem(i);
        intent.putExtra(Resources.OBJECT_ID, data.getObjectId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
class OnScroll implements AbsListView.OnScrollListener{
    BaseListAdapter adapter;
    Class type;

    public OnScroll(BaseListAdapter adapter, Class type) {
        this.adapter = adapter;
        this.type = type;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if ((i+i1 - i2 == 0) && !adapter.allLoaded && (adapter.loadData == null || (adapter.loadData.getStatus() == AsyncTask.Status.FINISHED))){
            adapter.loadData = new LoadData(adapter.getCount(), 10, type, adapter, adapter.allLoaded);
            adapter.loadData.execute();
        }
    }
}

