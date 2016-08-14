package kg.kloop.rinat.zvonilka.adapters;

import android.widget.BaseAdapter;

import java.util.List;

import kg.kloop.rinat.zvonilka.LoadDataList;

public abstract class BaseListAdapter extends BaseAdapter {
    public LoadDataList loadDataList;
    public boolean allLoaded = false;
    public abstract void add(List list);
}
