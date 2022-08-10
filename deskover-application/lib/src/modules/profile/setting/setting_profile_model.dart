import 'dart:io';

import 'package:deskover_develop/src/apis/user/response/user_response.dart';
import 'package:deskover_develop/src/usecases/user_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:image_picker/image_picker.dart';
import 'package:injectable/injectable.dart';

@injectable
class SettingProfileModel extends ViewModel{
  final UserUserCase _userUserCase;

  final Rx<XFile?> imgUpload = Rx<XFile?>(null);

  final TextEditingController fullname = TextEditingController();
  final TextEditingController phone = TextEditingController();
  final TextEditingController address = TextEditingController();
  final TextEditingController email = TextEditingController();

  final TextEditingController role_name = TextEditingController();
  final TextEditingController ranking_name = TextEditingController();
  final TextEditingController id = TextEditingController();
  final GlobalKey<FormState> formKey = GlobalKey<FormState>();

  Rxn<User> user = Rxn();

  SettingProfileModel(this._userUserCase);

  @override
   initState() {
    loading(() async {
      await Future.wait([
       loadProfiled(),
      ]);
    });


  }
  Future<void> loadProfiled()async {
    await _userUserCase.doGetProfiled().then((value) async{
          user.value = value;
          fullname.text = value.fullname!;
          phone.text = value.phone!;
          email.text = value.email!;
          id.text = 'CUSTOMER${value.id}';
          print( user.value?.avatar);
    });
  }

  void updateProfile() async{
    loading(() async{
      if((imgUpload.value?.path ?? '').isNotEmpty){
        await _userUserCase.doPostUploadFile(File(imgUpload.value?.path ?? ''));
        if ((imgUpload.value?.path.isEmpty ?? false)) {
          throw 'Có lỗi khi upload hình ảnh';
        }
        await _userUserCase.doGetProfiled().then((value) async{
              user.value = value;
        });
        User? users = user.value;
        users?.fullname = fullname.text;
        users?.email = email.text;
        await _userUserCase.doPutUpdate(users!);
      }else{
        User? users = user.value;
        users?.fullname = fullname.text;
        users?.email = email.text;
        await _userUserCase.doPutUpdate(users!);
      }

    });


  }

}