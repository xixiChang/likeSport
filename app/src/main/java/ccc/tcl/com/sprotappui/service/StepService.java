package ccc.tcl.com.sprotappui.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;

import ccc.tcl.com.sprotappui.step_detector.StepDetector;

public class StepService extends Service {
    public static Boolean flag = false;
    private SensorManager sensorManager;
    private StepDetector stepDetector;
    public StepService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();
    }
    private void startStepDetector(){
        flag = true;

        stepDetector = new StepDetector(this);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);//获取传感器管理器的实例
        Sensor mySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);//获取加速度传感器
        Sensor sysSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //注册。参数：SensorEventListener的实例，Sensor的实例，更新速率
        if (sysSensor == null)
            sensorManager.registerListener(stepDetector,mySensor,SensorManager.SENSOR_DELAY_NORMAL);
        else
            sensorManager.registerListener(stepDetector,sysSensor,SensorManager.SENSOR_DELAY_NORMAL);
//        List<Sensor> list = sensorManager.getSensorList(Sensor.TYPE_ALL);  //获取传感器的集合
//        for (Sensor sensor11:list){
//            Log.d("asad", "startStepDetector: "+sensor11.getName());
//        }
//        if (sensor1==null)
//            //Toast.makeText(this,"啊啊啊啊啊啊啊",Toast.LENGTH_LONG).show();
//            Log.d("asad", "startStepDetector: "+Build.VERSION.SDK_INT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        if (stepDetector != null)
            sensorManager.unregisterListener(stepDetector);
    }
}
