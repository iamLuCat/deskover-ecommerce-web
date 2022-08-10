import 'package:deskover_develop/src/apis/addrees/response/addrees_response.dart';
import 'package:deskover_develop/src/apis/user_addrees/response/user_address.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/address/add_addrees/add_address_model.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_input_form_widget.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class NotAddressPage extends StatefulWidget {
  const NotAddressPage({Key? key, this.defaultAddress, this.confirmAddress}) : super(key: key);

  final UserAddress? defaultAddress;
  final void Function(UserAddress value)? confirmAddress;

  @override
  State<NotAddressPage> createState() => _NotAddressPageState();
}

class _NotAddressPageState extends ViewWidget<NotAddressPage, AddAddressModel> {

  @override
  void initState() {
    super.initState();
    viewModel.defaultAddress = widget.defaultAddress;
    // viewModel.confirmAddress = widget.confirmAddress;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        iconTheme: const IconThemeData(
          color: Colors.black,
        ),
        title: Text(
          widget.defaultAddress == null ? 'Thêm địa chỉ giao hàng' : 'Sửa địa chỉ giao hàng',
          style: const TextStyle(fontSize: 15, fontWeight: FontWeight.bold),
        ),
      ),
      body: Column(
        children: [
          Expanded(
            child: SingleChildScrollView(
              child: Form(
                key: viewModel.fromKey,
                child: Container(
                  color: Colors.white,
                  padding: const EdgeInsets.all(SpaceValues.space16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      GlobalInputFormWidget(
                        controller: viewModel.inputfullname,
                        title: 'Họ và tên',
                        hint: 'Nhập họ và tên',
                        requireInput: '',
                        validator: Validator.fullname,
                      ),
                      const SizedBox(height: 16,),
                      GlobalInputFormWidget(
                        controller: viewModel.inputPhone,
                        title: 'Số điện thoại',
                        hint: 'Nhập số điện thoại',
                        textInputType: TextInputType.phone,
                        requireInput: '',
                        validator: Validator.fullname,
                      ),
                      const SizedBox(height: 16,),
                      GlobalInputFormWidget(
                        controller: viewModel.inputEmail,
                        title: 'Email',
                        hint: 'Nhập email',
                        textInputType: TextInputType.emailAddress,
                        requireInput: '',
                        validator: Validator.emailCanEmpty,
                      ),
                      const SizedBox(height: 16,),
                      RichText(
                        text: TextSpan(
                          text: "Tỉnh/ Thành phố",
                          style: Theme.of(context).textTheme.headline6,
                          children: const [
                            TextSpan(
                              text: ' *',
                              style: TextStyle(color: UIColors.error80),
                            )
                          ],
                        ),
                      ),
                      const SizedBox(height: SpaceValues.space4,),
                      Obx(() => InputDecorator(
                        decoration: InputDecoration(
                          hintText: "Chọn Tỉnh/ Thành phố",
                          contentPadding: const EdgeInsets.only(left: 5),
                          // border: viewModel.validCity.isNotEmpty ? const OutlineInputBorder() : Theme.of(context).inputDecorationTheme.errorBorder,
                          // errorText: viewModel.validCity.isNotEmpty ? '  ${viewModel.validCity.value}' : null,
                        ),
                        child: DropdownButtonHideUnderline(
                          child: DropdownButton<Province?>(
                            isExpanded: true,
                            value: viewModel.provinceValue.value,
                            items: viewModel.provinces.map((e)
                            => DropdownMenuItem(
                              child: Padding(
                                padding: const EdgeInsets.only(left: 6),
                                child:  Text(e.name ?? 'Chọn Tỉnh/ Thành phố'),
                              ), value: e,
                            ),
                            ).toList(),
                            onChanged: (value) async {
                              viewModel.provinceValue.value = value!;
                              viewModel.provincesId.value = viewModel.provinceValue.value?.id ?? 1;
                              viewModel.inputAddress.text = '';
                             await viewModel.loadDistricts();
                            },
                          ),
                        ),
                      )),
                      const SizedBox(height: SpaceValues.space16),
                      RichText(
                        text: TextSpan(
                          text: "Quận/ Huyện",
                          style: Theme.of(context).textTheme.headline6,
                          children: const [
                            TextSpan(
                                text: ' *',
                                style: TextStyle(color: UIColors.error80)
                            )
                          ],
                        ),
                      ),
                      const SizedBox(height: SpaceValues.space4,),
                      Obx(() => InputDecorator(
                        decoration: InputDecoration(
                          hintText: "Chọn Quận/ Huyện",
                          contentPadding: const EdgeInsets.only(left: 5),
                          border: viewModel.validDistrict.isNotEmpty ? const OutlineInputBorder() : Theme.of(context).inputDecorationTheme.errorBorder,
                          errorText: viewModel.validDistrict.isNotEmpty ? '  ${viewModel.validDistrict.value}' : null,
                        ),
                        child: DropdownButtonHideUnderline(
                          child: DropdownButton<District?>(
                            isExpanded: true,
                            value: viewModel.districtsValue.value,
                            items: viewModel.districts.map((e)
                            => DropdownMenuItem(
                              child: Padding(
                                padding: const EdgeInsets.only(left: 6),
                                child: Text(e.name ?? 'Chọn Quận/ Huyện',),
                              ),
                              value: e,
                            ),
                            ).toList(),
                            onChanged: viewModel.districts.length > 1 ?
                                (value) {
                                  viewModel.inputAddress.text = '';
                                  viewModel.districtsValue.value = value;
                                  viewModel.districtId.value =  viewModel.districtsValue.value?.id ?? 1;
                                  viewModel.loadWards();
                            } :
                            null,
                          ),
                        ),
                      )),
                      const SizedBox(height: SpaceValues.space16),
                      RichText(
                        text: TextSpan(
                          text: "Phường/ Xã",
                          style: Theme.of(context).textTheme.headline6,
                          children: const [
                            TextSpan(
                                text: ' *',
                                style: TextStyle(color: UIColors.error80)
                            ),
                          ],
                        ),
                      ),
                      const SizedBox(height: SpaceValues.space4,),
                      Obx(() => InputDecorator(
                        decoration: InputDecoration(
                          hintText: "Chọn Phường/ Xã",
                          contentPadding: const EdgeInsets.only(left: 5),
                          border: viewModel.validWard.isNotEmpty ? const OutlineInputBorder() : Theme.of(context).inputDecorationTheme.errorBorder,
                          errorText: viewModel.validWard.isNotEmpty ? '  ${viewModel.validWard.value}' : null,
                        ),
                        child: DropdownButtonHideUnderline(
                          child: DropdownButton<Ward?>(
                            isExpanded: true,
                            value: viewModel.wardsValue.value,
                            items: viewModel.wards.map((e)
                            => DropdownMenuItem(
                              child: Padding(
                                padding: const EdgeInsets.only(left: 6),
                                child: Text(e.name ?? 'Chọn Phường/ Xã'),
                              ), value: e,
                            ),
                            ).toList(),
                            onChanged: viewModel.wards.length > 1 ?
                                (value) {
                                  viewModel.inputAddress.text = '';
                                  viewModel.wardsValue.value = value;
                                  viewModel.wardId.value =  viewModel.wardsValue.value?.id ?? 0;
                            } :
                            null,
                          ),
                        ),
                      )),
                      const SizedBox(height: SpaceValues.space16),
                      GlobalInputFormWidget(
                        controller: viewModel.inputAddress,
                        textInputType: TextInputType.streetAddress,
                        validator: Validator.address,
                        title: 'Địa chỉ cụ thể',
                        hint: 'Tên đường, số nhà, thôn, xóm',
                        minLines: 3,
                        maxLines: 4,
                        requireInput: '',
                      ),

                    ],
                  ),
                ),
              ),
            ),
          ),
          Container(
            padding: const EdgeInsets.all(SpaceValues.space16),
            child: FractionallySizedBox(
              widthFactor: 1,
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  primary: UIColors.black70,
                ),
                onPressed: (){
                  viewModel.btnConfirm();
                },
                child: Text(widget.defaultAddress == null ? 'Thêm địa chỉ' : 'Cập nhật địa chỉ'),
              ),
            ),
          ),
        ],
      ),
    );
  }

  @override
  AddAddressModel createViewModel() => getIt<AddAddressModel>();
}
