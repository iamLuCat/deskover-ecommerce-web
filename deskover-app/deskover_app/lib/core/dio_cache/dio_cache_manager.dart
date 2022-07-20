import 'dart:convert';
import 'dart:developer';
import 'package:deskover_app/core/dio_cache/store/dio_cache_memory.dart';
import 'package:deskover_app/core/dio_cache/store/i_dio_cache_store.dart';
import 'package:dio/dio.dart';

import '../../config/static_date.dart';
import 'core/dio_cache_ano.dart';
import 'core/dio_cache_object.dart';

class DioCacheManager {
  InterceptorsWrapper? _interceptor;
  final IDioCacheStore _memoryStore = DioCacheMemory();
  InterceptorsWrapper get interceptor {
    _interceptor ??= InterceptorsWrapper(
      onRequest: _onRequest,
      onResponse: _onResponse,
      onError: _onError,
    );
    return _interceptor!;
  }

  _onRequest(RequestOptions options, RequestInterceptorHandler handler,) async {
    if (StaticData.isShowLog) {
      try {
        String headerCurl = '';
        options.headers.forEach((key, value) {
          headerCurl += '--header "$key: $value" ';
        });
        String logCurl = 'curl ${Uri.encodeFull(options.uri.toString())} --request ${options.method} $headerCurl--data ${jsonEncode(options.data ?? '{}')}';
        log('<<< Request: ${options.method} ${options.uri}'
            '\n<<< Header request: ${jsonEncode(options.headers)}'
            '\n<<< Body request: ${jsonEncode(options.data)}'
            '\n<<< Fash request: $logCurl');
      } catch (exception) {
        log('>> log error: $exception');
      }
    }

    if (null != options.extra[DioCacheAno.subkey] && '${options.extra[DioCacheAno.subkey]}' != '${options.queryParameters}') {
      handler.next(options);
      return;
    }
    if (null != options.extra[DioCacheAno.refresh] && options.extra[DioCacheAno.refresh] is bool) {
      if (options.extra[DioCacheAno.refresh]) {
        handler.next(options);
        return;
      }
    }
    if (null == options.extra[DioCacheAno.age] || options.extra[DioCacheAno.age] is! Duration) {
      handler.next(options);
      return;
    }
    DioCacheObject? cacheObject = await _memoryStore.getCache(options.path, subkey: '${options.extra[DioCacheAno.subkey] ?? options.queryParameters}');
    if (null == cacheObject) {
      handler.next(options);
      return;
    }
    if ((cacheObject.maxExpirationDate ?? 0) < DateTime.now().microsecondsSinceEpoch) {
      handler.next(options);
      return;
    }
    handler.resolve(Response(
      data: jsonDecode(cacheObject.content ?? '{}'),
      requestOptions: options,
    ));
  }
  _onResponse(Response e, ResponseInterceptorHandler handler,) async {
    if (StaticData.isShowLog) {
      try {
        log('<<< Response: ${e.requestOptions.method} ${e.requestOptions.uri}'
            '\n<<< Body response: ${jsonEncode(e.data)}');
      }
      catch (exception) {
        log('>> log error: $exception');
      }

    }

    if (null != e.requestOptions.extra[DioCacheAno.subkey] && '${e.requestOptions.extra[DioCacheAno.subkey]}' != '${e.requestOptions.queryParameters}') {
      handler.next(e);
      return;
    }
    if (null == e.requestOptions.extra[DioCacheAno.age] || e.requestOptions.extra[DioCacheAno.age] is! Duration) {
      handler.next(e);
      return;
    }
    if (null == e.requestOptions.extra[DioCacheAno.expired] || e.requestOptions.extra[DioCacheAno.expired] is! Duration) {
      e.requestOptions.extra[DioCacheAno.expired] = e.requestOptions.extra[DioCacheAno.age];
    }

    int ageDate = DateTime.now().add(e.requestOptions.extra[DioCacheAno.age]).microsecondsSinceEpoch;
    int expiredDate = DateTime.now().add(e.requestOptions.extra[DioCacheAno.expired]).microsecondsSinceEpoch;

    if ((e.statusCode ?? 0) == 200) {
      await _memoryStore.setCache(DioCacheObject(
        e.requestOptions.path,
        jsonEncode(e.data),
        subKey: '${e.requestOptions.queryParameters}',
        maxAge: ageDate,
        maxExpired: expiredDate,
        statusCode: e.statusCode,
      ));
    }
    handler.next(e);
  }
  _onError(DioError e, ErrorInterceptorHandler handler) async {
    if (StaticData.isShowLog) {
      try {
        String headerCurl = '';
        e.requestOptions.headers.forEach((key, value) {
          headerCurl += '--header "$key: $value" ';
        });
        String logCurl = 'curl ${Uri.encodeFull(e.requestOptions.uri.toString())} --request ${e.requestOptions.method} $headerCurl--data ${jsonEncode(e.requestOptions.data ?? '{}')}';
        log('<<< Error: ${jsonEncode(e.error)} | ${e.requestOptions.method} ${e.requestOptions.uri}'
            '\n<<< Header request error: ${jsonEncode(e.requestOptions.headers)}'
            '\n<<< Body request error: ${jsonEncode(e.requestOptions.data)}'
            '\n<<< Body error: ${e.response?.data}'
            '\n<<< Status error: ${e.response?.statusCode} | ${e.response?.statusMessage}'
            '\n<<< Fash error: $logCurl');
      }
      catch (exception) {
        log('>> log error: $exception');
      }
    }

    if (null != e.requestOptions.extra[DioCacheAno.refresh] && e.requestOptions.extra[DioCacheAno.refresh] is bool) {
      if (e.requestOptions.extra[DioCacheAno.refresh]) {
        handler.next(e);
        return;
      }
    }
    if (null == e.requestOptions.extra[DioCacheAno.expired] || e.requestOptions.extra[DioCacheAno.expired] is! Duration) {
      handler.next(e);
      return;
    }
    DioCacheObject? cacheObject = await _memoryStore.getCache(e.requestOptions.path, subkey: '${e.requestOptions.queryParameters}');
    if (null == cacheObject) {
      handler.next(e);
      return;
    }
    if ((cacheObject.maxExpirationDate ?? 0) > DateTime.now().microsecondsSinceEpoch) {
      handler.next(e);
      return;
    }
    handler.resolve(Response(
      data: jsonDecode(cacheObject.content ?? '{}'),
      requestOptions: e.requestOptions,
    ));
  }

}
