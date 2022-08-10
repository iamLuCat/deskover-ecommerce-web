import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class FormPassModel extends ViewModel {
  String? title;
  String? hint;
  TextEditingController? passtxt;
  RxBool isShowPass = true.obs;
  onShowPass() {
    isShowPass.value = !isShowPass.value;
  }
}
