package netdb.course.softwarestudio.xmas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import netdb.course.softwarestudio.xmas.model.Session;
import netdb.course.softwarestudio.xmas.model.User;
import netdb.course.softwarestudio.xmas.rest.RestManager;


public class LoginActivity extends Activity {

    private static final String TAG = "LoginActivity";
    private StatusCallback statusCallback = new com.facebook.Session.StatusCallback() {
        @Override
        public void call(com.facebook.Session session, SessionState state, Exception exception) {
            switch (state) {
                case OPENED:
                case OPENED_TOKEN_UPDATED:
                    Log.d(TAG, "Facebook Session Opened.");

                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                    // get access token
                    String fbAccessToken = session.getAccessToken();

                    Session s = new Session();


                    // TODO provide access token to session s, so our server can verify it
                    s.setFbAccessToken(fbAccessToken);
                    loginOwnServer(s);

                    break;

                case CLOSED:
                    Log.d(TAG, "Facebook Session Closed.");
                    break;

                case CLOSED_LOGIN_FAILED:
                    Log.d(TAG, "Facebook Login Failed");
                    Toast.makeText(LoginActivity.this, "Failed to Log in.", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    // CREATED, CREATED_TOKEN_LOADED, OPENING
                    break;

            }
        }
    };
    // TODO request access for user status updates
    // HINT https://developers.facebook.com/docs/facebook-login/permissions/v2.2#reference-read_stream
    private static final List<String> FB_PERMISSIONS = Arrays.asList("public_profile","user_status");
    private UiLifecycleHelper lifecycleHelper;
    private RestManager restMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        restMgr = RestManager.getInstance(this);
        com.facebook.Session.openActiveSession(this, true, FB_PERMISSIONS, statusCallback);
        // TODO initialize lifecycleHelper
        lifecycleHelper = new UiLifecycleHelper(this, statusCallback);
        lifecycleHelper.onCreate(savedInstanceState);

        Button loginBtn = (Button) this.findViewById(R.id.fb_login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Opening Facebook Session...");

                com.facebook.Session activeFbSession = com.facebook.Session.getActiveSession();

                if (activeFbSession == null || !activeFbSession.getState().isOpened()) {
                    com.facebook.Session.openActiveSession(LoginActivity.this, true,
                            FB_PERMISSIONS, statusCallback);
                }
            }
        });

    }

    // TODO override related lifecycle methods and get lifecycleHelper involved

    @Override
    protected void onResume(){
        super.onResume();
        lifecycleHelper.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        lifecycleHelper.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        lifecycleHelper.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        lifecycleHelper.onDestroy();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        lifecycleHelper.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        lifecycleHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void loginOwnServer(Session session) {

        restMgr.postResource(netdb.course.softwarestudio.xmas.model.Session.class, session, new RestManager.PostResourceListener<Session>() {
            @Override
            public void onResponse(int code, Map<String, String> headers) {
                getLoginUserIdAndEnter();
            }

            @Override
            public void onRedirect(int code, Map<String, String> headers,
                                   String url) {
                onError(null, null, code, headers);
            }

            @Override
            public void onError(String message, Throwable cause, int code,
                                Map<String, String> headers) {

                // New user
                if (code == 404) {

                    final User u = new User();

                    // TODO provide access token to our server,
                    // HINT use com.facebook.Session.getActiveSession()
                    //      to retrieve current Facebook session.
                    com.facebook.Session session  = com.facebook.Session.getActiveSession();
                    Request.newMeRequest(session, new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser graphUser, Response response) {
                            // print user's name and birthday in debug logs
                            Log.i(TAG, graphUser.getName());
                            u.setName(graphUser.getName());
                        }
                    }).executeAsync();
                    u.setFbAccessToken(session.getAccessToken());
                    registerNewUser(u);

                }
            }
        }, TAG);
    }

    private void registerNewUser(User user) {

        restMgr.postResource(User.class, user, new RestManager.PostResourceListener<User>() {
            @Override
            public void onResponse(int code, Map<String, String> headers) {

                getLoginUserIdAndEnter();
            }

            @Override
            public void onRedirect(int code, Map<String, String> headers,
                                   String url) {
                onError(null, null, code, headers);
            }

            @Override
            public void onError(String message, Throwable cause, int code,
                                Map<String, String> headers) {

            }
        }, TAG);
    }

    private void getLoginUserIdAndEnter(){

        restMgr.getResource(Session.class, null, new RestManager.GetResourceListener<Session>() {
            @Override
            public void onResponse(int code, Map<String, String> headers, Session resource) {
                Long userId = resource.getUserId();
                enterMainActivity(userId);
            }

            @Override
            public void onRedirect(int code, Map<String, String> headers, String url) {

            }

            @Override
            public void onError(String message, Throwable cause, int code, Map<String, String> headers) {

            }
        }, TAG);
    }

    private void enterMainActivity(Long userId) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // put user id
        intent.putExtra(MainActivity.PARAM_USER_ID, userId);

        startActivity(intent);
        LoginActivity.this.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        finish();

    }

}
