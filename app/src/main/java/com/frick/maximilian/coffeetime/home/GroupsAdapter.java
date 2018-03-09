package com.frick.maximilian.coffeetime.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.models.Group;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsAdapter extends FirebaseRecyclerAdapter<Group, GroupsAdapter.GroupViewHolder> {
   class GroupViewHolder extends RecyclerView.ViewHolder {

      @BindView (R.id.name)
      TextView name;

      GroupViewHolder(View itemView) {
         super(itemView);
         ButterKnife.bind(this, itemView);
      }
   }

   private final GroupSelectedListener listener;

   /**
    * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
    * {@link FirebaseRecyclerOptions} for configuration options.
    */
   GroupsAdapter(@NonNull FirebaseRecyclerOptions<Group> options, GroupSelectedListener listener) {
      super(options);
      this.listener = listener;
   }

   @Override
   public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      return new GroupViewHolder(inflater.inflate(R.layout.group_item, parent, false));
   }

   @Override
   protected void onBindViewHolder(@NonNull GroupViewHolder holder, int position,
         @NonNull final Group model) {
      holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            listener.onGroupClicked(model);
         }
      });
      holder.name.setText(model.getName());
   }

}
