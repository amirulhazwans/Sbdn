package com.example.sbdn;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RequestListAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> requestList;

    public RequestListAdapter(Context context, ArrayList<String> requests) {
        super(context, R.layout.list_item_request, requests);
        this.context = context;
        this.requestList = requests;
    }

    private static class ViewHolder {
        TextView requestInfoTextView;
        Button cancelRequestButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_request, parent, false);
            holder = new ViewHolder();
            holder.requestInfoTextView = convertView.findViewById(R.id.requestInfoTextView);
            holder.cancelRequestButton = convertView.findViewById(R.id.cancelRequestButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String request = requestList.get(position);
        holder.requestInfoTextView.setText(request);

        holder.requestInfoTextView.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setTitle("Request Action")
                    .setMessage("What would you like to do with this request?")
                    .setPositiveButton("Still Waiting", (dialog, which) -> {
                        Toast.makeText(context, "Marked as still waiting", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .setNegativeButton("Delete", (dialog, which) -> {
                        requestList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Request deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    })
                    .setNeutralButton("Cancel", null)
                    .show();
        });

        holder.cancelRequestButton.setOnClickListener(view -> {
            requestList.remove(position);
            notifyDataSetChanged();
            Toast.makeText(context, "Request canceled", Toast.LENGTH_SHORT).show();
        });

        return convertView;
    }
}
