import 'package:flutter/material.dart';

import 'package:pin_code_fields/pin_code_fields.dart';

import '../../../themes/space_values.dart';
import '../../../themes/ui_colors.dart';

class GlobalOTPScreen extends StatelessWidget {
  final VoidCallback? reSend;
  final ValueChanged<String>? onCompleted;
  final TextEditingController _textEditingController = TextEditingController();

  GlobalOTPScreen({Key? key, this.reSend, this.onCompleted}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: UIColors.white,
        title: const Text('Xác nhận OTP'),
      ),
      body: Center(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(SpaceValues.space24),
              child: Text(
                'Nhập mã xác thực',
                style: Theme.of(context).textTheme.headline1?.merge(
                  const TextStyle(color: UIColors.title),
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(SpaceValues.space16),
              child: PinCodeTextField(
                autoFocus: true,
                enablePinAutofill: true,
                controller: _textEditingController,
                appContext: context,
                length: 6,
                onCompleted: onCompleted,
                onChanged: (String value) {},
                animationType: AnimationType.scale,
                keyboardType: TextInputType.number,
                pinTheme: PinTheme(
                  shape: PinCodeFieldShape.box,
                  selectedColor: UIColors.navSelected,
                  inactiveColor: UIColors.navSelected,
                  activeColor: UIColors.navSelected,
                  borderRadius: BorderRadius.circular(SpaceValues.space4),
                  borderWidth: 1,
                  fieldHeight: 50,
                  fieldWidth: 50,
                ),
              ),
            ),
            Row(mainAxisSize: MainAxisSize.min, children: [
              const Text('Không nhận được?'),
              TextButton(onPressed: reSend, child: const Text('Gửi lại')),
            ])
          ],
        ),
      ),
    );
  }
}
