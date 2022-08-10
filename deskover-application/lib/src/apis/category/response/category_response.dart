import 'package:json_annotation/json_annotation.dart';
part 'category_response.g.dart';
@JsonSerializable()
class CategoryReponse {
  @JsonKey(name: 'id')
  int? id;
  @JsonKey(name: 'name')
  String? name;
  @JsonKey(name: 'img')
  String? img;
  @JsonKey(name: 'imgUrl')
  String? imgUrl;
  @JsonKey(name: 'description')
  String? description;
  @JsonKey(name: 'slug')
  String? slug;
  @JsonKey(name: 'modifiedAt')
  String? modifiedAt;
  @JsonKey(name: 'actived')
  bool? actived;
  @JsonKey(name: 'modifiedBy')
  String? modifiedBy;

  CategoryReponse(
      {this.id,
        this.name,
        this.img,
        this.imgUrl,
        this.description,
        this.slug,
        this.modifiedAt,
        this.actived,
        this.modifiedBy
      });
  factory CategoryReponse.fromJson(Map<String, dynamic> json) => _$CategoryReponseFromJson(json);
  Map<String, dynamic> toJson() => _$CategoryReponseToJson(this);

}