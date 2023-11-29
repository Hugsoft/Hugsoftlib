package hugsoft.com.preferences

import androidx.compose.runtime.*
import androidx.datastore.preferences.core.*
import hugsoft.com.preferences.Key.Key1
import hugsoft.com.preferences.Key.Key2
import androidx.datastore.preferences.core.Preferences as StorePreference
internal typealias StoreKey<T> = StorePreference.Key<T>

interface Saver<S, O> {
    fun save(value: O): S
    fun restore(value: S): O
}

sealed interface Key<S, O> {
    val name: String
    class Key1<S, O> internal constructor(
        internal val value: StoreKey<S>,
        internal val saver: Saver<S, O>?
    ) : Key<S, O> {
        override val name: String
            get() = value.name
    }
    class Key2<S, O> internal constructor(
        internal val value: StoreKey<S>,
        internal val default: O,
        internal val saver: Saver<S, O>?
    ) : Key<S, O> {
        override val name: String
            get() = value.name
    }
}

typealias IntSaver<O> = Saver<Int, O>


fun intPreferenceKey(name: String) =
    Key1<Int, Int>(intPreferencesKey(name), null)


fun intPreferenceKey(name: String, defaultValue: Int) =
    Key2(intPreferencesKey(name), defaultValue, null)


fun <O> intPreferenceKey(name: String, saver: IntSaver<O>) =
    Key1(intPreferencesKey(name), saver)


fun <O> intPreferenceKey(name: String, defaultValue: O, saver: IntSaver<O>) =
    Key2(intPreferencesKey(name), defaultValue, saver)



typealias FloatSaver<O> = Saver<Float, O>


fun floatPreferenceKey(name: String) =
    Key1<Float, Float>(floatPreferencesKey(name), null)


fun floatPreferenceKey(name: String, defaultValue: Float) =
    Key2(floatPreferencesKey(name), defaultValue, null)


fun <O> floatPreferenceKey(name: String, saver: FloatSaver<O>) =
    Key1(floatPreferencesKey(name), saver)


fun <O> floatPreferenceKey(name: String, defaultValue: O, saver: FloatSaver<O>) =
    Key2(floatPreferencesKey(name), defaultValue, saver)


typealias DoubleSaver<O> = Saver<Double, O>

fun doublePreferenceKey(name: String) =
    Key1<Double, Double>(doublePreferencesKey(name), null)

fun doublePreferenceKey(name: String, defaultValue: Double) =
    Key2(doublePreferencesKey(name), defaultValue, null)

fun <O> doublePreferenceKey(name: String, saver: DoubleSaver<O>) =
    Key1(doublePreferencesKey(name), saver)

fun <O> doublePreferenceKey(name: String, defaultValue: O, saver: DoubleSaver<O>) =
    Key2(doublePreferencesKey(name), defaultValue, saver)

typealias LongSaver<O> = Saver<Long, O>

fun longPreferenceKey(name: String) =
    Key1<Long, Long>(longPreferencesKey(name), null)


fun longPreferenceKey(name: String, defaultValue: Long) =
    Key2(longPreferencesKey(name), defaultValue, null)

fun <O> longPreferenceKey(name: String, saver: LongSaver<O>) =
    Key1(longPreferencesKey(name), saver)


fun <O> longPreferenceKey(name: String, defaultValue: O, saver: LongSaver<O>) =
    Key2(longPreferencesKey(name), defaultValue, saver)

fun booleanPreferenceKey(name: String) =
    Key1<Boolean, Boolean>(booleanPreferencesKey(name), null)

fun booleanPreferenceKey(name: String, defaultValue: Boolean) =
    Key2(booleanPreferencesKey(name), defaultValue, null)

typealias StringSaver<O> = Saver<String, O>


fun stringPreferenceKey(name: String) =
    Key1<String, String>(stringPreferencesKey(name), null)

fun stringPreferenceKey(name: String, defaultValue: String) =
    Key2(stringPreferencesKey(name), defaultValue, null)

fun <O> stringPreferenceKey(name: String, saver: StringSaver<O>) =
    Key1(stringPreferencesKey(name), saver)

fun <O> stringPreferenceKey(name: String, defaultValue: O, saver: StringSaver<O>) =
    Key2(stringPreferencesKey(name), defaultValue, saver)

fun stringSetPreferenceKey(name: String) =
    Key1<Set<String>, Set<String>>(stringSetPreferencesKey(name), null)

fun stringSetPreferenceKey(name: String, defaultValue: Set<String>) =
    Key2(stringSetPreferencesKey(name), defaultValue, null)

