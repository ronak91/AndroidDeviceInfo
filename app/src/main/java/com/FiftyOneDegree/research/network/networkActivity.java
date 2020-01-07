package com.FiftyOneDegree.research.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.FiftyOneDegree.research.R;

import static java.nio.file.Paths.get;

public class networkActivity extends AppCompatActivity {

    Button btn_network_types;
    TextView txt_network_types, txt_networks_speed;


    public static final int NETWORK_TYPE_EHRPD = 14; // API Level 11
    public static final int NETWORK_TYPE_EVDO_B = 12; // API Level 9
    public static final int NETWORK_TYPE_HSPAP = 15; // API Level 13
    public static final int NETWORK_TYPE_IDEN = 11; // API Level 8
    public static final int NETWORK_TYPE_LTE = 13; // API Level 11

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        btn_network_types = findViewById(R.id.btn_network_types);
        txt_networks_speed = findViewById(R.id.txt_networks_speed);
        txt_network_types = findViewById(R.id.txt_networks);

        btn_network_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isConected()) {
                    txt_networks_speed.setText(checkingNetworkSpeed());
                    txt_network_types.setText(getNetworkType() + "  AND  " + getNetworkClass() + "\n Last All Details : "+ getAllDetails());
                } else {
                    txt_networks_speed.setText("No NetWork Access");
                    txt_network_types.setText("No NetWork Access");
                }


            }
        });

    }


    public Boolean isConected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return (info != null && info.isConnected());
    }

    private static String getConnectivityType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if (isNetwork) {
            return "Network";
        } else if (isWifi) {
            return "Wifi";
        }
        return "";
    }

    public String getAllDetails() {
        String Alldetails = "";
        TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();

        // Network Type and Speed
        Alldetails = Alldetails + "Network and Speed : " + checkingNetworkSpeed();

        // Number of Sims
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int numberOfSim = 0;
            numberOfSim = mTelephonyManager.getPhoneCount();
            Alldetails = Alldetails + "\nNumber of Sim : " + numberOfSim;
        }

        Alldetails = Alldetails + "\nGSM : " + ((isGSM()==1)?"Available":"NotAvailabele");

        Alldetails = Alldetails + "\nCDMA : " + ((isCDMA()==1)?"Available":"NotAvailabele");

        Alldetails = Alldetails + "\nCurrent Phone Type : " + getCurrentPhoneType();

        Alldetails = Alldetails + "\nLte On Cdma : " + getLteOnCdmaMode();

        Alldetails = Alldetails + "\nLte On Cdma : " + getLteOnCdmaMode();





        return Alldetails;
    }

    public Integer isGSM() {
        TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.PHONE_TYPE_GSM;
    }

    public Integer isCDMA() {

        TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.PHONE_TYPE_CDMA;
    }

    public Boolean getLteOnCdmaMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            CarrierConfigManager configManager = (CarrierConfigManager) getSystemService(Context.CARRIER_CONFIG_SERVICE);
            if (configManager != null) {
                PersistableBundle b = configManager.getConfig();
                if (b != null) {
                    final CarrierConfigManager carrierConfigMan = (CarrierConfigManager) this.getSystemService(Context.CARRIER_CONFIG_SERVICE);
                    final PersistableBundle carrierConfig;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        carrierConfig = carrierConfigMan.getConfigForSubId(mTelephonyManager.getSimCarrierId());
                        return carrierConfig.getBoolean(CarrierConfigManager.KEY_SHOW_CDMA_CHOICES_BOOL);
                    }
                }
            }
        }
        return false;
    }

//    public static CarrierConfigManager getCarrierConfigManager()
//    {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            return (CarrierConfigManager) get(CARRIER_CONFIG_SERVICE);
//        }
//        return null;
//    }

    @TargetApi(23)
    public CarrierConfigManager getCarrierConfigManager() {
        return (CarrierConfigManager) getSystemService(Context.CARRIER_CONFIG_SERVICE);
    }

    public Integer getCurrentPhoneType() {
        TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        return mTelephonyManager.getPhoneType();
    }

    public String checkingNetworkSpeed() {

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if (isWifi) {
            return "CONNECTED VIA WIFI";
        } else if (isNetwork) {

            TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = mTelephonyManager.getNetworkType();

            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "NETWORK TYPE 1xRTT - Speed: ~50 - 100 Kbps";
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "NETWORK TYPE CDMA (3G) Speed: ~14-64 Kbps";
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "NETWORK TYPE EDGE (2.75G) Speed: 100-120 Kbps";
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return "NETWORK TYPE EVDO_0 Speed: ~400-1000 Kbps";
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return "NETWORK TYPE EVDO_A Speed: ~600-1400 Kbps";
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "NETWORK TYPE GPRS (2.5G) Speed: ~100 Kbps";
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return "NETWORK TYPE HSDPA (4G) Speed: 2-14 Mbps";
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return "NETWORK TYPE HSPA (4G) Speed: 0.7-1.7 Mbps";
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "NETWORK TYPE HSUPA (3G) Speed: 1-23 Mbps";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return "NETWORK TYPE UMTS (3G) Speed: 0.4-7 Mbps";

                // API level 7 not supported this type
                case NETWORK_TYPE_EHRPD:
                    return "NETWORK TYPE EHRPD Speed: ~1-2 Mbps";
                case NETWORK_TYPE_EVDO_B:
                    return "NETWORK_TYPE_EVDO_B Speed: ~5 Mbps";
                case NETWORK_TYPE_HSPAP:
                    return "NETWORK TYPE HSPA+ (4G) Speed: 10-20 Mbps";
                case NETWORK_TYPE_IDEN:
                    return "NETWORK TYPE IDEN Speed: ~25 Kbps";
                case NETWORK_TYPE_LTE:
                    return "NETWORK TYPE LTE (4G) Speed: 10+ Mbps";

                // Unknown type
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    return "NETWORK TYPE UNKNOWN";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }


    private String getNetworkType() {
        TelephonyManager mTelephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "IDEN";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO_0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO_A";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO_B";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "EHRPD";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPAP";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            default:
                return "Unknown";
        }
    }

    public String getNetworkClass() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:     // api< 8: replace by 11
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:   // api< 9: replace by 12
                case TelephonyManager.NETWORK_TYPE_EHRPD:    // api<11: replace by 14
                case TelephonyManager.NETWORK_TYPE_HSPAP:    // api<13: replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:      // api<11: replace by 13
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                case 19: // LTE_CA
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:       // api<29: replace by 20
                    return "5G";
                default:
                    return "?";
            }
        }
        return "?";
    }

}
