// Generated by view binder compiler. Do not edit!
package com.example.xr_phone.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.xr_phone.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityDevModeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final EditText commandToEnter;

  @NonNull
  public final LinearLayout primaryLayout;

  @NonNull
  public final Button sendCommand;

  @NonNull
  public final TextView serverResponse;

  @NonNull
  public final TextView textDevMode;

  private ActivityDevModeBinding(@NonNull ConstraintLayout rootView,
      @NonNull EditText commandToEnter, @NonNull LinearLayout primaryLayout,
      @NonNull Button sendCommand, @NonNull TextView serverResponse,
      @NonNull TextView textDevMode) {
    this.rootView = rootView;
    this.commandToEnter = commandToEnter;
    this.primaryLayout = primaryLayout;
    this.sendCommand = sendCommand;
    this.serverResponse = serverResponse;
    this.textDevMode = textDevMode;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDevModeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDevModeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_dev_mode, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDevModeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.commandToEnter;
      EditText commandToEnter = ViewBindings.findChildViewById(rootView, id);
      if (commandToEnter == null) {
        break missingId;
      }

      id = R.id.primary_layout;
      LinearLayout primaryLayout = ViewBindings.findChildViewById(rootView, id);
      if (primaryLayout == null) {
        break missingId;
      }

      id = R.id.sendCommand;
      Button sendCommand = ViewBindings.findChildViewById(rootView, id);
      if (sendCommand == null) {
        break missingId;
      }

      id = R.id.serverResponse;
      TextView serverResponse = ViewBindings.findChildViewById(rootView, id);
      if (serverResponse == null) {
        break missingId;
      }

      id = R.id.textDevMode;
      TextView textDevMode = ViewBindings.findChildViewById(rootView, id);
      if (textDevMode == null) {
        break missingId;
      }

      return new ActivityDevModeBinding((ConstraintLayout) rootView, commandToEnter, primaryLayout,
          sendCommand, serverResponse, textDevMode);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}