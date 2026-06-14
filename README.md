# All-In-One Tester APK 🛠️

Egy professzionális Android diagnosztikai alkalmazás, modern alapokra építve, kifejezetten **Samsung Galaxy A35 (One UI 8.5, Android 16)** optimalizációval.

Mivel a fejlesztés mobiltelefonon történik, a repository nem tartalmaz bináris Gradle fájlokat. A buildelés és az APK generálás automatizáltan, felhőben fut a GitHub Actions segítségével.

## 🛠️ Funkciók (21+ teszt)
Egyetlen felületen elindítható és naplózható tesztek az alábbi kategóriákban:

* 🌐 **Internet elérés ellenőrzés** & 🔍 **DNS lookup**
* 🌍 **Publikus IP cím** & ⏱️ **Weboldal válaszidő**
* ⚡ **Sebességteszt** & 📡 **Ping**
* 🔓 **Port scanner**
* 📁 **Mappa méretezés** (Teljes rekurzív méretvizsgálat, Android 11+ `MANAGE_EXTERNAL_STORAGE` támogatással)
* 🔋 **Akku állapot** & 📶 **Hálózati állapot**
* 💾 **RAM használat** & 💿 **Tárhely információ**
* 📱 **Eszköz info** (Kijelző felbontás, CPU magok, Szenzorok)
* ☀️ **Képernyő fényerő**
* 📦 **Telepített alkalmazások** listázása
* 📱 **Mobil hálózat típusa**
* 🔒 **Biztonsági patch** & ⚠️ **Root ellenőrzés**

## 📁 Projektstruktúra (Clean Architecture)

A kód átláthatósága és karbantarthatósága érdekében az alábbi rétegekre bontva:
* `config/` - Konfigurációk
* `domain/usercases/` - Üzleti logika (21 dedikált use case)
* `presentation/ui/` - UI komponensek (Jetpack Compose)
* `utils/` - Segédfüggvények
* `data/` - Adatmodellek

## 🔧 Technológiai háttér

* **Nyelv:** Kotlin 2.0.0 (Legújabb Compose Compilerrel)
* **Build Rendszer:** Android Gradle Plugin 8.5.0, Version Catalogs (`libs.versions.toml`)
* **UI:** Jetpack Compose
* **Aszinkronitás:** Coroutines
* **Hálózat:** OkHttp
* **Dependency Injection:** Dagger Hilt

## 📱 Kompatibilitás

* **Minimum:** Android 8.0 (API 26)
* **Target:** Android 16 (API 36)
* **Optimalizálva:** Samsung Galaxy A35 (One UI 8.5)
