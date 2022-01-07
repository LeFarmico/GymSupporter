package com.lefarmico.exercise_menu.viewModel

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.utils.ValidationState
import com.lefarmico.core.validator.EmptyValidator
import com.lefarmico.core.validator.ExistedValidator
import com.lefarmico.core.validator.ValidateHandler
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.exercise_menu.action.SubcategoryAction
import com.lefarmico.exercise_menu.intent.SubcategoryIntent
import com.lefarmico.exercise_menu.intent.SubcategoryIntent.*
import com.lefarmico.exercise_menu.reduce
import com.lefarmico.exercise_menu.state.LibraryListEvent
import com.lefarmico.exercise_menu.state.LibraryListState
import com.lefarmico.navigation.Router
import com.lefarmico.navigation.notification.Notification
import com.lefarmico.navigation.params.LibraryParams
import com.lefarmico.navigation.params.ToastBarParams
import com.lefarmico.navigation.screen.Screen
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SubcategoryViewModel @Inject constructor() : BaseViewModel<
    SubcategoryIntent, SubcategoryAction, LibraryListState, LibraryListEvent
    >() {

    private val validator = ValidateHandler()
    private val validateSubject = PublishSubject.create<String>()
    private var validateCache = listOf<String>()

    @Inject
    lateinit var repo: LibraryRepository

    @Inject
    lateinit var router: Router

    init {
        validator()
    }

    private fun getSubCategories(categoryId: Int) {
        repo.getSubCategories(categoryId)
            .observeUi()
            .doAfterSuccess { dataState ->
                val viewState = dataState.reduce()
                mState.value = viewState
                putToCache(viewState)
            }
            .subscribe()
    }

    private fun putToCache(state: LibraryListState) {
        if (state is LibraryListState.LibraryResult)
            try {
                validateCache = state.libraryList.map { (it as LibraryViewData.SubCategory).title }
            } catch (e: java.lang.IllegalArgumentException) {
                throw (e)
            }
    }

    private fun validator() {
        Observable.create<String> { input -> validateSubject.subscribe { input.onNext(it) } }
            .debounce(1, TimeUnit.SECONDS)
            .distinctUntilChanged()
            .observeUi()
            .doOnNext { validateField -> validate(validateField, validateCache) }
            .subscribe()
    }

    private fun validate(field: String, fieldList: List<String>) {
        validator.resetValidators()
        validator.addValidator(EmptyValidator(field))
        validator.addValidator(ExistedValidator(field, fieldList))

        val event = when (validator.validate()) {
            EmptyValidator.EmptyState -> LibraryListEvent.ValidationResult.Empty
            ExistedValidator.ExistedState -> LibraryListEvent.ValidationResult.AlreadyExist
            com.lefarmico.core.validator.ValidationState.SuccessState -> LibraryListEvent.ValidationResult.Success
        }
        mEvent.postValue(event)
    }

    private fun addSubcategory(subcategoryTitle: String, categoryId: Int) {
        val subCategory = LibraryDto.SubCategory(0, subcategoryTitle, categoryId)
        repo.addSubCategory(subCategory)
            .observeUi()
            .doAfterSuccess { getSubCategories(subCategory.categoryId) }
            .subscribe()
    }

    private fun goToExerciseListScreen(
        subcategory: LibraryViewData.SubCategory,
        isFromWorkoutScreen: Boolean
    ) {
        val categoryId = subcategory.categoryId
        val subcategoryId = subcategory.id
        router.navigate(
            screen = Screen.EXERCISE_LIST_SCREEN,
            data = LibraryParams.ExerciseList(categoryId, subcategoryId, isFromWorkoutScreen)
        )
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    override fun triggerAction(action: SubcategoryAction) {
        when (action) {
            is SubcategoryAction.AddSubcategory -> addSubcategory(action.title, action.categoryId)
            is SubcategoryAction.ClickSubcategory -> goToExerciseListScreen(
                action.subcategory, action.isFromWorkoutScreen
            )
            is SubcategoryAction.GetSubcategories -> getSubCategories(action.categoryId)
            is SubcategoryAction.ShowToast -> showToast(action.text)
            is SubcategoryAction.Validate -> validateSubject.onNext(action.text)
        }
    }

    override fun intentToAction(intent: SubcategoryIntent): SubcategoryAction {
        return when (intent) {
            is AddSubcategory -> SubcategoryAction.AddSubcategory(intent.title, intent.categoryId)
            is ClickItem -> SubcategoryAction.ClickSubcategory(intent.item, intent.isFromWorkoutScreen)
            is GetSubcategories -> SubcategoryAction.GetSubcategories(intent.categoryId)
            is ShowToast -> SubcategoryAction.ShowToast(intent.text)
            is Validate -> SubcategoryAction.Validate(intent.title)
        }
    }
}
