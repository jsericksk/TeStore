package com.kproject.testore.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Utils {

    fun showSnackbar(
        view: View,
        anchorView: View? = null,
        text: String,
        actionText: String = "",
        duration: Int = Snackbar.LENGTH_SHORT,
        onAction: () -> Unit = {}
    ) {
        val snackbar = Snackbar.make(view, text, duration).setAction(actionText) { onAction.invoke() }
        anchorView?.let {
            snackbar.setAnchorView(it)
        }
        snackbar.show()
    }
}