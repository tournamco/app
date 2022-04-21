package co.tournam.schedule;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import co.tournam.ui.header.SmallHeader;

public class QRGenActivity extends AppCompatActivity {

    //Variable Declarations
    private ImageView qrCodeView;
    private LinearLayout headerLayout;
    Context context;

    //On create method of the QR Generator Activity calling and setting up functions and variables
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        String inviteToken = null;

        if (b != null) {
            inviteToken = b.getString("token");
        }
        setContentView(R.layout.qr_code_gen);
        this.context = this.getApplicationContext();

        setHeader();
        generateQRCode(inviteToken);

    }

    //Sets and builds the Header of the activity
    public void setHeader() {
        headerLayout = findViewById(R.id.qr_code_gen_header);
        SmallHeader header = new SmallHeader(context, "QR Code", () -> finish());
        headerLayout.addView(header);
    }

    //Sets and builds the Generated QR code
    public void setImageView(Bitmap bm) {
        qrCodeView = findViewById(R.id.qr_code_gen_image_view);
        qrCodeView.setImageBitmap(bm);
    }

    /**
     * Generates QR code from the passed in string. Calls the setImageView() method.
     *
     * @param token team invite token
     */
    public void generateQRCode(String token) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            int size = 500;
            BitMatrix bitMatrix = qrCodeWriter.encode(token, BarcodeFormat.QR_CODE, size, size);
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            setImageView(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


