import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:neptunpro/data/models/course_info.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class CoursesHandler extends Cubit<CoursesState> {
  CoursesHandler() : super(CoursesStateInitial()){
    retrieveCourses();
  }

  Future<void> retrieveCourses() async {
    emit(CoursesStateLoading());
    DioHelper.get(url: 'course', query: {}).then((value) {
      List<Course> courses = [];
      value.data.forEach((course){
        courses.add(Course.fromJson(course));
      });
      emit(CoursesStateSuccessful(courses: courses));
    }).catchError((error){
      print(error.toString());
      emit(CoursesStateFailure(error: error.toString()));
    });
  }
}
