import 'package:dio/dio.dart';
import 'package:neptunpro/data/models/authentication_request.dart';
import 'package:neptunpro/logic/helpers/local/cache_helper.dart';

class DioHelper {
  static Dio? dio;
  static init() {
    dio = Dio(
      BaseOptions(
        baseUrl: 'https://spacial-shoe-production.up.railway.app/api/',
        receiveDataWhenStatusError: true,
      ),
    );
  }

  static Future<Response> signIn(
    AuthenticationRequest authenticationRequest,
  ) async {
    dio!.options.headers = {
      'Content-Type': 'application/json',
    };
    Map<String, dynamic> credentials = {
      'email': authenticationRequest.email,
      'password': authenticationRequest.password
    };
    return await dio!.post(
      "auth/authentication",
      data: credentials,
    );
  }

  static Future<Response> get(
      {required String url, required Map<String, dynamic> query}) async {
    dio!.options.headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${CacheHelper.getData(key: 'token')}',
    };
    return await dio!.get(url, queryParameters: query);
  }

  static Future<Response> post(
      {required String url, Map<String, dynamic>? payload}) async {
    dio!.options.headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${CacheHelper.getData(key: 'token')}',
    };
    return await dio!.post(
      url,
      data: payload,
    );
  }
}
