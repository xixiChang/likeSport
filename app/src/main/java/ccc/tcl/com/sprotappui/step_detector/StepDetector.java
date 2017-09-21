package ccc.tcl.com.sprotappui.step_detector;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by user on 17-8-16.
 */

public class StepDetector implements SensorEventListener{
    public static int CURRENT_STEP = 0;
    public static float SENSITIVITY = 8;//灵敏度
    private float mLastValues[] = new float[6];
    private float mScale[] = new float[6];
    private float mYOffset;
    private static long end = 0;
    private static long start = 0;
    SensorManager AsensorM;
    //最后加速度方向
    private float mLastDirections[] = new float[6];
    private float mLastExtremes[][] = {new float[6],new float[6]};
    private float mLastDiff[] = new float[6];
    private int mLastMatch = -1;
    //传入上下文的构造函数 @param context
    public StepDetector(Context context){
        super();
        int h = 480;
        mYOffset = h * 0.5f;
        mScale[0] = -(h * 0.5f * (1.0f / (SensorManager.STANDARD_GRAVITY * 2)));
        mScale[1] = -(h * 0.5f * (1.0f / (SensorManager.MAGNETIC_FIELD_EARTH_MAX)));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        synchronized (this){
            if (sensor.getType()==Sensor.TYPE_STEP_DETECTOR||sensor.getType()==Sensor.TYPE_STEP_COUNTER) {
                //CURRENT_STEP = (int)event.values[0];
                CURRENT_STEP++;
                Log.d("---------", "TYPE_STEP_DETECTOR:>>>>>>>>>>>>>> " + event.values[0]);
            }
            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float vSum = 0;
                for (int i = 0;i<3;i++){
                    final float v = mYOffset + event.values[i] * mScale[1];
                    vSum += v;
                }
                int k = 0;
                float v = vSum / 3;
//                float v = (float)Math.sqrt(Math.pow(event.values[0],2)
//                        +Math.pow(event.values[1],2)+Math.pow(event.values[2],2));
                float direction = (v > mLastValues[k] ? 1 : (v < mLastValues[k] ? -1 : 0));
                if (direction == -mLastDirections[k]){
                    //Direction change
                    int extType = (direction > 0 ? 0 : 1);//min or max
                    mLastExtremes[extType][k] = mLastValues[k];
                    float diff = Math.abs(mLastExtremes[extType][k] - mLastExtremes[1-extType][k]);
                    if (diff > SENSITIVITY){
                        boolean isAlmostLargeAsPrevious = diff > (mLastDiff[k] * 2 / 3);
                        boolean isPreviousLargeEnough = mLastDiff[k] > (diff / 3);
                        boolean isNotContra = (mLastMatch != 1 - extType);
                        if (isAlmostLargeAsPrevious && isPreviousLargeEnough && isNotContra){
                            end = System.currentTimeMillis();
                            if ((end - start >= 300)){
                                //Log.d("++++++++++++++++", "start>>>>>: "+start+"  end>>>>>"+end);
                                CURRENT_STEP++;
                                mLastMatch = extType;
                                start = end;
                            }
                        }
                        else {
                            mLastMatch = -1;
                        }
                    }
                    mLastDiff[k] = diff;
                }
                mLastDirections[k] = direction;
                mLastValues[k] = v;
            }
            Log.d("---------", "onSensorChanged:>>>>>>>>>>>>>> "+CURRENT_STEP);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
