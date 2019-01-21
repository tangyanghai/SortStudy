package com.tyh.java.lib_base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.Holder> {

    public List<T> datas = new ArrayList<>();

    public BaseAdapter(List<T> datas) {
        this.datas.addAll(datas);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false);
        return new Holder(view);
    }

    /**
     * 条目的layoutId
     */
    public abstract int getItemLayout(int viewType);

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setDatas(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public void add(T data) {
        if (data != null) {
            datas.add(data);
            notifyItemInserted(datas.size() - 1);
        }
    }

    public void remove(T data) {
        if (data != null) {
            int position = datas.indexOf(data);
            if (position < 0) {
                return;
            }
            boolean remove = datas.remove(data);
            if (remove) {
                notifyItemRemoved(position);
            }
        }
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);

        }

        public <V extends View> V findViewById(int id) {
            return itemView.findViewById(id);
        }

    }
}
