import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

import '../../../config/assets/image_asset.dart';
import '../../../themes/space_values.dart';
import '../../../themes/ui_colors.dart';


class GlobalConfigurationScreen extends StatelessWidget {
  final String? textContent;
  final Widget? widgetContent;
  final String? textTitle;
  final String? textButton;
  final VoidCallback? pressButton;

  const GlobalConfigurationScreen(
      {Key? key,
      this.textContent,
      this.textTitle,
      this.textButton,
      this.pressButton,
      this.widgetContent,})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: UIColors.white,
        title: Text(textTitle ?? 'Đã hoàn thành'),
        centerTitle: true,
        // leading: const SizedBox.shrink(),
      ),
      body: Column(
        children: [
          Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              const SizedBox(
                height: SpaceValues.space48,
              ),
              SvgPicture.asset(SvgImageAssets.imgConfiguration),
              const SizedBox(
                height: SpaceValues.space32,
              ),
              Center(
                child: Visibility(
                  visible: widgetContent != null,
                  child: widgetContent ?? const SizedBox.shrink(),
                  replacement: Text(
                    textContent ?? 'Chúc mừng bạn\nđã thiết lập thành công',
                    style: Theme.of(context).textTheme.headline1,
                    textAlign: TextAlign.center,
                  ),
                ),
              )
            ],
          ),
          const Expanded(child: SizedBox.shrink()),
          Container(
            color: UIColors.white,
            width: double.infinity,
            padding: const EdgeInsets.all(SpaceValues.space16),
            child: ElevatedButton(
              onPressed: pressButton,
              child: Text(textButton ?? 'Tiếp tục'),
            ),
          ),
        ],
      ),
    );
  }
}
