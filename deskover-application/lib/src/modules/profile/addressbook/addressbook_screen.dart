import 'package:flutter/material.dart';
import 'package:get/get.dart';

import '../../../themes/ui_colors.dart';
import '../add_address/add_address_screen.dart';


class AddressBookScreen extends StatefulWidget {
  const AddressBookScreen({Key? key}) : super(key: key);

  @override
  State<AddressBookScreen> createState() => _AddressBookScreenState();
}

class _AddressBookScreenState extends State<AddressBookScreen> {
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
          "Sổ địa chỉ",
          style: TextStyle(fontSize: 14, fontWeight: FontWeight.w700),
        ),
        shape:
            Border(bottom: BorderSide(color: Colors.grey.shade300, width: 0.5)),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            SizedBox(
              height: 20,
            ),
            Center(
              child: Container(
                  width: MediaQuery.of(context).size.width * .9,
                  decoration: BoxDecoration(
                      border: Border.all(width: 1, color: Color(0xff1B68B5)),
                      borderRadius: BorderRadius.circular(4)),
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 20, vertical: 10),
                    child: Column(
                      children: [
                        Row(
                          // mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Text(
                              "Lâm Thu Đang",
                              style: TextStyle(
                                  fontSize: 14,
                                  fontWeight: FontWeight.w700,
                                  height: 2.1,
                                  letterSpacing: 0.5),
                            ),
                            Text(
                              " (Địa chỉ mặc định)",
                              style: TextStyle(
                                  fontSize: 12,
                                  fontStyle: FontStyle.italic,
                                  color: Color(0xff1B68B5),
                                  height: 2.1,
                                  letterSpacing: 0.5),
                            )
                          ],
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Row(
                          children: [
                            Flexible(
                              child: Text(
                                "dsd, Phường Thạnh Xuân, Quận 12, Thành phố Hồ Chí Minh",
                                
                                style:
                                    TextStyle(fontSize: 12, letterSpacing: 0.5),
                              ),
                            ),
                          ],
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Row(
                          children: [
                            Text("0398 975 708",
                                style: TextStyle(
                                    fontSize: 12, letterSpacing: 0.5)),
                          ],
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Row(
                          children: [
                            ElevatedButton(
                              onPressed: () {},
                              // style: ElevatedButton.styleFrom(
                              //   shape: RoundedRectangleBorder(
                              //     borderRadius: BorderRadius.circular(4)
                              //   )
                              // ),
                              child: Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 5),
                                child: Text(
                                  "Chỉnh sửa",
                                  style: TextStyle(
                                      fontSize: 13,
                                      fontWeight: FontWeight.w500),
                                ),
                              ),
                            ),
                            SizedBox(
                              width: 30,
                            ),
                            GestureDetector(
                              onTap: (){},
                              child: Image.asset(
                                "resources/images/recyclebin_address.png",
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),
                  )),
            ),
            SizedBox(
              height: 15,
            ),
            Center(
              child: Container(
                  width: MediaQuery.of(context).size.width * .9,
                  decoration: BoxDecoration(
                      border: Border.all(width: 1, color: Colors.grey.shade300),
                      borderRadius: BorderRadius.circular(4)),
                  child: Padding(
                    padding: const EdgeInsets.symmetric(
                        horizontal: 20, vertical: 10),
                    child: Column(
                      children: [
                        Row(
                          // mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Text(
                              "Lâm Đang",
                              style: TextStyle(
                                  fontSize: 14,
                                  fontWeight: FontWeight.w700,
                                  height: 2.1,
                                  letterSpacing: 0.5),
                            ),
                          ],
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Row(
                          children: [
                            Flexible(
                              child: Text(
                                "dsd, Phường Thạnh Xuân, Quận 12, Thành phố Hồ Chí\nMinh",
                                style:
                                    TextStyle(fontSize: 12, letterSpacing: 0.5,),

                              ),
                            ),

                          ],
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Row(
                          children: [
                            Text("0398 975 708",
                                style: TextStyle(
                                    fontSize: 12, letterSpacing: 0.5)),
                          ],
                        ),
                        SizedBox(
                          height: 15,
                        ),
                        Row(
                          children: [
                            ElevatedButton(
                              onPressed: () {},
                              // style: ElevatedButton.styleFrom(
                              //   shape: RoundedRectangleBorder(
                              //     borderRadius: BorderRadius.circular(4)
                              //   )
                              // ),
                              child: Padding(
                                padding:
                                    const EdgeInsets.symmetric(horizontal: 5),
                                child: Text(
                                  "Chỉnh sửa",
                                  style: TextStyle(
                                      fontSize: 13,
                                      fontWeight: FontWeight.w500),
                                ),
                              ),
                            ),
                            SizedBox(
                              width: 30,
                            ),
                            GestureDetector(
                              onTap: (){},
                              child: Image.asset(
                                "resources/images/recyclebin_address.png",
                              ),
                            ),
                          ],
                        ),

                      ],
                    ),
                  )),
            ),
            SizedBox(height: 20,),
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
                  "Thêm địa chỉ mới",
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
