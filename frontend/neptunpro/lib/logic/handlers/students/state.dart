part of 'handler.dart';

@immutable
sealed class StudentsState {}

final class StudentsStateInitial extends StudentsState {}

final class StudentsStateLoading extends StudentsState {}

final class StudentsStateSuccess extends StudentsState {
  final List<Student> students;

  StudentsStateSuccess({required this.students});
  
}

final class StudentsStateFailure extends StudentsState {
  final String error;

  StudentsStateFailure({required this.error});
  
}
