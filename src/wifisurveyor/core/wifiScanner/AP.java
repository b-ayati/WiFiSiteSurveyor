package wifisurveyor.core.wifiScanner;

/**
 * Created by hamid on 3/6/2017.
 */
public class AP {
    public AP(String ssid, String channel, String mac, float power) {
        this.ssid = ssid;
        this.channel = channel;
        this.mac = mac;
        this.power = power;
    }

    public String ssid;
    public String channel;
    public String mac;
    public float power;
}
