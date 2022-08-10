import 'package:deskover_develop/src/apis/addrees/response/addrees_response.dart';
import 'package:deskover_develop/src/apis/addrees/service/add_addrees_api.dart';
import 'package:injectable/injectable.dart';

abstract class AddAddreesDataSource{
  Future<List<Province>?> doGetAllProVince();

  Future<List<District>?> doGetAllDistrict(int provinceId);

  Future<List<Ward>?> doGetAllWard(int provinceId,int districtId);

  Future<Province> doGetProVinceById( int id);

  Future<Ward> doGetWardById( int id);
  Future<District> doGetDistrictById(int id);

}
@LazySingleton(as: AddAddreesDataSource)
class AddAddreesDataSourceImpl extends AddAddreesDataSource{
  final AddAddreesAPI _addAddreesAPI;

  AddAddreesDataSourceImpl(this._addAddreesAPI);
  @override
  Future<List<District>?> doGetAllDistrict(int provinceId)
  => _addAddreesAPI.doGetAllDistrict(provinceId);

  @override
  Future<List<Province>?> doGetAllProVince()
  => _addAddreesAPI.doGetAllProVince();

  @override
  Future<List<Ward>?> doGetAllWard(int provinceId, int districtId)
  => _addAddreesAPI.doGetAllWard(provinceId, districtId);

  @override
  Future<Province> doGetProVinceById(int id)
  => _addAddreesAPI.doGetProVinceById(id);

  @override
  Future<District> doGetDistrictById(int id)
  => _addAddreesAPI.doGetDistrictById(id);

  @override
  Future<Ward> doGetWardById(int id)
  => _addAddreesAPI.doGetWardById(id);

}