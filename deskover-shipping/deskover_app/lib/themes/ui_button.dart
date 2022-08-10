import 'package:flutter/material.dart';

class ButtonMutosi extends StatelessWidget {
  final String title;
  final Function press;

  const ButtonMutosi({Key? key, required this.title, required this.press})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: () {},
      child: Container(
        width: double.infinity,
        height: 45,
        decoration: BoxDecoration(
            color: Colors.grey, borderRadius: BorderRadius.circular(5)),
        child: Center(
          child: Text(
            "Vui lòng nhập đầy đủ thông tin",
            style: TextStyle(
                color: Colors.white, fontSize: 14, fontWeight: FontWeight.w500),
          ),
        ),
      ),
    );
  }
}
