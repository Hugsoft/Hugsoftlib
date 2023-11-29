@file:OptIn(DelicateCoroutinesApi::class)

package hugsoft.com.preferences

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.compose.runtime.*
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import hugsoft.com.preferences.Key.Key1
import hugsoft.com.preferences.Key.Key2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.datastore.preferences.core.Preferences as StorePreference

import androidx.datastore.preferences.protobuf.ExperimentalApi
import hugsoft.com.preferences.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


interface Preferences {

    operator fun <S, O> get(key: Key1<S, O>): Flow<O?>

    operator fun <S, O> get(key: Key2<S, O>): Flow<O>

    operator fun <S, O> set(key: Key<S, O>, value: O)

    operator fun minusAssign(key: Key<*, *>)

    @WorkerThread
    operator fun contains(key: Key<*, *>): Boolean


    fun clear(x: MutablePreferences)

    fun remove(key: Key<*, *>)

    companion object {


        private const val DEFAULT_NAME = "preferences.file"

        @Volatile
        private var INSTANCE: Preferences? = null

        @Deprecated("Create instance and use with Hilt")
        fun get(context: Context): Preferences {


            return INSTANCE ?: synchronized(this) {
                val instance =
                    PreferencesImpl(context.applicationContext as Application, DEFAULT_NAME, GlobalScope)
                INSTANCE = instance
                instance
            }
        }

        operator fun invoke(context: Context, name: String = DEFAULT_NAME, scope: CoroutineScope = GlobalScope): Preferences =
            PreferencesImpl(context.applicationContext, name, scope)
    }
}

private inline  val <S, O> Key<S, O>.storeKey get() = when(this){
    is Key1 -> value
    is Key2 -> value
}

private inline val <S, O> Key<S, O>.saver: Saver<S, O>? get() = when(this){
    is Key1 -> saver
    is Key2 -> saver
}

private class PreferencesImpl(context: Context, name: String, val scope: CoroutineScope) : Preferences {
    private val Context.store by preferencesDataStore(name)

    private val store = context.store
    private val flow: Flow<StorePreference> = store.data.catch { exception ->
        when (exception) {
            is IOException -> {
                emit(emptyPreferences())
            }
            else -> throw exception
        }
    }

    override fun <S, O> get(key: Key1<S, O>): Flow<O?> {
        return flow.map { preferences ->
            if (key.saver == null)
                preferences[key.value] as O
            else
                preferences[key.value]?.let { key.saver.restore(it) }
        }
    }

    override fun <S, O> get(key: Key2<S, O>): Flow<O> {
        return flow.map { preferences ->
            if (key.saver == null)
                (preferences[key.value] ?: key.default) as O
            else
                preferences[key.value]?.let { key.saver.restore(it) } ?: key.default
        }
    }
    override fun <S, O> set(key: Key<S, O>, value: O) {
        scope.launch {
            store.edit {
                val saver = key.saver
                it[key.storeKey] = if (saver == null) (value as S) else saver.save(value)
            }
        }
    }

    override fun minusAssign(key: Key<*, *>) {
        scope.launch {
            store.edit {
                it -= key.storeKey
            }
        }
    }

    override fun contains(key: Key<*, *>): Boolean {
        return runBlocking {
            flow.map { preference -> key.storeKey in preference }.first()
        }
    }

    override fun clear(x: MutablePreferences) {
        scope.launch {
            store.edit {
                it.clear()
            }
        }
    }

    override fun remove(key: Key<*, *>) {
        scope.launch {
            store.edit {
                it.remove(key.storeKey)
            }
        }
    }
}

@Composable
private inline fun <S, O> Preferences.observe(key: Key<S, O>): State<O?> {
    val flow = when(key){
        is Key1 -> this[key]
        is Key2 -> this[key]
    }

    val first = remember(key.name) {
        runBlocking { flow.first() }
    }
    return flow.collectAsState(initial = first)
}


@Composable
@NonRestartableComposable
fun <S, O> Preferences.observeAsState(key: Key1<S, O>): State<O?> = observe(key = key)


@Composable
@NonRestartableComposable
fun <S, O> Preferences.observeAsState(key: Key2<S, O>): State<O> = observe(key = key) as State<O>


fun <S, O> Preferences.value(key: Key1<S, O>): O? = runBlocking { this@value[key].first() }

@WorkerThread
fun <S, O> Preferences.value(key: Key2<S, O>): O = runBlocking { this@value[key].first() }

@ExperimentalApi
operator fun <S, O> Preferences.invoke(key: Key1<S, O>) = value(key)

@ExperimentalApi
operator fun <S, O> Preferences.invoke(key: Key2<S, O>) = value(key)
