package com.cryptoemergency.cryptoemergency.lib

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

/**
 * @param label User-visible label for the clip data.
 * @param text The actual text in the clip.
 **/
fun copyTextToClipboard(
    context: Context,
    text: String,
    label: String,
) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
}
