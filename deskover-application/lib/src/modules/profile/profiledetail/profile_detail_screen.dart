import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../themes/ui_colors.dart';
import '../../global_modules/widget/global_input_form_widget.dart';


class ProfileDetailScreen extends StatefulWidget {
  const ProfileDetailScreen({Key? key}) : super(key: key);

  @override
  State<ProfileDetailScreen> createState() => _ProfileDetailScreenState();
}

class _ProfileDetailScreenState extends State<ProfileDetailScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: UIColors.white,
      appBar: AppBar(
          backgroundColor: UIColors.white,
          leading: IconButton(
              onPressed: () {
                Get.back();
              },
              icon: Icon(Icons.arrow_back)),
          title: Text(
            "Thông tin tài khoản",
            style: TextStyle(fontSize: 14, fontWeight: FontWeight.w700),
          ),
          shape: Border(
              bottom: BorderSide(color: UIColors.border10))),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(
              height: 15,
            ),
            Center(
              child: CircleAvatar(
                backgroundImage: AssetImage("resources/images/avt_oval.png"),
                radius: 60,
                child: Image.asset(
                  ("resources/images/Item_camera.png"),
                ),
              ),
              // child: Image.asset(
              //   "resources/images/avt_oval.png",
              //   height: 100,
              //   fit: BoxFit.cover,
              //
              // ),
            ),
            SizedBox(
              height: 20,
            ),
            Container(
              width: MediaQuery.of(context).size.width * .9,
              child: Column(
                children: [
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        "Thông tin tài khoản",
                        style: TextStyle(
                            fontSize: 14, fontWeight: FontWeight.w500),
                      ),
                      Icon(
                        Icons.drive_file_rename_outline,
                        color: UIColors.black,
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 5,
                  ),
                  GlobalInputFormWidget(
                    title: "ID",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Tên đăng nhập",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Email",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Giới tính",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Ngày sinh",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Tỉnh/Thành phô",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Quận/Huyện",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Phường/Xã",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 10,
                  ),
                  GlobalInputFormWidget(
                    title: "Địa chỉ cụ thể",
                    requireInput: "",
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Row(
                    children: [
                      Text(
                        "Bảo mật",
                        style: TextStyle(
                            fontSize: 14, fontWeight: FontWeight.w500),
                      )
                    ],
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  GestureDetector(
                    onTap: () {
                      // Get.to(ProfileDetailScreen());
                    },
                    child: Container(
                      width: MediaQuery.of(context).size.width * .9,
                      child: Padding(
                        padding: const EdgeInsets.symmetric(vertical: 5),
                        child: ListTile(
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(15)
                          ),
                          tileColor: Color(0xffF7F9FA),
                          leading: Image.asset(
                            "resources/images/icon_key.png",
                            height: 60,
                          ),
                          title: Text(
                            "Đổi mật khẩu",
                            style: TextStyle(fontSize: 12),
                          ),
                          trailing: Icon(
                            Icons.arrow_forward_ios,
                            color: Colors.black,
                            size: 20,
                          ),
                        ),
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 30,
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
