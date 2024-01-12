package com.example.notes_hoodlab.detail_screen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes_hoodlab.Utils
import com.example.notes_hoodlab.ui.theme.Notes_HoodLabTheme
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    noteId: String,
    onNavigate: () -> Unit,
) {
    var detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isFormsNotBlank = detailUiState.title.isNotBlank() && detailUiState.description.isNotBlank()

    val selectedColor by animateColorAsState(
        label = "",
        targetValue = Utils.colors[detailUiState.colorIndex],
    )

    val isNoteIdIsNotBlank = noteId.isNotBlank()
    val icon = if (isNoteIdIsNotBlank) {
        Icons.Rounded.Edit
    } else {
        Icons.Rounded.Check
    }

    LaunchedEffect(key1 = Unit) {
        if (isNoteIdIsNotBlank) {
            detailViewModel?.getNote(noteId, onError = {}, onSuccess = {
                detailUiState = detailUiState.copy(
                    selectedNote = it
                )
            })
        } else {
            detailViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            AnimatedVisibility(visible = isFormsNotBlank) {
                FloatingActionButton(onClick = {
                    if (isNoteIdIsNotBlank) {
                        detailViewModel?.updateNote(noteId)
                    } else {
                        detailViewModel?.addNote()
                    }
                }
                ) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(color = selectedColor)
        ) {
            if (detailUiState.noteAdded) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Note added",
                    )
                    detailViewModel?.resetNoteAdded()
                    onNavigate.invoke()
                }
            }
            if (detailUiState.noteUpdated) {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "Note updated",
                    )
                    detailViewModel?.resetNoteAdded()
                    onNavigate.invoke()
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                contentPadding = PaddingValues(
                    vertical = 16.dp,
                    horizontal = 8.dp
                )
            ) {
                itemsIndexed(Utils.colors) { index, color ->
                    ColorItem(
                        color = color,
                        onClick = {
                            detailViewModel?.onColorChange(index)
                        }
                    )
                }

            }
            OutlinedTextField(value = detailUiState.title, onValueChange = {
                detailViewModel?.onTitleChange(it)
            }, label = {
                Text(text = "Title")
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            OutlinedTextField(value = detailUiState.description, onValueChange = {
                detailViewModel?.onDescriptionChange(it)
            }, label = {
                Text(text = "Description")
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp)
            )

        }
    }

}


@Composable
fun ColorItem(
    color: Color,
    onClick: () -> Unit,
) {
    Surface(
        color = color,
        shape = CircleShape,
        modifier = Modifier
            .padding(8.dp)
            .size(36.dp)
            .clickable {
                onClick.invoke()
            },
        border = BorderStroke(2.dp, Color.Black)
    ) {

    }

}


@Preview(showSystemUi = true)
@Composable
fun PrevDetailScreen() {
    Notes_HoodLabTheme {
        DetailScreen(detailViewModel = null, noteId = "") {

        }
    }

}





