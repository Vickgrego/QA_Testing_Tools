package testing.hryhorchuk.com.testingtools;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;

public class CallInterupter {
    private final int REQUEST_PERMISSION_PHONE_STATE = 1;
    private Activity ctx;
    private String mPhoneNumber;
    private TelephonyManager tMgr;

    public CallInterupter(Context ctx) {
//        this.tMgr = (TelephonyManager) ctx.getSystemService("phone");
//        this.ctx = (Activity) ctx;
    }

    public String getNumber() {
//        if (!(ContextCompat.checkSelfPermission(this.ctx, "android.permission.READ_SMS") == 0 || ContextCompat.checkSelfPermission(this.ctx, "android.permission.READ_PHONE_STATE") == 0)) {
//            ActivityCompat.requestPermissions(this.ctx, new String[]{"android.permission.READ_PHONE_STATE"}, 1);
//        }
//        this.mPhoneNumber = this.tMgr.getLine1Number();
//        return this.mPhoneNumber;
        return null;
    }
}