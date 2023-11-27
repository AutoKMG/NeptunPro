part of 'handler.dart';

@immutable
sealed class DashboardState {}

final class DashboardStateInitial extends DashboardState {}

final class DashboardStateLoading extends DashboardState {}

final class DashboardStateSuccess extends DashboardState {
  final int studentsCount;
  final int teachersCout;
  final int coursesCount;

  DashboardStateSuccess({
    required this.studentsCount,
    required this.teachersCout,
    required this.coursesCount,
  });
}

final class DashboardStateFailure extends DashboardState {
  final String error;

  DashboardStateFailure({required this.error});
}
