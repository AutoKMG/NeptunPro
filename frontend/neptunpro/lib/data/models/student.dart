import 'package:neptunpro/data/models/modificator_info.dart';

class Student {
  int id;
  String firstname;
  String lastname;
  int programId;
  double gpa;
  int version;
  int createdBy;
  int modifiedBy;
  DateTime createdAt;
  DateTime modifiedAt;
  ProgramInfo programInfo;
  ModificatorInfo createdByInfo;
  ModificatorInfo modifiedByInfo;

  Student({
    required this.id,
    required this.firstname,
    required this.lastname,
    required this.programId,
    required this.gpa,
    required this.version,
    required this.createdBy,
    required this.modifiedBy,
    required this.createdAt,
    required this.modifiedAt,
    required this.programInfo,
    required this.createdByInfo,
    required this.modifiedByInfo,
  });

  factory Student.fromJson(Map<String, dynamic> map) {
    return Student(
      id: map['id'] as int,
      firstname: map['firstname'] as String,
      lastname: map['lastname'] as String,
      programId: map['programId'] as int,
      gpa: map['gpa'] as double,
      version: map['version'] as int,
      createdBy: map['createdBy'] as int,
      modifiedBy: map['modifiedBy'] as int,
      createdAt: DateTime.parse(map['createdAt']),
      modifiedAt: DateTime.parse(map['modifiedAt']),
      programInfo: ProgramInfo.fromJson(map['programInfo'] as Map<String,dynamic>),
      createdByInfo: ModificatorInfo.fromJson(map['createdByInfo'] as Map<String,dynamic>),
      modifiedByInfo: ModificatorInfo.fromJson(map['modifiedByInfo'] as Map<String,dynamic>),
    );
  }
}

class ProgramInfo {
  int id;
  String name;
  ProgramInfo({
    required this.id,
    required this.name,
  });

  factory ProgramInfo.fromJson(Map<String, dynamic> map) {
    return ProgramInfo(
      id: map['id'] as int,
      name: map['name'] as String,
    );
  }

}
