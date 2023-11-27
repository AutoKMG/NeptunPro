class Course {
  int id;
  String name;
  String type;
  int teacherId;
  TeacherInfo teacherInfo;

  Course({
    required this.id,
    required this.name,
    required this.type,
    required this.teacherId,
    required this.teacherInfo,
  });

  factory Course.fromJson(Map<String, dynamic> map) => Course(
        id: map['id'] as int,
        name: map['name'] as String,
        type: map['type'] as String,
        teacherId: map['teacherId'] as int,
        teacherInfo: TeacherInfo.fromJson(map['teacherInfo']),
      );
}

class TeacherInfo {
  int id;
  String firstname;
  String lastname;
  String email;
  TeacherInfo(
      {required this.id,
      required this.firstname,
      required this.lastname,
      required this.email});

  factory TeacherInfo.fromJson(Map<String, dynamic> map) => TeacherInfo(
        id: map['id'] as int,
        firstname: map['firstname'] as String,
        lastname: map['lastname'],
        email: map['email'],
      );
}
