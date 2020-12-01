package com.simongame

import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class LiveData : ViewModel() {
    val gameSec = MutableLiveData<MutableList<Int>>()
    val userSec = MutableLiveData<MutableList<Int>>()
    val gameState = MutableLiveData<Boolean>()

    init {
        userSec.value = mutableListOf<Int>()
        gameSec.value = mutableListOf<Int>()
        gameState.value = true
    }

    fun init_game() {
        gameState.value = false
        reset()
        startSec()
    }

    private fun startSec() {
        val num = Random.nextInt(4) + 1
        gameSec.value?.add(num)
        gameSec.postValue(gameSec.value)
    }

    fun checkSec(): Boolean {
        var ret = false
        if (gameSec.value == userSec.value && gameState.value == false) {
            startSec()
            userSec.value?.clear()
            ret = true
        } else {
            gameState.value = true
        }
        return ret
    }

    private fun reset() {
        gameSec.value?.clear()
        userSec.value?.clear()
    }

    fun addUserSec(color: Int) {
        when (color) {
            1 -> userSec.value?.add(1)
            2 -> userSec.value?.add(2)
            3 -> userSec.value?.add(3)
            else -> userSec.value?.add(4)
        }
    }

    fun getSec(): MutableList<Int> {
        return gameSec.value!!
    }

    fun showSec(listButton: List<Button>) {
        CoroutineScope(Dispatchers.Main).launch {
            for (colors in gameSec.value!!) {
                delay(1000)
                listButton.get(colors - 1).isPressed = true
                delay(1000)
                listButton.get(colors - 1).isPressed = false
            }
        }
    }
}