package com.viswakarma.jewelleryworks.resourceprovider

import android.content.Context
import android.graphics.drawable.Drawable
import javax.inject.Singleton


/**
 * @author Ryan on 9/21/17.
 */
@Singleton
interface ResourceProvider{
    fun getStringWithArgs(resourceId: Int, args: Any): String
    fun getString(resourceId: Int): String
    fun getColor(resourceId: Int): Int
    fun getStringArray(resourceId: Int): Array<String>
    fun isResource(context: Context?,resourceId: Int):Boolean
    fun getDrawable(resourceId: Int): Drawable?
}