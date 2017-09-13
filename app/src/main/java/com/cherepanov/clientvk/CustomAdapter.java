package com.cherepanov.clientvk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;

/**
 * Created by Денис on 13.09.2017.
 */

class CustomAdapter extends BaseAdapter {
    private ArrayList<String> users, messages;
    private Context context;
    private VKList<VKApiDialog> dialogList;

    public CustomAdapter(Context context, ArrayList<String> users, ArrayList<String> messages, VKList<VKApiDialog> dialogList) {
        this.context = context;
        this.users = users;
        this.messages = messages;
        this.dialogList = dialogList;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View resultView = inflater.inflate(R.layout.style_list_view, null);

        DialogViewHolder dialog = new DialogViewHolder(resultView);

        dialog.userName.setText(users.get(i));
        dialog.message.setText(messages.get(i));

        resultView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VKRequest request = new VKRequest("messages.send",
                        VKParameters.from(VKApiConst.USER_ID, dialogList.get(i).message.user_id,
                                VKApiConst.MESSAGE, "Test text"));

                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        System.out.println("Сообщение отправлено");
                    }
                });

            }
        });
        return resultView;
    }

    class DialogViewHolder{
        TextView userName;
        TextView message;

        DialogViewHolder(View view){
            userName = (TextView) view.findViewById(R.id.user_name_text_view);
            message = (TextView) view.findViewById(R.id.message_text_view);
        }

    }
}
