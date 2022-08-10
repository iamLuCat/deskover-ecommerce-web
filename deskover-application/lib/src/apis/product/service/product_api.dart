import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'product_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class ProductAPI {
  @factoryMethod
  factory ProductAPI(Dio dio) = _ProductAPI;

  @GET('/v1/api/display/product-new')
  Future<DataProductResponse> goGetAll(
      @Query('page') int? page, @Query('size') int? size);

  @GET('/v1/api/display/product-sale')
  Future<DataProductResponse> goGetProductSale(
      @Query('page') int? page, @Query('size') int? size);

  @GET('/v1/api/display/product-category')
  Future<DataProductResponse> doGetProductByCategoryId(
      @Query('categoryId') int? categoryId,
      @Query('page') int? page,
      @Query('size') int? size,
      @Query('keySort') String? keySort);

  @GET('/v1/api/display/product-subcategory')
  Future<DataProductResponse> doGetProductBySubId(
      @Query('subId') int? subId,
      @Query('page') int? page,
      @Query('size') int? size,
      @Query('keySort') String? keySort
     );

  @GET('/v1/api/display/product/{id}')
  Future<Product> getById(@Path('id') int? id);

  @GET('/v1/api/display/product')
  Future<DataProductResponse> getSearch(@Query('search') String? search,
      @Query('page') int? page, @Query('size') int? size);
}
