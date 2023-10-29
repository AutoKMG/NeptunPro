part of 'handler.dart';

@immutable
sealed class LandingPageState {}

final class LandingPageStateInitial extends LandingPageState {}

final class LandingPageStatePageChanged extends LandingPageState {}