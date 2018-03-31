package com.frick.maximilian.coffeetime.status.views.coffeeness;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;

import com.frick.maximilian.coffeetime.R;
import com.frick.maximilian.coffeetime.status.views.StatusView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CoffeenessView extends FrameLayout
      implements StatusView<CoffeenessPresenter>, CoffeenessContract.View {
   @BindView (R.id.action_button_1)
   Button takeButton;
   private CoffeenessPresenter coffeenessPresenter;

   public CoffeenessView(@NonNull Context context) {
      super(context);
   }

   public CoffeenessView(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
   }

   public CoffeenessView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
   }

   @Override
   public int getViewType() {
      return CoffeeStatus.COFFEENESS;
   }

   @Override
   public void setPresenter(CoffeenessPresenter coffeenessPresenter) {
      this.coffeenessPresenter = coffeenessPresenter;
   }

   @Override
   public void showAmountOfCups(Long amountOfCups) {
      takeButton.setText(String.format(Locale.getDefault(), "TAKE (%d)", amountOfCups));
   }

   @Override
   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      coffeenessPresenter.retrieveAmountOfCups();
   }

   @Override
   protected void onFinishInflate() {
      super.onFinishInflate();
      ButterKnife.bind(this);
      setPresenter(new CoffeenessPresenter(this));
   }

   @OnClick (R.id.action_button_1)
   void onTakeClicked() {
      coffeenessPresenter.takeACup();
   }

   @OnClick(R.id.action_button_2)
   void onNewSessionClicked() {
      coffeenessPresenter.resetSession();
   }
}
