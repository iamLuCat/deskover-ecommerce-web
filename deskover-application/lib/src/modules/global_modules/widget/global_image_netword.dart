import 'package:cached_network_image/cached_network_image.dart';
import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:flutter/material.dart';


class GlobalImageNetWord extends StatelessWidget {
  final String? url;
  final BoxFit? fit;
  final double? width;
  final double? height;
  final String placeholder;
  final Size? sizeServer;
  final bool isScale;

  const GlobalImageNetWord(
      this.url, {
        Key? key,
        this.sizeServer,
        this.fit,
        this.width,
        this.height,
        this.placeholder = ImageAssets.imgPlaceholder,
        this.isScale = true,
      }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    String urlX;
    if (url == null) {
      urlX =
      'https://www.kindpng.com/picc/m/20-200041_website-clipart-mobile-friendly-computer-phone-tablet-icon.png';
    } else {
      urlX = url!;
    }

    String imageUrl = isScale
        ? '$urlX?width=${sizeServer?.width.ceil() ?? width?.ceil() ??
        ''}&height=${sizeServer?.height.ceil() ?? height?.ceil() ?? ''}'
        : urlX;
    return Image.network(
      imageUrl,
      fit: fit,
      width: width,
      height: height,
      errorBuilder: (BuildContext context, Object exception,
          StackTrace? stackTrace) {
        return Image.asset(
          ImageAssets.imgPlaceholder,
          fit: BoxFit.cover,
        );
      },
    );
  }
}
