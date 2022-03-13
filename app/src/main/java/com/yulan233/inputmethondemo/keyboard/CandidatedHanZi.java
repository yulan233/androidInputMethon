package com.yulan233.inputmethondemo.keyboard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yulan233.inputmethondemo.R;
import com.yulan233.inputmethondemo.chineseEngine.engineLib.searchedHanZi;

import java.util.List;

public class CandidatedHanZi extends RecyclerView.Adapter<CandidatedHanZi.HanZiViewHolder> {
    private List<searchedHanZi> data;
    private Context context;
    private KeyboardView mkeyboardView;
    public CandidatedHanZi(List<searchedHanZi> data, Context context,KeyboardView mkeyboardView) {
        this.data = data;
        this.context = context;
        this.mkeyboardView=mkeyboardView;
    }
    @NonNull
    @Override
    public HanZiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_list_item,null);
        return new HanZiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HanZiViewHolder holder, int position) {
        holder.hanzi.setText(data.get(position).getHanzi());
        holder.hanzi.setOnClickListener(mkeyboardView);
    }

    @Override
    public int getItemCount() {
        return data!=null ? data.size() :0;
    }

    public class HanZiViewHolder extends RecyclerView.ViewHolder {
        private TextView hanzi;
        public HanZiViewHolder(@NonNull View itemView) {
            super(itemView);
            hanzi= itemView.findViewById(R.id.hanzi);
//            imageV = itemView.findViewById(R.id.imageViewSrc);
        }
    }
}
