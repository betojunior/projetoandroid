package br.com.meuprontuario.meuprontuario;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Intent tela;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // já vai estar na tela
                    return true;
                case R.id.navigation_emergencia:
                    tela = new Intent(HomeActivity.this,EmergenciaActivity.class);
                    startActivity(tela);
                    return true;
                /*case R.id.navigation_prontuario:
                    mTextMessage.setText(R.string.title_prontuario);
                    return true;*/
                case R.id.navigation_perfil:
                    tela = new Intent(HomeActivity.this,PerfilActivity.class);
                    startActivity(tela);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
