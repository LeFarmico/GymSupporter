package com.lefarmico.lerecycle

class Wrapper<T : IViewHolderFactory<ItemType>>(val types: Array<out T>) {

    fun getViewTypeNumber(type: T): Int {
        return types.indexOf(type)
    }

    fun getType(index: Int): T {
        return types[index]
    }
}
inline fun <reified T> extractValues(): Array<out T>
        where T : Enum<T>,
              T : IViewHolderFactory<ItemType> {

    return if (T::class.java.enumConstants != null) {
        T::class.java.enumConstants!!
    } else {
        throw (NullPointerException("Class have not type parameters"))
    }
}
inline fun <reified T> extractValue(): Array<out T>
        where T : IViewHolderFactory<ItemType> {

    return arrayOf(T::class.java.newInstance())
}
