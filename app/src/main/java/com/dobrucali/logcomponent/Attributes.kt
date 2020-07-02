package com.dobrucali.logcomponent

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

@DslMarker
annotation class AttributeSetDsl

@AttributeSetDsl
class AttributeSetBuilder(
    val elements: TypedArray
)

inline fun getAttributes(context: Context, attrs: AttributeSet?, resIds: IntArray, fn: AttributeSetBuilder.() -> Unit) {
    val elements = context
        .theme
        .obtainStyledAttributes(attrs, resIds, 0, 0)

    try {
        AttributeSetBuilder(elements)
            .apply(fn)
    } finally {
        elements.recycle()
    }
}