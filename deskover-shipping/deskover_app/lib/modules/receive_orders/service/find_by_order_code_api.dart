import 'package:deskover_app/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';

import '../../../entity/order/order_response.dart';
import '../../../entity/order/order_responses.dart';

part 'find_by_order_code_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class OrderApi{

    @factoryMethod
    factory OrderApi(Dio dio) = _OrderApi;

    @GET('/v1/api/admin/orders/{orderCode}')
    Future<OrderReponses> getByOrderCodeAndStatusCode(@Path('orderCode') String orderCode,@Query('status') String? status);


    @POST('/v1/api/admin/orders/{orderCode}')
    Future<void> doPostPickupOrder(@Path('orderCode') String orderCode,@Query('status') String? status,@Query('note') String? note);

    @GET('/v1/api/admin/orders/delivery')
    Future<DataOrderReponse> getAllOrderDelivery(@Query('status') String? status);

    @GET('/v1/api/admin/orders/statistical')
    Future<DataOrderReponse> getAllOrderByUser();


}