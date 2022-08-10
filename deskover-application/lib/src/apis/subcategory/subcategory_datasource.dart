import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/apis/subcategory/service/subcategory_api.dart';
import 'package:injectable/injectable.dart';

abstract class SubCategoryDataSource{
  Future<List<SubCategory>?> doGetSubByCategoryId(int categoryId);
}
@LazySingleton(as: SubCategoryDataSource)
class SubCategoryDataSourceImpl extends SubCategoryDataSource{

  final SubCategoryAPI _subCategoryAPI;

  SubCategoryDataSourceImpl(this._subCategoryAPI);

  @override
  Future<List<SubCategory>?> doGetSubByCategoryId(int categoryId)
  => _subCategoryAPI.doGetSubByCategoryId(categoryId);



}