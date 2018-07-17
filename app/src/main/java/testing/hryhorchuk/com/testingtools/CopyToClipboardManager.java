package testing.hryhorchuk.com.testingtools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class CopyToClipboardManager {
    private ClipData clip;
    private ClipboardManager clipboard;

    public CopyToClipboardManager(Context context) {
        this.clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    protected void copyToClipboard(String text) {
        this.clip = ClipData.newPlainText("generateText", text);
        this.clipboard.setPrimaryClip(this.clip);
    }
}