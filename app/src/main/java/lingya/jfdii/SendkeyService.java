
package lingya.jfdii;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class SendkeyService extends Service {
    
    private final static String TAG = "SendkeyService";
    private KeybroadProxy keybroadProxy = new KeybroadProxy();

    @Override
    public void onCreate() {
        super.onCreate();
        testSendKeys(100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    private void testSendKeys(final int count) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                for (int i=0;i<count;i++) {
                    Thread.sleep(1000);
                    keybroadProxy.sendString( Integer.toString(i)+ "134.123");
                    Log.d(TAG,"send key " + i);
                }
            }catch(InterruptedException ie){
               // Log.d(TAG,ie.getMessage());
            }
            }
        }).start();
    }
}