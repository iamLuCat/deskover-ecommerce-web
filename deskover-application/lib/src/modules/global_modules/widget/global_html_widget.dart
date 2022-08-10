import 'package:flutter/material.dart';
import 'package:flutter_html/flutter_html.dart';
import 'package:flutter_html_all/flutter_html_all.dart';
import 'package:get/get.dart';
import 'package:photo_view/photo_view.dart';

import '../../../core/helper/utils.dart';
import '../../../themes/ui_colors.dart';
import '../app/global_html.dart';
import 'global_image.dart';

class GlobalHtmlWidget extends StatelessWidget {
  final String? data;

  const GlobalHtmlWidget({Key? key, this.data}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Html(
      shrinkWrap: true,
      data: Util.htlmWrapper(data ?? ''),
      onLinkTap: (url, context, attributes, element,) async {
        if ((url ?? '').isEmpty && (element?.outerHtml ?? '').isEmpty) {
          return;
        }
        Get.to(GlobalHtml(url: url, content: element?.outerHtml,));
      },

      style: {
        'table': Style(
          border: Border.all(color: UIColors.border10,),
          padding: EdgeInsets.zero,
          margin: EdgeInsets.zero,
        ),
        'td': Style(
          border: Border.all(color: UIColors.border10),
          padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 4),
        ),
        'th': Style(
          border: Border.all(color: UIColors.border10),
          padding: const EdgeInsets.symmetric(
              vertical: 2, horizontal: 4),
        ),
      },
      customRenders: {
            (context) => context.tree.element?.localName == "table":
        CustomRender.widget(widget: (context, child) {
          return SingleChildScrollView(
            scrollDirection: Axis.horizontal,
            child: tableRender.call().widget!.call(context, child),
          );
        }),
      },
      onImageTap: (url, context, attributes, element,) async {
        Get.to(GlobalImageViewer(url: url,));
      },
    );
  }

}

class GlobalImageViewer extends StatelessWidget {
  final String? url;

  const GlobalImageViewer({Key? key, this.url}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Xem ảnh'),
        backgroundColor: UIColors.white,
      ),
      backgroundColor: Colors.grey.shade200,
      body: PhotoView(
        imageProvider: NetworkImage(url ?? ''),
        loadingBuilder: (context, chunk) {
          return const Center(child: GlobalImage(''));
        },
        backgroundDecoration: const BoxDecoration(
          color: Colors.transparent,
        ),
        errorBuilder: (context, error, stackTrace,) {
          print('>> ImageViewer error: $error $context $stackTrace');
          return const GlobalImage(null);
        },
      ),
    );
  }

}