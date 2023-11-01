import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:neptunpro/data/models/authentication_request.dart';
import 'package:neptunpro/logic/helpers/local/cache_helper.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class SigninHandler extends Cubit<SigninState> {
  SigninHandler() : super(SigninStateInitial()) {
    _initializeState();
  }

  // Login Form Variables
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  var formKey = GlobalKey<FormState>();
  bool isPasswordHidden = true;
  IconData suffixIcon = Icons.visibility;

  void _initializeState() {
    DioHelper.get(url: 'demo', query: {}).then((value) {
      if (value.statusCode == 200) {
        emit(SigninStateSuccessful(token: CacheHelper.getData(key: 'token')));
      } else {
        emit(SigninStateInitial());
      }
    }).catchError((error) {
      emit(SigninStateInitial());
    });
  }

  void userSignin() {
    emit(SigninStateLoading());
    AuthenticationRequest authenticationRequest =
        AuthenticationRequest(emailController.text, passwordController.text);
    DioHelper.signIn(authenticationRequest).then((value) {
      emailController.clear();
      passwordController.clear();
      emit(SigninStateSuccessful(token: value.data['token']));
    }).catchError((error) {
      emit(SigninStateFailure());
    });
  }

  void changePasswordVisibility() {
    isPasswordHidden = !isPasswordHidden;
    suffixIcon = Icons.visibility_off;
    emit(SigninStatePasswordVisibilityChanged());
  }
}
