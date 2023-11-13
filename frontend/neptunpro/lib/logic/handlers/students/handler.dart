import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:neptunpro/data/models/student_info.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class StudentsHandler extends Cubit<StudentsState> {
  StudentsHandler() : super(StudentsStateInitial()) {
    retrieveStudents();
  }

  Future<void> retrieveStudents() async {
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
}
