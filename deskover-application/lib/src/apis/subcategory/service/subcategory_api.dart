import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'subcategory_api.g.dart';


@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class SubCategoryAPI{
  @factoryMethod
  factory SubCategoryAPI(Dio dio) = _SubCategoryAPI;

  @GET('/v1/api/display/subcategory')
  Future<List<SubCategory>?> doGetSubByCategoryId(@Query('categoryId') int? categoryId);

}