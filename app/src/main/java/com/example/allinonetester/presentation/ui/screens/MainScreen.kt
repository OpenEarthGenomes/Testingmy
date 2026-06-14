package com.example.allinonetester.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.allinonetester.presentation.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onSelectSdCard: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    
    var internalPathInput by remember { mutableStateOf("/storage/emulated/0/Download/teszt.txt") }
    var targetCopyPathInput by remember { mutableStateOf("/storage/emulated/0/Download/teszt_masolat.txt") }
    var sdCardRelativePath by remember { mutableStateOf("teszt_mappa/mentes.txt") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("All-In-One Immersive Tester") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Rendszerállapot:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = uiState, style = MaterialTheme.typography.bodyLarge)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Memória Kezelés", style = MaterialTheme.typography.titleMedium)
            Button(
                onClick = { viewModel.runRamCleanup() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Háttérfolyamatok lelövése (RAM Ürítés)")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(text = "Belső Tárhely Műveletek", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = internalPathInput,
                onValueChange = { internalPathInput = it },
                label = { Text("Fájl vagy mappa útvonala") },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = targetCopyPathInput,
                onValueChange = { targetCopyPathInput = it },
                label = { Text("Másolás célútvonala") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.checkFolderSize(internalPathInput) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Méret mérése")
                }
                Button(
                    onClick = { viewModel.deleteFile(internalPathInput, isExternal = false) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Törlés")
                }
            }

            Button(
                onClick = { viewModel.copyFile(internalPathInput, targetCopyPathInput, toExternal = false) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Másolás belső tárhelyen")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(text = "Külső SD Kártya Kezelés (SAF)", style = MaterialTheme.typography.titleMedium)
            
            if (viewModel.sdCardUri.value == null) {
                Button(
                    onClick = onSelectSdCard,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("SD Kártya Gyökér mappa engedélyezése")
                }
            } else {
                Text(
                    text = "SD Kártya hozzáférés aktív ✅",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            OutlinedTextField(
                value = sdCardRelativePath,
                onValueChange = { sdCardRelativePath = it },
                label = { Text("Relatív útvonal az SD kártyán (pl. mappa/fájl.txt)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.copyFile(internalPathInput, sdCardRelativePath, toExternal = true) },
                    enabled = viewModel.sdCardUri.value != null,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Másolás SD-re")
                }
                Button(
                    onClick = { viewModel.deleteFile(sdCardRelativePath, isExternal = true) },
                    enabled = viewModel.sdCardUri.value != null,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Törlés SD-ről")
                }
            }
        }
    }
}
