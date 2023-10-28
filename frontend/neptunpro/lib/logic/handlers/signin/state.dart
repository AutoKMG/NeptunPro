part of 'handler.dart';

@immutable
sealed class SigninState {}

final class SigninStateInitial extends SigninState {}

final class SigninStateLoading extends SigninState {}

final class SigninStateSuccessful extends SigninState {
  final String token;

  SigninStateSuccessful({required this.token});
}

final class SigninStateFailure extends SigninState {}

final class SigninStatePasswordVisibilityChanged extends SigninState {}