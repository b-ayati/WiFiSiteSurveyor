package wifisurveyor.core.wifiScanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hamid on 3/6/2017.
 */
public class Command {
    public static String execute (String command){
        try {
            String ssid;
            StringBuilder stringBuilder = new StringBuilder();
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", command);
            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = r.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return null;
        }
    }
    public static String mock(){
        return "Wireless System Information Summary\n" +
                "(Time: 3/6/2017 9:33:05 AM Iran Standard Time)\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "============================== SHOW DRIVERS =========================== \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Interface name: Wi-Fi\n" +
                "\n" +
                "    Driver                    : Broadcom 802.11n Network Adapter\n" +
                "    Vendor                    : Broadcom\n" +
                "    Provider                  : Broadcom\n" +
                "    Date                      : 6/2/2013\n" +
                "    Version                   : 6.30.223.256\n" +
                "    INF file                  : C:\\Windows\\INF\\netbc64.inf\n" +
                "    Files                     : 2 total\n" +
                "                                C:\\Windows\\system32\\DRIVERS\\BCMWL63a.SYS\n" +
                "                                C:\\Windows\\system32\\DRIVERS\\vwifibus.sys\n" +
                "    Type                      : Native Wi-Fi Driver\n" +
                "    Radio types supported     : 802.11n 802.11g 802.11b\n" +
                "    FIPS 140-2 mode supported : Yes\n" +
                "    802.11w Management Frame Protection supported : Yes\n" +
                "    Hosted network supported  : Yes\n" +
                "    Authentication and cipher supported in infrastructure mode:\n" +
                "                                Open            None\n" +
                "                                Open            WEP\n" +
                "                                WPA2-Enterprise TKIP\n" +
                "                                WPA2-Personal   TKIP\n" +
                "                                WPA2-Enterprise CCMP\n" +
                "                                WPA2-Personal   CCMP\n" +
                "                                Vendor defined  Vendor defined\n" +
                "                                Vendor defined  Vendor defined\n" +
                "                                WPA-Enterprise  TKIP\n" +
                "                                WPA-Personal    TKIP\n" +
                "                                WPA-Enterprise  CCMP\n" +
                "                                WPA-Personal    CCMP\n" +
                "    Authentication and cipher supported in ad-hoc mode:\n" +
                "                                WPA2-Personal   CCMP\n" +
                "                                Open            None\n" +
                "                                Open            WEP\n" +
                "    Wireless Display Supported: No (Graphics Driver: No, Wi-Fi Driver: Yes)\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "============================= SHOW INTERFACES ========================= \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "There is 1 interface on the system: \n" +
                "\n" +
                "    Name                   : Wi-Fi\n" +
                "    Description            : Broadcom 802.11n Network Adapter\n" +
                "    GUID                   : 6a03de25-ef87-4ede-87bb-4b9a690efc98\n" +
                "    Physical address       : 24:fd:52:81:31:15\n" +
                "    State                  : connected\n" +
                "    SSID                   : CE_WLAN\n" +
                "    BSSID                  : 04:18:d6:c2:bf:0d\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : 802.11n\n" +
                "    Authentication         : Open\n" +
                "    Cipher                 : None\n" +
                "    Connection mode        : Auto Connect\n" +
                "    Channel                : 1\n" +
                "    Receive rate (Mbps)    : 72\n" +
                "    Transmit rate (Mbps)   : 72\n" +
                "    Signal                 : 98% \n" +
                "    Profile                : CE_WLAN \n" +
                "\n" +
                "    Hosted network status  : Not started\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "=========================== SHOW HOSTED NETWORK ======================= \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Hosted network settings \n" +
                "----------------------- \n" +
                "    Mode                   : Allowed\n" +
                "    SSID name              : \"hamid\"\n" +
                "    Max number of clients  : 100\n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "\n" +
                "Hosted network status   \n" +
                "--------------------- \n" +
                "    Status                 : Not started\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "============================= SHOW SETTINGS =========================== \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Wireless LAN settings \n" +
                "--------------------- \n" +
                "    Show blocked networks in visible network list: No\n" +
                "\n" +
                "    Only use GP profiles on GP-configured networks: No\n" +
                "\n" +
                "    Hosted network mode allowed in WLAN service: Yes\n" +
                "\n" +
                "    Allow shared user credentials for network authentication: Yes\n" +
                "\n" +
                "    Block period: Not Configured.\n" +
                "\n" +
                "    Auto configuration logic is enabled on interface \"Wi-Fi\"\n" +
                "    MAC randomization not available on interface Wi-Fi\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "============================== SHOW FILTERS =========================== \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Allow list on the system (group policy) \n" +
                "--------------------------------------- \n" +
                "    <None>\n" +
                "\n" +
                "Allow list on the system (user) \n" +
                "------------------------------- \n" +
                "    <None>\n" +
                "\n" +
                "Block list on the system (group policy) \n" +
                "--------------------------------------- \n" +
                "    <None>\n" +
                "\n" +
                "Block list on the system (user) \n" +
                "------------------------------- \n" +
                "    <None>\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "=========================== SHOW CREATEALLUSER ======================== \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Everyone is allowed to create all user profiles.\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "============================= SHOW PROFILES =========================== \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Profiles on interface Wi-Fi:\n" +
                "\n" +
                "Group policy profiles (read only)\n" +
                "---------------------------------\n" +
                "    <None>\n" +
                "\n" +
                "User profiles\n" +
                "-------------\n" +
                "    All User Profile     : CE_WLAN\n" +
                "    All User Profile     : Sam\n" +
                "    All User Profile     : mohammad\n" +
                "    All User Profile     : mohammad?s iPad\n" +
                "    All User Profile     : SE_LAB\n" +
                "    All User Profile     : GSME0\n" +
                "    All User Profile     : GSME2\n" +
                "    All User Profile     : h\n" +
                "    All User Profile     : Wireless_Ap10\n" +
                "    All User Profile     : Ayneh\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "========================== SHOW PROFILES NAME=* ======================= \n" +
                "=======================================================================\n" +
                "\n" +
                "\n" +
                "Profile CE_WLAN on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : CE_WLAN\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"CE_WLAN\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : Open\n" +
                "    Cipher                 : None\n" +
                "    Security key           : Absent\n" +
                "    Key Index              : 1\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile Sam on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : Sam\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"Sam\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Fixed\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : User\n" +
                "\n" +
                "\n" +
                "Profile mohammad on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : mohammad\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"mohammad\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile mohammad?s iPad on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : mohammad?s iPad\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"mohammad\u0083??s iPad\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile SE_LAB on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : SE_LAB\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"SE_LAB\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile GSME0 on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : GSME0\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"GSME0\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile GSME2 on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : GSME2\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"GSME2\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile h on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : h\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"h\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "Profile Wireless_Ap10 on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : Wireless_Ap10\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"Wireless_Ap10\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Fixed\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : User\n" +
                "\n" +
                "\n" +
                "Profile Ayneh on interface Wi-Fi: \n" +
                "======================================================================= \n" +
                "\n" +
                "Applied: All User Profile    \n" +
                "\n" +
                "Profile information \n" +
                "------------------- \n" +
                "    Version                : 1\n" +
                "    Type                   : Wireless LAN\n" +
                "    Name                   : Ayneh\n" +
                "    Control options        : \n" +
                "        Connection mode    : Connect automatically\n" +
                "        Network broadcast  : Connect only if this network is broadcasting\n" +
                "        AutoSwitch         : Do not switch to other networks\n" +
                "        MAC Randomization  : Disabled\n" +
                "\n" +
                "Connectivity settings \n" +
                "--------------------- \n" +
                "    Number of SSIDs        : 1\n" +
                "    SSID name              : \"Ayneh\"\n" +
                "    Network type           : Infrastructure\n" +
                "    Radio type             : [ Any Radio Type ]\n" +
                "    Vendor extension          : Not present\n" +
                "\n" +
                "Security settings \n" +
                "----------------- \n" +
                "    Authentication         : WPA2-Personal\n" +
                "    Cipher                 : CCMP\n" +
                "    Security key           : Present\n" +
                "\n" +
                "Cost settings \n" +
                "------------- \n" +
                "    Cost                   : Unrestricted\n" +
                "    Congested              : No\n" +
                "    Approaching Data Limit : No\n" +
                "    Over Data Limit        : No\n" +
                "    Roaming                : No\n" +
                "    Cost Source            : Default\n" +
                "\n" +
                "\n" +
                "======================================================================= \n" +
                "======================= SHOW NETWORKS MODE=BSSID ====================== \n" +
                "=======================================================================\n" +
                "\n" +
                " \n" +
                "Interface name : Wi-Fi \n" +
                "There are 3 networks currently visible. \n" +
                "\n" +
                "SSID 1 : CE_WLAN\n" +
                "    Network type            : Infrastructure\n" +
                "    Authentication          : Open\n" +
                "    Encryption              : None \n" +
                "    BSSID 1                 : 04:18:d6:e2:b1:16\n" +
                "         Signal             : 78%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 1 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "    BSSID 2                 : 04:18:d6:c7:b2:9a\n" +
                "         Signal             : 72%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 1 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "    BSSID 3                 : 04:18:d6:c2:bf:0d\n" +
                "         Signal             : 90%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 1 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "    BSSID 4                 : 04:18:d6:e2:a0:e8\n" +
                "         Signal             : 80%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 1 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "    BSSID 5                 : 04:18:d6:93:00:eb\n" +
                "         Signal             : 72%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 11 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "    BSSID 6                 : 44:d9:e7:0b:9a:4c\n" +
                "         Signal             : 66%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 11 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "    BSSID 7                 : 04:18:d6:c7:2e:eb\n" +
                "         Signal             : 22%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 11 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 6 11 12 24\n" +
                "         Other rates (Mbps) : 9 18 36 48 54\n" +
                "\n" +
                "SSID 2 : \n" +
                "    Network type            : Infrastructure\n" +
                "    Authentication          : WPA2-Personal\n" +
                "    Encryption              : CCMP \n" +
                "    BSSID 1                 : e4:6f:13:73:a5:5e\n" +
                "         Signal             : 40%  \n" +
                "         Radio type         : 802.11n\n" +
                "         Channel            : 4 \n" +
                "         Basic rates (Mbps) : 1 2 5.5 11\n" +
                "         Other rates (Mbps) : 6 9 12 18 24 36 48 54\n" +
                "\n" +
                "SSID 3 : HP-Print-74-DNSLAB814\n" +
                "    Network type            : Infrastructure\n" +
                "    Authentication          : Open\n" +
                "    Encryption              : None \n" +
                "    BSSID 1                 : 60:6d:c7:78:8a:74\n" +
                "         Signal             : 28%  \n" +
                "         Radio type         : 802.11g\n" +
                "         Channel            : 11 \n" +
                "         Basic rates (Mbps) : 6\n" +
                "         Other rates (Mbps) : 9 12 18 24 36 48 54\n" +
                "\n";
    }
}
