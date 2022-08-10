import 'package:json_annotation/json_annotation.dart';
part 'addrees_response.g.dart';

@JsonSerializable()
class Province{
  int? id;
  String? name;
  String? code;

  Province({this.id,this.name,this.code});

  factory Province.fromJson(Map<String, dynamic> json) => _$ProvinceFromJson(json);
  Map<String, dynamic> toJson() => _$ProvinceToJson(this);

}

@JsonSerializable()
class District{
  int? id;
  String? name;
  String? prefix;
  int? provinceId;

  District({this.id,this.name,this.prefix,this.provinceId});

  factory District.fromJson(Map<String, dynamic> json) => _$DistrictFromJson(json);
  Map<String, dynamic> toJson() => _$DistrictToJson(this);

}

@JsonSerializable()
class Ward{
  int? id;
  String? name;
  String? prefix;
  int? provinceId;
  int? districtId;

  Ward({this.id,this.name,this.provinceId,this.districtId});

  factory Ward.fromJson(Map<String, dynamic> json) => _$WardFromJson(json);
  Map<String, dynamic> toJson() => _$WardToJson(this);

}
