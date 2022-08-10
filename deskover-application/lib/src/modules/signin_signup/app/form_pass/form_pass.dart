import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/signin_signup/app/form_pass/form_pass_model.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';


class FormPass extends StatefulWidget {
  String? title;

  Function? press;
  TextEditingController? passtxt;
  final FormFieldValidator? validator;
  final TextInputType? textInputType;
  String? hint;

  FormPass(
      {this.passtxt,
      this.title,
      this.press,
      this.validator,
      this.textInputType,
      this.hint});

  @override
  State<FormPass> createState() => _FormPassState();
}

class _FormPassState extends ViewWidget<FormPass, FormPassModel> {
  Key? key;

  @override
  Widget build(BuildContext context) {
    return Obx(
      () => TextFormField(
        //textAlign: TextAlign.center,
        key: key,
        onChanged: null,
        controller: viewModel.passtxt,
        obscureText: viewModel.isShowPass.value,
        cursorColor: UIColors.brandA,

        decoration: InputDecoration(
          labelText: viewModel.title,
          focusedBorder: OutlineInputBorder(
              borderSide: BorderSide(color: Colors.grey),
              borderRadius: BorderRadius.circular(5.0)),
          labelStyle: TextStyle(color: Colors.black54, fontSize: 14),
          border: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.grey),
          ),
          fillColor: Colors.white,
          suffixIcon: IconButton(
            onPressed: () {
              viewModel.onShowPass();
            },
            icon: Icon(
              !viewModel.isShowPass.value
                  ? Icons.visibility
                  : Icons.visibility_off,
              color: UIColors.brandA,
            ),
            color: Colors.black54,
            iconSize: 20.0,
          ),
        ),
      ),
    );
  }

  @override
  FormPassModel createViewModel() => getIt<FormPassModel>();
}
