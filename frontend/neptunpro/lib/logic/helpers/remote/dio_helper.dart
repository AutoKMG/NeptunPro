import 'package:dio/dio.dart';
import 'package:neptunpro/data/models/authentication_request.dart';

class DioHelper {
  static Dio? dio;
  static init() {
    dio = Dio(
      BaseOptions(
        baseUrl: 'http://localhost:8080/api/',
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
}
