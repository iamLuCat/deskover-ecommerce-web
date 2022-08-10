import 'dart:io';
import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_input_form_widget.dart';
import 'package:deskover_develop/src/modules/profile/setting/password/change_password.dart';
import 'package:deskover_develop/src/modules/profile/setting/setting_profile_model.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:image_picker/image_picker.dart';

class SettingProfile extends StatefulWidget {
  const SettingProfile({Key? key}) : super(key: key);

  @override
  State<SettingProfile> createState() => _SettingProfile();
}

class _SettingProfile extends ViewWidget<SettingProfile, SettingProfileModel> {
  bool _isVisible = true;
  void showToast() {
    setState(() {
      _isVisible = !_isVisible;
    });
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Thông tin cá nhân",
            style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16)),
        backgroundColor: UIColors.white,
        shape: const Border(bottom: BorderSide(color: UIColors.border10)),
      ),
      // backgroundColor: UIColors.white,
      body: RefreshIndicator(
        onRefresh: ()=> viewModel.loadProfiled(),
        child: SingleChildScrollView(
          child: Column(
            children: [
              Container(
                color:UIColors.white,
                child: Padding(
                  padding: const EdgeInsets.all(SpaceValues.space16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      InkWell(
                        onTap: () {
                          ImagePicker ip = ImagePicker();
                          ip.pickImage(source: ImageSource.gallery).then((value) {
                            viewModel.imgUpload.value = value;
                          });
                        },
                        child: Obx(
                              () => Visibility(
                            visible: null != viewModel.imgUpload.value?.path,
                            child: Center(
                              child: Stack(
                                children: <Widget>[
                                  CircleAvatar(
                                    backgroundColor: UIColors.black,
                                    radius: 60,
                                    child: Padding(
                                      padding: const EdgeInsets.all(5),
                                      child: ClipOval(
                                        child: Image.file(
                                          File(viewModel.imgUpload.value?.path ?? ''),
                                          fit: BoxFit.cover,
                                          width: 120,
                                          height: 120,
                                        ),
                                      ),
                                    ),
                                  ),
                                  const Positioned(
                                      bottom: 0,
                                      right: 0,
                                      child: CircleAvatar(
                                        backgroundColor: UIColors.white,
                                      )),
                                  const Positioned(
                                      bottom: 8,
                                      right: 8,
                                      child: Icon(
                                        Icons.camera_alt,
                                        color: UIColors.black,
                                      ))
                                ],
                              ),
                            ),
                            replacement: Center(
                              child: Stack(
                                children: <Widget>[
                                  Visibility(
                                    // visible:viewModel.avartar.isNotEmpty ,
                                    child: CircleAvatar(
                                      backgroundColor: Colors.grey,
                                      radius: 60,
                                      child: Padding(
                                        padding: const EdgeInsets.all(3.0),
                                        child: ClipOval(
                                          child: Image.network(
                                            BaseApi.baseUrlUser+'${viewModel.user.value?.avatar}',
                                            fit: BoxFit.cover,
                                            width: 120,
                                            height: 120,
                                            errorBuilder: (BuildContext context, Object exception,
                                                StackTrace? stackTrace) {
                                              return Image.asset(
                                                ImageAssets.imgPlaceholder,
                                                fit: BoxFit.cover,
                                              );
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                  ),
                                  const Positioned(
                                      bottom: 0,
                                      right: 0,
                                      child: CircleAvatar(
                                        backgroundColor: UIColors.white,
                                      )),
                                  const Positioned(
                                      bottom: 8,
                                      right: 8,
                                      child: Icon(
                                        Icons.camera_alt,
                                        color: UIColors.black,
                                      ))
                                ],
                              ),
                            ),
                          ),
                        ),
                      ),
                      /////////////////////////////////
                      const SizedBox(height: SpaceValues.space24),
                      Form(
                          key: viewModel.formKey,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: [
                              GlobalInputFormWidget(
                                controller: viewModel.fullname,
                                title: 'Tên',
                                hint: 'Nguyễn Văn A',
                                textInputType: TextInputType.name,
                                requireInput: '',
                                validator: Validator.fullnameCanEmpty,
                                suffixIcon: const Icon(
                                  Icons.drive_file_rename_outline,
                                ),
                              ),
                              const SizedBox(height: SpaceValues.space24),
                              GlobalInputFormWidget(
                                readOnly: true,
                                controller: viewModel.role_name,
                                requireInput: '',
                                title: 'Loại tài khoản',
                                hint: '',
                              ),
                              const SizedBox(height: SpaceValues.space24),
                              GlobalInputFormWidget(
                                readOnly: true,
                                controller: viewModel.id,
                                requireInput: '',
                                title: 'ID',
                                hint: 'id...',
                                textInputType: TextInputType.phone,
                              ),
                              const SizedBox(height: SpaceValues.space24),
                              GlobalInputFormWidget(
                                readOnly: true,
                                controller: viewModel.ranking_name,
                                requireInput: '',
                                title: 'Hạng thành viên',
                                hint: '',
                                textInputType: TextInputType.phone,
                              ),
                              // const SizedBox(height: SpaceValues.space24),
                              // RichText(
                              //   text: TextSpan(
                              //     text: "Giới tính",
                              //     style: Theme.of(context).textTheme.headline6,
                              //   ),
                              // ),
                              // const SizedBox(height: SpaceValues.space4),
                              // Obx(() => InputDecorator(
                              //   decoration: InputDecoration(
                              //     hintText: "Chọn giới tính",
                              //     contentPadding: const EdgeInsets.only(left: 5),
                              //     border: viewModel.validGender.isNotEmpty
                              //         ? const OutlineInputBorder()
                              //         : Theme.of(context)
                              //         .inputDecorationTheme
                              //         .errorBorder,
                              //     errorText: viewModel.validGender.isNotEmpty
                              //         ? '  ${viewModel.validGender.value}'
                              //         : null,
                              //   ),
                              //   child: DropdownButtonHideUnderline(
                              //     child: DropdownButton(
                              //       isExpanded: true,
                              //       value: viewModel.inputGender.value,
                              //       items: const [
                              //         DropdownMenuItem(
                              //             child: Padding(
                              //               padding: EdgeInsets.symmetric(
                              //                   horizontal: SpaceValues.space8),
                              //               child: Text("Chọn giới tính"),
                              //             ),
                              //             value: -1),
                              //         DropdownMenuItem(
                              //             child: Padding(
                              //               padding: EdgeInsets.symmetric(
                              //                   horizontal: SpaceValues.space8),
                              //               child: Text("Nam"),
                              //             ),
                              //             value: 1),
                              //         DropdownMenuItem(
                              //             child: Padding(
                              //               padding: EdgeInsets.symmetric(
                              //                   horizontal: SpaceValues.space8),
                              //               child: Text("Nữ"),
                              //             ),
                              //             value: 0),
                              //         DropdownMenuItem(
                              //             child: Padding(
                              //               padding: EdgeInsets.symmetric(
                              //                   horizontal: SpaceValues.space8),
                              //               child: Text("Khác"),
                              //             ),
                              //             value: 3),
                              //       ],
                              //       onChanged: (value) => viewModel.inputGender.value =
                              //           int.tryParse(value.toString()) ?? 1,
                              //     ),
                              //   ),
                              // )),
                              const SizedBox(height: SpaceValues.space24),
                              GlobalInputFormWidget(
                                readOnly: true,
                                controller: viewModel.phone,
                                requireInput: '',
                                title: 'Số điện thoại',
                                hint: '(Không tìm thấy số điện thoại)',
                                textInputType: TextInputType.phone,
                              ),
                              const SizedBox(height: SpaceValues.space24),
                              GlobalInputFormWidget(
                                readOnly: false,
                                controller: viewModel.email,
                                validator: Validator.emailCanEmpty,
                                textInputType: TextInputType.emailAddress,
                                requireInput: '',
                                title: 'Email',
                                hint: '(Chưa có email)',
                                suffixIcon: const Icon(
                                  Icons.drive_file_rename_outline,
                                ),
                              ),
                              const SizedBox(height: SpaceValues.space24),
                            ],
                          ))
                    ],
                  ),
                ),
              ),
              Container(
                width: double.infinity,
                padding: const EdgeInsets.all(16),
                color: UIColors.white,
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                      primary: Color(0xB2000000), minimumSize: const Size(0, 48)),
                  onPressed: () {
                    viewModel.updateProfile();
                  },
                  child: const Text(
                    "Cập nhật thông tin",
                    style: TextStyle(fontSize: 16),
                  ),
                ),
              ),
              Padding(
                padding: const EdgeInsets.fromLTRB(16, 0, 16, 16),
                child: InkWell(
                  onTap: () => Get.to(ChangePasswordScreen()),
                  child: Container(
                    padding: const EdgeInsets.all(12),
                    width: double.infinity,
                    decoration: BoxDecoration(
                        border: Border.all(color: Color(0xB2000000)),
                        borderRadius: BorderRadius.circular(8)),
                    child: const Text(
                      "Thay đổi mật khẩu",
                      textAlign: TextAlign.center,
                      style: TextStyle(
                          fontSize: 16,
                          color: Color(0xB2000000),
                          fontWeight: FontWeight.bold),
                    ),
                  ),
                ),
              ),
              // Visibility(
              //   visible: Theme.of(context).platform.name != 'android',
              //   child: Padding(
              //     padding: const EdgeInsets.fromLTRB(16,0,16,16),
              //     child: InkWell(
              //       onTap: () {
              //         TextEditingController inputPassword = TextEditingController();
              //         RxInt count = 15.obs;
              //
              //         AppUtils().showPopup(
              //           title: 'Xóa tài khoản',
              //           subtitle: 'Để xóa tài khoản, vui lòng nhập lại mật khẩu và ấn vào nút xóa 15 lần!\nDữ liệu của  bạn sẽ bị xóa khỏi hệ thống và không thể khôi phục lại.',
              //           action: [
              //             Expanded(
              //               child: Obx(
              //                       () {
              //                     return Padding(
              //                       padding: const EdgeInsets.symmetric(horizontal: 8),
              //                       child: Column(
              //                         children: [
              //                           const SizedBox(height: 8),
              //                           Visibility(
              //                             visible: count.value == 15,
              //                             child: GlobalInputFormWidget(
              //                               controller: inputPassword,
              //                               requireInput: '',
              //                               security: true,
              //                               validator: Validator.passwordEasy,
              //                             ),
              //                           ),
              //                           Visibility(
              //                             visible: count.value == 15,
              //                             child: const SizedBox(height: 16),
              //                           ),
              //                           Row(
              //                             children: [
              //                               Expanded(
              //                                 child: ElevatedButton(
              //                                   style: ElevatedButton.styleFrom(
              //                                     primary: count.value <= 5
              //                                         ? UIColors.error80
              //                                         : count.value <= 10
              //                                         ? Colors.yellow.shade900
              //                                         : null,
              //                                   ),
              //                                   onPressed: () {
              //                                     if (count <= 0) {
              //                                       viewModel.deleteUser();
              //                                     }
              //                                     if (viewModel.checkPassword(inputPassword.text)) {
              //                                       count.value = count.value - 1;
              //                                     } else {
              //                                       viewModel.loading(() async => throw 'Mật khẩu không đúng');
              //                                     }
              //                                   },
              //                                   child: Text('Xóa${count.value <= 0 ? '' : ' (${count.value})'}'),
              //                                 ),
              //                               ),
              //                               const SizedBox(width: 8,),
              //                               Expanded(
              //                                 child: ElevatedButton(
              //                                   onPressed: Get.back,
              //                                   child: const Text('Trở về'),
              //                                 ),
              //                               ),
              //                             ],
              //                           ),
              //                         ],
              //                       ),
              //                     );
              //                   }
              //               ),
              //             ),
              //           ],
              //           isSuccess: false,
              //         );
              //       },
              //       child: Container(
              //         padding: const EdgeInsets.all(12),
              //         width: double.infinity,
              //         decoration: BoxDecoration(
              //             color: UIColors.error80,
              //             border: Border.all(color: UIColors.brandA),
              //             borderRadius: BorderRadius.circular(8)
              //         ),
              //         child:  const Text(
              //           "Xóa tài khoản",
              //           textAlign: TextAlign.center,
              //           style: TextStyle(fontSize: 16,color: UIColors.white,fontWeight: FontWeight.bold),
              //         ),
              //       ),
              //     ),
              //   ),
              // ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  SettingProfileModel createViewModel() => getIt<SettingProfileModel>();
}
