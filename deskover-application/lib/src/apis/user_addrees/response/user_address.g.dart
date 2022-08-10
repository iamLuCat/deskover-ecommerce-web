// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_address.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserAddress _$UserAddressFromJson(Map<String, dynamic> json) => UserAddress(
      id: json['id'] as int?,
      provinceId: json['provinceId'] == null
          ? null
          : Province.fromJson(json['provinceId'] as Map<String, dynamic>),
      address: json['address'] as String?,
      fullname: json['fullname'] as String?,
      province: json['province'] as String?,
      district: json['district'] as String?,
      ward: json['ward'] as String?,
      tel: json['tel'] as String?,
      email: json['email'] as String?,
      wardId: json['wardId'] as int?,
      districtId: json['districtId'] as int?,
      choose: json['choose'] as bool?,
      actived: json['actived'] as bool?,
    );

Map<String, dynamic> _$UserAddressToJson(UserAddress instance) =>
    <String, dynamic>{
      'id': instance.id,
      'provinceId': instance.provinceId,
      'address': instance.address,
      'fullname': instance.fullname,
      'province': instance.province,
      'district': instance.district,
      'districtId': instance.districtId,
      'ward': instance.ward,
      'wardId': instance.wardId,
      'tel': instance.tel,
      'email': instance.email,
      'choose': instance.choose,
      'actived': instance.actived,
    };
