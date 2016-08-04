package kg.kloop.rinat.zvonilka;


import com.backendless.Backendless;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.List;

public class GetBackendlessData {
    public static List getData(Class type, BackendlessDataQuery query){
        return Backendless.Persistence.of(type).find(query).getData();
    }
}