import 'package:flutter/material.dart';
import 'package:neptunpro/logic/helpers/local/cache_helper.dart';
import 'package:neptunpro/presentation/screens/signin.dart';

class LandingPageScreen extends StatelessWidget {
  const LandingPageScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
          child: Container(
        width: 800,
        height: 700,
        padding: const EdgeInsets.all(10),
        decoration: BoxDecoration(
            color: Colors.black45, borderRadius: BorderRadius.circular(20)),
        child: Center(
            child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const Text(
              "Yaaayyyyy! you made it to the app!",
              style: TextStyle(color: Colors.white, fontSize: 50),
            ),
            Text("Your token is ${CacheHelper.getData(key: "token")}",
                style: const TextStyle(color: Colors.white, fontSize: 20)),
            SizedBox(
              height: 90,
              width: 300,
              child: TextButton(
                  onPressed: () {
                    CacheHelper.removeData(key: "token");
                    Navigator.pushReplacement(context,
                        MaterialPageRoute(builder: (context) {
                      return const SigninScreen();
                    }));
                  },
                  style: ButtonStyle(
                      backgroundColor: MaterialStateProperty.resolveWith(
                          (state) => const Color.fromRGBO(8, 31, 74, 1))),
                  child: const Text(
                    "Sign out",
                    style: TextStyle(
                      fontSize: 30,
                      color: Colors.white,
                    ),
                  )),
            )
          ],
        )),
      )),
    );
  }
}
