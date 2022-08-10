import 'package:deskover_develop/src/apis/category/response/category_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';

part 'category_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class CategoryAPI {
  @factoryMethod
  factory CategoryAPI(Dio dio) = _CategoryAPI;

  @GET('/v1/api/display/category')
  Future<List<CategoryReponse>?> goGetAll();

}
