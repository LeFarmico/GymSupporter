package com.lefarmico.exercise_menu.viewModel

import com.lefarmico.core.base.BaseViewModel
import com.lefarmico.core.entity.LibraryViewData
import com.lefarmico.core.extensions.observeUi
import com.lefarmico.core.validator.EmptyValidator
import com.lefarmico.core.validator.ExistedValidator
import com.lefarmico.core.validator.ValidateHandler
import com.lefarmico.core.validator.ValidationState
import com.lefarmico.domain.entity.LibraryDto
import com.lefarmico.domain.repository.LibraryRepository
import com.lefarmico.exercise_menu.action.CategoryAction
import com.lefarmico.exercise_menu.intent.CategoryIntent
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
import java.lang.IllegalArgumentException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CategoryViewModel @Inject constructor() : BaseViewModel<
    CategoryIntent, CategoryAction, LibraryListState, LibraryListEvent>() {

    @Inject
    lateinit var repo: LibraryRepository
    @Inject
    lateinit var router: Router

    private val validator = ValidateHandler()
    private val validateSubject = PublishSubject.create<String>()
    private var validateCache = listOf<String>()

    init {
        validator()
    }

    private fun getCategories() {
        repo.getCategories()
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
                validateCache = state.libraryList.map { (it as LibraryViewData.Category).title }
            } catch (e: IllegalArgumentException) {
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
            ValidationState.SuccessState -> LibraryListEvent.ValidationResult.Success
        }
        mEvent.postValue(event)
    }

    private fun addCategory(categoryTitle: String) {
        val category = LibraryDto.Category(title = categoryTitle)
        repo.addCategory(category)
            .observeUi()
            .doAfterSuccess { getCategories() }
            .subscribe()
    }

    private fun goToSubcategoryScreen(categoryId: Int, isFromWorkoutScreen: Boolean) {
        router.navigate(
            screen = Screen.SUBCATEGORY_LIST_SCREEN,
            data = LibraryParams.SubcategoryList(categoryId, isFromWorkoutScreen)
        )
    }

    private fun showToast(text: String) {
        router.show(Notification.TOAST, ToastBarParams(text))
    }

    override fun triggerAction(action: CategoryAction) {
        when (action) {
            is CategoryAction.AddCategory -> addCategory(action.title)
            is CategoryAction.ClickCategory -> goToSubcategoryScreen(action.categoryId, action.isFromWorkoutScreen)
            CategoryAction.GetCategories -> getCategories()
            is CategoryAction.ShowToast -> showToast(action.text)
            is CategoryAction.Validate -> validateSubject.onNext(action.text)
        }
    }

    override fun intentToAction(intent: CategoryIntent): CategoryAction {
        return when (intent) {
            is CategoryIntent.AddCategory -> CategoryAction.AddCategory(intent.title)
            is CategoryIntent.ClickItem -> CategoryAction.ClickCategory(
                intent.item.id, intent.isFromWorkoutScreen
            )
            CategoryIntent.GetCategories -> CategoryAction.GetCategories
            is CategoryIntent.ShowToast -> CategoryAction.ShowToast(intent.text)
            is CategoryIntent.Validate -> CategoryAction.Validate(intent.text)
        }
    }
}