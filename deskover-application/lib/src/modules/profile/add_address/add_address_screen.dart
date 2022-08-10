import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../themes/ui_colors.dart';
import '../../global_modules/widget/global_input_form_widget.dart';


class AddAddressScreen extends StatefulWidget {
  const AddAddressScreen({Key? key}) : super(key: key);

  @override
  State<AddAddressScreen> createState() => _AddAddressScreenState();
}

class _AddAddressScreenState extends State<AddAddressScreen> {
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
          "Thêm địa chỉ mới",
          style: TextStyle(fontSize: 14, fontWeight: FontWeight.w700),
        ),
        shape:
        Border(bottom: BorderSide(color: Colors.grey.shade300, width: 0.5)),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(height: 20,),
            Center(
              child: Container(
                width: MediaQuery.of(context).size.width * .9,
                child: Column(
                  children: [
                    GlobalInputFormWidget(
                      title: "Họ và tên",
                      requireInput: "",
                    ),
                    SizedBox(height: 10,),
                    GlobalInputFormWidget(
                      title: "Số điện thoại",
                      requireInput: "",
                    ),
                    SizedBox(height: 10,),
                    GlobalInputFormWidget(
                      title: "Tỉnh/Thành phố",
                      requireInput: "",
                    ),
                    SizedBox(height: 10,),
                    GlobalInputFormWidget(
                      title: "Quận/Huyện",
                      requireInput: "",
                    ),
                    SizedBox(height: 10,),
                    GlobalInputFormWidget(
                      title: "Phường/Xã",
                      requireInput: "",
                    ),
                    SizedBox(height: 10,),
                    GlobalInputFormWidget(
                      title: "Địa chỉ cụ thể",
                      requireInput: "",
                    ),
                    SizedBox(height: 40,),
                    Row(
                      children: [
                        Icon(Icons.check_box,size: 30,color: Colors.black,),
                        SizedBox(
                          width: 10,
                        ),
                        Text("Đặt làm mặc định")
                      ],
                    )
                  ],
                ),
              ),
            )
          ],
        ),
      ),
      bottomNavigationBar: BottomAppBar(
        color: UIColors.white,
        child: Container(
          width: MediaQuery.of(context).size.width * .9,
          // decoration: BoxDecoration(
          //   border: Border(bottom:BorderSide(color: Colors.grey.shade300, width: 0.5))
          // ),
          child: Padding(
            padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 10),
            child: ElevatedButton(
              onPressed: () {
                Get.to(AddAddressScreen());
              },
              child: Padding(
                padding: const EdgeInsets.symmetric(vertical: 8),
                child: Text(
                  "Hoàn tất",
                  style: TextStyle(fontSize: 16, fontWeight: FontWeight.w700),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
