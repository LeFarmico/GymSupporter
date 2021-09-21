package com.lefarmico.donetime.di.module

import com.lefarmico.donetime.navigation.NotificationResolverImpl
import com.lefarmico.donetime.navigation.RouterImpl
import com.lefarmico.donetime.navigation.ScreenResolverImpl
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.NotificationResolver
import com.lefarmico.navigation.screen.ScreenResolver
import dagger.Binds
import dagger.Module

@Module
abstract class NavigationModule {

    @Binds
    abstract fun getScreenResolver(
        screenResolverImpl: ScreenResolverImpl
    ): ScreenResolver

    @Binds
    abstract fun getNotificationResolver(
        notificationResolverImpl: NotificationResolverImpl
    ): NotificationResolver

    @Binds
    abstract fun getRouter(
        routerImpl: RouterImpl
    ): Router
}
