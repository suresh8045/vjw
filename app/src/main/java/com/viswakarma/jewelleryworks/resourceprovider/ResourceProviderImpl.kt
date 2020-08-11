package com.viswakarma.jewelleryworks.resourceprovider

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton


/**
 * @author Ryan on 9/21/2017.
 */
@Singleton
class ResourceProviderImpl @Inject constructor (private val context: Context): ResourceProvider {


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

    override fun getDrawable(resourceId: Int): Drawable? {
        return context.getDrawable(resourceId)
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