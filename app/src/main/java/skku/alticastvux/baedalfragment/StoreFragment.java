package skku.alticastvux.baedalfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.alticastvux.R;
import skku.alticastvux.activity.PlaybackActivity;
import skku.alticastvux.adapter.GridStoreAdapter;
import skku.alticastvux.adapter.GridStoreMenuAdapter;
import skku.alticastvux.data.Store;
import skku.alticastvux.data.StoreMenu;
import skku.alticastvux.util.ExpandableGridView;
import skku.alticastvux.util.FragmentStackV4;

public class StoreFragment extends BaseBaedalFragment {

    @BindView(R.id.gridview_menu)
    ExpandableGridView grid_menu;

    @BindView(R.id.imageview_store)
    ImageView imageview_store;

    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.tv_storename)
    TextView tv_storename;

    @BindView(R.id.orderButton)
    Button orderButton;

    View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        if(layout != null) {
            return layout;
        }
        */
        layout = inflater.inflate(R.layout.fragment_store, null);
        ButterKnife.bind(this, layout);
        grid_menu.setExpanded(true);
        final String storeName = getArguments().getString("storeName");
        tv_storename.setText(storeName);
        final String storeID = getArguments().getString("storeID");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://m.store.naver.com/restaurants/"+storeID+"/tabs/menus/default/list").get();
                    final Elements metadesc = doc.select("meta[property=og:description]");
                    final Elements metaimage = doc.select("meta[property=og:image]");

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setImgDesc(metaimage.attr("content"), metadesc.attr("content"));
                        }
                    });

                    Elements ul = doc.select("div.list_area > ul > div");
                    Elements li = ul;
                    final ArrayList<StoreMenu> storeMenuList = new ArrayList<>();
                    for (int i = 0; i < li.size(); i++) {
                        StoreMenu m = new StoreMenu();
                        m.name = li.get(i).select("span > span").text();
                        m.price = li.get(i).select("div.price").text();
                        m.description = li.get(i).select("div.txt").text();
                        m.thumUrl = li.get(i).select("img").attr("src");
                        storeMenuList.add(m);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData(storeMenuList);
                        }
                    });
                } catch (Exception e) {
                    Log.e("test", e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
        return layout;
    }

    public void setImgDesc(String img, String desc) {
        tv_desc.setText(desc);
        Picasso.get().load(img).into(imageview_store);
    }

    public void setData(final ArrayList<StoreMenu> storeMenuList) {
        grid_menu.setAdapter(new GridStoreMenuAdapter(getActivity(), storeMenuList));
        grid_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // add item to checkout list
                ((PlaybackActivity)getActivity()).addItemToCheckoutList(storeMenuList.get(i));
            }
        });

        orderButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                OrderFragment fragment = new OrderFragment();
                Bundle args = new Bundle();
                args.putString("storeName",  getArguments().getString("storeName"));
                fragment.setArguments(args);
                FragmentStackV4.add(getFragmentManager(), R.id.layout_order, fragment);
            }
        });

        imageview_store.setFocusableInTouchMode(true);
        imageview_store.setFocusable(true);
        imageview_store.requestFocus();
    }

    @Override
    public void popBacked() {
        imageview_store.setFocusableInTouchMode(true);
        imageview_store.setFocusable(true);
        imageview_store.requestFocus();
    }
}
