package com.viswakarma.jewelleryworks.model.resourceprovider

import android.content.Context
import android.graphics.drawable.Drawable


/**
 * @author Ryan on 9/21/17.
 */
interface ResourceProvider{
    fun getStringWithArgs(resourceId: Int, args: Any): String
    fun getString(resourceId: Int): String
    fun getColor(resourceId: Int): Int
    fun getStringArray(resourceId: Int): Array<String>
    fun isResource(context: Context?,resourceId: Int):Boolean
    fun getDrawable(context: Context,resourceId: Int): Drawable?
}