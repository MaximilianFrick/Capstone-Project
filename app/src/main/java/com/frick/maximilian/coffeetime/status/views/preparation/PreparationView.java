package com.frick.maximilian.coffeetime.status.views.preparation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.status.views.StatusView;
import com.frick.maximilian.coffeetime.status.views.asking.PreparationViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreparationView extends FrameLayout
      implements StatusView<PreparationPresenter>, PreparationContract.View {

   @BindView (R.id.coffee_amount)
   TextView coffeeAmountView;
   @BindView (R.id.cups_amount)
   TextView cupsAmountView;
   @BindView (R.id.timer)
   TextView timerView;
   @BindView (R.id.water_amount)
   TextView waterAmountView;
   private PreparationPresenter preparationPresenter;

   public PreparationView(@NonNull Context context) {
      super(context);
   }

   public PreparationView(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public PreparationView(@NonNull Context context, @Nullable AttributeSet attrs,
         int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   public void displayPreparationInformation(PreparationViewModel data) {
      if (Integer.valueOf(data.getCups()) == 0) {
         Toast.makeText(getContext(), "Add at least one cup!", Toast.LENGTH_SHORT)
               .show();
         preparationPresenter.setAskingStatus();
         return;
      }
      coffeeAmountView.setText(data.getCoffeeAmount());
      cupsAmountView.setText(data.getCups());
      waterAmountView.setText(data.getWaterAmount());
      timerView.setText(data.getTimer());
   }

   @Override
   public int getViewType() {
      return CoffeeStatus.PREPARATION;
   }

   @Override
   public void setPresenter(PreparationPresenter preparationPresenter) {
      this.preparationPresenter = preparationPresenter;
   }

   @Override
   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      preparationPresenter.getAmountOfCups();
   }

   @Override
   protected void onFinishInflate() {
      super.onFinishInflate();
      ButterKnife.bind(this);
      setPresenter(new PreparationPresenter(this));
   }

   @OnClick (R.id.action_button_1)
   void onStartClicked() {
      preparationPresenter.setPatienceStatus();
   }
}
