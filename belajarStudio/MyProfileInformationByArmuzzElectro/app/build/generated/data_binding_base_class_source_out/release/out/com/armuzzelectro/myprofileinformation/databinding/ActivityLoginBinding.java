// Generated by view binder compiler. Do not edit!
package com.armuzzelectro.myprofileinformation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.armuzzelectro.myprofileinformation.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView bgImage;

  @NonNull
  public final ImageView btnBackWelcome;

  @NonNull
  public final Button btnLogin;

  @NonNull
  public final ImageView btnViewPassword;

  @NonNull
  public final ConstraintLayout constraintLayout;

  @NonNull
  public final EditText inputEmail;

  @NonNull
  public final EditText inputPassword;

  @NonNull
  public final TextView txtGoToSignUp;

  @NonNull
  public final TextView txtSignUpNow;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView bgImage,
      @NonNull ImageView btnBackWelcome, @NonNull Button btnLogin,
      @NonNull ImageView btnViewPassword, @NonNull ConstraintLayout constraintLayout,
      @NonNull EditText inputEmail, @NonNull EditText inputPassword,
      @NonNull TextView txtGoToSignUp, @NonNull TextView txtSignUpNow) {
    this.rootView = rootView;
    this.bgImage = bgImage;
    this.btnBackWelcome = btnBackWelcome;
    this.btnLogin = btnLogin;
    this.btnViewPassword = btnViewPassword;
    this.constraintLayout = constraintLayout;
    this.inputEmail = inputEmail;
    this.inputPassword = inputPassword;
    this.txtGoToSignUp = txtGoToSignUp;
    this.txtSignUpNow = txtSignUpNow;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bgImage;
      ImageView bgImage = ViewBindings.findChildViewById(rootView, id);
      if (bgImage == null) {
        break missingId;
      }

      id = R.id.btnBackWelcome;
      ImageView btnBackWelcome = ViewBindings.findChildViewById(rootView, id);
      if (btnBackWelcome == null) {
        break missingId;
      }

      id = R.id.btnLogin;
      Button btnLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin == null) {
        break missingId;
      }

      id = R.id.btnViewPassword;
      ImageView btnViewPassword = ViewBindings.findChildViewById(rootView, id);
      if (btnViewPassword == null) {
        break missingId;
      }

      ConstraintLayout constraintLayout = (ConstraintLayout) rootView;

      id = R.id.inputEmail;
      EditText inputEmail = ViewBindings.findChildViewById(rootView, id);
      if (inputEmail == null) {
        break missingId;
      }

      id = R.id.inputPassword;
      EditText inputPassword = ViewBindings.findChildViewById(rootView, id);
      if (inputPassword == null) {
        break missingId;
      }

      id = R.id.txtGoToSignUp;
      TextView txtGoToSignUp = ViewBindings.findChildViewById(rootView, id);
      if (txtGoToSignUp == null) {
        break missingId;
      }

      id = R.id.txtSignUpNow;
      TextView txtSignUpNow = ViewBindings.findChildViewById(rootView, id);
      if (txtSignUpNow == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, bgImage, btnBackWelcome,
          btnLogin, btnViewPassword, constraintLayout, inputEmail, inputPassword, txtGoToSignUp,
          txtSignUpNow);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
