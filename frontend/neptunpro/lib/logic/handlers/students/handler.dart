import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:neptunpro/data/models/student.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class StudentsHandler extends Cubit<StudentsState> {
  StudentsHandler() : super(StudentsStateInitial());

  Future<void> retrieveStudents() async {
    emit(StudentsStateLoading());
    DioHelper.get(url: 'student', query: {}).then((value) {
      List<Student> students = [];
      value.data.forEach((student) {
        students.add(Student.fromJson(student));
      });
      emit(StudentsStateSuccess(students: students));
    }).catchError((error) {
      emit(StudentsStateFailure(error: error.toString()));
    });
  }

  Future<void> retrieveStudentsByName(String name) async {
    if (state is StudentsStateSuccess) {
      if (name.isEmpty) {
        if ((state as StudentsStateSuccess).students.isNotEmpty) {
          return;
        }
        retrieveStudents();
      }
    }
    emit(StudentsStateLoading());
    DioHelper.get(url: 'student/search/$name', query: {}).then((value) {
      List<Student> students = [];
      value.data.forEach((student) {
        students.add(Student.fromJson(student));
      });
      emit(StudentsStateSuccess(students: students));
    }).catchError((error) {
      emit(StudentsStateFailure(error: error.toString()));
    });
  }

  Future<void> addNewStudent(Map<String, dynamic> payload) async {
    DioHelper.post(url: 'student', payload: payload).then((_) {
      emit(StudentsStateAddSuccess());
      retrieveStudents();
    }).catchError((error) {
      print(error.toString());
      emit(StudentsStateAddFailure());
    });
  }
}
