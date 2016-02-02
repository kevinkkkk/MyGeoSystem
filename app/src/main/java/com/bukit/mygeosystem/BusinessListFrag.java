package com.bukit.mygeosystem;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bukit.mygeosystem.ModeYellowPage.BusinessItem;
import com.bukit.mygeosystem.ModeYellowPage.Listing;
import com.bukit.mygeosystem.ModeYellowPage.YellowPagesReturn;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kevin on 12/10/2015.
 */
public class BusinessListFrag extends Fragment implements WeatherView<YellowPagesReturn>{

    String keyWords;
    BusinessListPresenter presenter;
    ArrayList<BusinessItem> items= new ArrayList<>();
    BusinessAdapter adapter;
    Double myLon, myLat;
    @Bind(R.id.keyWords) EditText keyWordsView;
    @Bind(R.id.start) Button go;
    @Bind(R.id.vendorList) ListView vendorList;

    interface OnVendorSelectedListener{
        void goToMapView(Bundle bundle);
    }

    OnVendorSelectedListener listener;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null){
            keyWords = bundle.getString("Selected");
            myLon=bundle.getDouble("LON");
            myLat=bundle.getDouble("LAT");
        }
        presenter=new BusinessListPresenter(this);
        listener=(OnVendorSelectedListener)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.business_frag, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter = new BusinessAdapter(getActivity(), items);
        vendorList.setAdapter(adapter);
        vendorList.setOnItemClickListener(vendorListener);

        go.setVisibility(View.GONE);
        if (!keyWords.equals("Find businesses?")) {
            if (keyWords.equals("you name it")) {
                vendorList.setVisibility(View.GONE);
                keyWordsView.setVisibility(View.VISIBLE);
                keyWordsView.setFocusable(true);
                go.setVisibility(View.VISIBLE);
                go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        keyWords = keyWordsView.getText().toString();
                        if (keyWords != null) {

                            searchBusinesses(keyWords);
                        }
                    }
                });
            } else {
                searchBusinesses(keyWords);
            }
        }
    }

    AdapterView.OnItemClickListener vendorListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Double desLat = items.get(position).getLat();
            Double desLon = items.get(position).getLon();
            Bundle bundle = new Bundle();
            bundle.putDouble("MYLAT", myLat);
            bundle.putDouble("MYLON", myLon);
            bundle.putDouble("DESLAT", desLat);
            bundle.putDouble("DESLON", desLon);
            bundle.putString("NAME", items.get(position).getName());


            listener.goToMapView(bundle);



        }
    };

    private void searchBusinesses(String keyWords) {
        keyWordsView.setVisibility(View.GONE);
        go.setVisibility(View.GONE);
        vendorList.setVisibility(View.VISIBLE);
        presenter.loadData(keyWords);
    }

    @Override
    public void setData(YellowPagesReturn w) {
        int len = w.getListings().size();
        List<Listing> returnList = w.getListings();
        for (int i = 0; i<len; i++){
            String name = returnList.get(i).getName();
            String address = returnList.get(i).getAddress().getStreet()
                    +", "+returnList.get(i).getAddress().getPcode();
            String city = returnList.get(i).getAddress().getCity();
            String km = returnList.get(i).getDistance()+" KM";
            Double lat=myLat;
            Double lon=myLon;
            try {
                lat = returnList.get(i).getGeoCode().getLatitude();
                lon = returnList.get(i).getGeoCode().getLongitude();
            }catch (NullPointerException e){

            }

            BusinessItem item = new BusinessItem(name,address,city, km,lon,lat);
            items.add(item);
        }

        adapter.notifyDataSetChanged();

    }

    @Override
    public void showError() {

    }

    class BusinessAdapter extends ArrayAdapter<BusinessItem>{



        public BusinessAdapter(Context context, List<BusinessItem> objects) {
            super(context, R.layout.item_in_business, R.id.businessName, objects);
        }

        class ViewHolder{
            TextView businessName;
            TextView km;
            TextView address;
            TextView city;

            public ViewHolder(View view){
                businessName =(TextView) view.findViewById(R.id.businessName);
                address = (TextView) view.findViewById(R.id.address);
                city = (TextView) view.findViewById(R.id.city);
                km=(TextView) view.findViewById(R.id.km);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            ViewHolder holder = (ViewHolder)v.getTag();
            if (holder == null) {
                holder = new ViewHolder(v);
                v.setTag(holder);
            }
            holder.businessName.setText(getItem(position).getName());
            holder.address.setText(getItem(position).getAddress());
            holder.city.setText(getItem(position).getCity());
            holder.km.setText(getItem(position).getKm());

            return v;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
