import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/apis/user/response/user_response.dart';
import 'package:json_annotation/json_annotation.dart';
part 'cart_response.g.dart';

@JsonSerializable()
class Cart{
  int? id;
  User? user;
  Product? product;
  int? quantity;

  Cart({this.id, this.user, this.product, this.quantity});

  factory Cart.fromJson(Map<String, dynamic> json) => _$CartFromJson(json);
  Map<String, dynamic> toJson() => _$CartToJson(this);
}