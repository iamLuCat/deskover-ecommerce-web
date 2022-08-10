// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'add_addrees_api.dart';

// **************************************************************************
// RetrofitGenerator
// **************************************************************************

// ignore_for_file: unnecessary_brace_in_string_interps

class _AddAddreesAPI implements AddAddreesAPI {
  _AddAddreesAPI(this._dio, {this.baseUrl}) {
    baseUrl ??= 'http://10.11.52.56:8080';
  }

  final Dio _dio;

  String? baseUrl;

  @override
  Future<List<Province>?> doGetAllProVince() async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<List<dynamic>>(
        _setStreamType<List<Province>>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v0/client/province',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    var value = _result.data
        ?.map((dynamic i) => Province.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }

  @override
  Future<Province> doGetProVinceById(id) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<Province>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v0/client/province/${id}',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = Province.fromJson(_result.data!);
    return value;
  }

  @override
  Future<List<District>?> doGetAllDistrict(provinceId) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{r'provinceId': provinceId};
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<List<dynamic>>(
        _setStreamType<List<District>>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v0/client/district',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    var value = _result.data
        ?.map((dynamic i) => District.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }

  @override
  Future<District> doGetDistrictById(id) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(
        _setStreamType<District>(
            Options(method: 'GET', headers: _headers, extra: _extra)
                .compose(_dio.options, '/v0/client/district/${id}',
                    queryParameters: queryParameters, data: _data)
                .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = District.fromJson(_result.data!);
    return value;
  }

  @override
  Future<List<Ward>?> doGetAllWard(provinceId, districtId) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{
      r'provinceId': provinceId,
      r'districtId': districtId
    };
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<List<dynamic>>(_setStreamType<List<Ward>>(
        Options(method: 'GET', headers: _headers, extra: _extra)
            .compose(_dio.options, '/v0/client/ward',
                queryParameters: queryParameters, data: _data)
            .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    var value = _result.data
        ?.map((dynamic i) => Ward.fromJson(i as Map<String, dynamic>))
        .toList();
    return value;
  }

  @override
  Future<Ward> doGetWardById(id) async {
    const _extra = <String, dynamic>{};
    final queryParameters = <String, dynamic>{};
    queryParameters.removeWhere((k, v) => v == null);
    final _headers = <String, dynamic>{};
    final _data = <String, dynamic>{};
    final _result = await _dio.fetch<Map<String, dynamic>>(_setStreamType<Ward>(
        Options(method: 'GET', headers: _headers, extra: _extra)
            .compose(_dio.options, '/v0/client/ward/${id}',
                queryParameters: queryParameters, data: _data)
            .copyWith(baseUrl: baseUrl ?? _dio.options.baseUrl)));
    final value = Ward.fromJson(_result.data!);
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
