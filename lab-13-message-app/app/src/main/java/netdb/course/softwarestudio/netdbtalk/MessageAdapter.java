package netdb.course.softwarestudio.netdbtalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import netdb.course.softwarestudio.netdbtalk.model.Message;

public class MessageAdapter extends BaseAdapter {

    private List<Message> mMessageList;
    private LayoutInflater mMyInflater;

    public MessageAdapter(Context c, ArrayList<Message> list) {
        this.mMessageList = list;
        mMyInflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setList(ArrayList<Message> list) {
        this.mMessageList = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mMyInflater.inflate(R.layout.message_item, null);
        Message message = mMessageList.get(position);

        TextView userTxt = (TextView) convertView.findViewById(R.id.txt_user);
        TextView contentTxt = (TextView) convertView.findViewById(R.id.txt_content);

        userTxt.setText(message.getUser());
        contentTxt.setText(message.getContent());

        return convertView;
    }

}
