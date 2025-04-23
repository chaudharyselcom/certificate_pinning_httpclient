import 'dart:async';

import 'package:flutter/services.dart';

class FlutterJailbreakDetection {
  static const MethodChannel _channel = MethodChannel(
    "certificate_pinning_httpclient",
  );

  static Future<bool> get jailbroken async {
    bool? jailbroken = await _channel.invokeMethod("jailbroken");
    return jailbroken ?? true;
  }

  static Future<bool> get developerMode async {
    bool? developerMode = await _channel.invokeMethod('developerMode');
    return developerMode ?? true;
  }
}
