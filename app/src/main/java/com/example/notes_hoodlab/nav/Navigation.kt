package com.example.notes_hoodlab.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.notes_hoodlab.detail_screen.DetailScreen
import com.example.notes_hoodlab.detail_screen.DetailViewModel
import com.example.notes_hoodlab.home.HomePage
import com.example.notes_hoodlab.home.HomeViewModel
import com.example.notes_hoodlab.login.LoginScreen
import com.example.notes_hoodlab.login.LoginViewModel
import com.example.notes_hoodlab.login.SignUpScreen

enum class LoginRoutes {
    SignIn,
    SignUp
}

enum class NestedRoutes {
    Main,
    Login
}

enum class HomeRoutes {
    Home,
    Detail
}

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginViewModel: LoginViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {

    NavHost(
        navController = navController,
        startDestination = NestedRoutes.Main.name
    ) {
        authGraph(
            navController = navController,
            loginViewModel = loginViewModel
        )
        homeGraph(
            navController = navController,
            detailViewModel = detailViewModel,
            homeViewModel = homeViewModel
        )
    }


}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
) {
    navigation(
        startDestination = LoginRoutes.SignIn.name,
        route = NestedRoutes.Login.name
    ) {
        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        launchSingleTop = true
                        popUpTo(route = LoginRoutes.SignIn.name) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignUp.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.SignUp.name) {
            SignUpScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        popUpTo(LoginRoutes.SignUp.name) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
    }

}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel
) {
    navigation(
        startDestination = HomeRoutes.Home.name,
        route = NestedRoutes.Main.name,
    ) {
        composable(HomeRoutes.Home.name) {
            HomePage(
                homeViewModel = homeViewModel,
                onNoteClick = { noteId ->
                    navController.navigate(
                        HomeRoutes.Detail.name + "?id=$noteId"
                    ) {
                        launchSingleTop = true
                    }
                },
                navToDetailPage = {
                    navController.navigate(HomeRoutes.Detail.name)
                }
            ) {
                navController.navigate(NestedRoutes.Login.name) {
                    launchSingleTop = true
                    popUpTo(0) {
                        inclusive = true
                    }
                }

            }
        }

        composable(
            route = HomeRoutes.Detail.name + "?id={id}",
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
                defaultValue = ""
            })
        ) { entry ->
            DetailScreen(
                detailViewModel = detailViewModel,
                noteId = entry.arguments?.getString("id") as String,
            ) {
                navController.navigateUp()
            }
        }
    }

}
