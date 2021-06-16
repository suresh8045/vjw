package com.viswakarma.jewelleryworks.model.resourceprovider

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


/**
 * @author Ryan on 9/21/2017.
 */
class ResourceProviderImpl(private val context: Context): ResourceProvider {


    override fun getString(resourceId: Int): String {
		return context.resources.getString(resourceId)
	}
    override fun getStringWithArgs(resourceId: Int, args: Any): String {
        return context.resources.getString(resourceId, args)
    }

    override fun getColor(resourceId: Int): Int {
        return ContextCompat.getColor(context, resourceId)
    }

    override fun getStringArray(resourceId: Int): Array<String> {
        return context.resources.getStringArray(resourceId)
    }

    override fun getDrawable(context: Context,resourceId: Int): Drawable? {
        return ContextCompat.getDrawable(context, resourceId)
    }

    override fun isResource(context: Context?, resourceId: Int): Boolean {
        if (context != null) {
            try {
                return context.resources.getResourceName(resourceId) != null
            } catch (ignore: Resources.NotFoundException) {
            }
        }
        return false
    }

}