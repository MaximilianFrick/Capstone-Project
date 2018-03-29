package com.frick.maximilian.coffeetime.status;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.status.views.StatusView.CoffeeStatus;

class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder> {
   class ViewHolder extends RecyclerView.ViewHolder {
      ViewHolder(View itemView) {
         super(itemView);
      }
   }

   private int status;

   @Override
   public int getItemCount() {
      return 1;
   }

   @Override
   public int getItemViewType(int position) {
      return status;
   }

   @Override
   public void onBindViewHolder(ViewHolder holder, int position) {

   }

   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      switch (viewType) {
         case CoffeeStatus.PATIENCE:
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.status_patience, parent, false));
         case CoffeeStatus.PREPARATION:
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.status_preparation, parent, false));
         case CoffeeStatus.ASKING:
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.status_asking, parent, false));
         case CoffeeStatus.IDLE:
         default:
            return new ViewHolder(LayoutInflater.from(parent.getContext())
                  .inflate(R.layout.status_emptiness, parent, false));
      }
   }

   void setStatus(int status) {
      this.status = status;
      notifyItemChanged(0);
   }
}
