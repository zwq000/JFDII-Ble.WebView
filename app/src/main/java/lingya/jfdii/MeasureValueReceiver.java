package lingya.jfdii;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;

import lingya.jfdii.io.MeasureValue;

/**
 * @author zwq00000
 */
public class MeasureValueReceiver extends BroadcastReceiver {
    private static final String TAG = "MeasureValueReceiver";
    private static final String ACTTION_DATA_AVALIABLE = "JFDII.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_MEASURE_VALUE = "EXTRA_MEASURE_VALUE";

    /**
     * 声明 键盘代理
     */
    private KeybroadProxy proxy = new KeybroadProxy(KeyEvent.KEYCODE_UNKNOWN,KeyEvent.KEYCODE_TAB);

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTTION_DATA_AVALIABLE)){
            //MeasureValue.Creator.createFromParcel
            MeasureValue value = intent.getParcelableExtra(EXTRA_MEASURE_VALUE);
            Log.d(TAG,"receive " + value.toString());
            proxy.sendString(value.getValue());
        }
    }
}
