package io.github.yusufahmadi.labelcalculator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy", new DateFormatSymbols(Locale.US));
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        settings = getSharedPreferences(mdlPublic.PREFS_PASSCODE, 0);

        initLayouts();
    }

    private AutoCompleteTextView txtPasscode;
    private MaterialCardView cvSignIn;
    private TextView tvVersion, tvResetPasscode, tvStatus, tvUbahPasscode;
    private void initLayouts() {
        String myVersionName = "not available"; // initialize String
        try {
            myVersionName = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);

            txtPasscode        = findViewById(R.id.txtPasscode);
            cvSignIn           = findViewById(R.id.cvSignIn);
            cvSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtPasscode.getText().toString().equals(settings.getString("Passcode", mdlPublic.PWD_DEFAULT_PASSCODE))) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Passcode yang anda masukkan salah!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            tvVersion          = findViewById(R.id.tvVersion);
            tvVersion.setText("App Version : " + myVersionName);
            tvResetPasscode    = findViewById(R.id.tvResetPasscode);
            tvResetPasscode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResetPasscode();
                }
            });
            tvUbahPasscode    = findViewById(R.id.tvChangePasscode);
            tvUbahPasscode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UbahPasscode();
                }
            });
            tvStatus           = findViewById(R.id.tvStatus);
            tvStatus.setText("Passcode At : " + settings.getString("AtPasscode", "Default"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ShowHidePass(View view){

        if(view.getId()==R.id.show_pass_btn){

            if(txtPasscode.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                ((ImageView)(view)).setImageResource(R.drawable.ic_invisible_black);

                //Show Password
                txtPasscode.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_visibility_black);

                //Hide Password
                txtPasscode.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        if (!mdlPublic.UserLogin.getManagementUser()) {
//            menu.getItem(1).setVisible(false);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;
    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            ExitApp();
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tekan Back Sekali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    private void ExitApp() {
        finish();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode,resultCode,data);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE)
                && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void ResetPasscode() {
        try {
            View modelBottomSheet = View.inflate(LoginActivity.this, R.layout.dialog_reset_passcode, null);
            final BottomSheetDialog dialog = new BottomSheetDialog(LoginActivity.this);
            dialog.setContentView(modelBottomSheet);
            dialog.setCancelable(true);

            final TextInputEditText txtPassword     = dialog.findViewById(R.id.txtPassword);
            txtPassword.setText("");

            Button btnSave  = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValidasi = true;
                    try {
                        if (isValidasi && !txtPassword.getText().toString().equals(mdlPublic.PWD_RESET_PASSCODE)) {
                            Toast.makeText(getApplicationContext(),
                                    "Password untuk Reset Password Salah, Silahkan menghubungi Developer jika Kesulitan!",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }

                        if (isValidasi) {
                            SharedPreferences.Editor editor = settings.edit();
                            Calendar c = Calendar.getInstance();
                            editor.putString("Passcode", mdlPublic.PWD_DEFAULT_PASSCODE);
                            editor.putString("AtPasscode", dt.format(c.getTime()));
                            editor.apply();

                            tvStatus.setText("Passcode At : " + settings.getString("AtPasscode", "Default"));

                            Toast.makeText(getApplicationContext(),
                                    "Passcode telah diReset.",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Save", e.getMessage(), e);
                    }
                }
            });
            Button btnClose = dialog.findViewById(R.id.btnTutup);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.getMessage());
        }
    }

    private void UbahPasscode() {
        try {
            View modelBottomSheet = View.inflate(LoginActivity.this, R.layout.dialog_ubah_passcode, null);
            final BottomSheetDialog dialog = new BottomSheetDialog(LoginActivity.this);
            dialog.setContentView(modelBottomSheet);
            dialog.setCancelable(true);

            final TextInputEditText txtOldPassword     = dialog.findViewById(R.id.txtOldPasscode);
            final TextInputEditText txtNewPassword     = dialog.findViewById(R.id.txtNewPasscode);
            final TextInputEditText txtReTypePassword  = dialog.findViewById(R.id.txtRetypePasscode);
            txtOldPassword.setText("");
            txtNewPassword.setText("");
            txtReTypePassword.setText("");

            Button btnSave  = dialog.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isValidasi = true;
                    try {
                        if (isValidasi && !txtNewPassword.getText().toString().equals(txtReTypePassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(),
                                    "Passcode Baru harus sama!",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi && txtNewPassword.getText().toString().length()==0) {
                            Toast.makeText(getApplicationContext(),
                                    "Passcode Baru harus diisi!",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }
                        if (isValidasi && !txtOldPassword.getText().toString().equals(settings.getString("Passcode", mdlPublic.PWD_DEFAULT_PASSCODE))) {
                            Toast.makeText(getApplicationContext(),
                                    "Passcode Lama masih salah!",
                                    Toast.LENGTH_SHORT).show();
                            isValidasi = false;
                        }

                        if (isValidasi) {
                            SharedPreferences.Editor editor = settings.edit();
                            Calendar c = Calendar.getInstance();
                            editor.putString("Passcode", txtNewPassword.getText().toString());
                            editor.putString("AtPasscode", dt.format(c.getTime()));
                            editor.apply();

                            tvStatus.setText("Passcode At : " + settings.getString("AtPasscode", "Default"));

                            Toast.makeText(getApplicationContext(),
                                    "Passcode telah diRubah.",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Save", e.getMessage(), e);
                    }
                }
            });
            Button btnClose = dialog.findViewById(R.id.btnTutup);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", e.getMessage());
        }
    }
}
