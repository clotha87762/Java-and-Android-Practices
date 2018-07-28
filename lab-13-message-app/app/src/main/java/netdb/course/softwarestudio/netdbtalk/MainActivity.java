package netdb.course.softwarestudio.netdbtalk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import netdb.course.softwarestudio.netdbtalk.model.Message;
import netdb.course.softwarestudio.netdbtalk.service.rest.RestManager;

public class MainActivity extends Activity {

    private ArrayList<Message> mMessageList = new ArrayList<Message>();
    private ListView mListView;
    private MessageAdapter mMessageAdapter;

    private RestManager mRestMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        mListView = (ListView) findViewById(R.id.list_messages);

        // Create a custom mMaMessageAdapter and set to the list view
        mMessageAdapter = new MessageAdapter(this, mMessageList);
        mListView.setAdapter(mMessageAdapter);

        // Create a REST manager instance to do REST methods
        mRestMgr = RestManager.getInstance(getApplication());

        // Start a thread to get the messages from server.
        // After getting the messages, notify the UI thread to
        // redraw the UI view
        getMessages();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle the action bar items pressed
        switch (item.getItemId()) {
            case R.id.action_refresh:

                getMessages();

                return true;

            case R.id.action_add:

                Intent intent = new Intent(this,PostMessageActivity.class);
                startActivity(intent);
                // TODO Start the activity for posting message



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            getMessages();

            // Use UI thread to update UI view
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageAdapter.notifyDataSetChanged();
                }
            });
        } else if (resultCode == RESULT_CANCELED) {

            Toast.makeText(this, "Post Failure", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Get all messages from server sorted by timestamp in desc order.
     */
    private void getMessages() {

        Map<String, String> params = new HashMap<String, String>();
        mRestMgr.listResource(Message.class, params, new RestManager.ListResourceListener<Message>() {
            @Override
            public void onResponse(int code, Map<String, String> headers,
                                   List<Message> resources) {
                if (resources != null) {

                    mMessageList.clear();
                    for(Message m : resources){
                        mMessageList.add(m);
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMessageAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void onRedirect(int code, Map<String, String> headers, String url) {
                onError(null, null, code, headers);
            }

            @Override
            public void onError(String message, Throwable cause, int code,
                                Map<String, String> headers) {
                Log.d(this.getClass().getSimpleName(), "" + code + ": " + message);
            }
        }, null);
    }

}
