package com.lefarmico.buildsrc

object Deps {
    const val kotlin =
        "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val javax =
        "javax.inject:javax.inject:${Versions.javax}"
    const val timber =
        "com.jakewharton.timber:timber:${Versions.timber}"
    const val gson =
        "com.google.code.gson:gson:${Versions.gson}"
    const val adapterDelegates =
        "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:${Versions.adapterDelegates}"

    object Androidx {
        const val appcompat =
            "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val recyclerView =
            "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
        const val lifecycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
        const val room =
            "androidx.room:room-runtime:${Versions.room}"
        const val viewpager2 =
            "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
        const val cardView =
            "androidx.cardview:cardview:${Versions.cardView}"
        const val material =
            "com.google.android.material:material:${Versions.material}"
        const val preferences =
            "androidx.preference:preference-ktx:${Versions.preferences}"
        const val navigationRuntime =
            "androidx.navigation:navigation-runtime-ktx:${Versions.navigationRuntime}"
    }

    object Dagger {
        const val dagger =
            "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerAndroid =
            "com.google.dagger:dagger-android-support:${Versions.dagger}"
        const val daggerAndroidProcessor =
            "com.google.dagger:dagger-android-processor:${Versions.dagger}"
        const val daggerCompiler =
            "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Ktx {
        const val core =
            "androidx.core:core-ktx:${Versions.coreKtx}"
        const val fragment =
            "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
        const val liveDataCore =
            "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.liveDataCoreKtx}"
        const val liveData =
            "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.liveDataKtx}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.viewModelKtx}"
        const val room =
            "androidx.room:room-ktx:${Versions.roomKtx}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigationKtx}"
        const val navigationUi =
            "androidx.navigation:navigation-ui-ktx:${Versions.navigationKtx}"
        const val legacySup =
            "androidx.legacy:legacy-support-v4:${Versions.legacySup}"
    }

    object Test {
        const val junit =
            "junit:junit:${Versions.junit}"
        const val junitKtx =
            "androidx.test.ext:junit-ktx:${Versions.junitKtx}"
    }

    object UiTest {
        const val junit =
            "androidx.test.ext:junit:${Versions.junitUi}"
        const val espresso =
            "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val espressoContrib =
            "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val espressoIdling =
            "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}"
    }

    object RXJava {
        const val rxjavaAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxJava}"
        const val rxjava = "io.reactivex.rxjava3:rxjava:${Versions.rxJava}"
        const val rxjavaKotlin = "io.reactivex.rxjava3:rxkotlin:${Versions.rxJava}"
        const val rxjavaRetrofitAdapter = "com.github.akarnokd:rxjava3-retrofit-adapter:${Versions.rxJava}"
    }

    object Room {
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomRx = "androidx.room:room-rxjava3:${Versions.room}"
    }

    object Firebase {
        const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebase}"
        const val analytics = "com.google.firebase:firebase-analytics-ktx:${Versions.analytics}"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:${Versions.crashlytics}"
    }
}
