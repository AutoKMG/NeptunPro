class ModificatorInfo {
  int id;
  String firstname;
  String lastname;
  String email;

  ModificatorInfo({
    required this.id,
    required this.firstname,
    required this.lastname,
    required this.email,
  });

  factory ModificatorInfo.fromJson(Map<String, dynamic> map) {
    return ModificatorInfo(
      id: map['id'] as int,
      firstname: map['firstname'] as String,
      lastname: map['lastname'] as String,
      email: map['email'] as String,
    );
  }
}
