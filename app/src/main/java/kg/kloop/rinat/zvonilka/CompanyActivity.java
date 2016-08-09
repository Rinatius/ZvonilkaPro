package kg.kloop.rinat.zvonilka;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import kg.kloop.rinat.zvonilka.adapters.BaseListAdapter;
import kg.kloop.rinat.zvonilka.adapters.CompanyAdapter;

public class CompanyActivity extends AppCompatActivity {

    ListView companeList;
    CompanyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        initUI();
    }

    private void initUI() {
        companeList = (ListView) findViewById(R.id.company_activity_list);
    }
}