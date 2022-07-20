import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../../component/widget/global_input_form_widget.dart';
import '../../../config/injection_config.dart';
import '../../../themes/space_values.dart';
import '../../../themes/ui_colors.dart';
import '../../../utils/widgets/view_widget.dart';
import 'change_password_model.dart';

class ChangePasswordScreen extends StatefulWidget {
  ChangePasswordScreen({Key? key}) : super(key: key);

  @override
  State<ChangePasswordScreen> createState() => _ChangePasswordScreenState();
}

class _ChangePasswordScreenState
    extends ViewWidget<ChangePasswordScreen, ChangepasswordModel> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title:const  Text("Thay đổi mật khẩu"),
        backgroundColor: UIColors.white,
        shape: const Border(bottom: BorderSide(color: UIColors.border10)),
      ),
      body: Column(
        children: [
          Expanded(
            child: SingleChildScrollView(
              child: Form(
                key: viewModel.formKey,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      color: UIColors.white,
                      margin: EdgeInsets.only(top: 6),
                      padding: EdgeInsets.only(bottom: 32),
                      child: Column(
                        children: [
                          SizedBox(
                            height: 10,
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: SpaceValues.space32,
                            ),
                            child: GlobalInputFormWidget(
                              title: "Mật khẩu cũ",
                              controller: viewModel.inputPassword,
                              validator: Validator.passwordEasy,
                              textInputType: TextInputType.visiblePassword,
                              // title: 'Số điện thoại',
                              hint: 'Nhập mật khẩu',
                              security: true,
                              requireInput: '*',
                            ),
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: SpaceValues.space32,
                            ),
                            child: GlobalInputFormWidget(
                              title: "Mật khẩu mới",
                              controller: viewModel.inputPasswordnew,
                              validator: Validator.password,
                              textInputType: TextInputType.visiblePassword,
                              // title: 'Số điện thoại',
                              hint: 'Nhập mật khẩu',
                              security: true,
                              requireInput: '*',
                            ),
                          ),
                          SizedBox(
                            height: 10,
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: SpaceValues.space32,
                            ),
                            child: GlobalInputFormWidget(
                              title: "Nhập lại Mật khẩu mới",
                              controller: viewModel.inputPasswordconfirm,
                              validator:  (valueDy) => Validator.rePassword(valueDy, viewModel.inputPasswordnew.text),
                              textInputType: TextInputType.visiblePassword,
                              // title: 'Số điện thoại',
                              hint: 'Nhập mật khẩu',
                              security: true,
                              requireInput: '*',
                            ),
                          ),
                        ],
                      ),
                    ),

                  ],
                ),
              ),
            ),
          ),
          Container(
            width: double.infinity,
            padding: const EdgeInsets.all(16),
            color: UIColors.white,
            child: ElevatedButton(
              style: ElevatedButton.styleFrom(
                  primary: Color(0xB2000000),
                  minimumSize: const Size(0, 48)),
              onPressed: () {
                viewModel.changePassword();
              },
              child: const Text(
                "Xác nhận",
                style: TextStyle(fontSize: 16),
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  ChangepasswordModel createViewModel() => getIt<ChangepasswordModel>();
}
