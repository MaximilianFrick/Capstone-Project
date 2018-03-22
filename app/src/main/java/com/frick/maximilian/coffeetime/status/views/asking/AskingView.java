package com.frick.maximilian.coffeetime.status.views.asking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.frick.maximilian.coffeetime.status.views.StatusView;

import butterknife.ButterKnife;

public class AskingView extends FrameLayout implements StatusView<AskingPresenter> {
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
      setPresenter(new AskingPresenter());
   }
}
