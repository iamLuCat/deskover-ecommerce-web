import 'package:deskover_develop/src/apis/addrees/response/addrees_response.dart';
import 'package:json_annotation/json_annotation.dart';
part 'user_address.g.dart';

@JsonSerializable()
class UserAddress{
  int? id;
  Province? provinceId;
  String? address;
  String? fullname;
  String? province;
  String? district;
  int? districtId;
  String? ward;
  int? wardId;
  String? tel;
  String? email;
  bool? choose;
  bool? actived;

  UserAddress(
      {this.id,
        this.provinceId,
        this.address,
        this.fullname,
        this.province,
        this.district,
        this.ward,
        this.tel,
        this.email,
        this.wardId,
        this.districtId,
        this.choose,
        this.actived
      });
  factory UserAddress.fromJson(Map<String, dynamic> json) => _$UserAddressFromJson(json);
  Map<String, dynamic> toJson() => _$UserAddressToJson(this);

}
