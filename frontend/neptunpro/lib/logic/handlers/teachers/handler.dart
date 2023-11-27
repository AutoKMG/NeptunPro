import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:neptunpro/data/models/teacher.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class TeachersHandler extends Cubit<TeachersState> {
  TeachersHandler() : super(TeachersStateInitial());

  Future<void> retrieveTeachers() async {
    emit(TeachersStateLoading());
    DioHelper.get(url: 'user', query: {}).then((value) {
      List<Teacher> teachers = [];
      value.data.forEach((teacher) {
        teachers.add(Teacher.fromJson(teacher));
      });
      emit(TeachersStateSuccess(teachers: teachers));
    }).catchError((error) {
      emit(TeachersStateFailure(error: error.toString()));
    }); 
  }

  Future<void> retrieveTeachersByName(String name) async {
    
    if (state is TeachersStateSuccess) {
      if (name.isEmpty) {
        if ((state as TeachersStateSuccess).teachers.isNotEmpty) {
          return;
        }
        retrieveTeachers();
      }
    }

    emit(TeachersStateLoading());
    DioHelper.get(url: 'user/search/$name', query: {}).then((value) {
      List<Teacher> teachers = [];
      value.data.forEach((teacher) {
        teachers.add(Teacher.fromJson(teacher));
      });
      emit(TeachersStateSuccess(teachers: teachers));
    }).catchError((error) {
      emit(TeachersStateFailure(error: error.toString()));
    }); 
  }
}
