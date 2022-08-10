// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'addrees_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

Province _$ProvinceFromJson(Map<String, dynamic> json) => Province(
      id: json['id'] as int?,
      name: json['name'] as String?,
      code: json['code'] as String?,
    );

Map<String, dynamic> _$ProvinceToJson(Province instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'code': instance.code,
    };

District _$DistrictFromJson(Map<String, dynamic> json) => District(
      id: json['id'] as int?,
      name: json['name'] as String?,
      prefix: json['prefix'] as String?,
      provinceId: json['provinceId'] as int?,
    );

Map<String, dynamic> _$DistrictToJson(District instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'prefix': instance.prefix,
      'provinceId': instance.provinceId,
    };

Ward _$WardFromJson(Map<String, dynamic> json) => Ward(
      id: json['id'] as int?,
      name: json['name'] as String?,
      provinceId: json['provinceId'] as int?,
      districtId: json['districtId'] as int?,
    )..prefix = json['prefix'] as String?;

Map<String, dynamic> _$WardToJson(Ward instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'prefix': instance.prefix,
      'provinceId': instance.provinceId,
      'districtId': instance.districtId,
    };
