package com.example.fssoft.statusaclululuview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fssoft.statuslib.CustomStateOptions;
import com.fssoft.statuslib.StatusAclululuView;

public class MainActivity extends AppCompatActivity {

    private StatusAclululuView aclululu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aclululu = (StatusAclululuView) findViewById(R.id.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.error:
                aclululu.showError(listener);
                break;
            case R.id.empty:
                aclululu.showEmpty(listener);
                break;
            case R.id.progress:
                aclululu.showLoading();
                break;
            case R.id.content:
                aclululu.showContent();
                break;
            case R.id.nonetwork:
                aclululu.showOffline(listener);
                break;
            case R.id.custom:
                aclululu.showCustom(new CustomStateOptions().image(R.drawable.ic_launcher).buttonText("自定义").message("12345,beast").buttonClickListener(listener));
                break;

        }
        return true;
    }



    View.OnClickListener listener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_SHORT).show();
        }
    };


}
