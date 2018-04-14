package lingya.jfdii;

import android.app.Instrumentation;
import android.os.Looper;
import android.view.KeyEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KeybroadProxy {

    private ExecutorService singleThreadPool;
    private Instrumentation instrumentation;

    public KeybroadProxy(){
        singleThreadPool = Executors.newSingleThreadExecutor();
        instrumentation = new Instrumentation();
    }

    /**
     *
     * @param prefix 前缀 KeyCode
     * @param suffix 后缀 KeyCode
     */
    public KeybroadProxy(int prefix,int suffix){
        this();
        prefixKeyCode = prefix;
        suffixKeyCode = suffix;
    }

    /**
     * 前缀 KeyCode
     * @see KeyEvent#KEYCODE_TAB;
     */
    public int prefixKeyCode = KeyEvent.KEYCODE_UNKNOWN;

    /**
     * 后缀 KeyCode
     * 默认值为 @see KeyEvent#KEYCODE_UNKNOWN
     */
    public int suffixKeyCode = KeyEvent.KEYCODE_UNKNOWN;


    public void sendString(final String sendStr) {
        if(isAppThread()){
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    sendStringImpl(sendStr);
                }
            });
        }else{
            sendStringImpl(sendStr);
        }
    }

    /**
     * 当前线程是否为主线程
     * @return
     */
    private boolean isAppThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private void sendStringImpl(String sendStr){
        //前缀
        if(prefixKeyCode>0){
            sendKeySync(prefixKeyCode);
        }
        for (int i = 0; i < sendStr.length(); i++) {
            int keyCode = getKeyCode(sendStr.charAt(i));
            if(keyCode>0){
                sendKeySync(keyCode);
            }
        }

        //内容后缀
        if(suffixKeyCode >0){
            sendKeySync(suffixKeyCode);
        }
    }

    private static int getKeyCode(char c) {
        switch (c) {
            case '0':
                return KeyEvent.KEYCODE_0;
            case '1':
                return KeyEvent.KEYCODE_1;
            case '2':
                return KeyEvent.KEYCODE_2;
            case '3':
                return KeyEvent.KEYCODE_3;
            case '4':
                return KeyEvent.KEYCODE_4;
            case '5':
                return KeyEvent.KEYCODE_5;
            case '6':
                return KeyEvent.KEYCODE_6;
            case '7':
                return KeyEvent.KEYCODE_7;
            case '8':
                return KeyEvent.KEYCODE_8;
            case '9':
                return KeyEvent.KEYCODE_9;
            case '.':
                return KeyEvent.KEYCODE_PERIOD;
            case '-':
                return KeyEvent.KEYCODE_MINUS;
            case '+':
                return KeyEvent.KEYCODE_PLUS;
            default:
                return KeyEvent.KEYCODE_UNKNOWN;
        }
    }


    private void sendKeySync(int keyCode) {
        instrumentation.sendKeyDownUpSync(keyCode);
    }
}
