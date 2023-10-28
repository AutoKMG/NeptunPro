import 'package:bloc/bloc.dart';
import 'package:flutter/material.dart';
import 'package:neptunpro/data/models/authentication_request.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class SigninHandler extends Cubit<SigninState> {
  SigninHandler() : super(SigninStateInitial());

  // Login Form Variables
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  var formKey = GlobalKey<FormState>();
  bool isPasswordHidden = true;
  IconData suffixIcon = Icons.visibility;

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
