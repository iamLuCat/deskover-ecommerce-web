import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_inappwebview/flutter_inappwebview.dart';
import 'package:get/get.dart';

import '../../../config/injection_config.dart';
import '../../../core/helper/utils.dart';
import '../../../themes/dialogs/loading_dialog.dart';
import '../../../themes/ui_colors.dart';

class GlobalHtml extends StatelessWidget {
  const GlobalHtml({Key? key, this.url, this.content, this.header}) : super(key: key);

  final String? url;
  final String? content;
  final String? header;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(header ?? ''),
        backgroundColor: UIColors.white,
      ),
      backgroundColor: UIColors.white,
      body: InAppWebView(
        onConsoleMessage: kDebugMode ? (controller, consoleMessage) {
          print('>> htmllog: $consoleMessage');
        } : null,
        onLoadStart: (controller, uri) {
          if (!(Get.isDialogOpen ?? false)) {
            Get.dialog(const LoadingDialog(
              message: 'Đang tải dữ liệu',
            ), barrierDismissible: false);
          }
        },
        onLoadStop: (controller, uri) {
          if (Get.isDialogOpen ?? false) {
            Get.back();
          }
        },
        initialOptions: InAppWebViewGroupOptions(
          android: AndroidInAppWebViewOptions(
            useHybridComposition: true,
          ),
          ios: IOSInAppWebViewOptions(
            allowsInlineMediaPlayback: true,
          )
        ),
        onWebViewCreated: (controller) {
          if ((url ?? '').isNotEmpty) {
            controller.loadUrl(
              urlRequest: URLRequest(
                url: Uri.parse(url ?? ''),
                headers: Map<String, String>.from(getIt<Dio>().options.headers),
              ),
            );
            return;
          }
          if ((content ?? '').isNotEmpty) {
            controller.loadData(
              data: Util.htlmWrapper(content ?? ''),
            );
            return;
          }
        },
      ),
      // WebViewPlus(
      //   javascriptMode: JavascriptMode.unrestricted,
      //   // initialUrl: url,
      //   onWebViewCreated: (webviewcreate) {
      //     if ('' != (url ?? '')) {
      //       webviewcreate.loadUrl(url!, headers: Map<String, String>.from(getIt<Dio>().options.headers));
      //       return;
      //     }
      //     if ('' != (content ?? '')) {
      //       webviewcreate.loadString(Util.htlmWrapper(content!));
      //       return;
      //     }
      //   },
      // ),
    );
  }

}