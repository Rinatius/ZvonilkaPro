package kg.kloop.rinat.zvonilka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;

import kg.kloop.rinat.zvonilka.adapters.CompanyAdapter;
import kg.kloop.rinat.zvonilka.data.AppCompany;

public class CompanyActivity extends AppCompatActivity {

    ListView companyList;
    CompanyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        initUI();
    }

    private void initUI() {
        companyList = (ListView) findViewById(R.id.company_activity_list);
        adapter = new CompanyAdapter(CompanyActivity.this, new ArrayList<AppCompany>());
        companyList.setAdapter(adapter);
        companyList.setOnScrollListener(new OnScroll(adapter, AppCompany.class));
        companyList.setOnItemClickListener(new OnItemClick(getApplicationContext(), AppCompanyActivity.class));
    }
}