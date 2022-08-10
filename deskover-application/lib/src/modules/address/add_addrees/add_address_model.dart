import 'package:deskover_develop/src/apis/addrees/response/addrees_response.dart';
import 'package:deskover_develop/src/apis/user_addrees/response/user_address.dart';
import 'package:deskover_develop/src/usecases/add_addrees_usercase/add_addrees_usercase.dart';
import 'package:deskover_develop/src/usecases/cart_usercase/cart_usercase.dart';
import 'package:deskover_develop/src/utils/AppUtils.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';

@injectable
class AddAddressModel extends ViewModel{
  final AddAddreesUserCase _addAddreesUserCase;
  final CartUserCase _cartUserCase;

  TextEditingController inputfullname = TextEditingController();
  TextEditingController inputAddress = TextEditingController();
  TextEditingController inputPhone = TextEditingController();
  TextEditingController inputEmail = TextEditingController();
  RxBool checkIsDefault = false.obs;

  UserAddress? defaultAddress;


  final RxList<Province>  provinces = RxList.empty();

  final RxList<District> districts = RxList.empty();

  final RxList<Ward> wards = RxList.empty();

  Rxn<Province> provinceValue = Rxn();
  Rxn<District> districtsValue = Rxn();
  Rxn<Ward> wardsValue = Rxn();


  final RxInt provincesId = 1.obs;
  final RxInt districtId = 1.obs;
  final RxInt wardId = 1.obs;

  final RxString validProvince = ''.obs;
  final RxString validDistrict = ''.obs;
  final RxString validWard = ''.obs;


  GlobalKey<FormState> fromKey = GlobalKey();

  AddAddressModel(this._addAddreesUserCase, this._cartUserCase);


  @override
  void initState(){

     loading(() async => await  Future.wait([loadCities()])).then((value) async => {

       if(defaultAddress!= null){
           provinceValue.value = provinces.firstWhere((element) => element.id == defaultAddress?.provinceId?.id),
           inputfullname.text = defaultAddress?.fullname ?? '',
           inputPhone.text = defaultAddress?.tel ?? '',
           inputEmail.text = defaultAddress?.email ?? '',
           provincesId.value = defaultAddress?.provinceId?.id ?? 1,
           inputAddress.text = defaultAddress?.address ?? '',
           loadDistricts().then((value) {
              districtId.value = defaultAddress?.districtId ?? 1;
              districtsValue.value = districts.firstWhere((element) => element.id == defaultAddress?.districtId);
           loadWards().then((value){
              wardId.value = defaultAddress?.wardId ?? 1;
              wardsValue.value = wards.firstWhere((element) => element.id == defaultAddress?.wardId);
         });
       }),
  }
    });

  }

  Future<void> loadCities() async {
    await _addAddreesUserCase.doGetAllProVince().then((value) async {
      provinces.value = value ?? [];
      provincesId.value = provinces.first.id!;
      provinceValue.value = provinces.first;
    });
    await loadDistricts();

  }
  Future<void> loadDistricts() async {
    await _addAddreesUserCase.doGetAllDistrict(provincesId.value).then((value) {
      districts.value = value ?? [];
      districtId.value = districts.value.first.id!;
      districtsValue.value = districts.first;
    });
    await loadWards();
  }
  Future<void> loadWards() async {
    await _addAddreesUserCase.doGetAllWard(provincesId.value, districtId.value).then((value) {
      wards.value = value ?? [];
      wardId.value = wards.value.first.id!;
      wardsValue.value = wards.first;
    });
  }
  void btnConfirm() async {
    if (!validAll()) {
      await loading(() => throw 'Vui lòng kiểm tra lại thông tin');
      return;
    }

    if(defaultAddress == null){
      UserAddress request = UserAddress(
        fullname: inputfullname.text,
        provinceId: provinceValue.value,
        province: provinceValue.value?.name,
        district: '${districtsValue.value?.prefix} ${districtsValue.value?.name}',
        districtId: districtsValue.value?.id,
        ward: '${wardsValue.value?.prefix} ${wardsValue.value?.name}',
        wardId: wardsValue.value?.id,
        actived: false,
        choose: false,
        email: inputEmail.text,
        address: '${inputAddress.text}, ${wardsValue.value?.prefix} ${wardsValue.value?.name}, ${districtsValue.value?.prefix} ${districtsValue.value?.name}, ${provinceValue.value?.name}',
        tel: inputPhone.text,
      );
        await loading(() async {
          await _cartUserCase.doPostAddress(request);
        }).then((value) async {
          Get.back();
        });
    }else{
      print('>>>>>>');
      print(inputAddress.text);
      UserAddress request = UserAddress(
        id:  defaultAddress?.id,
        fullname: inputfullname.text,
        provinceId: provinceValue.value,
        province: provinceValue.value?.name,
        district: '${districtsValue.value?.prefix} ${districtsValue.value?.name}',
        districtId: districtsValue.value?.id,
        ward: '${wardsValue.value?.prefix} ${wardsValue.value?.name}',
        wardId: wardsValue.value?.id,
        email: inputEmail.text,
        address: inputAddress.text == defaultAddress?.address  ?  defaultAddress?.address : '${inputAddress.text}, ${wardsValue.value?.prefix} ${wardsValue.value?.name}, ${districtsValue.value?.prefix} ${districtsValue.value?.name}, ${provinceValue.value?.name}',
        tel: inputPhone.text,
        actived: defaultAddress?.actived,
        choose: defaultAddress?.choose
      );
      await loading(() async {
        await _cartUserCase.doPutAddress(request);
      }).then((value) async {
        Get.back();
      });
    }

  }

  bool validAll() {
    bool result = fromKey.currentState?.validate() ?? false;
    return result;
  }



}