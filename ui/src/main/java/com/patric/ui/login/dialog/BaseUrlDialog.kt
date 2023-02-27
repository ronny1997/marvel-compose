package com.patric.ui.login.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.patric.ui.R
import com.patric.ui.theme.AppTheme

@Composable
fun BaseUrlDialog(
    baseUrl: String, dismissDialog: () -> Unit, persistBaseUrl: (String) -> Unit
) {
    var url by remember { mutableStateOf(baseUrl) }
    Dialog(onDismissRequest = { dismissDialog() }) {
        Text(
            color = Color.Black,
            style = MaterialTheme.typography.h5,
            text = stringResource(id = R.string.base_url_dialog_title),
        )
    }
}

@Preview
@Composable
fun BaseUrlDialogPreview() {
    AppTheme {
        BaseUrlDialog("", {}, {})
    }
}