package netdb.course.softwarestudio.netdbtalk;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import netdb.course.softwarestudio.netdbtalk.model.Message;
import netdb.course.softwarestudio.netdbtalk.service.rest.RestManager;


public class PostMessageActivity extends Activity {

    public static final int REQUEST_CODE = 1;

    private EditText mTitleTxt;
    private EditText mTextTxt;
    private Button mPostBtn;

    private RestManager mRestMgr;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_message);

        // Create a REST manager instance to do REST methods
        mRestMgr = RestManager.getInstance(getApplication());

        mTitleTxt = (EditText) findViewById(R.id.editTextUser);
        mTextTxt = (EditText) findViewById(R.id.editTextContent);
        mPostBtn = (Button) findViewById(R.id.button);
        mPostBtn.setOnClickListener( new Button.OnClickListener() {


            public void onClick(View v) {
                String user = mTitleTxt.getText().toString();
                String content = mTextTxt.getText().toString();
                postMail(user, content);
            }

        } );

        // TODO: Initialize UI views

        // TODO: Set button listener for posting message

    }

    /**
     * Create a new message
     *
     * @param user The user name
     * @param content  The content of this message
     */
    private void postMail(String user, String content) {
        final Message message = new Message();
        message.setUser(user);
        message.setContent(content);

        mRestMgr.postResource(Message.class, message, new RestManager.PostResourceListener<Message>() {
            @Override
            public void onResponse(int code, Map<String, String> headers) {

                setResult(RESULT_OK);

                finish();
                // TODO: Finish this activity

            }

            @Override
            public void onRedirect(int code, Map<String, String> headers, String url) {
                onError(null, null, code, headers);
            }

            @Override
            public void onError(String message, Throwable cause, int code, Map<String, String> headers) {
                Log.d(this.getClass().getSimpleName(), "" + code + ": " + message);

                setResult(RESULT_CANCELED);
                finish();
            }
        }, null);
    }


}
