package kg.kloop.rinat.zvonilka;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import kg.kloop.rinat.zvonilka.data.BackendlessData;

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
        context.startActivity(intent);
    }
}

