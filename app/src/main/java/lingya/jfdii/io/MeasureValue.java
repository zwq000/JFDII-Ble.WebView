package lingya.jfdii.io;


import android.os.Parcel;
import android.os.Parcelable;

public class MeasureValue implements Parcelable {
    private String gageId;
    private String unit;
    private String value;

    public MeasureValue(String gageId,String value,String unit){
        this.gageId = gageId;
        this.value = value;
        this.unit = unit;
    }

    /**
     * 获取 发射器Id
     * @return
     */
    public String getGageId() {
        return gageId;
    }

    /**
     * 计量单位
     * @return
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 测量值
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        if(unit=="error"){
            return String.format("empty[id:%s]",gageId);
        }
        return String.format("id:%s val:%s %s",gageId,value,unit);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeString(this.unit);
        dest.writeString(this.gageId);
    }

    protected MeasureValue(Parcel in) {
        this.value = in.readString();
        this.unit = in.readString();
        this.gageId = in.readString();
    }

    public static final Creator<MeasureValue> CREATOR = new Creator<MeasureValue>() {
        @Override
        public MeasureValue createFromParcel(Parcel source) {
            return new MeasureValue(source);
        }

        @Override
        public MeasureValue[] newArray(int size) {
            return new MeasureValue[size];
        }
    };
}
