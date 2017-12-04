package info.devexchanges.universalsharedpreferences;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.zookey.universalpreferences.UniversalPreferences;

public class LoginActivity extends AppCompatActivity {

    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;
    private TextInputLayout userName;
    private TextInputLayout password;
    public final static String KEY_USER_NAME = "username";
    public final static String KEY_PASSWORD = "password";
    public final static String IS_LOGIN = "is_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if logged in, redirect user to main activity
        boolean isLoggedIn = UniversalPreferences.getInstance().get(IS_LOGIN, false);
        if (isLoggedIn) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_login);

            userName = (TextInputLayout) findViewById(R.id.username_field);
            password = (TextInputLayout) findViewById(R.id.password_field);
            View btnLogin = findViewById(R.id.btn_login);
            linearLayout = (LinearLayout)findViewById(R.id.liearLayout);
            animationDrawable = (AnimationDrawable) linearLayout.getBackground();
            animationDrawable.setEnterFadeDuration(5000);
            animationDrawable.setExitFadeDuration(1000);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userName.setError(null);
                    userName.setError(null);
                    if (!hasText(userName)) {
                        userName.setError("OOPs! username is required");
                    } else if (!hasText(password)) {
                        password.setError("forgo");
                    } else {
                        //go to the main screen after store data in Universal SharedPreferences
                        UniversalPreferences.getInstance().put(KEY_USER_NAME, getText(userName));
                        UniversalPreferences.getInstance().put(KEY_PASSWORD, getText(password));
                        UniversalPreferences.getInstance().put(IS_LOGIN, true);

                        //Go to Main Activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }

    private boolean hasText(TextInputLayout inputLayout) {
        return !inputLayout.getEditText().getText().toString().trim().equals("");
    }

    private String getText(TextInputLayout inputLayout) {
        return inputLayout.getEditText().getText().toString().trim();
    }
}
