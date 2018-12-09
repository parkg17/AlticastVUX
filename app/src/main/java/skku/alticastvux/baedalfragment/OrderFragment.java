package skku.alticastvux.baedalfragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import skku.alticastvux.R;
import skku.alticastvux.activity.PlaybackActivity;
import skku.alticastvux.adapter.OrderListviewAdapter;
import skku.alticastvux.data.StoreMenu;

public class OrderFragment extends BaseBaedalFragment{

    @BindView(R.id.store_name)
    TextView store_name;

    @BindView(R.id.selected_menu)
    TextView selected_menu;

    @BindView(R.id.selected_lv)
    ListView selected_lv;

    @BindView(R.id.payment)
    TextView payment;

    @BindView(R.id.payment_detail)
    TextView payment_detail;

    @BindView(R.id.deliver_place)
    TextView deliver_place;

    @BindView(R.id.price)
    TextView price;

    @BindView(R.id.price_detail)
    TextView price_detail;

    @BindView(R.id.pay_button)
    Button pay_button;

    private Button btn_click;

    private PopupWindow popupWindow;
    View layout;

    int total_price=0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        layout = inflater.inflate(R.layout.fragment_order, null);
        ButterKnife.bind(this, layout);

        final String storeName = getArguments().getString("storeName");
        store_name.setText(storeName);
        selected_menu.setText("선택 메뉴");

        ArrayList<StoreMenu> checkoutList = ((PlaybackActivity) getActivity()).getCheckoutList(); //args로 전달하는거 알아보기
        OrderListviewAdapter adapter = new OrderListviewAdapter();
        ListView listview = (ListView) layout.findViewById(R.id.selected_lv);

        for(int i=0; i<checkoutList.size(); i++)
        {
            adapter.addItem(checkoutList.get(i));
            total_price+=Integer.parseInt(checkoutList.get(i).price.replaceAll("\\D+",""));
        }
        adapter.notifyDataSetChanged();
        listview.setAdapter(adapter);

        //selected_lv
        payment.setText("결제 수단");
        payment_detail.setText("신용 카드");
        deliver_place.setText("배달 장소");
        price.setText("결제 금액");
        price_detail.setText(total_price+"");
        btn_click= (Button) layout.findViewById(R.id.pay_button);
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = getLayoutInflater().inflate(R.layout.pay_popup,null);
                popupWindow=new PopupWindow(popupView, RelativeLayout.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                popupWindow.showAsDropDown(pay_button,50,50);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,-100);
            }
        });

        btn_click.setFocusableInTouchMode(true);
        btn_click.setFocusable(true);
        btn_click.requestFocus();

        return layout;
    }




/*
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pay_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

    }
*/

   /* public void showPopup(View anchorView){

        View popupView = getLayoutInflater().inflate(R.layout.pay_popup, null);
        PopupWindow pw = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        TextView tv = (TextView) popupView.findViewById(R.id.pay_argument);
        EditText et = (EditText) popupView.findViewById(R.id.password);

    }*/


}
