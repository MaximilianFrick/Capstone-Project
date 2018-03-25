package com.frick.maximilian.coffeetime.status.views.asking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.status.views.StatusView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AskingView extends FrameLayout
      implements StatusView<AskingPresenter>, AskingContract.View {
   @BindView (R.id.amount_cups)
   TextView amountOfCupsView;
   private AskingPresenter askingPresenter;

   public AskingView(@NonNull Context context) {
      super(context);
   }

   public AskingView(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public AskingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   public void displayAmountOfCups(long cupDrinkerAmount) {
      amountOfCupsView.setText(String.valueOf(cupDrinkerAmount));
   }

   @Override
   public int getViewType() {
      return CoffeeStatus.ASKING;
   }

   @Override
   public void setPresenter(AskingPresenter askingPresenter) {
      this.askingPresenter = askingPresenter;
   }

   @Override
   protected void onFinishInflate() {
      super.onFinishInflate();
      ButterKnife.bind(this);
      setPresenter(new AskingPresenter(this));
      askingPresenter.listenToNumberOfDrinkers();
   }

   @OnClick (R.id.action_button_1)
   void onAddCupClicked() {
      askingPresenter.addCupDrinker();
   }
}
