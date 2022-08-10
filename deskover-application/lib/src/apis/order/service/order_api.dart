import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/apis/order/request/order/order_response.dart';
import 'package:deskover_develop/src/apis/order/request/order_resquest.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';

part 'order_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class OrderAPI{
  @factoryMethod
  factory OrderAPI(Dio dio) = _OrderAPI;


  @POST('/v1/api/customer/order')
  Future<MessageResponse> addOrder(@Body() OrderResquest orderResquest);

  @POST('/v1/api/customer/order/cancel/{orderCode}')
  Future<MessageResponse> canCelOrder(@Path('orderCode') String? orderCode, @Query('statusOrder') String statusOrder);

  @GET('/v1/api/customer/order/{orderCode}')
  Future<OrderReponse> getOrderByUser(@Path('orderCode') String? orderCode);

  @GET('/v1/api/customer/order')
  Future<List<OrderReponse>?> getAllOrderByStatusCode(@Query('statusCode') String? statusCode);

}