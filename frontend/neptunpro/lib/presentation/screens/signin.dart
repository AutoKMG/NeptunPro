import 'package:conditional_builder_null_safety/conditional_builder_null_safety.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:neptunpro/logic/handlers/signin/handler.dart';
import 'package:neptunpro/logic/helpers/local/cache_helper.dart';
import 'package:neptunpro/presentation/screens/landing_page.dart';
import 'package:neptunpro/presentation/widgets/default_text_form_field.dart';

class SigninScreen extends StatelessWidget {
  const SigninScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) => SigninHandler(),
      child: Scaffold(
        body: Stack(
          children: [
            Container(
              decoration: const BoxDecoration(
                image: DecorationImage(
                    image: AssetImage("assets/images/login_background.jpg"),
                    fit: BoxFit.cover),
              ),
            ),
            Container(
              decoration: const BoxDecoration(
                color: Color.fromRGBO(8, 31, 74, 0.4),
              ),
            ),
            Center(
              child: Container(
                width: 885,
                height: 559,
                decoration: BoxDecoration(
                  color: const Color.fromRGBO(196, 203, 211, 0.8),
                  borderRadius: BorderRadius.circular(20),
                ),
                child: Padding(
                  padding:
                      const EdgeInsets.symmetric(vertical: 50, horizontal: 40),
                  child: BlocConsumer<SigninHandler, SigninState>(
                    listener: (context, state) {
                      if (state is SigninStateSuccessful) {
                        CacheHelper.putData(key: "token", value: state.token);
                        Navigator.pushReplacement(context,
                            MaterialPageRoute(builder: (context) {
                          return const LandingPageScreen();
                        }));
                      }
                    },
                    builder: (context, state) {
                      SigninHandler handler = BlocProvider.of(context);
                      return Form(
                        key: handler.formKey,
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Text(
                              "Welcome Back!",
                              style: TextStyle(
                                  fontSize: 64,
                                  fontFamily: "Roboto",
                                  color: Color.fromRGBO(8, 31, 74, 1)),
                            ),
                            const Expanded(
                              child: SizedBox(
                                height: 20,
                              ),
                            ),
                            defaultTextFormField(
                              context: context,
                              controller: handler.emailController,
                              type: TextInputType.emailAddress,
                              validate: (value) {
                                if (value!.isEmpty) {
                                  return 'Please provide your email';
                                }
                                return null;
                              },
                              label: 'Email',
                              prefix: Icons.email_outlined,
                            ),
                            const SizedBox(
                              height: 20,
                            ),
                            defaultTextFormField(
                              context: context,
                              controller: handler.passwordController,
                              isPassword: handler.isPasswordHidden,
                              suffix: handler.suffixIcon,
                              suffixPressed: () {
                                handler.changePasswordVisibility();
                              },
                              type: TextInputType.emailAddress,
                              validate: (value) {
                                if (value!.isEmpty) {
                                  return 'Password cannot be empty';
                                }
                                return null;
                              },
                              label: 'Password',
                              onSubmit: (_) {
                                if (handler.formKey.currentState!.validate()) {
                                  handler.userSignin();
                                }
                              },
                              prefix: Icons.visibility,
                            ),
                            const Expanded(
                              child: SizedBox(
                                height: 20,
                              ),
                            ),
                            SizedBox(
                              height: 90,
                              width: double.infinity,
                              child: ConditionalBuilder(
                                condition: state is! SigninStateLoading,
                                builder: (context) {
                                  return TextButton(
                                    onPressed: () {
                                      if (handler.formKey.currentState!
                                          .validate()) {
                                        handler.userSignin();
                                      }
                                    },
                                    style: ButtonStyle(
                                      foregroundColor:
                                          MaterialStateProperty.resolveWith(
                                              (state) => Colors.white),
                                      backgroundColor:
                                          MaterialStateProperty.resolveWith(
                                              (state) => const Color.fromRGBO(
                                                  8, 31, 74, 1)),
                                    ),
                                    child: const Text(
                                      "Sign In",
                                      style: TextStyle(
                                        fontSize: 50,
                                      ),
                                    ),
                                  );
                                },
                                fallback: (context) {
                                  return const Center(
                                    child: CircularProgressIndicator(),
                                  );
                                },
                              ),
                            ),
                            const SizedBox(
                              height: 10,
                            ),
                            ConditionalBuilder(
                                condition: state is SigninStateFailure,
                                builder: (context) {
                                  return Text(
                                    "Wrong email or password entered!",
                                    style: TextStyle(
                                      fontSize: 20,
                                      color: Colors.red[900],
                                    ),
                                  );
                                },
                                fallback: (context) {
                                  return Container();
                                }),
                          ],
                        ),
                      );
                    },
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
