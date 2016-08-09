package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.LoadData;
import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.ToDo;


public class ToDoAdapter extends BaseListAdapter {
    Context context;
    List<ToDo> toDoList;

    public ToDoAdapter(Context context, List<ToDo> toDoList) {
        this.context = context;
        this.toDoList = toDoList;
        loadDataAsync();
    }

    @Override
    public int getCount() {
        return toDoList.size();
    }

    @Override
    public ToDo getItem(int i) {
        return toDoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void add(List toDos) {
        toDoList.addAll(toDos);
    }

    @Override
    void loadDataAsync() {
        if (!allLoaded && (loadData == null || (loadData.getStatus() == AsyncTask.Status.FINISHED))){
            loadData = new LoadData(toDoList.size(), 10, ToDo.class, this, allLoaded);
            loadData.execute();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i == getCount() - 5) {
            loadDataAsync();
        }
        View v = view;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.user_todo_list, null);
        }
        ToDo toDo = getItem(i);

        TextView name = (TextView) v.findViewById(R.id.user_to_do_list_name);
        TextView events = (TextView) v.findViewById(R.id.user_to_do_list_notes);

//        if (toDo != null) {
//            String nameStr = context.getResources().getString(R.string.name) + toDo.getUserData_ID().getFirstName()
//                    + " " + toDo.getUserData_ID().getSecondName();
//            name.setText(nameStr);
//        }
        String dateStr = context.getResources().getString(R.string.notes) + toDo.getDeadline();
        events.setText(dateStr);

        return v;
    }
}
