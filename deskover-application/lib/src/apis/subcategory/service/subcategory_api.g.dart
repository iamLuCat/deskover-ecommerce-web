// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'subcategory_api.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

// ignore_for_file: unnecessary_brace_in_string_interps

class _SubCategoryAPI implements SubCategoryAPI {
  _SubCategoryAPI(this._dio, {this.baseUrl}) {
    baseUrl ??= 'http://10.11.52.56:8080';
  }

  final Dio _dio;

  String? baseUrl;

  @override
  Future<List<SubCategory>?> doGetSubByCategoryId(categoryId) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{r'categoryId': categoryId};
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<List<dynamic>>(
        _setStreamType<List<SubCategory>>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v1/api/display/subcategory',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    var value = _result.data
        ?.map((dynamic i) => SubCategory.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }

  RequestOptions _setStreamType<T>(RequestOptions requestOptions) {
    if (T != dynamic &&
        !(requestOptions.responseType == ResponseType.bytes ||
            requestOptions.responseType == ResponseType.stream)) {
      if (T == String) {
        requestOptions.responseType = ResponseType.plain;
      } else {
        requestOptions.responseType = ResponseType.json;
      }
    }
    return requestOptions;
  }
}
