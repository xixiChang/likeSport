package ccc.tcl.com.sprotappui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;

import android.icu.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;


import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.CountTimerActivity;


import ccc.tcl.com.sprotappui.activity.RecordActivity;




import ccc.tcl.com.sprotappui.constant.Constant;
import ccc.tcl.com.sprotappui.model.Record;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.Utility;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


@SuppressLint("ValidFragment")
public class SportFragment extends Fragment {
    private static final String TAG="SimpleCardFragment";
    private int spinnerFlag;
    private  Intent intent,r_Intent;
    final String key = "e84cede8d2f249b8b1454f3159bcc2a2";
    final String cityName ="huizhou";
    private RecordPresenter recordPresenter;
    //控件
    TextView textView1,textView2,textView3,tv,tw1,tw2,tw3;
    ImageView iv;
    Button goRun,rButton;
    View v;
    Spinner spinner;
    private static final int VIEW_TYPE_RUN=0;
    private static final int VIEW_TYPE_WALK=1;
    private static final int VIEW_TYPE_RIDE=2;

    public static SportFragment getInstance() {
        SportFragment sf = new SportFragment();
        return sf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fr_simple_card, null);
        init();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /**
         * 获取用户所有历史记录的总值
         * map key:step, distance, spent_time, calorie
         * @return map
         */


    }

    private SportAppView<ResponseResult<List<Record>>> sportAppView
            = new SportAppView<ResponseResult<List<Record>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Record>> response) {
            List<Record> list = response.getResult();
            Log.d(TAG, "spinnerFlag: "+spinnerFlag);

            float []dt={0,0,0};
            float []st={0,0,0};
            float []cl={0,0,0};
            if (response.isSuccess()) {
                    for(int i=0;i<list.size();i++){
                        if (VIEW_TYPE_RUN==(list.get(i).getType())){
                            dt[0]=dt[0]+list.get(i).getDistance();
                            st[0]=st[0]+list.get(i).getSpent_time();
                            cl[0]=cl[0]+list.get(i).getCalorie();
                        }else if (VIEW_TYPE_WALK==(list.get(i).getType())){
                            dt[1]=dt[1]+list.get(i).getStep();
                            st[1]=st[1]+list.get(i).getSpent_time();
                            cl[1]=cl[1]+list.get(i).getCalorie();
                        }else if (VIEW_TYPE_RIDE==(list.get(i).getType())){
                            dt[2]=dt[2]+list.get(i).getDistance();
                            st[2]=st[2]+list.get(i).getSpent_time();
                            cl[2]=cl[2]+list.get(i).getCalorie();
                        }
                    }
                    switch(spinnerFlag){
                        case 0:
                            tv.setText("公里");
                            Log.d(TAG, "onSuccess:"+dt[0]);
                            textView1.setText(dt[0]+"");
                            textView2.setText(st[0]+"");
                            textView3.setText(cl[0]+"");
                            break;
                        case 1:
                            tv.setText("步数");
                            textView1.setText(dt[1]+"");
                            textView2.setText(st[1]+"");
                            textView3.setText(cl[1]+"");
                            break;
                        case 2:
                            tv.setText("公里");
                            textView1.setText(dt[2]+"");
                            textView2.setText(st[2]+"");
                            textView3.setText(cl[2]+"");
                            break;
                        default:break;
                    }
                }
            }
        @Override
        public void onRequestError(String msg) {
        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        recordPresenter.onStop();
    }
    private void init(){
        //显示
        textView1=(TextView)v.findViewById(R.id.DISTANCE );
        textView2=(TextView)v.findViewById(R.id.TIME);
        textView3=(TextView)v.findViewById(R.id.SPEED);
        tv=(TextView)v.findViewById(R.id.tv_change) ;
        goRun=(Button)v.findViewById(R.id.pause );

        tw1 =(TextView)v.findViewById(R.id.QUALITY);//空气质量
        tw2 =(TextView)v.findViewById(R.id.TXT1);//天气状况
        iv =(ImageView)v.findViewById(R.id.TXT );//天气状况
        tw3 =(TextView)v.findViewById(R.id.TEP );//温度
        sendRequst( cityName,key);
        //历史记录按键触发
        rButton=(Button)v.findViewById( R.id.RECORD );
        rButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                r_Intent=new Intent(getContext(),RecordActivity.class);
                getActivity().startActivity( r_Intent);
            }
        } );

        spinner=(Spinner) v.findViewById(R.id.spinner);
        rButton=(Button)v.findViewById(R.id.RECORD);
        List<String> list1=new ArrayList<>();
        list1.add( "跑步" );
        list1.add( "步行" );
        list1.add( "骑行" );

        //适配器
        final ArrayAdapter <String>adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,list1 );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item );
        spinner.setAdapter( adapter );
        //做判断 ，初始化下拉栏的值
        String flag=getActivity().getIntent().getStringExtra("type");
        Log.d(TAG, "init: "+flag);
        if(flag==null);
           else spinner.setSelection(Integer.valueOf(flag));
        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");//获取日期
        Log.d(TAG, "onViewCreated: "+sf.format(new Date()));//当前日期
        recordPresenter = new RecordPresenter();
        recordPresenter.onCreate();
        recordPresenter.attachView(sportAppView);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
                spinnerFlag=(int) adapter.getItemId( position );
                Log.d("tag", "onItemSelected: "+spinnerFlag);
                recordPresenter.getDayAll(sf.format(new Date()));//固定日期：c
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //开始按键事件触发

        goRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent(getContext(),CountTimerActivity.class);
                intent.putExtra( "FLAG",spinnerFlag);
                Log.d("tag", "onClick: "+spinnerFlag);
                getActivity().startActivity(intent);
            }
        });
    }
    //请求获取天气数据
    private void sendRequst(String cityName,String key ) {
        final String address1 = "https://free-api.heweather.com/v5/aqi?city=shenzhen&key="+key;
        final String address2 = "https://free-api.heweather.com/v5/now?city="+cityName+"&key="+key;

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request1 = new Request.Builder().url(address1).build();
                Request request2 = new Request.Builder().url(address2).build();
                try {
                    Response response1 = client.newCall(request1).execute();
                    String responseData1 = response1.body().string();
                    String aqiData = Utility.handleAqiResponse(responseData1);
                    Response response2 = client.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    List<String>nowData;
                    nowData = Utility.handleNowResponse(responseData2);
                    String txt=nowData.get(0);
                    String tmp=nowData.get(1);
                    showResponse(aqiData,txt,tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void showResponse(final String aqiData,final String txt,final String tmp) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                    tw1.setText(aqiData);
                    Log.d(TAG, "run: "+txt);
                    iv.setImageResource(Constant.weatherMap.get(txt));//天气图标显示
                    tw2.setText(txt);
                    tw3.setText( tmp +"℃");
                }
                catch (Exception e)
                {e.printStackTrace();}
            }
        });
    }
}

