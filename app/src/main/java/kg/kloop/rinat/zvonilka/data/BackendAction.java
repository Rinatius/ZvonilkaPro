package kg.kloop.rinat.zvonilka.data;


import android.content.Entity;

import com.backendless.Backendless;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.List;

public class BackendAction {
    public static List<BackendlessData> getData(Class type, BackendlessDataQuery query){
        return Backendless.Persistence.of(type).find(query).getData();
    }

    public static void saveData(Class type, BackendlessData data){
        Backendless.Persistence.of(type).save(data);
    }
}