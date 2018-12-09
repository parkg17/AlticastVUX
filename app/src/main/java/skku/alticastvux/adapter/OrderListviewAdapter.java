package skku.alticastvux.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import skku.alticastvux.R;
import skku.alticastvux.app.SKKUVuxApp;
import skku.alticastvux.data.StoreMenu;
import skku.alticastvux.model.Order_ListviewItem;

public class OrderListviewAdapter extends BaseAdapter {

    private ArrayList<Order_ListviewItem> listviewItemList = new ArrayList<Order_ListviewItem>();

    public OrderListviewAdapter() {

    }

    @Override
    public int getCount() {
        return listviewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listviewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) SKKUVuxApp.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_item, null);
        }

        TextView name_tv = (TextView) view.findViewById(R.id.menu_name);
        TextView price_tv = (TextView) view.findViewById(R.id.menu_price);

        Order_ListviewItem listviewItem = listviewItemList.get(position);

        name_tv.setText(listviewItem.getName());
        price_tv.setText(listviewItem.getPrice());
        return view;
    }

    public void addItem(StoreMenu checkoutListItem) {
        Order_ListviewItem item = new Order_ListviewItem();

        item.setName(checkoutListItem.name);
        item.setPrice(checkoutListItem.price);

        listviewItemList.add(item);
    }
}