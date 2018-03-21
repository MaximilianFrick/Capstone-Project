package com.frick.maximilian.coffeetime.status.views.emptiness;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.status.views.StatusView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptinessView extends FrameLayout implements StatusView<EmptinessPresenter> {

   private EmptinessPresenter presenter;

   public EmptinessView(@NonNull Context context) {
      super(context);
      init();
   }

   public EmptinessView(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
      init();
   }

   public EmptinessView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      init();
   }

   @Override
   public int getViewType() {
      return CoffeeStatus.IDLE;
   }

   @Override
   public void setPresenter(EmptinessPresenter presenter) {
      this.presenter = presenter;
   }

   @Override
   protected void onFinishInflate() {
      super.onFinishInflate();
      ButterKnife.bind(this);
   }

   @OnClick (R.id.action_button_1)
   void onAskClicked() {
      presenter.askGroupForRoundOfCoffee();
   }

   private void init() {
      setPresenter(new EmptinessPresenter());
   }
}
