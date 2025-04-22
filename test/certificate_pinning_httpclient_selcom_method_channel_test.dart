import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:certificate_pinning_httpclient_selcom/certificate_pinning_httpclient_selcom_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelCertificatePinningHttpclientSelcom platform = MethodChannelCertificatePinningHttpclientSelcom();
  const MethodChannel channel = MethodChannel('certificate_pinning_httpclient');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
