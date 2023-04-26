import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import javax.swing.JFileChooser

@Composable
@Preview
fun Purifiler() {
    val chooseFolderText by remember { mutableStateOf("Choose folder") }
    var folderPath by remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val fileChooser = JFileChooser()
                        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
                        fileChooser.dialogTitle = chooseFolderText
                        val result = fileChooser.showOpenDialog(null)
                        if (result == JFileChooser.APPROVE_OPTION) {
                            val file = fileChooser.selectedFile
                            folderPath = file.absolutePath
                            val fileList = file.listFiles()
                            fileList?.forEach { originalFile ->
                                val originalFileName = originalFile.name
                                val newFileName = originalFileName.toLowerCase().replace("-", "_")
                                val newFile = File(originalFile.parent, newFileName)
                                originalFile.renameTo(newFile)
                            }
                        }
                    }
                    .padding(bottom = 8.dp)
            ) {
                if (folderPath.isNotEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Folder selected",
                            tint = Color.Green,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = "Folder selected: $folderPath",
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Choose folder",
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = chooseFolderText,
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        Purifiler()
    }
}
