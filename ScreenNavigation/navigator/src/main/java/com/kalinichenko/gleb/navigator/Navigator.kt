package com.kalinichenko.gleb.navigator

import io.reactivex.subjects.PublishSubject
import java.util.*

class Navigator (private var stack: Stack<NavigatorEntity> = Stack(),
                 var publisher: PublishSubject<NavigatorEntity>? = PublishSubject.create<NavigatorEntity>()) {

    fun push(className: String, params: Any, isMakeRootElement: Boolean, isSendToPublish: Boolean = true, isAddInStack: Boolean = true) {
        var item = NavigatorEntity(className, params)

        if (isMakeRootElement) {
            stack.clear()
        }

        if (isAddInStack)
            stack.push(item)

        if (isSendToPublish)
            publisher!!.onNext(item)

    }

    fun pop(isSendToPublish: Boolean = true) {
        stack.pop()

        if (isSendToPublish) {
            var item = stack.peek()
            item.isForwardAnimation = false
            publisher!!.onNext(item)
        }
    }

    fun isEmptyStack() = stack.isEmpty()

    fun getTop() = stack.peek().className

    fun clear() = stack.clear()
}