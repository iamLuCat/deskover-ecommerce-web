import 'package:deskover_develop/src/apis/addrees/response/addrees_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'add_addrees_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class AddAddreesAPI{
  @factoryMethod
  factory AddAddreesAPI(Dio dio) = _AddAddreesAPI;

  @GET('/v0/client/province')
  Future<List<Province>?> doGetAllProVince();

  @GET('/v0/client/province/{id}')
  Future<Province> doGetProVinceById(@Path('id') int? id);

  @GET('/v0/client/district')
  Future<List<District>?> doGetAllDistrict(@Query('provinceId') int? provinceId);

  @GET('/v0/client/district/{id}')
  Future<District> doGetDistrictById(@Path('id') int? id);

  @GET('/v0/client/ward')
  Future<List<Ward>?> doGetAllWard(@Query('provinceId') int? provinceId,@Query('districtId') int? districtId);

  @GET('/v0/client/ward/{id}')
  Future<Ward> doGetWardById(@Path('id') int? id);



  

}