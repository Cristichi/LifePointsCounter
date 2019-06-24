package com.cristichi.lifepointscounter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cristichi.lifepointscounter.obj.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ButtonsEditorActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRows;
    private RecyclerViewAdapter recyclerViewAdapter;

    private Button btnAddRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons_editor);

        recyclerViewRows = findViewById(R.id.recyclerViewRows);
        btnAddRow = findViewById(R.id.btnAddRow);

        recyclerViewAdapter = new RecyclerViewAdapter();

        recyclerViewRows.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewRows.setAdapter(recyclerViewAdapter);
        Settings.current.readFromFile();
        recyclerViewAdapter.setValues(Settings.current.buttons);

        btnAddRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewAdapter.addNewRow();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        List<List<Integer>> savedList = new ArrayList<>(recyclerViewAdapter.getItemCount());
        List<RecyclerViewAdapter.RecyclerViewInsideAdapter> list = recyclerViewAdapter.getValues();
        for (RecyclerViewAdapter.RecyclerViewInsideAdapter adapter : list){
            savedList.add(adapter.getValues());
        }
        Settings.current.buttons = savedList;
        if (Settings.current.writeToFile())
            Toast.makeText(this, R.string.buttons_saved, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, R.string.buttons_not_saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String out = "";
        List<RecyclerViewAdapter.RecyclerViewInsideAdapter> list = recyclerViewAdapter.getValues();
        for (RecyclerViewAdapter.RecyclerViewInsideAdapter adapter : list){
            out= out.concat(";");
            for (Integer integer : adapter.getValues()){
                out = out.concat(integer+":");
            }
        }
        outState.putString("saved", out);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String in = savedInstanceState.getString("saved", "");
        StringTokenizer stringTokenizer1 = new StringTokenizer(in, ";");
        List<List<Integer>> dataIn = new ArrayList<>(stringTokenizer1.countTokens());
        while(stringTokenizer1.hasMoreTokens()){
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer1.nextToken(), ":");
            List<Integer> list = new ArrayList<>(stringTokenizer2.countTokens());
            while(stringTokenizer2.hasMoreTokens()){
                try{
                    list.add(Integer.parseInt(stringTokenizer2.nextToken()));
                }catch (NumberFormatException e){
                    e.printStackTrace();
                }
            }
            dataIn.add(list);
        }
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.setValues(dataIn);
        recyclerViewRows.setAdapter(recyclerViewAdapter);
    }

    private static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerViewRow;
        ImageButton btnMoveLeft;
        ImageButton btnDeleteRow;
        ImageButton btnAddValue;
        ImageButton btnMoveRight;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            recyclerViewRow = itemView.findViewById(R.id.recyclerViewRow);
            btnMoveLeft = itemView.findViewById(R.id.btnMoveLeft);
            btnDeleteRow = itemView.findViewById(R.id.btnDeleteRow);
            btnAddValue = itemView.findViewById(R.id.btnAddValue);
            btnMoveRight = itemView.findViewById(R.id.btnMoveRight);
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder>{
        private List<RecyclerViewInsideAdapter> rowsData;

        RecyclerViewAdapter(){
            rowsData = new ArrayList<>();
        }

        public void setValues(List<List<Integer>> data){
            rowsData = new ArrayList<>(data.size());
            for (List<Integer> list : data){
                rowsData.add(new RecyclerViewInsideAdapter(list));
            }
            notifyDataSetChanged();
        }

        public List<RecyclerViewInsideAdapter> getValues() {
            return rowsData;
        }

        public void addNewRow(){
            rowsData.add(new RecyclerViewInsideAdapter());
            notifyDataSetChanged();
        }

        public void clear(){
            rowsData.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return rowsData.size();
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_buttons_editor, viewGroup, false);
            return new RecyclerViewHolder(layoutView);
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {
            holder.recyclerViewRow.setLayoutManager(new LinearLayoutManager(ButtonsEditorActivity.this));
            holder.recyclerViewRow.setAdapter(rowsData.get(position));

            holder.btnMoveLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    if (pos!=0){
                        RecyclerViewInsideAdapter obj = rowsData.remove(pos);
                        rowsData.add(pos-1, obj);
                        notifyDataSetChanged();
                    }
                }
            });

            holder.btnMoveRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getAdapterPosition();
                    if (pos!=rowsData.size()-1){
                        RecyclerViewInsideAdapter obj = rowsData.remove(pos);
                        rowsData.add(pos+1, obj);
                        notifyDataSetChanged();
                    }
                }
            });

            holder.btnDeleteRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = holder.getAdapterPosition();
                    new AlertDialog.Builder(ButtonsEditorActivity.this)
                            .setTitle("TITULO")
                            .setMessage("HOLA")
                            .setCancelable(false)
                            .setPositiveButton("sipe", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    rowsData.remove(pos);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("nope", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.btnAddValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomDialogAsk dialogAsk = new CustomDialogAsk(ButtonsEditorActivity.this, rowsData.get(holder.getAdapterPosition()));
                    dialogAsk.show();
                }
            });
        }

        //RecyclerView Interno
        private class RecyclerViewInsideHolder extends RecyclerView.ViewHolder {
            TextView tvValue;
            ImageButton btnDelete;

            RecyclerViewInsideHolder(View itemView) {
                super(itemView);

                tvValue = itemView.findViewById(R.id.tvValue);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
        }

        private class RecyclerViewInsideAdapter extends RecyclerView.Adapter<RecyclerViewInsideHolder>{
            private List<Integer> values;

            RecyclerViewInsideAdapter(){
                values = new ArrayList<>();
            }

            RecyclerViewInsideAdapter(List<Integer> values){
                this.values = values;
            }

            public void addValue(int value){
                values.add(value);
            }

            public List<Integer> getValues() {
                return values;
            }

            @Override
            public int getItemCount() {
                return values.size();
            }

            @NonNull
            @Override
            public RecyclerViewInsideHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_item_buttons_editor, viewGroup, false);
                return new RecyclerViewInsideHolder(layoutView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerViewInsideHolder holder, int position) {
                holder.tvValue.setText(String.valueOf(values.get(position)));
                holder.btnDelete.setTag(position);
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        values.remove((int)v.getTag());
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    private class CustomDialogAsk extends Dialog {
        private RecyclerViewAdapter.RecyclerViewInsideAdapter adapter;
        private EditText etInput;
        private Button btnCancel;

        public CustomDialogAsk(Activity a, RecyclerViewAdapter.RecyclerViewInsideAdapter adapter) {
            super(a);
            this.adapter = adapter;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_add_value_buttons);

            etInput = findViewById(R.id.etValue);

            btnCancel = findViewById(R.id.btnCancel);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancel();
                }
            });

            Button btnOk = findViewById(R.id.btnOk);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str = etInput.getText().toString().trim();
                    if (str.isEmpty()){
                        return;
                    }
                    try {
                        adapter.addValue(Integer.parseInt(str));
                        dismiss();
                    }catch (NumberFormatException e){
                    }
                }
            });
        }

        @Override
        protected void onStart() {
            super.onStart();

            etInput.requestFocus();
            try{
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etInput, 0);
            }catch (Exception e){
            }
        }
    }
}

