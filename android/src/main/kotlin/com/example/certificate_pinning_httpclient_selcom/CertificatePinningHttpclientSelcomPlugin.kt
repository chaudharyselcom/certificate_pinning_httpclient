package com.example.certificate_pinning_httpclient_selcom
import android.provider.Settings

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import com.scottyab.rootbeer.RootBeer //jailbroken



import android.os.Handler
import android.os.Looper
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.*
import java.net.URL
import java.security.cert.Certificate
import javax.net.ssl.HttpsURLConnection

/** CertificatePinningHttpclientSelcomPlugin */
class CertificatePinningHttpclientSelcomPlugin: FlutterPlugin, MethodChannel.MethodCallHandler {

  private var channel: MethodChannel? = null
  private lateinit var context: Context
  companion object {
    private const val FETCH_CERTIFICATES_TIMEOUT_MS = 3000
    private var initializedConfig: String? = null
  }

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    val messenger: BinaryMessenger = flutterPluginBinding.binaryMessenger
    channel = MethodChannel(
      messenger,
      "certificate_pinning_httpclient",
      StandardMethodCodec.INSTANCE,
      messenger.makeBackgroundTaskQueue()
    )
    channel?.setMethodCallHandler(this)
    context = binding.applicationContext
  }

 // @SuppressLint("NewApi")
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: MethodChannel.Result)  {
    if (call.method == "fetchHostCertificates") {
      try {
        val url = URL(call.argument<String>("url"))
        val connection = url.openConnection() as HttpsURLConnection
        connection.connectTimeout = FETCH_CERTIFICATES_TIMEOUT_MS
        connection.connect()
        val certificates = connection.serverCertificates
        val hostCertificates = certificates.map { it.encoded }
        connection.disconnect()
        result.success(hostCertificates)
      } catch (e: Exception) {
        result.error("fetchHostCertificates", e.localizedMessage, null)
      }
    }
    else if(call.method == "jailbroken") {
      try {
      val rootBeer = true;//RootBeer(context)
      result.success(rootBeer.isRooted)
      } catch (e: Exception) {
        result.error("jailbroken", e.localizedMessage, null)
      }
    } else if(call.method == "developerMode") {
      try {
      result.success(isDevMode())
      } catch (e: Exception) {
        result.error("developerMode", e.localizedMessage, null)
      }
    }
    else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel?.setMethodCallHandler(null)
    channel = null
  }


  private fun isDevMode(): Boolean {
    return Settings.Secure.getInt(
      context.contentResolver,
      Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0
    ) != 0
  }
}