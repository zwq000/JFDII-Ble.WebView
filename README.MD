
=============


1. Receiver

注册 接收器
ACTION JFDII.ACTION_DATA_AVAILABLE

```xml
    <receiver
        android:name="lingya.jfdii.MeasureValueReceiver"
        android:exported="true"
        android:enabled="true" >
        <intent-filter>
            <action android:name="JFDII.ACTION_DATA_AVAILABLE" />
        </intent-filter>
    </receiver>

```

2.在接收器中 声明 KeybroadProxy 并设置 前缀/后缀 键
前缀/后缀 参考 KeyEvent.KEYCODE_XXX
默认值为 KeyEvent.KEYCODE_UNKNOWN

```java
    private KeybroadProxy proxy = new KeybroadProxy(KeyEvent.KEYCODE_UNKNOWN,KeyEvent.KEYCODE_TAB);

```


3. 在接收器中转发键盘事件

```java

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTTION_DATA_AVALIABLE)){
            MeasureValue value = intent.getParcelableExtra(EXTRA_MEASURE_VALUE);
            Log.d(TAG,"receive " + value.toString());
            proxy.sendString(value.getValue());
        }
    }

```

