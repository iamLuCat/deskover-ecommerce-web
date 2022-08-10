import 'package:deskover_develop/src/apis/cart/response/cart_response.dart';
import 'package:deskover_develop/src/apis/cart/response/fee.dart';
import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'cart_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class CartAPI{
  @factoryMethod
  factory CartAPI(Dio dio) = _CartAPI;

  @GET('/v1/api/customer/cart')
  Future<List<Cart>?> doGetAllCartOrder();

  @POST('/v1/api/ghtk/fee')
  Future<FeeGHTK> getFee(@Body() dynamic body);

  @POST('/v1/api/customer/add-cart')
  Future<MessageResponse> addToCart(@Query('productId') int? productId, @Query('quantity') int? quantity);

  @POST('/v1/api/customer/minus-cart')
  Future<MessageResponse> minusCart(@Query('productId') int? productId);

  @DELETE('/v1/api/customer/cart')
  Future<MessageResponse> deleteCart(@Query('productId') int? productId);


}