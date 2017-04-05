package wifisurveyor.core.wifiScanner;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by hamid on 3/6/2017.
 */
public class Parser {
    private String[] SSIDs = null;
    private ArrayList<AP> APs = new ArrayList<>();

    public Parser(String data){
        data = data.substring(data.indexOf("Interface name : Wi-Fi"));
        data = data.replaceAll("bss#", "b#");
        data = data.replaceAll("BSSID", "bss#");
        data = data.replaceAll(": SSID", ": ssid");
        SSIDs = data.split("SSID");
        createAps();
    }

    private void createAps(){
        for (int i=1; i< SSIDs.length; i++){
            String ssid = SSIDs[i];
            Scanner scanner = new Scanner(ssid);
            String firstLine = scanner.nextLine();
            String name = firstLine.substring(firstLine.indexOf(":") + 2);
            if(name.equals(""))
                continue;
            String[] bss = ssid.split("bss#");
            for (int j = 1  ; j < bss.length ; j++) {
                parseBss(bss[j], name);
            }
        }
    }
    private void parseBss(String data, String name){
        Scanner scanner = new Scanner(data);
        String firstLine = scanner.nextLine();
        String mac = firstLine.substring(firstLine.indexOf(":")+2);
        String secondLine = scanner.nextLine();
        String power = secondLine.substring(secondLine.indexOf(":")+2);
        power = power.replace("%", "");
        scanner.nextLine();
        String forthLine = scanner.nextLine();
        String channel = forthLine.substring(forthLine.indexOf(":") + 2);
        float p = Float.parseFloat(power);
        p /=2;
        this.APs.add(new AP(name, channel, mac, p - (float)100));
    }

    public ArrayList<AP> getAPs(){
        return APs;
    }
}
