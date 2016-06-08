package com.hnrblock.chatfile;

import java.util.ArrayList;
import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnrblock.chatfile.helpers.MyTimeUtils;
import com.hnrblock.chatfile.objects.ChatMessage;
import com.hnrblock.chatfile.objects.ReportRow;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {
    // private TextView chatText;
    private List<ChatMessage> chatMessageList = new ArrayList<ChatMessage>();
    private List<String> idsList = new ArrayList<String>();
    private Context context;
    private MessageEditClickListener mecl;
    // private ChatMessage typing;
    public int typingPos = -1;

    @Override
    public void add(ChatMessage object) {
        // if (!idsList.contains(object.id)) {
        // idsList.add(object.id);
        // chatMessageList.add(object);
        // }
        chatMessageList.add(object);
        super.add(object);
        notifyDataSetChanged();
    }

    public void remove(String id) {
        for (int i = chatMessageList.size() - 1; i >= 0; i--) {
            ChatMessage cm = chatMessageList.get(i);
            if (cm.id.equalsIgnoreCase(id)) {
                chatMessageList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void removeAllAfter(String id) {
        int idInt = Integer.parseInt(id);
        // boolean found = false;
        for (int i = chatMessageList.size() - 1; i >= 0; i--) {
            ChatMessage cm = chatMessageList.get(i);
            if (/* found || */Integer.parseInt(cm.id) >= idInt) {
                // found = true;
                chatMessageList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void insert(ChatMessage object, int pos) {
        if (object.timestamp.equalsIgnoreCase("-1")
                && object.message.equalsIgnoreCase("typing...")) {
            chatMessageList.add(object);
            typingPos = chatMessageList.size() - 1;
        } else {
            if (typingPos != -1) {
                try {
                    chatMessageList.remove(typingPos);
                }catch (Exception e){
                    e.printStackTrace();
                }
                typingPos = -1;
            }
            if (pos != -1) {
                chatMessageList.remove(pos);
                chatMessageList.add(pos, object);
                super.insert(object, pos);
            } else {
                chatMessageList.add(object);
                super.add(object);
            }
        }
        notifyDataSetChanged();
    }

    public int getQPosition(int id) {
        int idInt = id;// Integer.parseInt(id);
        int q_pos = -1;
        for (int i = chatMessageList.size() - 1; i >= 0; i--) {
            ChatMessage cm = chatMessageList.get(i);
            if (/* found || */Integer.parseInt(cm.id) == idInt) {
                // found = true;
                // chatMessageList.remove(i);
                q_pos = i;
                break;
            }
        }
        return q_pos;
    }

    public ChatArrayAdapter(Context context, int textViewResourceId,
                            MessageEditClickListener mecl) {
        super(context, textViewResourceId);
        this.mecl = mecl;
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ChatMessage cmo = getItem(position);
        LayoutInflater inflater = (LayoutInflater) this.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        holder = new ViewHolder();
        if (cmo.isReport) {
            convertView = inflater.inflate(R.layout.report, parent, false);
            holder.list = (LinearLayout) convertView.findViewById(R.id.list);
            ArrayList<ReportRow> rows = cmo.getRows();
            for (int i = 0; i < rows.size(); i++) {
                ReportRow rr = rows.get(i);
                View vi = inflater.inflate(R.layout.report_row, null);
                ((TextView) vi.findViewById(R.id.title)).setText(rr.getTitle());
                ((TextView) vi.findViewById(R.id.value)).setText(rr.getValue());
                holder.list.addView(vi);
            }
        } else if (cmo.timestamp.equalsIgnoreCase("-1")) {
            convertView = inflater.inflate(R.layout.typing, parent, false);
            ImageView mImageViewFilling = (ImageView) convertView
                    .findViewById(R.id.typing_animation);
            ((AnimationDrawable) mImageViewFilling.getDrawable()).start();
        } else {
            if (cmo.left) {
                convertView = inflater
                        .inflate(R.layout.question, parent, false);
                holder.help = (Button) convertView.findViewById(R.id.chatHelp);
                holder.help.setTag(position);
                holder.help.setOnClickListener(onHelpClick);
                //make help visible always
//                if (holder.help != null) {
//                    if (!cmo.help.equalsIgnoreCase("")
//                            && !cmo.help.equalsIgnoreCase("null")) {
//                        holder.help.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.help.setVisibility(View.GONE);
//                    }
//                }
                //////////
                holder.skip = (Button) convertView.findViewById(R.id.chatSkip);
                holder.skip.setTag(position);
                holder.skip.setOnClickListener(onSkipClick);
                holder.imageView = (ImageView) convertView.findViewById(R.id.img);
                holder.password = (Button) convertView.findViewById(R.id.chatPass);
                holder.password.setTag(position);
                if (cmo.id.equalsIgnoreCase(ChatActivity.QNO_OLD_PASS + "") && ChatActivity.currentQId.equalsIgnoreCase(cmo.id)) {
                    holder.password.setVisibility(View.VISIBLE);
                    holder.password.setOnClickListener(onPassClick);
                } else {
                    holder.password.setVisibility(View.GONE);
                }
            } else {
                convertView = inflater.inflate(R.layout.answer, parent, false);
                holder.editButton = (Button) convertView
                        .findViewById(R.id.editButton);
                holder.editOverButton = (Button) convertView
                        .findViewById(R.id.editOverButton);
                holder.editLayout = (RelativeLayout) convertView
                        .findViewById(R.id.editLayout);
                switch (Integer.parseInt(cmo.id)) {
                    case ChatActivity.QNO_EMAIL_ID:
                    case ChatActivity.QNO_OLD_PASS:
                    case ChatActivity.QNO_NEW_PASS:
                        holder.editLayout.setVisibility(View.GONE);
                        break;

                    default:
                        if (holder.editLayout != null) {
                            holder.editLayout.setTag(position);
                            holder.editOverButton.setTag(position);
                            holder.editLayout.setOnClickListener(onClick);
                            holder.editOverButton.setOnClickListener(onClick);
                        }
                        break;
                }
                if (holder.editLayout != null
                        && ChatActivity.NO_EDIT.contains(Integer
                        .parseInt(cmo.id))) {
                    holder.editLayout.setVisibility(View.GONE);
                }
            }

            holder.tag = cmo.id;
            convertView.setTag(holder);

            holder.timestampText = (TextView) convertView
                    .findViewById(R.id.timestamp);
            if (!cmo.timestamp.equalsIgnoreCase("-1")) {
                holder.timestampText
                        .setText(MyTimeUtils.getTime(cmo.timestamp));
            } else {
                holder.timestampText.setText("");
            }
            holder.chatText = (TextView) convertView.findViewById(R.id.msgr);
            if (cmo.message.contains("https://") && cmo.left && (cmo.id.equalsIgnoreCase("45") || cmo.id.equalsIgnoreCase("46"))) {
                final String[] strArr = cmo.message.split("https:");
                holder.chatText.setText(strArr[0]);
                ImageLoader.getInstance().displayImage("https:" + strArr[1], holder.imageView, SplashActivity.options);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https:" + strArr[1]));
                            context.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                holder.chatText.setText(cmo.message);
            }
            if (!cmo.left && (cmo.id.equalsIgnoreCase(ChatActivity.QNO_OLD_PASS + "") || cmo.id.equalsIgnoreCase(ChatActivity.QNO_NEW_PASS + ""))) {
                holder.chatText.setTransformationMethod(new PasswordTransformationMethod());
            }
            if (holder.help != null) {
                if (!cmo.help.equalsIgnoreCase("")
                        && !cmo.help.equalsIgnoreCase("null")) {
                    holder.help.setVisibility(View.VISIBLE);
                } else {
                    holder.help.setVisibility(View.GONE);
                }
            }
            if (ChatActivity.currentQId.equalsIgnoreCase(cmo.id)) {
                holder.chatText.setTypeface(null, Typeface.BOLD);
//                if (holder.help != null) {
//                    if (!cmo.help.equalsIgnoreCase("")
//                            && !cmo.help.equalsIgnoreCase("null")) {
//                        holder.help.setVisibility(View.VISIBLE);
//                    } else {
//                        holder.help.setVisibility(View.GONE);
//                    }
//                }
                if (holder.skip != null) {
                    if (cmo.canSkip) {
                        holder.skip.setVisibility(View.VISIBLE);
                    } else {
                        holder.skip.setVisibility(View.GONE);
                    }
                }
            } else {
                if (holder.help != null) {
                    holder.help.setVisibility(View.GONE);
                }
                if (holder.skip != null) {
                    holder.skip.setVisibility(View.GONE);
                }
            }
        }
        return convertView;
    }

    private OnClickListener onClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            mecl.onEditClicked(pos, getItem(pos).id);
        }
    };
    private OnClickListener onHelpClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            mecl.onHelpClicked(pos, getItem(pos).id);
        }
    };
    private OnClickListener onSkipClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            mecl.onSkipClicked(pos, getItem(pos).id);
        }
    };
    private OnClickListener onPassClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int pos = (int) v.getTag();
            mecl.onForgotPassClicked(pos, getItem(pos).id);
        }
    };

    private class ViewHolder {
        String tag;
        Button editButton, help, skip, password, editOverButton;
        RelativeLayout editLayout;
        // ImageView brandImage, tickImage;//, receiptImage, noImage;
        TextView chatText, timestampText;
        ImageView imageView;
        LinearLayout list;
    }

    public interface MessageEditClickListener {
        public void onEditClicked(int pos, String id);

        public void onHelpClicked(int pos, String id);

        public void onSkipClicked(int pos, String id);

        public void onForgotPassClicked(int pos, String id);
    }
}
