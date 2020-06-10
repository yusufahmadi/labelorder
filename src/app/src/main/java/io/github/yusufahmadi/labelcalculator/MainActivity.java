package io.github.yusufahmadi.labelcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import io.github.yusufahmadi.labelcalculator.ui.label.LabelFragment;
import io.github.yusufahmadi.labelcalculator.ui.master.MasterFragment;
import io.github.yusufahmadi.labelcalculator.ui.ribbon.RibbonFragment;
import io.github.yusufahmadi.labelcalculator.ui.paket.PaketFragment;

import io.github.yusufahmadi.labelcalculator.repository.mdlPublic;
import io.github.yusufahmadi.labelcalculator.ui.taffeta.TaffetaFragment;

public class MainActivity extends AppCompatActivity {
    private static BottomNavigationView bottomNavigationView;
    private static int LastFragment;

    public static void navigate(String Key) {
        LastFragment = 0;
        switch (Key.toLowerCase()) {
            case "key.label":
                bottomNavigationView.setSelectedItemId(R.id.navigation_label);
                break;
            case "key.ribbon":
                bottomNavigationView.setSelectedItemId(R.id.navigation_ribbon);
                break;
            case "key.taffeta":
                bottomNavigationView.setSelectedItemId(R.id.navigation_taffeta);
                break;
            case "key.master":
                bottomNavigationView.setSelectedItemId(R.id.navigation_master);
                break;
            case "key.paket":
                bottomNavigationView.setSelectedItemId(R.id.navigation_paket);
                break;
            default:
                break;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //creating fragment object
            Fragment fragment = null;
            if (LastFragment!=item.getItemId()) {
                LastFragment = item.getItemId();

                switch (item.getItemId()) {
                    case R.id.navigation_label:
                        fragment = new LabelFragment();
                        break;
                    case R.id.navigation_ribbon:
                        fragment = new RibbonFragment();
                        break;
                    case R.id.navigation_taffeta:
                        fragment = new TaffetaFragment();
                        break;
                    case R.id.navigation_master:
                        fragment = new MasterFragment();
                        break;
                    case R.id.navigation_paket:
                        fragment = new PaketFragment();
                        break;
                    default:
                        fragment = null;
                        break;
                }
            }

            //replacing the fragment
            if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragment);
                ft.commitAllowingStateLoss();
                return true;
            } else {
                return false;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting Into Dark Theme
        getDelegate().setLocalNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        mdlPublic.mainActivity = this;

        bottomNavigationView = findViewById(R.id.btm_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigate("key.label");
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
}
