package ccc.tcl.com.sprotappui.activity;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteException;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.db.AppDBHelper;
import ccc.tcl.com.sprotappui.db.SQLParaWrapper;
import ccc.tcl.com.sprotappui.db.SQLStatement;
import ccc.tcl.com.sprotappui.model.UserSport;
import ccc.tcl.com.sprotappui.service.StepService;
import ccc.tcl.com.sprotappui.step_detector.StepDetector;
import ccc.tcl.com.sprotappui.ui.SlideView;
import ccc.tcl.com.sprotappui.utils.BaiduMapUtil;

/*实现实时动态画运动轨迹*/

public class TraceRecordActivity extends BaseActivity implements SensorEventListener,View.OnClickListener, View.OnLongClickListener {
	private SDKReceiver mReceiver;
	private LinearLayout end;
	private LinearLayout pause;
	private LinearLayout lockScreen;
	private RelativeLayout lockScreenArea;
	private RelativeLayout share;
	private RelativeLayout back;
	private RelativeLayout top;
	private LinearLayout progressBarArea;
	private LinearLayout endShowArea;
	private LinearLayout distanceMArea;

	private TextView tvDistance;
	private TextView endDistance;
	private TextView nianYueRi;
	private TextView tvSpeed;
	private TextView tvUser;
	private TextView tvStep;
	private TextView info;
	private TextView tvPause;
	private ImageView pause_img;
	private ImageView go_on_img;
	private ImageView userPic;
	private long rangeTime;
	private long rangeTime1;
	private long totalTime = 0;
	private boolean PAUSE=false;
	Chronometer meter;

	Runnable updateDataRunnable;
	Handler handler = new Handler();
	private SQLParaWrapper sqlParaWrapper;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private int mCurrentDirection = 0;
	private double mCurrentLat = 0.0;
	private double mCurrentLon = 0.0;

	MapView mMapView;
	BaiduMap mBaiduMap;

	boolean isFirstLoc = true; /*是否首次定位*/
	private MyLocationData locData;
	float mCurrentZoom = 18f;/*默认地图缩放比例值*/

	private SensorManager mSensorManager;
 	private static final String TAG ="add TraceRecord";
//	private UserSport userSport;


	/*起点图标*/
	BitmapDescriptor startBD;
	/*终点图标*/
	BitmapDescriptor finishBD;

	List<LatLng> points = new ArrayList<LatLng>();/*位置点集合*/
	Polyline mPolyline;/*运动轨迹图层*/
	LatLng last = new LatLng(0, 0);/*上一个定位点*/
	MapStatus.Builder builder;

	/*开启这个后就可以正常使用Selector这样的DrawableContainers了*/
	static {
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
	}
	/*构造广播监听类，监听 SDK key 验证以及网络异常广播*/
	public class SDKReceiver extends BroadcastReceiver {

		public void onReceive(Context context, Intent intent) {
			String s = intent.getAction();

			if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
				Toast.makeText(TraceRecordActivity.this, R.string.api_fail,Toast.LENGTH_SHORT).show();
			} else if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK)) {
				Toast.makeText(TraceRecordActivity.this,R.string.api_success,Toast.LENGTH_SHORT).show();
			} else if (s.equals(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR)) {
				Toast.makeText(TraceRecordActivity.this,R.string.network_fail,Toast.LENGTH_SHORT).show();
			}
		}
	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_trace_record);
		startBD = BitmapDescriptorFactory.fromResource(R.drawable.ic_me_history_startpoint);
		finishBD = BitmapDescriptorFactory.fromResource(R.drawable.ic_me_history_finishpoint);

		tvDistance = (TextView)findViewById(R.id.tv_distance);
		endDistance = (TextView)findViewById(R.id.end_distance);
		nianYueRi = (TextView)findViewById(R.id.nianYueRi);
		tvSpeed = (TextView)findViewById(R.id.tv_recorder_speed);
		info = (TextView) findViewById(R.id.info);
		tvUser = (TextView)findViewById(R.id.tv_user);
		tvPause = (TextView) findViewById(R.id.tv_pause);
		tvStep = (TextView)findViewById(R.id.tv_step);
		pause_img = (ImageView) findViewById(R.id.pause_img);
		go_on_img = (ImageView) findViewById(R.id.go_on_img);
		userPic = (ImageView) findViewById(R.id.userPic);

		end = (LinearLayout) findViewById(R.id.btnEnd);
		pause = (LinearLayout) findViewById(R.id.btnPause);
		lockScreen = (LinearLayout) findViewById(R.id.lockScreen);
		lockScreenArea = (RelativeLayout) findViewById(R.id.lockScreenArea);
		progressBarArea = (LinearLayout) findViewById(R.id.progressBarArea);
		endShowArea = (LinearLayout) findViewById(R.id.endShowArea);
		distanceMArea = (LinearLayout) findViewById(R.id.distanceMArea);
		top = (RelativeLayout) findViewById(R.id.top);
		share = (RelativeLayout) findViewById(R.id.share);
		back = (RelativeLayout) findViewById(R.id.back);

		meter = (Chronometer)findViewById(R.id.chronometer1);


		end.setOnClickListener(this);
		end.setOnLongClickListener(this);
		pause.setOnClickListener(this);
		lockScreen.setOnClickListener(this);
		share.setOnClickListener(this);
		back.setOnClickListener(this);

		TestGPS();
		isNetworkConn(TraceRecordActivity.this);

		/*apikey的授权需要一定的时间，在授权成功之前地图相关操作会出现异常；apikey授权成功后会发送广播通知，这里注册 SDK 广播监听者*/
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
		iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
		iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
		mReceiver = new SDKReceiver();
		registerReceiver(mReceiver, iFilter);


		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);// 获取传感器管理服务

		/*地图初始化*/
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		/*开启定位图层*/
		mBaiduMap.setMyLocationEnabled(true);

		mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
				com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

		/**
		 * 添加地图缩放状态变化监听，当手动放大或缩小地图时，拿到缩放后的比例，然后获取到下次定位，
		 *  给地图重新设置缩放比例，否则地图会重新回到默认的mCurrentZoom缩放比例
		 */
		mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

			@Override
			public void onMapStatusChangeStart(MapStatus arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMapStatusChangeFinish(MapStatus arg0) {
				mCurrentZoom = arg0.zoom;
			}

			@Override
			public void onMapStatusChange(MapStatus arg0) {
				// TODO Auto-generated method stub

			}
		});

		/*定位初始化*/
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//只用gps定位，需要在室外定位。
		option.setOpenGps(true); /*打开gps*/
		option.setCoorType("bd09ll"); /*设置坐标类型*/
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
		startRecorder();
	}



	/*点击相应按钮区域*/
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			/*点击结束*/
			case R.id.btnEnd:
				Toast.makeText(TraceRecordActivity.this, getString(R.string.long_press_to_stop), Toast.LENGTH_SHORT).show();
				break;
			/*点击暂停/继续*/
			case R.id.btnPause:
				pauseRecorder();
				break;
			/*点击锁屏*/
			case R.id.lockScreen:
				lockScreen();
				break;
			/*运动结束，点击分享*/
			case R.id.share:

				break;
			/*运动结束，点击返回*/
			case R.id.back:
				/*Intent intent = new Intent();
				intent.setClass(TraceRecord.this,
						PrevActivity.class);
				startActivity(intent);*/
				break;
		}
	}


	/*长按结束*/
	@Override
	public boolean onLongClick(View view) {
		stopRecorder();
		return true;
	}

	/*锁屏和滑动解锁*/
	public void lockScreen(){
		end.setVisibility(View.GONE);
		pause.setVisibility(View.GONE);
		progressBarArea.setVisibility(View.GONE);
		lockScreen.setVisibility(View.GONE);
		lockScreenArea.setVisibility(View.VISIBLE);

		SlideView slideView = (SlideView) findViewById(R.id.slider);
		slideView.setSlideListener(new SlideView.SlideListener() {
			@Override
			public void onDone() {
				Toast.makeText(TraceRecordActivity.this,R.string.unlock_success, Toast.LENGTH_SHORT).show();
				lockScreenArea.setVisibility(View.GONE);
				lockScreen.setVisibility(View.VISIBLE);
				end.setVisibility(View.VISIBLE);
				pause.setVisibility(View.VISIBLE);

			}

		});
		slideView.reset();/*解锁完成，重置*/
	}




	/*运动结束，停止记录*/
	public void stopRecorder(){
		TraceRecordActivity.this.rangeTime1=SystemClock.elapsedRealtime()-meter.getBase();
		meter.stop();//结束记录
		//如果用户有运动数据
		if(points.size()>0){
			stopTrace();
			endShow();
		}else{
			showSimpleDialog();
		}
	}




	/*停止地图记录服务并绘制轨迹*/
	public void stopTrace() {
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
			progressBarArea.setVisibility(View.GONE);

			if (isFirstLoc) {
				points.clear();
				last = new LatLng(0, 0);
				return;
			}

			MarkerOptions oFinish = new MarkerOptions();// 地图标记覆盖物参数配置类
			oFinish.position(points.get(points.size() - 1));
			oFinish.icon(finishBD);// 设置覆盖物图片
			mBaiduMap.addOverlay(oFinish); // 在地图上添加此图层
//			停止记步服务
			Intent stopStep = new Intent(getApplicationContext(), StepService.class);
			stopService(stopStep);
			// 保存此次运动数据
//			addRecord();
		}
	}

	/*运动结束时的显示信息*/
	public void endShow(){

		end.setVisibility(View.GONE);
		pause.setVisibility(View.GONE);
		lockScreen.setVisibility(View.GONE);
		progressBarArea.setVisibility(View.GONE);
		distanceMArea.setVisibility(View.GONE);
		endShowArea.setVisibility(View.VISIBLE);
		top.setVisibility(View.VISIBLE);
//		显示用户名
		tvUser.setText(App.userInfo.getName());
//		显示用户头像
//		userPic.setImageResource();
//		显示步数
		String strStep = String.valueOf(StepDetector.CURRENT_STEP);
		tvStep.setText(strStep);
//		显示日期
		nianYueRi.setText(getDate());
	}

	/*运动结束时显示日期*/
/*	public void getDate(TextView tv){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		tv.setText("日期:" + year + "年 " + month + "月 " + day + "日 ");
	}*/

   /*运动结束时显示日期*/
	public String getDate(){
		SimpleDateFormat sdFmt = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String sdFmtStr=String.valueOf(sdFmt.format(now));
		return sdFmtStr;
	}
	/*暂停和继续*/
	public void pauseRecorder() {
		if(!PAUSE)/*暂停计时*/
		{
			mLocClient.stop();
			progressBarArea.setVisibility(View.GONE);
			tvPause.setText(R.string.go_on);
			pause_img.setVisibility(View.GONE);
			go_on_img.setVisibility(View.VISIBLE);
			/*rangeTime为下次继续记时与上次暂停计时的时间差*/
			TraceRecordActivity.this.rangeTime=SystemClock.elapsedRealtime()-meter.getBase();
			meter.stop();
		}
		else /*继续计时*/
		{
			mLocClient.start();
			tvPause.setText(R.string.pause);
			pause_img.setVisibility(View.VISIBLE);
			go_on_img.setVisibility(View.GONE);
			progressBarArea.setVisibility(View.VISIBLE);/*GPS信号搜索显示*/
			info.setText(R.string.GPS_searching);

			/*设置计时器的基时间*/
			meter.setBase(SystemClock.elapsedRealtime()-rangeTime);
			meter.start();

		}
		PAUSE=!PAUSE;
	}

	/*开始记录用时并计算距离，速度(初始化从0开始)*/
	public void startRecorder() {

		lockScreen.setVisibility(View.VISIBLE);

		if (mLocClient != null && !mLocClient.isStarted()) {
			mLocClient.start();
			progressBarArea.setVisibility(View.VISIBLE);
			info.setText(R.string.GPS_searching);
			mBaiduMap.clear();
		}
		initChronometer();
		meter.start();

		/*里程(米)和速度初始化*/
		tvDistance.setText("0");
		tvSpeed.setText("0.00");
		//开始记步服务
		Intent startStep = new Intent(getApplicationContext(), StepService.class);
		startService(startStep);

		updateDataRunnable = new Runnable() {

			@Override
			public void run() {
				try {
					Log.i("TraceRecord", "run");
					if (points.size() >= 2) {
						/*更新距离*/
						double distanceM = 0;
						for (int i = 0; i < points.size() - 1; i++) {
							double lon1 = points.get(i).longitude;
							double lat1 = points.get(i).latitude;
							double lon2 = points.get(i + 1).longitude;
							double lat2 = points.get(i + 1).latitude;
							/*BaiduMapUtil.GetDistance 计算出的结果distanceM是米*/
							distanceM = distanceM + BaiduMapUtil.GetDistance(lon1, lat1, lon2, lat2);
						}
						/*米转换成公里*/
						double distanceKM = distanceM / 1000;
						/*显示米*/
						String strDistanceM = String.valueOf(distanceM);
						/*显示公里*/
						String strDistanceKM = String.valueOf(distanceKM);

						/*米，无小数点*/
						if (strDistanceM.contains(".")) {
							int pointIndex = strDistanceM.indexOf(".");
							strDistanceM = strDistanceM.substring(0,
									pointIndex);
						}
						tvDistance.setText(strDistanceM);

						// 公里数保留2位小数点
						if (strDistanceKM.contains(".")) {
							int pointIndex = strDistanceKM.indexOf(".");
							strDistanceKM = strDistanceKM.substring(0,
									pointIndex + 3);
							endDistance.setText(strDistanceKM);
						}

						/*换算时间，以01:20:55这个用时为例
						[0] 1小时
						[1] 20分钟
						[2] 55秒钟*/
						String showTime = meter.getText().toString();
						double minute = 0, second = 0, hour = 0,totalSecond=0;

						/*对用时进行单位换算成小时*/
						String strHour = showTime.split(":")[0];
						String strMinute = showTime.split(":")[1];
						String strSecond = showTime.split(":")[2];
						hour = Double.parseDouble(strHour);
						minute = Double.parseDouble(strMinute);
						second = Double.parseDouble(strSecond);

						totalSecond = hour * 60 * 60 + minute * 60 + second;
						//hour = (hour * 60 * 60 + minute * 60 + second ) / 60 / 60;
						hour = totalSecond / 60 / 60;
						// 更新速度
						double speed = distanceKM / hour;

						double speedMS = distanceM / totalSecond;

						String strSpeed = String.valueOf(speed);
						// 将速度speed保留2位小数点
						if (strSpeed.contains(".")) {
							int pointIndex = strSpeed.indexOf(".");
							strSpeed = strSpeed.substring(0, pointIndex + 3);
							tvSpeed.setText(strSpeed);
						}

						//保存此次运动数据
						sqlParaWrapper= new SQLParaWrapper(TraceRecordActivity.this);
						sqlParaWrapper.sqLiteDatabase.execSQL(SQLStatement.AddSportRecord);
						ContentValues cv = new ContentValues();
						cv.put("date", getDate());
						cv.put("distance", distanceM);
						cv.put("mean_speed", speedMS);
						cv.put("spent_time", totalSecond);
						cv.put("step", StepDetector.CURRENT_STEP);
						sqlParaWrapper.sqLiteDatabase.insert(SQLStatement.RECORD_TABLE_NAME, null, cv);
						sqlParaWrapper.sqLiteDatabase.close();

					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					/*再次调用run*/
					handler.postDelayed(this, 2000);
				}
			}
		};
		/*让updateDataRunnable运行*/
		handler.post(updateDataRunnable);
	}


	/*初始化计时器*/
	private void initChronometer() {
		meter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

			public void onChronometerTick(Chronometer cArg) {
				totalTime++;

				long time = SystemClock.elapsedRealtime()
						- cArg.getBase();
						/*chronometer以毫秒计*/
				int h = (int) (time / 3600000);
				int m = (int) (time - h * 3600000) / 60000;
				int s = (int) (time - h * 3600000 - m * 60000) / 1000;
				String hh = h < 10 ? "0" + h : h + "";
				String mm = m < 10 ? "0" + m : m + "";
				String ss = s < 10 ? "0" + s : s + "";
				cArg.setText(hh + ":" + mm + ":" + ss);
			}
		});
		meter.setBase(SystemClock.elapsedRealtime());
	}


	private void showSimpleDialog() {
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setMessage(R.string.record_nothing);
		builder.setNegativeButton(R.string.end_record, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				stopTrace();
				endShow();
			}
		});
		builder.setPositiveButton(R.string.go_on_record, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				lockScreen.setVisibility(View.VISIBLE);
				progressBarArea.setVisibility(View.VISIBLE);

				meter.setBase(SystemClock.elapsedRealtime() - rangeTime1);
				meter.start();
			}
		});

		builder.setCancelable(true);
		AlertDialog dialog=builder.create();
		dialog.show();
	}



	/*检测和打开手机GPS*/
	private void TestGPS() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		// 判断GPS模块是否开启，如果没有则开启
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(TraceRecordActivity.this, R.string.open_GPS, Toast.LENGTH_SHORT).show();
			final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage(R.string.open_GPSc);
			dialog.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// 转到手机设置界面，用户设置GPS
					Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					Toast.makeText(TraceRecordActivity.this, R.string.GPS_content, Toast.LENGTH_SHORT).show();
					startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
				}
			});
			dialog.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					arg0.dismiss();
				}
			});
			dialog.show();
		} else {
			// 弹出Toast
			Toast.makeText(TraceRecordActivity.this, R.string.GPS_opened,Toast.LENGTH_LONG).show();
		}

	}



	/*
	 * 判断网络连接是否已经开启
	 */
	public static boolean isNetworkConn(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
			searchNetwork(context);//弹出提示对话框
		}
		return false;
	}



	/*
	 * 判断网络是否连接成功，连接成功不做任何操作
	 */
	public static void searchNetwork(final Context context) {
		//提示对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.network_set).setMessage(R.string.network_disabled).setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = null;
				//判断手机系统的版本  即API大于10 就是3.0或以上版本
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				context.startActivity(intent);
			}
		}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}



	double lastX;

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		double x = sensorEvent.values[SensorManager.DATA_X];

		if (Math.abs(x - lastX) > 1.0) {
			mCurrentDirection = (int) x;

			if (isFirstLoc) {
				lastX = x;
				return;
			}

			locData = new MyLocationData.Builder().accuracy(0)
					/*此处设置开发者获取到的方向信息，顺时针0-360*/
					.direction(mCurrentDirection).latitude(mCurrentLat).longitude(mCurrentLon).build();
			mBaiduMap.setMyLocationData(locData);
		}
		lastX = x;

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {

	}

	/*定位SDK监听函数*/
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(final BDLocation location) {

			if (location == null || mMapView == null) {
				return;
			}

			//注意这里只接受gps点，需要在室外定位。
			if (location.getLocType() == BDLocation.TypeGpsLocation) {

				info.setText(R.string.GPS_weak);

				if (isFirstLoc) {//首次定位
					//第一个点很重要，决定了轨迹的效果，gps刚开始返回的一些点精度不高，尽量选一个精度相对较高的起始点
					LatLng ll = null;

					ll = getMostAccuracyLocation(location);
					if(ll == null){
						return;
					}
					isFirstLoc = false;
					points.add(ll);//加入集合
					last = ll;

					//显示当前定位点，缩放地图
					locateAndZoom(location, ll);

					//标记起点图层位置
					MarkerOptions oStart = new MarkerOptions();// 地图标记覆盖物参数配置类
					oStart.position(points.get(0));// 覆盖物位置点，第一个点为起点
					oStart.icon(startBD);// 设置覆盖物图片
					mBaiduMap.addOverlay(oStart); // 在地图上添加此图层

					progressBarArea.setVisibility(View.GONE);

					return;//画轨迹最少得2个点，首地定位到这里即可返回
				}

				//从第二个点开始
				LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
				//sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，可以设置点之间距离大于为5米才添加到集合中
				if (DistanceUtil.getDistance(last, ll) < 5) {
					return;
				}

				points.add(ll);//如果要运动完成后画整个轨迹，位置点都在这个集合中

				last = ll;

				//显示当前定位点，缩放地图
				locateAndZoom(location, ll);

				//清除上一次轨迹，避免重叠绘画
				mMapView.getMap().clear();

				//起始点图层也会被清除，重新绘画
				MarkerOptions oStart = new MarkerOptions();
				oStart.position(points.get(0));
				oStart.icon(startBD);
				mBaiduMap.addOverlay(oStart);

				//将points集合中的点绘制轨迹线条图层，显示在地图上
				OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(points);
				mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
			}
		}

	}

	private void locateAndZoom(final BDLocation location, LatLng ll) {
		mCurrentLat = location.getLatitude();
		mCurrentLon = location.getLongitude();
		locData = new MyLocationData.Builder().accuracy(0)
				// 此处设置开发者获取到的方向信息，顺时针0-360
				.direction(mCurrentDirection).latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();
		mBaiduMap.setMyLocationData(locData);

		builder = new MapStatus.Builder();
		builder.target(ll).zoom(mCurrentZoom);
		mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
	}

	/**
	 * 首次定位很选一个精度相对较高的起始点
	 * 如果一直显示gps信号弱，说明过滤的标准过高，
	 可以将location.getRadius()>25中的过滤半径调大，比如>40，
	 并且将连续5个点之间的距离DistanceUtil.getDistance(last, ll ) > 5也调大一点，比如>10，
	 如果轨迹刚开始效果不是很好，你可以将半径调小，两点之间距离也调小，
	 gps的精度半径一般是10-50米
	 */
	private LatLng getMostAccuracyLocation(BDLocation location){

		if (location.getRadius()>40) {//gps位置精度大于40米的点直接弃用
			return null;
		}

		LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

		if (DistanceUtil.getDistance(last, ll ) > 10) {
			last = ll;
			points.clear();//有任意连续两点位置大于10，重新取点
			return null;
		}
		points.add(ll);
		last = ll;
		//有5个连续的点之间的距离小于10，认为gps已稳定，以最新的点为起始点
		if(points.size() >= 5){
			points.clear();
			return ll;
		}
		return null;
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
		// 为系统的方向传感器注册监听器
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	protected void onStop() {
		// 取消注册传感器监听
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位
		mLocClient.unRegisterLocationListener(myListener);
		if (mLocClient != null && mLocClient.isStarted()) {
			mLocClient.stop();
		}

		//复位
		points.clear();
		last = new LatLng(0, 0);
		isFirstLoc = true;

		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		mMapView.getMap().clear();
		mMapView.onDestroy();
		mMapView = null;
		startBD.recycle();
		finishBD.recycle();
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

}