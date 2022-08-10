
import 'package:json_annotation/json_annotation.dart';

part 'dio_cache_object.g.dart';

@JsonSerializable()
class DioCacheObject {
  String key;
  String? subKey;
  @JsonKey(name: "max_age_date")
  int? maxAgeDate;
  // @JsonKey(name: "max_smart_date")
  // int? maxSmartDate;
  @JsonKey(name: "max_expiration_date")
  int? maxExpirationDate;
  String? content;
  int? statusCode;
  List<int>? headers;

  DioCacheObject._(this.key, this.subKey, this.content, this.statusCode, this.headers);

  factory DioCacheObject(
    String key,
    String content,
    {
      String? subKey = "",
      int? maxAge,
      // Duration? maxSmart,
      int? maxExpired,
      int? statusCode = 200,
      List<int>? headers,
    }
  ) {
    return DioCacheObject._(key, subKey, content, statusCode, headers)
      ..maxAge = maxAge
      // ..maxSmart = maxSmart
      ..maxExpired = maxExpired;
  }

  set maxAge(int? duration) {
    if (null != duration) maxAgeDate = duration;
  }

  // set maxSmart(Duration? duration) {
  //   if (null != duration) maxSmartDate = _convertDuration(duration);
  // }

  set maxExpired(int? duration) {
    if (null != duration) maxExpirationDate = duration;
  }

  factory DioCacheObject.fromJson(Map<String, dynamic> json) => _$DioCacheObjectFromJson(json);
  Map<String, dynamic> toJson() => _$DioCacheObjectToJson(this);
}
