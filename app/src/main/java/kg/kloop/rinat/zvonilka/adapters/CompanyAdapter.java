package kg.kloop.rinat.zvonilka.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import kg.kloop.rinat.zvonilka.R;
import kg.kloop.rinat.zvonilka.data.AppCompany;

public class CompanyAdapter extends BaseListAdapter {
    List<AppCompany> appCompanyList;
    Context context;

    public CompanyAdapter(Context context, List<AppCompany> appCompanyList) {
        this.appCompanyList = appCompanyList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appCompanyList.size();
    }

    @Override
    public AppCompany getItem(int i) {
        return appCompanyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void add(List list) {
        appCompanyList.addAll(list);
    }

    @Override
    public void replaceAdapter(List list) {
        appCompanyList = list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = view;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.app_company_list, null);
        }

        AppCompany company = getItem(i);
        TextView companyName = (TextView) v.findViewById(R.id.app_company_list_name);
        companyName.setText(company.getName());

        TextView companyPhone = (TextView) v.findViewById(R.id.app_company_list_phone);
        companyPhone.setText(company.getPhoneNumber());

        TextView companyEmail = (TextView) v.findViewById(R.id.app_company_list_email);
        companyEmail.setText(company.getEmail());

        return v;
    }

}
