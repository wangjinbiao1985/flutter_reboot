package com.wangjinbiao.flutter_reboot;
import android.content.Context;
import androidx.annotation.NonNull;
import com.jakewharton.processphoenix.ProcessPhoenix;
import eu.chainfire.libsuperuser.Shell;
import java.io.IOException;
import java.util.List;

import android.util.Log;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** FlutterRebootPlugin */
public class FlutterRebootPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Context context;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    context = flutterPluginBinding.getApplicationContext();
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "flutter_reboot");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("reboot_app")) {
      ProcessPhoenix.triggerRebirth(context);
    } else {
      result.notImplemented();
    }
    if (call.method.equals("reboot_system")) {
      reboot();
    } else {
      result.notImplemented();
    }
    if (call.method.equals("is_rooted")) {
      result.success(isDeviceRooted());
    } else {
      result.notImplemented();
    }
//    if (call.method.equals("getPlatformVersion")) {
//      result.success("Android " + android.os.Build.VERSION.RELEASE);
//    } else {
//      result.notImplemented();
//    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  public void reboot() {
    runAsRoot("reboot");
  }

  public List<String> runAsRoot(String command) {
    return Shell.SU.run(command);
  }

  public boolean isDeviceRooted() {
    return Shell.SU.available();
  }
}
