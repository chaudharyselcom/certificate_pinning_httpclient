import 'package:flutter_test/flutter_test.dart';
import 'package:certificate_pinning_httpclient_selcom/certificate_pinning_httpclient_selcom.dart';
import 'package:certificate_pinning_httpclient_selcom/certificate_pinning_httpclient_selcom_platform_interface.dart';
import 'package:certificate_pinning_httpclient_selcom/certificate_pinning_httpclient_selcom_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockCertificatePinningHttpclientSelcomPlatform
    with MockPlatformInterfaceMixin
    implements CertificatePinningHttpclientSelcomPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final CertificatePinningHttpclientSelcomPlatform initialPlatform = CertificatePinningHttpclientSelcomPlatform.instance;

  test('$MethodChannelCertificatePinningHttpclientSelcom is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelCertificatePinningHttpclientSelcom>());
  });

  test('getPlatformVersion', () async {
    CertificatePinningHttpclientSelcom certificatePinningHttpclientSelcomPlugin = CertificatePinningHttpclientSelcom();
    MockCertificatePinningHttpclientSelcomPlatform fakePlatform = MockCertificatePinningHttpclientSelcomPlatform();
    CertificatePinningHttpclientSelcomPlatform.instance = fakePlatform;

    expect(await certificatePinningHttpclientSelcomPlugin.getPlatformVersion(), '42');
  });
}
