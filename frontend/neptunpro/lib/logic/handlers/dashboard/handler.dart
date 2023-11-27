import 'package:bloc/bloc.dart';
import 'package:meta/meta.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';

part 'state.dart';

class DashboardHandler extends Cubit<DashboardState> {
  DashboardHandler() : super(DashboardStateInitial());

  Future<void> retrieveDashboardInfo() async {
    emit(DashboardStateLoading());

    DioHelper.get(url: 'dashboard', query: {}).then((value) {
      emit(
        DashboardStateSuccess(
          studentsCount: value.data['studentCount'],
          teachersCout: value.data['userCount'],
          coursesCount: value.data['courseCount'],
        ),
      );
    }).catchError((error) {
      print(error.toString());
      emit(DashboardStateFailure(error: error.toString()));
    });
  }
}
