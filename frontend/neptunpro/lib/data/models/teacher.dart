class Teacher {
  int id;
  String firstname;
  String lastname;
  String email;
  Teacher({
    required this.id,
    required this.firstname,
    required this.lastname,
    required this.email,
  });

  factory Teacher.fromJson(Map<String, dynamic> map) {
    return Teacher(
      id: map['id'],
      firstname: map['firstname'],
      lastname: map['lastname'],
      email: map['email'],
    );
  }
}
