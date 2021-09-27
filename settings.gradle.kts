rootProject.name = "DoneTime"
include(
    ":app",
    ":domain",
    ":data",
    ":core",
    ":features",
    ":features:home",
    ":features:exercise_menu",
    ":features:workout",
    ":features:exercise_addition",
    ":features:exercise_library",
    ":features:exercise_details",
    ":features:create_new_exercise",
)
include(":navigation")
include(":features:edit_record_workout")
