
import 'dart:async';

import 'package:flutter/services.dart';

class FlutterReboot {
  static const MethodChannel _channel =
      const MethodChannel('flutter_reboot');

  // static Future<String?> get platformVersion async {
  //   final String? version = await _channel.invokeMethod('getPlatformVersion');
  //   return version;
  // }

  static Future<void> rebootApp async {
    await _channel.invokeMethod('reboot_app');
  }

  static Future<void> rebootSystem async {
    await _channel.invokeMethod('reboot_system');
  }

  static Future<bool?> get isRooted async {
    final bool? root = await _channel.invokeMethod('is_rooted');
    return root;
  }
}
