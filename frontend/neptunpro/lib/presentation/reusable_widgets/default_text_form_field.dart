import 'package:flutter/material.dart';

Widget defaultTextFormField({
  required context,
  required TextEditingController controller,
  required TextInputType type,
  Function(String)? onSubmit,
  Function(String)? onChange,
  Function()? onTap,
  bool isPassword = false,
  String? Function(String?)? validate,
  required String label,
  required IconData prefix,
  IconData? suffix,
  void Function()? suffixPressed,
  bool showCursor = true,
  bool readOnly = false,
}) =>
    TextFormField(
      controller: controller,
      keyboardType: type,
      obscureText: isPassword,
      onFieldSubmitted: onSubmit,
      onChanged: onChange,
      onTap: onTap,
      showCursor: showCursor,
      readOnly: readOnly,
      validator: validate,
      style: const TextStyle(
        color: Colors.black,
      ),
      decoration: InputDecoration(
        filled: true,
        fillColor: const Color.fromRGBO(217, 217, 217, 1),
        labelText: label,
        prefixIcon: Icon(
          prefix,
        ),
        suffixIcon: suffix != null
            ? IconButton(
                onPressed: suffixPressed,
                icon: Icon(
                  suffix,
                ),
              )
            : null,
        border: const OutlineInputBorder(),
      ),
    );
