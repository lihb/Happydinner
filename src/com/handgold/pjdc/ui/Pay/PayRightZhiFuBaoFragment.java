package com.handgold.pjdc.ui.Pay;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.handgold.pjdc.R;
import com.handgold.pjdc.base.ApplicationEx;
import com.handgold.pjdc.base.Constant;
import com.handgold.pjdc.entitiy.Order;
import com.handgold.pjdc.entitiy.WeChatEntity;
import com.handgold.pjdc.util.CommonUtils;
import com.handgold.pjdc.util.WeChatUtil;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类说明：
 *
 * @author Administrator
 * @version 1.0
 * @date 2015/6/16
 */

public class PayRightZhiFuBaoFragment extends Fragment {

    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
    private final static String DEFAULT_ENCODING = "UTF-8";

    @InjectView(R.id.pay_price_tv)
    TextView payPriceTv;

    @InjectView(R.id.pay_choice_tv)
    TextView payChoiceTv;

    @InjectView(R.id.pay_info_step1_img)
    ImageView payInfoStep1Img;

    @InjectView(R.id.pay_info_step1_tv)
    TextView payInfoStep1Tv;

    @InjectView(R.id.pay_info_step2_img)
    ImageView payInfoStep2Img;

    @InjectView(R.id.pay_info_step2_tv)
    TextView payInfoStep2Tv;

    private Order mOrder = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pay_right_fragment, null);
        ButterKnife.inject(this, view);
        Bundle bundle = getArguments();
        float price = bundle.getFloat("price");
        float temp = CommonUtils.round(price, 2, BigDecimal.ROUND_HALF_UP);
        payPriceTv.setText("金额：" + temp + "元");
        payChoiceTv.setText("支付宝支付");
        payInfoStep1Tv.setText("1.打开手机支付宝应用\n     点击扫一扫功能");
        payInfoStep2Tv.setText("2.扫描该二维码，成功后\n     按照提示输入密码");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        generateData();
    }

    private void generateData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //获取订单数据
        mOrder = (Order) ((ApplicationEx)(getActivity()).getApplication()).receiveInternalActivityParam("order");
        if (mOrder != null) {
            String appid = Constant.APP_ID;
            String mch_id = Constant.MCH_ID;
            String device_info = "neuxs 5";
            String nonce_str = WeChatUtil.getRandomStringByLength(32);
            String body = mOrder.toString();
            String detail = "";
            String out_trade_no = mOrder.getOrderTime();
            int total_fee = (int)(mOrder.getTotalPrice() * 100);
            String spbill_create_ip=WeChatUtil.getLocalIp();
            String time_start = sdf.format(new Date());
            String time_expire = "";
            String goods_tag = "";
            String notify_url = Constant.NOTIFY_URL;
            String trade_type = "APP";
            String product_id = "";
            WeChatEntity weChatEntity = new WeChatEntity(appid, mch_id, device_info, nonce_str, body,
                    detail, out_trade_no,total_fee, spbill_create_ip, time_start,
                    time_expire, goods_tag, notify_url, trade_type, product_id);
            String result = postData(Constant.UNIFY_PAY_API, weChatEntity);
        }

    }

    private String postData(String unifyPayApi, WeChatEntity weChatEntity) {
        String result="";
        try {
            //解决XStream对出现双下划线的bug
            XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));

            //将要提交给API的数据对象转换成XML格式数据Post给API
            String postDataXML = xStreamForRequestPostData.toXML(weChatEntity);
            URL url = new URL(unifyPayApi);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);

            //write parameters
            writer.write(postDataXML);
            writer.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();

            //Output the response
            System.out.println(answer.toString());
            result =answer.toString();

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("PayRightZhiFuBaoFragment"); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("PayRightZhiFuBaoFragment");
    }
}
