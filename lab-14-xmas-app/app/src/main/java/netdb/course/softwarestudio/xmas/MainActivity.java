package netdb.course.softwarestudio.xmas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Map;

import netdb.course.softwarestudio.xmas.model.User;
import netdb.course.softwarestudio.xmas.rest.RestManager;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    public static final String PARAM_USER_ID = "userId";

    private RestManager restMgr;

    private TextView welcomeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        restMgr = RestManager.getInstance(this);

        long userId = getIntent().getLongExtra(PARAM_USER_ID, -1);
        getUser(userId);

    }

    private void getUser(long userId){

        restMgr.getResource(User.class, String.valueOf(userId), new RestManager.GetResourceListener<User>() {
            @Override
            public void onResponse(int code, Map<String, String> headers, User resource) {

                User user = resource;
                welcomeTxt = (TextView) findViewById(R.id.txt_welcome);
                welcomeTxt.setText("Hi, " + user.getName());

            }

            @Override
            public void onRedirect(int code, Map<String, String> headers, String url) {

            }

            @Override
            public void onError(String message, Throwable cause, int code, Map<String, String> headers) {

                Log.d(TAG, "code:" + code);

            }
        }, TAG);

    }
}
