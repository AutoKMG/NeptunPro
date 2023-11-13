part of 'handler.dart';

@immutable
sealed class CoursesState {}

final class CoursesStateInitial extends CoursesState {}

final class CoursesStateLoading extends CoursesState {}

final class CoursesStateSuccessful extends CoursesState {
  final List<Course> courses;

  CoursesStateSuccessful({required this.courses});
}

final class CoursesStateFailure extends CoursesState {
  final String error;

  CoursesStateFailure({required this.error});
}