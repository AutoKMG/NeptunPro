import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/helpers/local/cache_helper.dart';
import 'package:neptunpro/logic/helpers/remote/dio_helper.dart';
import 'package:neptunpro/logic/observers/bloc_observer.dart';
import 'package:neptunpro/presentation/screens/signin.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  Bloc.observer = MyBlocObserver();
  DioHelper.init();
  await CacheHelper.init();
  runApp(const NeptunProApplication());
}

class NeptunProApplication extends StatelessWidget {
  const NeptunProApplication({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'NeptunPro',
      theme: ThemeData(
        primaryColor: Colors.blue, // Set your primary color here
        textTheme: const TextTheme(
          titleLarge: TextStyle(
            fontSize: 22,
            fontWeight: FontWeight.w600,
            color: Colors.white
          ),
          bodyLarge: TextStyle(
            color: Colors.white,
            fontSize: 16,
          ),
        ),
      ),
      home: SigninScreen(),
    );
  }
}