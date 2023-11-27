part of 'handler.dart';

@immutable
sealed class TeachersState {}

final class TeachersStateInitial extends TeachersState {}


final class TeachersStateLoading extends TeachersState {}

final class TeachersStateSuccess extends TeachersState {
  final List<Teacher> teachers;

  TeachersStateSuccess({required this.teachers});
  
}

final class TeachersStateFailure extends TeachersState {
  final String error;

  TeachersStateFailure({required this.error});
  
}
