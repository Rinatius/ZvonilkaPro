package kg.kloop.rinat.zvonilka.adapters;

import android.os.AsyncTask;
import android.widget.BaseAdapter;

import java.util.List;

import kg.kloop.rinat.zvonilka.LoadData;

public abstract class BaseListAdapter extends BaseAdapter {
    LoadData loadData;
    boolean allLoaded = false;
    public abstract void add(List list);
    abstract void loadDataAsync();
}
