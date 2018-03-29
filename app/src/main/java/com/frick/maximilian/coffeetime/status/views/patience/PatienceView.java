package com.frick.maximilian.coffeetime.status.views.patience;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.status.views.StatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatienceView extends FrameLayout
      implements StatusView<PatiencePresenter>, PatienceContract.View {
   @BindView (R.id.progress_bar)
   ProgressBar progressBar;
   @BindView (R.id.remaining_time)
   TextView remainingTimeView;
   private PatiencePresenter patiencePresenter;

   public PatienceView(@NonNull Context context) {
      super(context);
   }

   public PatienceView(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public PatienceView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   public void displayRemainingTime(RemainingTimeViewModel remainingTime) {
      progressBar.setProgress(remainingTime.getPercantage());
      remainingTimeView.setText(remainingTime.getDisplayedText());
   }

   @Override
   public int getViewType() {
      return CoffeeStatus.PATIENCE;
   }

   @Override
   public void setPresenter(PatiencePresenter patiencePresenter) {
      this.patiencePresenter = patiencePresenter;
   }

   @Override
   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      patiencePresenter.unbind();
   }

   @Override
   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      patiencePresenter.listenToRemainingTime();
   }

   @OnClick(R.id.action_button_1)
   void onDoneClicked() {
      patiencePresenter.loadCoffeenessStatus();
   }

   @Override
   protected void onFinishInflate() {
      super.onFinishInflate();
      if (!isInEditMode()) {
         ButterKnife.bind(this);
         setPresenter(new PatiencePresenter(this));
      }
   }
}
